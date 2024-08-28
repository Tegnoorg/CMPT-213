package ca.cmpt213.as2;


import java.util.Objects;

public class Student {

    private String name;
    private String sfu_email;
    private Feedback contribution;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfu_email(String target) {
        setSfu_email(sfu_email.trim());
        if(Objects.equals(sfu_email, target)){
            return "-->";
        }
        return sfu_email;
    }

    public void setSfu_email(String sfu_email) {
        this.sfu_email = sfu_email;
    }

    public Feedback getContribution() {
        return contribution;
    }

    public void setContribution(Feedback comment) {
        this.contribution = comment;
    }

    public Student(String name, String sfu_email, Feedback contribution) {
        this.name = name;
        this.sfu_email = sfu_email;
        this.contribution = contribution;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sfu_email='" + this.getSfu_email("") + '\'' +
                ", contribution=" + contribution +
                '}';
    }
}
