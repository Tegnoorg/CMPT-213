package ca.cmpt213.a3;

import java.util.Scanner;


public class Game {
    private static int spellCount = 3;
    private static boolean initPos = false;


    //constructs game and plays it
    public Game(int numTokis, int numFoki,boolean cheatMode){
        Grid grid = new Grid(numTokis, numFoki, cheatMode);

        System.out.println(grid);

        Scanner in = new Scanner(System.in);

        boolean endGame = false;


        while(true){
            //System.out.println(grid.getScore());
            if(endGame){
                grid.setCheatMode(true);
                System.out.println("Game over");
                break;
            }
            if(grid.getScore() >= numTokis){
                grid.setCheatMode(true);
                System.out.println("YOU WIN!!!!!");
                break;
            }
            if(!initPos){
                System.out.println("Enter your grid position");
                String pos = in.nextLine();
                if(grid.validPosition(pos)){
                    endGame =  grid.setPosition(pos);
                    initPos = true;
                }else{
                    System.out.println("Position out of bound! (ex. B5)");
                }
            } else{
                System.out.print("Press WASD to move around (followed up by enter)");
                if(spellCount > 0){
                    System.out.print(" or press 2 to use spells ("+ spellCount + " uses left)");
                }
                System.out.println();
                String choice = in.nextLine();
                if("2".equals(choice) && spellCount > 0){
                    System.out.println("Spells: (Press 1, 2, 3 for options or anything other character to leave");
                    System.out.println("1: Jump (jump to a different position");
                    System.out.println("2: reveal (reveals one tokimon)");
                    System.out.println("3: kill (kills one fokimon)");
                    choice = in.nextLine();
                    if("1".equals(choice)){
                        System.out.println("Enter position");
                        choice = in.nextLine();
                        if(grid.validPosition(choice)){
                            endGame =  grid.setPosition(choice);
                            spellCount--;
                        }else{
                            System.out.println("Position out of bound! (ex. B5)");
                        }
                    }
                    else if("2".equals(choice)){
                        System.out.println("Toki revealed");
                        spellCount--;
                        grid.revealToki();
                    }
                    else if("3".equals(choice)){
                        System.out.println("Foki killed");
                        spellCount--;
                        grid.killFoki();
                    }
                }else{
                    endGame = grid.move(choice);
                }

            }

            System.out.println(grid);
            System.out.println("Your score is: " + grid.getScore() + "/" + numTokis );
        }
        in.close();


    }

}
