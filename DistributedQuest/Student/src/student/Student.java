package student;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Student {
    public static void main(String[] args) {
        String universityID = (args.length < 1) ? null : args[0];
        String host = (args.length < 2) ? null : args[1];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            StudentImplementation student = new StudentImplementation(universityID);
            Professor stub = (Professor) registry.lookup("Professor");
            stub.registerStudent(student);
            System.out.println("Student registered, waiting for notification");
            synchronized(student){
                // LOGICA DEL ALUMNO.
                System.exit(0);
            }

        } catch (RemoteException e) {
            System.err.println("remote exception: " + e.toString()); e.printStackTrace();
        } catch (Exception e){
            System.err.println("client exception: " + e.toString()); e.printStackTrace();
        }
    }
}
