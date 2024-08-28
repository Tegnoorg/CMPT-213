import ca.cmpt213.as2.*;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class PeerFeedbackProcessor {

        public static void main(String[] args) throws FileNotFoundException {
            if(args.length != 2) {
                System.err.println("Need 2 arguments file to read followed by result path");
                System.exit(-1);
            }
            String input = args[0];
            String output = args[1];

//            String input = "./InputTestDataSets/2-OneGroup";
//            String output = "1
//         .csv";


            File inputFile = new File(input);
            Gson gson = new Gson();
            List<Group> groups = new ArrayList<>();
            FileHandler fh = new FileHandler();
            fh.parseFile(input, gson, "");

            fh.printToCsv(output);
           //System.out.println(fh.getGroups());


        }
}