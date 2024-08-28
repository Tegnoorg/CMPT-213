package ca.cmpt213.as2;

import java.util.List;

public class Group {
    private List<Student> group;
    private String confidential_comments;

    public Group(List<Student> group, String confidential_comments) {
        this.group = group;
        this.confidential_comments = confidential_comments;
    }
    public List<Student> getGroup() {
        return group;
    }

    public void setGroup(List<Student> group) {
        this.group = group;
    }

    public String getConfidential_comments() {
        String comment = confidential_comments.replaceAll("\"", "'");
        int i = comment.indexOf("\n");
        return comment.substring(0,i);
    }

    public void setConfidential_comments(String confidential_comments) {
        this.confidential_comments = confidential_comments;
    }

    public void checkScores(){
        double score = 0;
        int memberCount = 0;
        for(Student student : group){
            score+= student.getContribution().getScore();
        }
        double predictedScore = 20*memberCount;
        score = score - predictedScore;
        if(-0.1 > score || score > 0.1){
            return;
        }
        System.err.println("Scores not valid");
        System.exit(-1);
    }

    @Override
    public String toString() {
        return "{" +
                "group=" + group +
                ", confidential_comments='" + this.getConfidential_comments() + '\'' +
                '}';
    }
}
