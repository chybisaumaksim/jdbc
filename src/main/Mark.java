package main;

public class Mark {

    private int id;
    private int studentId;
    private int lessonId;
    private int mark;

    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id;  }
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public int getLessonId() {
        return lessonId;
    }
    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
}
