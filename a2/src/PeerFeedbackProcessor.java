import ca.cmpt213.as2.*;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class PeerFeedbackProcessor {
        public static void main(String[] args) {
//            if(args.length != 2) {
//                System.err.println("Need 2 arguments file to read followed by result path");
//                System.exit(-1);
//            }
            String input = "./InputTestDataSets/1-Just1";
            String output = "./InputTestDataSets/1-Just1";

            System.out.println("Reading peer feedback from " + input + " to " + output);

            Gson gson = new Gson();
            // Read from file
            try{
                FileReader reader = new FileReader(input);
                Student student = gson.fromJson(reader, Student.class);
                System.out.println(student);
            } catch (FileNotFoundException e){
                throw new RuntimeException(e);
            }
        }

}