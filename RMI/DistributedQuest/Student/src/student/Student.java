package student;


import common.ProfessorInterface;
import common.Question;

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

            System.out.println("Enter the student id: ");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            String student_id = input;

            // 3. The students connect to the room and wait for the exam to start.
            //      a. When joining the exam, students will need to send their university ID.
            stub.joinExam(student_id, client);

            while(true) {
                Thread.sleep(1000);
                //6. The students chose their answer and send it back to the server.
                // a. It is possible that some students take longer to answer, this should not
                // be a problem for the other students

                while (client.hasNextQuestion()) {
                    Question question = client.getNextQuestion();
                    System.out.println(question.getQuestion());

                    System.out.printf("" + question.isCorrectAnswer(1));

                    int choice_number = 1;
                    for (String choice : question.getChoices()) {
                        System.out.println(choice_number + ": " + choice);
                        ++choice_number;
                    }

                    System.out.println("Introduce your answer: ");
                    Integer student_answer = sc.nextInt();

                    stub.setAnswer(client, student_answer);

                }

            }

        } catch (RemoteException e) {
            System.err.println("remote exception: " + e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("student exception: " + e.toString());
            e.printStackTrace();
        }
    }

}