package by.chester.entities;

public class Student {
    private int id;
    private String name;
    private String surname;
    private String birthDate;
    private int enterYear;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public int getEnterYear() {
        return enterYear;
    }
    public void setEnterYear(int enterYear) {
        this.enterYear = enterYear;
    }
}
