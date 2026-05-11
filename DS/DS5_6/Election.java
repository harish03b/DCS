import java.util.*;
public class Election {
    static int n;
    static int[] process;
    static boolean[] active;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        n
 = sc.nextInt();
        process = new int[n];
        active = new boolean[n];
        // Assign process IDs
        for (int i = 0; i < n; i++) {
            process[i] = i + 1; // IDs: 1,2,3...
            active[i] = true;
        }
        System.out.println("\nProcesses are:");
        for (int i = 0; i < n; i++) {
            System.out.print(process[i] + " ");
        }
        // Fail a process
        System.out.print("\n\nEnter process to fail: ");
        int fail = sc.nextInt();
        active[fail - 1] = false;
        System.out.println("Process " + fail + " failed.");
        // Choose algorithm
        System.out.println("\n1. Bully Algorithm");
        System.out.println("2. Ring Algorithm");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                bully(sc);
                break;
            case 2:
                ring(sc);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
    static void bully(Scanner sc) {
        System.out.print("\nEnter process initiating election: ");
        int initiator = sc.nextInt();
        System.out.println("\nElection messages:");
        for (int i = initiator; i < n; i++) {
            if (active[i]) {
                System.out.println("Process " + initiator + " -> Process " + process[i]);
            }
        }
        int leader = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (active[i]) {
                leader = process[i];
                break;
            }
        }
        System.out.println("\nLeader elected (Bully): Process " + leader);
    }
    static void ring(Scanner sc) {
        System.out.print("\nEnter process initiating election: ");
        int initiator = sc.nextInt() - 1;
        List<Integer> ring = new ArrayList<>();
        int i = initiator;
        System.out.println("\nElection Ring:");
        do {
            if (active[i]) {
                System.out.print(process[i] + " -> ");
                ring.add(process[i]);
            }
            i = (i + 1) % n;
        } while (i != initiator);
        // Find highest ID
        int leader = Collections.max(ring);
        System.out.println("\n\nLeader elected (Ring): Process " + leader);
    }
}