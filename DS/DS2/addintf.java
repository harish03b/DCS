import java.rmi.*; 
import java.rmi.RemoteException; 
 
public interface addintf extends Remote { 
      double add(double num1, double num2) throws RemoteException; 
} 