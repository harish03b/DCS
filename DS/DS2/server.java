import java.rmi.*; 
import java.rmi.registry.*; 
 
public class server { 
 
    public static void main(String args[]) { 
 
        try { 
            objimpl addObj = new objimpl(); 
 
            // Register object with RMI registry 
            Naming.rebind("AddServer", addObj); 
 
            System.out.println("Server Started..."); 
        } 
        catch (Exception e) { 
            System.out.println("Server Error: " + e); 
        } 
    } 
} 