package ca.cmpt213.a3;

import java.util.*;

public class Grid {
    private Cell[][] grid;
    private boolean cheatMode;
    private static final int GRID_SIZE = 10;
    private Set<String> usedCord = new HashSet<String>();
    private Set<String> validInput = new HashSet<String>();
    private List<String> tokiLocations = new ArrayList<>();
    private List<String>  fokiLocations = new ArrayList<>();
    private int oldX = 0;
    private int oldY = 0;
    private int score = 0;

    private char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};


    //constructor
    public Grid(int numToki, int numFoki, boolean cheatMode){
        grid = new Cell[GRID_SIZE][GRID_SIZE];
        this.cheatMode = cheatMode;
        initGrid(numToki, numFoki);
        this.usedCord.add("");
    }


    //initializes the grid
    private void initGrid(int numToki, int numFoki){
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = new Cell();
                int x = j+1;
                validInput.add(alphabet[i] + "" + x);
            }
        }

        initToki(numToki);
        //System.out.println(numToki);

        initFoki(numFoki);
        //System.out.println(numFoki);

    }

    //randomly puts tokis
    private void initToki(int numToki){
        Random rand = new Random();

        int count = 0;

        while(count < numToki){
            int x = rand.nextInt(GRID_SIZE);
            int y = rand.nextInt(GRID_SIZE);
            String coordinate = x + "," + y;

            if(usedCord.add(coordinate)){
                //System.out.print("Toki: ");
                //System.out.println(coordinate);
                grid[x][y].setToki(true);
                grid[x][y].setFoki(false);
                tokiLocations.add(coordinate);

                count++;
            }
        }
    }

    //randomly puts fokis
    private void initFoki(int numFoki){
        Random rand = new Random();

        int count = 0;

        while(count < numFoki){
            int x = rand.nextInt(GRID_SIZE);
            int y = rand.nextInt(GRID_SIZE);
            String coordinate = x + "," + y;

            if(usedCord.add(coordinate)){

                //System.out.print("Foki: ");
                //System.out.println(coordinate);
                grid[x][y].setToki(false);
                grid[x][y].setFoki(true);
                fokiLocations.add(coordinate);

                count++;
            }
        }
    }


    //reveals one toki and increases score by 1
    public void revealToki(){
        for(int i = 0; i < tokiLocations.size(); i++){
            System.out.println(tokiLocations.get(i).charAt(0));

            int x = Integer.parseInt(tokiLocations.get(i).split(",")[0]);
            int y = Integer.parseInt(tokiLocations.get(i).split(",")[1]);
            if(grid[x][y].isToki()){
                if(!grid[x][y].isVisted()){
                    grid[x][y].setVisted(true);
                    score++;
                    return;
                }

            }
        }
        System.out.println("All tokimon are revealed");
    }

    //kills one foki
    public void killFoki(){
        for(int i = 0; i < fokiLocations.size(); i++){
            int x = Integer.parseInt(fokiLocations.get(i).split(",")[0]);
            int y = Integer.parseInt(tokiLocations.get(i).split(",")[1]);
            if(grid[x][y].isFoki()){
               grid[x][y].setFoki(false);
               fokiLocations.remove(i);
               return;
            }

        }
        System.out.println("All tokimon are revealed");
    }

    public boolean validPosition( String pos){
        return validInput.contains(pos);
    }

    //used for wasd inputs
    public boolean move(String move){
            if("w".equals(move) || "W".equals(move)){
                if(oldX != 0){
                    grid[oldX][oldY].setCurrent(false);
                    grid[oldX-1][oldY].setCurrent(true);
                    oldX--;
                }else{
                    System.out.println("cannot move up");
                }
            }else if("s".equals(move) || "S".equals(move)){
                if(oldX != 9){
                    grid[oldX][oldY].setCurrent(false);
                    grid[oldX+1][oldY].setCurrent(true);
                    oldX++;
                }else{
                    System.out.println("cannot move down");
                }
            }else if("d".equals(move) || "D".equals(move)){
                if(oldY != 9){
                    grid[oldX][oldY].setCurrent(false);
                    grid[oldX][oldY+1].setCurrent(true);
                    oldY++;
                }else{
                    System.out.println("cannot move right");
                }
            }else if("a".equals(move) || "A".equals(move)){
                if(oldY != 0){
                    grid[oldX][oldY].setCurrent(false);
                    grid[oldX][oldY-1].setCurrent(true);
                    oldY--;
                }else{
                    System.out.println("cannot move left");
                }
            }else{
                System.out.println("Wrong input!");
            }
            grid[oldX][oldY].setVisted(true);

            if(grid[oldX][oldY].isFoki()){
                return true;
            }
            if(grid[oldX][oldY].isToki()){
                score++;
            }
            return false;
    }

    //sets positions and checks if its a valid input
    public boolean setPosition(String pos){
        grid[oldX][oldY].setCurrent(false);
        int x = -1;
        int y = Integer.parseInt(pos.substring(1));
        y = y-1;
        for(int i = 0; i < GRID_SIZE; i++){
            if(alphabet[i] == pos.charAt(0)){
                x = i;
            }
        }
        grid[x][y].setCurrent(true);
        grid[x][y].setVisted(true);
        oldX = x;
        oldY = y;
        if(grid[x][y].isToki()){
            score++;
            return false;
        }
        if(grid[x][y].isFoki()){
            return true;
        } else{
            return false;
        }
    }
    public void setCheatMode (boolean cheatMode){
        this.cheatMode = cheatMode;
    }

    public int getScore(){
        return score;
    }

    @Override
    public String toString() {
        System.out.println("\nGrid:");
        System.out.println("   1  2  3  4  5  6  7  8  9  10");
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(alphabet[i]+ " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(" ");
                    if(grid[i][j].isVisted() || cheatMode){
                        if(grid[i][j].isCurrent()){
                            System.out.print("@");
                        }
                        if(grid[i][j].isEmpty()){
                            System.out.print(" ");
                        }
                        if(grid[i][j].isFoki()) {
                            System.out.print("X");
                        }
                        if(grid[i][j].isToki()) {
                            System.out.print("$");
                        }
                    }else{
                        System.out.print("~");
                    }
                if(!grid[i][j].isCurrent()){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        return "";
    }
}
