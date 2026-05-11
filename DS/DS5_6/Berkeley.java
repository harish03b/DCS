import java.util.*;
public class Berkeley {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();
        int[] time = new int[n];
        // Input clock times
        for (int i = 0; i < n; i++) {
            System.out.print("Enter time for node " + i + ": ");
            time[i] = sc.nextInt();
        }
        int master = 0; // Node 0 is master
        System.out.println("\nMaster node is: " + master);
        // Calculate differences
        int sum = 0;
        int[] diff = new int[n];
        for (int i = 0; i < n; i++) {
            diff[i] = time[i] - time[master];
            sum += diff[i];
        }
        // Calculate average difference
        int avg = sum / n;
        System.out.println("\nAverage adjustment: " + avg);
        // Adjust clocks
        System.out.println("\nAdjusted Times:");
        for (int i = 0; i < n; i++) {
            time[i] = time[i] + (avg - diff[i]);
            System.out.println("Node " + i + " new time: " + time[i]);
        }
    }
}
