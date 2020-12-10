package professor;

import common.ProfessorInterface;
import common.Question;
import common.StudentInterface;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 */

class Exam implements Serializable {
    public List<Question> questions;
    public Integer questionIterator;
    Boolean examStarted;

    public Exam() {
        this.questions = new ArrayList<>();
        this.questionIterator = 0;
        this.examStarted = false;

    }

    public Boolean isCorrectAnswer(Integer question, Integer response) {
        return this.questions.get(question - 1).isCorrectAnswer(response);
    }

    public Question getNextQuestion() {
        if (this.questionIterator <= this.questions.size()) {
            Question nextQuestion = this.questions.get(this.questionIterator);
            this.questionIterator += 1;
            return nextQuestion;
        }
        return null;
    }

    public void startExam() {
        this.examStarted = true;
    }

    public void finishExam() {
        this.examStarted = false;
    }

    public Boolean isStarted() {
        return this.examStarted;
    }

}

public class ProfessorImplementation extends UnicastRemoteObject implements ProfessorInterface {
    Exam exam = new Exam();
    HashMap<StudentInterface, String> students = new HashMap<>();
    HashMap<StudentInterface, List<Integer>> studentAnswers = new HashMap<>();
    HashMap<StudentInterface, Boolean> studentIsFinished = new HashMap<>();

    protected ProfessorImplementation() throws RemoteException {
    }

    public void uploadCSV(File csv) {
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> choices = new ArrayList<>();
                Integer correctChoice;

                List<String> values = Arrays.asList(line.split(";"));

                for (int i = 1; i < values.size() - 1; i++) {
                    choices.add(values.get(i));
                }

                String question = values.get(0);
                correctChoice = Integer.parseInt(values.get(values.size() - 1));

                this.exam.questions.add(new Question(question, choices, correctChoice));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void waitStudents(Integer studentsNumber) throws InterruptedException {
        while (this.getNumStudents() < studentsNumber) {
            System.out.println("Number of students joined: [" + this.getNumStudents() + "/" + studentsNumber + "]");
            this.wait();
        }
        System.out.println("Number of students joined: [" + this.getNumStudents() + "/" + studentsNumber + "]");

    }

    public int getNumStudents() {
        return students.size();
    }

    public void joinExam(String id, StudentInterface student) throws RemoteException {
        //4. a. It is not possible for students to connect after the professor begins the exam. A message will be received indicating this.
        if (exam.isStarted()) {
            student.notifyAlreadyStarted();
        }

        synchronized (this) {
            this.students.put(student, id);
            this.studentAnswers.put(student, new ArrayList<>());
            this.studentIsFinished.put(student, false);
            this.notify();
        }

        //System.out.println("Student \"" + id + "\" joined");
    }

    public Boolean allStudentsFinishExam() {
        //System.out.printf("Finish?: "+this.studentIsFinished.values()+"\n");
        for (Boolean isFinished : this.studentIsFinished.values()) {
            if (!isFinished) {
                return false;
            }
        }

        return true;
    }

    public void startExam() {

        // Create file grades.txt and if its already created remove content
         try{
            File file = new File("grades.txt");
            if (!file.createNewFile()) {
                FileWriter myWriter = new FileWriter("grades.txt");
                myWriter.write("");
                myWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("The professor has started the exam");
        this.exam.startExam();

        List<StudentInterface> error_students = new ArrayList<StudentInterface>();
        for (StudentInterface student : students.keySet()) {
            try {
                student.notifyStart();
            } catch (RemoteException e) {
                System.out.println("Student is not reachable");
                error_students.add(student);
            }
        }
        for (StudentInterface c : error_students) {
            this.students.remove(c);
        }
    }

    public void sendQuestions() throws RemoteException {
        for (Question question : this.exam.questions) {
            Question q = new Question(question.getQuestion(), question.getChoices());
            List<StudentInterface> error_students = new ArrayList<StudentInterface>();
            for (StudentInterface student : students.keySet()) {
                try {
                    student.sendQuestion(q);
                } catch (RemoteException e) {
                    System.out.println("Student \"" + this.students.get(student) + "\" is not reachable");
                    error_students.add(student);
                    this.studentIsFinished.put(student, true);
                    calculateGrade(student);
                    this.notify();
                }
            }
            for (StudentInterface c : error_students) {
                this.students.remove(c);
            }
        }
        System.out.printf("The professor has sent the questions to the students\n");
    }

    public void setAnswer(StudentInterface student, int answer) throws RemoteException {
        synchronized (this) {
            //System.out.printf("Student \"" +this.students.get(student)+ "\" has sent the answer: "+answer+"\n");
            List<Integer> ans = this.studentAnswers.get(student);
            ans.add(answer);
            this.studentAnswers.put(student, ans);
            if (studentAnswers.get(student).size() == this.exam.questions.size()) {
                // The student is finished.
                this.studentIsFinished.put(student, true);
                int grade = calculateGrade(student);
                this.notify();
                student.notifyEnd(grade);
            }
            this.notify();
        }
    }

    public Integer calculateGrade(StudentInterface student) {
        int totalQuestions = this.exam.questions.size();
        int correctAnswers = 0;
        int question = 1;

        List<Integer> answers = this.studentAnswers.get(student);
        if (answers == null) {
            // Si no se contesta ninguna de las preguntas cuentan como incorrectas
            return 0;
        }
        for (Integer answer : answers) {
            if (this.exam.isCorrectAnswer(question, answer)) {
                correctAnswers += 1;
            }
            question += 1;
        }

        int grade = (10 * correctAnswers) / totalQuestions;
        storeGrade(student, grade);
        this.students.remove(student);
        return grade;
    }

    public void finishExam() {
        this.exam.finishExam();
        List<StudentInterface> error_students = new ArrayList<StudentInterface>();
        for (StudentInterface student : students.keySet()) {
            try {
                int grade = calculateGrade(student);
                student.notifyEnd(grade);
            } catch (RemoteException e) {
                //System.out.println("Student is not reachable");
                error_students.add(student);
            }
        }
        for (StudentInterface c : error_students) {
            this.students.remove(c);
        }
        System.out.printf("Exam finished");
        System.exit(0);
    }

    public void storeGrade(StudentInterface student, Integer grade) {
        String studentGrade = "The grade of the student \"" + this.students.get(student) + "\" is " + grade + "\n";
        System.out.printf(studentGrade);

        try {
            FileWriter myWriter = new FileWriter("grades.txt", true);
            myWriter.write(studentGrade);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
