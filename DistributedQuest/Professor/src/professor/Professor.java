package professor;

import common.StudentInterface;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
        final Integer STUDENTS_NUMBER = 1;
        try {
            Registry registry = startRegistry(null);
            ProfessorImplementation obj = new ProfessorImplementation();
            registry.bind("Exam", (ProfessorImplementation) obj);
            try {
                synchronized (obj) {

                    // 1. The professor will upload a csv file to the application with the exam’s questions, choices and answers, following this format:
                    //      Question?;choice1;choice2;choice3;...;correct_answer_number.
                    obj.uploadCSV(new File("./src/Exam.csv"));

                    //2. The professor will start the exam session and wait for the students to join the room:
                    //      a. The professor needs to know how many students are in the room.
                    obj.waitStudents(STUDENTS_NUMBER);

                    //4. The Professor will indicate when to begin the exam in the application.
                    //      a. It is not possible for students to connect after the professor begins the exam. A message will be received indicating this.
                    obj.startExam();

                    //5. The server will start sending the questions and choices to the students in order(The correct answer will never be sent).
                    for (StudentInterface c : obj.students.values()) {
                        System.out.printf(String.valueOf(obj.exam.getNextQuestion())+"\n");
                        System.out.printf(String.valueOf(obj.exam.getNextQuestion())+"\n");
                    }

                    /**
                    // Lista las id's de los alumnos que se han unido
                    for (String student : obj.students.keySet()) {
                        System.out.println("Student id: " + student);
                    }

                    int i = 0;
                    while (i<10) {
                        obj.wait();
                        System.out.println("Student:" + obj.students.keySet() + " - choice number answer: " + obj.clientNumber.values());
                        i += 1;

                        for (StudentInterface student : obj.clientNumber.keySet()) {
                            student.notify();
                        }

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
                     **/

                    System.exit(0);
                }

            } catch (InterruptedException e) {
                System.err.println("Server exception: " + e.toString()); e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString()); e.printStackTrace();
        }
    }

}
