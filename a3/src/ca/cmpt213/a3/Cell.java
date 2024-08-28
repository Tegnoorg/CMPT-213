package ca.cmpt213.a3;

public class Cell {
    private boolean hasToki;
    private boolean hasFoki;
    private boolean visted;
    private boolean current;

    public Cell(){
        this.hasToki = false;
        this.hasFoki = false;
        this.visted = false;
        this.current = false;
    }

    public boolean isFoki(){
        return hasFoki;
    }
    public boolean isToki(){
        return hasToki;
    }

    public boolean isEmpty(){
        return !hasToki && !hasFoki && !current;
    }

    public void setToki(boolean hasToki){

        this.hasToki =  hasToki;
    }

    public void setFoki(boolean hasFoki){
        this.hasFoki = hasFoki;
    }

    public void setVisted(boolean visted){
        this.visted = visted;
    }

    public boolean isVisted(){
        return visted;
    }

    public void setCurrent(boolean current){
        this.current = current;
    }

    public boolean isCurrent(){
        return current;
    }
}
