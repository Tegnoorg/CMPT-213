package a5.cmpt213.server.models;



import java.util.ArrayList;
import java.util.List;

public class TokimonList {
    private List<Tokimon> tokiList = new ArrayList<>();

    public List<Tokimon> getTokiList() {
        return tokiList;
    }

    public void addTokimon(Tokimon newTokimon) {
        tokiList.add(newTokimon);
    }

    public Tokimon deleteToki(long id){
        for (int i = 0; i < tokiList.size(); i++) {
            if (tokiList.get(i).getId() == id) {
                Tokimon temp = tokiList.get(i);
                tokiList.remove(i);
                return temp;
            }
        }
        return null;
    }



}
