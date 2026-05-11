import java.util.*;
public class TokenRing {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        boolean[] request = new boolean[n];
        // Input which processes want CS
        for (int i = 0; i < n; i++) {
            System.out.print("Does Process " + i + " want to enter CS? (1/0): ");
            request[i] = sc.nextInt() == 1;
        }
        int token = 0; // Start from process 0
        System.out.println("\n--- Token Ring Execution ---\n");
        // Run one full cycle
        for (int i = 0; i < n; i++) {
            System.out.println("Token at Process " + token);
            if (request[token]) {
                System.out.println("Process " + token + " ENTERING Critical Section...");
                // Simulate CS execution
                Thread.sleep(2000);
                System.out.println("Process " + token + " EXITING Critical Section\n");
                request[token] = false; // request served
            } else {
                System.out.println("Process " + token + " does not need CS\n");
            }
            // Pass token
            token = (token + 1) % n;
            // Small delay for visualization
            Thread.sleep(1000);
        }
        System.out.println("\n--- End of Execution ---");
    }
}