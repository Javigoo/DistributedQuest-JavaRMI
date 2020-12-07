package professor;

import common.StudentInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Professor {
    private static Registry startRegistry(Integer port)
            throws RemoteException {
        if(port == null) {
            port = 1099;
        }
        try {
            Registry registry = LocateRegistry.getRegistry(port);
            registry.list( );
            // The above call will throw an exception
            // if the registry does not already exist
            return registry;
        }
        catch (RemoteException ex) {
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located ");
            Registry registry= LocateRegistry.createRegistry(port);
            System.out.println("RMI registry created at port ");
            return registry;
        }
    }

    public static void main(String args[]) {
        final Integer STUDENTS_NUMBER = 2;
        try {
            Registry registry = startRegistry(null);
            ProfessorImplementation obj = new ProfessorImplementation();
            registry.bind("Exam", (ProfessorImplementation) obj);
            try {
                while(true) {
                    synchronized (obj) {
                        obj.uploadExam("./src/Exam.csv");   // Hacerlo compatible
                        System.out.println("The professor has uploaded the exam");

                        Random r = new Random();
                        int myNumber = r.nextInt(10);
                        //System.out.println("number "+myNumber);

                        while (obj.getNumStudents() < STUDENTS_NUMBER) {
                            System.out.println("Number of students joined: [" + obj.getNumStudents() + "/" + STUDENTS_NUMBER +"]");
                            obj.wait();
                        }
                        System.out.println("Number of students joined: [" + obj.getNumStudents() + "/" + STUDENTS_NUMBER +"]");

                        System.out.println("The professor has started the exam");
                        obj.notifyStart();
                        while (obj.answers < obj.getNumStudents()) {
                            System.out.println("recieved number");
                            obj.wait();
                        }
                        System.out.println("Finishing game");

                        List<StudentInterface> winners = obj.getWinners(myNumber);
                        List<StudentInterface> loosers = obj.getLoosers(myNumber);
                        for (StudentInterface c : winners) {
                            c.notifyWinner();
                        }
                        for (StudentInterface c : loosers) {
                            c.notifyLooser();
                        }
                        obj.restart();
                    }

                }
            } catch (InterruptedException e) {
                System.err.println("Server exception: " + e.toString()); e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString()); e.printStackTrace();
        }
    }

}
