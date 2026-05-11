import java.rmi.*; 
public class client { 
    public static void main(String args[]) { 
        try { 
            // Lookup remote object 
            addintf addObj =  
                (addintf) Naming.lookup("rmi://localhost/AddServer"); 
 
            double num1 = Double.parseDouble(args[0]); 
            double num2 = Double.parseDouble(args[1]); 
 
            double result = addObj.add(num1, num2); 
 
            System.out.println("Addition Result = " + result); 
        } 
        catch (Exception e) { 
            System.out.println("Client Error: " + e); 
        } 
    }}