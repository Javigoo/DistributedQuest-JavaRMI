package professor;

import common.ProfessorInterface;
import common.Question;
import common.StudentInterface;

import java.io.*;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.http.*;
import java.net.URI;
import java.net.http.HttpClient;



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
    HashMap<StudentInterface, Boolean> studentsFinished = new HashMap<>();

    int EXAM_ID = 0;
    List<String> studentsValidID = uploadExamStudents(new File("ExamStudents.csv"));
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    protected ProfessorImplementation() throws RemoteException {
    }

    public void uploadCSV(File csv) {
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String line;
            while ((line = br.readLine()) != null) {
                int correctChoice;
                List<String> choices = new ArrayList<>();
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

    public List<String> uploadExamStudents(File csv) {
        List<String> examStudents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String studentID;
            while ((studentID = br.readLine()) != null) {
                examStudents.add(studentID);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return examStudents;
    }

    public void sendExam(Integer port) {
        try {
            Map<Object, Object> data = new HashMap<>();
            data.put("description", "RMI exam");
            data.put("time", "2021-01-11 17:35:00");
            data.put("date", "2021-01-11");
            data.put("location", port);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(buildFormDataFromMap(data))
                    .uri(URI.create("http://127.0.0.1:8000/exams/"))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String json =  response.body().substring(1, response.body().length() - 1);
            String[] dataResponse = json.split(",");
            for (String key: dataResponse){
                if(key.contains("key")){
                    EXAM_ID = Integer.parseInt(key.split(":")[1]);
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        registerIDs(studentsValidID);

    }

    public void registerIDs(List<String> students){
        try{
            for (String id: students) {
                URL url = new URL("http://127.0.0.1:8000/exams/"+EXAM_ID+"/grades/");
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setDoOutput(true);

                String jsonInputString = "[{\"universityId\": \""+id+"\"}]";

                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                con.getResponseCode();
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

        if (!validID(id)) {
            System.out.printf("Not valid ID: " + id);
            student.notifyInvalidID();
        }

        synchronized (this) {
            this.students.put(student, id);
            this.studentAnswers.put(student, new ArrayList<>());
            this.studentsFinished.put(student, false);
            this.notify();
        }

        //System.out.println("Student \"" + id + "\" joined");
    }

    public boolean validID(String id) throws RemoteException {
        try {
            URL url = new URL("http://127.0.0.1:8000/uid/"+id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if(status == 200){
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean allStudentsFinishExam() {
        //System.out.printf("Finish?: "+this.studentIsFinished.values()+"\n");
        for (Boolean isFinished : this.studentsFinished.values()) {
            if (!isFinished) {
                return false;
            }
        }

        return true;
    }

    public void startExam() {

        // Create file grades.txt and if its already created remove content
        try {
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

        List<StudentInterface> error_students = new ArrayList<>();
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

    public void sendQuestions() {
        for (Question question : this.exam.questions) {
            Question q = new Question(question.getQuestion(), question.getChoices());
            List<StudentInterface> error_students = new ArrayList<>();
            for (StudentInterface student : students.keySet()) {
                try {
                    student.sendQuestion(q);
                } catch (RemoteException e) {
                    System.out.println("Student \"" + this.students.get(student) + "\" is not reachable");
                    error_students.add(student);
                    this.studentsFinished.put(student, true);
                    calculateGrade(student);
                    this.notify();
                }
            }
            for (StudentInterface c : error_students) {
                this.students.remove(c);
            }
        }
        System.out.print("The professor has sent the questions to the students\n");
    }

    public void setAnswer(StudentInterface student, int answer) throws RemoteException {
        synchronized (this) {
            System.out.printf("Student \"" +this.students.get(student)+ "\" has sent the answer: "+answer+"\n");
            List<Integer> ans = this.studentAnswers.get(student);
            ans.add(answer);
            this.studentAnswers.put(student, ans);
            if (studentAnswers.get(student).size() == this.exam.questions.size()) {
                // The student is finished.
                this.studentsFinished.put(student, true);
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
        List<StudentInterface> error_students = new ArrayList<>();
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
        System.out.print("Exam finished");
        System.exit(0);

    }

    public void storeGrade(StudentInterface student, Integer grade) {
        String studentGrade = "The grade of the student \"" + this.students.get(student) + "\" is " + grade + "\n";
        System.out.print(studentGrade);

        sendGrade(this.students.get(student), grade);

        try {
            FileWriter myWriter = new FileWriter("grades.txt", true);
            myWriter.write(studentGrade);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendGrade(String studentID, int grade) {

        try {
            URL url = new URL("http://127.0.0.1:8000/exams/"+EXAM_ID+"/grades/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            String jsonInputString = "[{\"universityId\":\""+studentID+"\", \"grade\":"+grade+"}]";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            System.out.println("\nPUT: "+ con.getResponseCode());

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

}
