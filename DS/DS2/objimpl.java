import java.rmi.*; 
import java.rmi.server.*; 
 
public class objimpl extends UnicastRemoteObject  
                           implements addintf { 
 
    public objimpl() throws RemoteException { 
        super(); 
    } 
 
    // This method will be executed remotely 
    public double add(double num1, double num2)  
            throws RemoteException { 
 
        System.out.println("Processing request in thread: "  
                           + Thread.currentThread().getName()); 
                                 return num1 + num2; 
    } 
}