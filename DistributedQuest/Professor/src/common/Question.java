package common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable {
    private final String question;
    private final List<String> choices;
    private final Integer correctChoice;

    public Question(String question, List<String> choices, Integer correctChoice) {
        this.question = question;
        this.choices = choices;
        this.correctChoice = correctChoice;
    }

    public Boolean isCorrectAnswer(Integer response) {
        return response == this.correctChoice;
    }

    public String getQuestion() {
        if (this.question != null) {
            return this.question;
        }
        return "No question";
    }

    public List<String> getChoices() {
        if (this.choices != null) {
            return this.choices;
        }
        return Collections.singletonList("No choices");
    }

    @Override
    public String toString() {
        return "Question{" +
                "choices=" + choices +
                ", correctChoice=" + correctChoice +
                '}';
    }
}
