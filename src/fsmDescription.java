import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
                if (line == null || line.equals("\r") || line.equals("\n") || line.equals("") ) {//Ensuring that an empty line is not included
                    continue;
                }
                Scanner scanner1 = new Scanner(line);
                scanner1.useDelimiter("\\s");
                /*System.out.print(parseInt(scanner1.next()));
                System.out.print(scanner1.next());
                System.out.print(scanner1.next());
                System.out.print(parseInt(scanner1.next()));
                System.out.println();*/
                currentStateList.add(parseInt(scanner1.next()));
                inputList.add(scanner1.next());
                outputList.add(scanner1.next());
                nextStateList.add(parseInt(scanner1.next()));
            }
        } catch (IOException e){
            System.out.println("Bad description");
            System.exit(0);
        }
    }

    public boolean wellFormed() {
        //Sample code
        boolean legal = true;
        if(this.filename == ""){
            legal = false;
        }
        for(int i = 0; i < nextStateList.size(); i++){
            if(!currentStateList.contains(nextStateList.get(i))){
                legal = false;
            }
        }
        HashSet distinctInput = new HashSet(inputList);
        HashSet distinctState = new HashSet(currentStateList);
        if(distinctInput.size()*distinctState.size()!=currentStateList.size()){
            legal = false;
        }
        if(currentStateList.size() != inputList.size() || currentStateList.size() != nextStateList.size() ||
        currentStateList.size() != outputList.size()) {
            legal = false;
        }
        return legal;
    }

    public ArrayList<Integer> getCurrentStateList() {
        return currentStateList;
    }

    public ArrayList<Integer> getNextStateList() {
        return nextStateList;
    }

    public ArrayList<String> getInputList() {
        return inputList;
    }

    public ArrayList<String> getOutputList() {
        return outputList;
    }
}
