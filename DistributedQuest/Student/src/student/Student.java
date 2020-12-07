package student;

import common.ProfessorInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 */
public class Student {
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            StudentImplementation client = new StudentImplementation();
            ProfessorInterface stub = (ProfessorInterface) registry.lookup("Exam");

            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter the student id: ");
            String student_id = keyboard.nextLine();

            stub.registerStudent(student_id,client);
            System.out.println("Student joined, waiting for notification");
            synchronized(client){
                client.wait();
                client.getSecretNumber();
                stub.sendAnswerNumber(client, client.secretNumber);
                client.wait();
                System.exit(0);
            }

        } catch (RemoteException e) {
            System.err.println("remote exception: " + e.toString()); e.printStackTrace();
        } catch (Exception e){
            System.err.println("student exception: " + e.toString()); e.printStackTrace();
        }
    }
}
