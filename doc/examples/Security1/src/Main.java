import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by eloigabal on 22/10/2019.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Will show some cat pictures");
        try {
            PrintWriter writer = new PrintWriter("/Users/eloigabal/Desktop/malicious.txt", "UTF-8");
            writer.println("I write some malicious words in your system");
            writer.println("Something bad can happen");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sorry the cat pictures can not be found");
    }
}
