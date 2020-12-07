package professor;

import common.ProfessorInterface;
import common.StudentInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class ProfessorImplementation extends UnicastRemoteObject implements ProfessorInterface {
    HashMap<String, StudentInterface> students = new HashMap<>();
    List<List<String>> questions = new ArrayList<>();

    HashMap<StudentInterface,Integer> clientNumber = new HashMap<>();
    int answers = 0;

    public ProfessorImplementation() throws RemoteException{
        super();
    }

    public void registerStudent(String id, StudentInterface client) throws RemoteException {
        synchronized (this) {
            students.put(id, client);
            this.notify();
        }
    }

    public void sendAnswerNumber(StudentInterface client, int number) throws RemoteException{
        synchronized (this) {
            //System.out.println(number);
            clientNumber.put(client, number);
            answers ++;
            this.notify();
        }
    }

    public List<StudentInterface> getWinners(int number){
        List<StudentInterface> returns = new ArrayList<>();
        for (StudentInterface client: students.values()){
            //System.out.println(clientNumber.get(client));
            //System.out.println(number);

            if (clientNumber.get(client) == number){
                returns.add(client);
            }
        }
        return returns;
    }

    public List<StudentInterface> getLoosers(int number){
        List<StudentInterface> returns = new ArrayList<>();
        for (StudentInterface client: students.values()){
            if (clientNumber.get(client) != number){
                returns.add(client);
            }
        }
        return returns;
    }

    public void notifyStart(){
        List<StudentInterface> error_clients = new ArrayList<StudentInterface>();
        for (StudentInterface c : students.values()) {
            try{
                c.notifyStart();
            }catch(RemoteException e){
                System.out.println("Student is not reachable");
                error_clients.add(c);
            }
        }
        for(StudentInterface c: error_clients){
            this.students.remove(c);
        }
    }

    public int getNumStudents(){
        return students.size();
    }

    public void restart(){
        this.students.clear();
        this.answers = 0;
        this.clientNumber.clear();
    }

    public void uploadExam(String csvFile) {
        List<List<String>> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                questions.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.questions = questions;
    }
}
