import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class fsmDescription {

    String filename;
    ArrayList<Integer> currentStateList = new ArrayList<>();
    ArrayList<String> inputList = new ArrayList<>();
    ArrayList<String> outputList = new ArrayList<>();
    ArrayList<Integer> nextStateList = new ArrayList<>();
    List<String> contents = new ArrayList<>();


    public fsmDescription(String filename) {
        this.filename = filename;
    }

    public void readContent() throws IOException {
        try {
            Scanner scanner = new Scanner(new File(filename));
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String line = scanner.next();
                if (line == null || line.equals("\r") || line.equals("\n")) {//Ensuring that an empty line is not included
                    continue;
                }
                Scanner scanner1 = new Scanner(line);
                scanner1.useDelimiter("\\s");
                /*System.out.println(scanner1.next());
                System.out.println(parseInt(scanner1.next()));
                System.out.println(scanner1.next());
                System.out.println(scanner1.next());*/
                currentStateList.add(parseInt(scanner1.next()));
                inputList.add(scanner1.next());
                outputList.add(scanner1.next());
                nextStateList.add(parseInt(scanner1.next()));
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Bad input");
            System.exit(0);
        }
    }

    public boolean wellFormed() {
        //Sample code
        if(this.filename == ""){
            return false;
        }
        return true;
        //TODO Decide if the description is well formed or not
    }

    public ArrayList<Integer> getCurrentStateList() {
        //TODO Read description file to return list of current states

        return currentStateList;
    }

    public ArrayList<Integer> getNextStateList() {
        //TODO Read description file to return list of next states
        return nextStateList;
    }

    public ArrayList<String> getInputList() {
        //TODO Read description file to return list of inputs
        return inputList;
    }

    public ArrayList<String> getOutputList() {
        //TODO Read description file to return list of outputs
        return outputList;
    }
}
