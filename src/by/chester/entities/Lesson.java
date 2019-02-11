package by.chester.entities;
import java.io.Serializable;

public class Lesson implements Serializable {
    private int id;
    private String lesson;

    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id;  }
    public String getLesson() {
        return lesson;
    }
    public void setLesson(String lesson) {
        this.lesson = lesson;
    }
}


