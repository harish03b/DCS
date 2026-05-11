import CalcApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
class CalculatorImpl extends CalculatorPOA {
    public int add(int a, int b) {
        return a + b;
    }
    public int subtract(int a, int b) {
        return a - b;
    }
    public int multiply(int a, int b) {
        return a * b;
    }
    public float divide(float a, float b) {
        if (b == 0) return 0;
        return a / b;
    }
}
public class CalculatorServer {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = 
POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            CalculatorImpl calcImpl = new CalculatorImpl();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(calcImpl);
            Calculator href = CalculatorHelper.narrow(ref);
            org.omg.CORBA.Object objRef = 
orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NameComponent path[] = ncRef.to_name("Calculator");
            ncRef.rebind(path, href);
            System.out.println("Calculator Server ready...");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}