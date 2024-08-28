import ca.cmpt213.a3.*;


public class Main {

    private static final int DEFAULT_NUM_TOKI = 10;
    private static final int DEFAULT_NUM_FOKI = 5;
    private static int numTokis = DEFAULT_NUM_TOKI;
    private static int numFoki = DEFAULT_NUM_FOKI;
    private static boolean cheatMode = false;




    private static void argsHandler( String[] args){
        for(String arg : args){
            if(arg.startsWith("--numToki=")){
                int x = Integer.parseInt(arg.split("=")[1]);
                if(x >= 5 ){
                    numTokis = x;
                }else{
                    System.out.println("Number of Tokimon is less than 5. Using default value of 10");
                }
            }
            if(arg.startsWith("--numFoki=")){
                int x = Integer.parseInt(arg.split("=")[1]);
                if(x >= 5 ){
                    numFoki = x;
                }else{
                    System.out.println("Number of Fokimon is less than 5. Using default value of 5");
                }
            }
            if(arg.startsWith("--cheat")){
                cheatMode = true;
            }
        }
    }


    public static void main(String[] args) {

        argsHandler(args);
        if(100 < (numTokis + numTokis)){
            System.out.print("numToki + numFoki is greater than 100. Using default values of ");
            numTokis = 10;
            numFoki = 5;
        }
        System.out.println("numTokis: " + numTokis + ", numFoki: " + numFoki);

        new Game(numTokis, numFoki, cheatMode);


    }

}