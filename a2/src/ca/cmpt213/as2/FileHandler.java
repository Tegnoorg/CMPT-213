package ca.cmpt213.as2;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FileHandler {
private static List<Classroom> groups = new ArrayList<>();

    public static void parseFile(String fileName, Gson gson, String name) {
        File file = new File(fileName);
        if (file == null || !file.exists()) {
            return;
        }
        File[] files = file.listFiles();

        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                //System.out.println(f.getName());
                parseFile(f.getAbsolutePath(), gson, f.getName());
            } else {
                if (f.getName().endsWith(".json")) {
                    try{
                        FileReader reader = new FileReader(f);
                        Group group = gson.fromJson(reader, Group.class);
                        Classroom student = new Classroom(name, group);
                        groups.add( student);
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
    }

    public void clearGroups(){
        groups.clear();
    }

    public List<Classroom> getGroups() {
        return groups;
    }


    public void printToCsv(String output) throws FileNotFoundException {
        File testCsv = new File(output);
        PrintWriter pw = new PrintWriter(testCsv);

        pw.println("Group#,Source Student,Target Student,Score,Comment,,Private");
        //System.out.println(groups.toArray().length);
        for (Classroom group : groups) {
            Group g = group.getGroups();
            double score = 0;
            int memberCount = 0;
            g.checkScores();
            for (Student s : g.getGroup()) {
                if (!(Objects.equals(group.getTarget_name(), s.getSfu_email("")))) {
                    score += s.getContribution().getScore();
                    memberCount++;
                }
                pw.println("," + s.getSfu_email(group.getTarget_name()) + "," + s.getSfu_email(group.getTarget_name()) + "," + s.getContribution().getScore() + "," + s.getContribution().getComment());
            }
            score = score / memberCount;
            if (memberCount == 0) {
                pw.println("," + group.getTarget_name() + "," + group.getTarget_name() + ",avg " + "NaN/0" + ",,," + g.getConfidential_comments());
            } else {
                String str = String.format("," + "-->" + "," + group.getTarget_name() + ",avg " + "%.1f" + "/" + memberCount + ",,," + g.getConfidential_comments(), score);
                pw.println(str);
            }

        }
        pw.close();
    }
}
