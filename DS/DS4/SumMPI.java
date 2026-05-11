import mpi.*;
public class SumMPI {
    public static void main(String args[]) throws Exception {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int N = 8;
        int[] array = {1,2,3,4,5,6,7,8};
        int elementsPerProcess = N / size;
        int remainder = N % size;
        int start = rank * elementsPerProcess + Math.min(rank, remainder);
        int end = start + elementsPerProcess;
        if (rank < remainder) {
            end += 1;
        }
        int localSum = 0;
        // Calculate sum first
        for (int i = start; i < end; i++) {
            localSum += array[i];
        }
        for (int i = 0; i < size; i++) {
            if (rank == i) {
                System.out.println("\n--- Processor " + rank + " ---");
System.out.print("Elements: ");
for (int j = start; j < end; j++) {
    System.out.print(array[j] + " ");
}
System.out.println();
System.out.println("Partial Sum: " + localSum);
System.out.println();
                // 2 seconds delay
                Thread.sleep(2000);
            }
            // Synchronize all processes
            MPI.COMM_WORLD.Barrier();
        }
        int[] globalSum = new int[1];
        MPI.COMM_WORLD.Reduce(
            new int[]{localSum}, 0,
            globalSum, 0,
            1,
            MPI.INT,
            MPI.SUM,
            0
        );
        if (rank == 0) {
            System.out.println("\n===== FINAL RESULT =====");
            System.out.println("Total Sum = " + globalSum[0]);
        }
        MPI.Finalize();
    }
}