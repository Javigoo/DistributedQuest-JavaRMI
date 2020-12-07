package professor;
import java.io.*;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import common.ProfessorInterface;
import common.StudentInterface;

/**
private class Exam {
    private class Question {
        List<String> choices = new ArrayList<String>();
        Integer correctChoice = null;

        public void question(List<String> choices, Integer correctChoice){
            if (choices.length() >= correctChoice){
                throw new Exception();
            }
            this.choices = choices;
            this.correctChoice = correctChoice;
        }

        public Boolean isTheCorrectResponse(Integer response){
            if (response==this.correctChoice){
                return true;
            }
            return false;
        }

    }

    List<Question> questions = new ArrayList<Question>();

    public Exam(){}

    public void uploadCSV(File csv){
        while( True){
            List<String> choices = new ArrayList<String>();
            Integer correctChoice = new Interger();
            while (True) {
                // Va leyendo el csv ...
            }
            try{
                Question question = new Question(choices, correctChoice);
            } catch (Exception e){
                System.err.println("remote exception: " + e.toString()); e.printStackTrace();
            }
            this.questions.add(question);
        }
    }

    public Boolean isCorrectAnswer(Integer question, Integer response){
        return this.questions[question].isTheCorrectResponse(response);
    }

    public String[] getNextQuestion(){
        return this.questions.next();
    }
}
**/
public class ProfessorImplementation extends UnicastRemoteObject implements ProfessorInterface {
    private static final String COMMA_DELIMITER = ";";

    protected ProfessorImplementation() throws RemoteException {
        super();
    }
    /**
     *
     */

    /**
    private static final long serialVersionUID = 1L;
    private HashMap<StudentInterface, List<Integer>> studentResponses = new HashMap<>();
    private Exam exam = new Exam();

    public ProfessorImplementation() throws RemoteException {
        super();
    }

    public void registerStudent(StudentInterface student) throws RemoteException {
        synchronized(this) {
            //this.studentResponses.add(student); // ¿Se guarda el universityID fuera del objeto?
            this.notify();
        }
    }

    public void responseAnAnswer(StudentInterface student, Integer choice) throws RemoteException {
        synchronized(this) {
            this.responses[student].add(choice);
            this.notify();
        }
    }

    public void sendNextQuestion() {
        Array question = Exam.getNextQuestion();
        for (int i=0; i<this.students.length(); i++){
            this.students[i].setNextQuestion(question);
        }
    }

    public void sendGrade(StudentInterface student){
        List<Integer> responses = this.studentResponses[student];
        Integer grade = 0;
        for (int i=0; i<responses.length(); i++){
            if (this.exam.isCorrectAnswer(i, responses[i])==true){
                grade++;
            }
        }
        student.setGrade(grade);
    }
    **/

    public void uploadExam(String csvFile) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(records);
    }

    public void startExam(Integer numStudents){

    }

    @Override
    public void registerStudent(StudentInterface student) throws RemoteException {

    }

    @Override
    public void responseAnAnswer(StudentInterface student, Integer choice) throws RemoteException {

    }

    /**
    public void beginExam(){
    } // Now, the students can no longer join the room.
    

    public void finishExam(){
    } // The professor decides to finish the exam.

     **/
}
