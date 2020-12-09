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

    static class ProfessorThread implements Runnable  {
        //9. When the professor decides to finish the exam, all currently connected students
        //   will receive their grade and the connection will end even if they have not finished the exam.
        //   All the grades will also be stored in a file on the professor’s computer.

        ProfessorImplementation exam;

        public ProfessorThread(ProfessorImplementation obj) {
            this.exam = obj;
        }

        @Override
        public void run() {
            System.out.println("Enter \"q\" to finish the exam");
            while(true) {
                try {
                    Scanner sc = new Scanner(System.in);
                    String input = sc.nextLine();

                    switch (input) {
                        case "q":
                            System.out.printf("Exam finished");
                            this.exam.finishExam(); // Students will receive their grade and all the grades will be stored in a file on the professor’s computer.
                            System.exit(0);
                            break;
                        case "":
                            break;
                        default:
                            System.out.printf("\""+input+"\" is not a valid input");
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {
        final Integer STUDENTS_NUMBER = 1;

        try {
            Registry registry = startRegistry(null);
            ProfessorImplementation obj = new ProfessorImplementation();
            registry.bind("Exam", (ProfessorImplementation) obj);

            ProfessorThread professorThread = new ProfessorThread(obj);
            Thread thread = new Thread(professorThread);
            thread.start();

            try {
                synchronized (obj) {

                    // 1. The professor will upload a csv file to the application with the exam’s questions, choices and answers, following this format:
                    //      Question?;choice1;choice2;choice3;...;correct_answer_number.
                    obj.uploadCSV(new File("Exam.csv"));

                    //2. The professor will start the exam session and wait for the students to join the room:
                    //      a. The professor needs to know how many students are in the room.
                    obj.waitStudents(STUDENTS_NUMBER);

                    //4. The Professor will indicate when to begin the exam in the application.
                    obj.startExam();

                    //5. The server will start sending the questions and choices to the students in order(The correct answer will never be sent).
                    for (int i = 0; i < obj.exam.questions.size(); i++){
                        Exam.Question question = obj.exam.getNextQuestion();
                        for (StudentInterface c : obj.students.values()) {
                            System.out.printf("Sent question "+ (i+1) + ": " + question + " to " + c + "\n");

                        }
                    }

                    //6. The students chose their answer and send it back to the server.
                    //  a. It is possible that some students take longer to answer, this should not be a problem for the other students.
                    Boolean allStudentsFinishExam = false;
                    while(!allStudentsFinishExam){
                        System.out.printf("Recibiendo respuestas\n");
                        Thread.sleep(2000);
                        //recibir respuestas alumnos
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
