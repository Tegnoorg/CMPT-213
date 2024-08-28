package ca.cmpt213.as2;

public class Feedback {
    private double score;
    private String comment;

    public Feedback(double score, String comment) {
        this.score = score;
        this.comment = comment;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getComment() {
        String c = comment.replaceAll("\"", "'");
        int i = c.indexOf("\n");
        return c.substring(0,i);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "score=" + score +
                ", comment='" + this.getComment() + '\'' +
                '}';
    }
}
