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


            synchronized (client) {
                client.wait();

                // Los estudiantes reciben las preguntas
                while(client.questions.size() <= 5) {
                    client.wait(); // No quitar Importante
                }
                System.out.printf("All questions: " + client.questions.toString() +"\n");


                //6. The students chose their answer and send it back to the server.
                // a. It is possible that some students take longer to answer, this should not
                // be a problem for the other students
                for (int i = 0; i <= 5; i++){
                    Question question = client.getNextQuestion();

                    System.out.println(question.getQuestion());
                    for (String choice : question.getChoices()) {
                        System.out.println(choice);
                    }

                    System.out.println("Introduce your answer: ");
                    //Integer student_answer = sc.nextInt();

                    stub.setAnswer(client, 1);

                }

                /**
                 client.getSecretNumber();
                 stub.sendAnswerNumber(client, client.secretNumber);
                 client.wait();
                 System.exit(0);
                 **/
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