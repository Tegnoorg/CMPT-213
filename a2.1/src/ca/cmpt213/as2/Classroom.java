package ca.cmpt213.as2;

import java.util.List;
public class Classroom {
    private String target_name;
    private Group groups;

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }

    public Group getGroups() {
        return groups;
    }

    public void setGroups(Group groups) {
        this.groups = groups;
    }

    public Classroom(String target_name, Group groups) {
        this.target_name = target_name;
        this.groups = groups;
    }


    @Override
    public String toString() {
        return "Classroom [target_name=" + target_name + ", groups=" + groups + "]";
    }
}
