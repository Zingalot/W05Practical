import java.io.File;
import java.io.IOException;
import java.util.*;

public class fsminterpreter {


    public static void main(String args[]){
        String badInput = "Bad input";

        String descriptionFileName = "";
        fsmDescription descriptionFile;

        ArrayList<HashMap<String, String>> inputOutputMaps = new ArrayList();
        ArrayList<HashMap<String, Integer>> inputNextStateMaps = new ArrayList();
        HashSet<Integer> distinctStates;
        int currentState;
        int initialState = -1;

        char[] inputArray = null;
        try {
            descriptionFileName = args[0];
            Scanner scanner = new Scanner(new File(args[1]));
            if(scanner.hasNext()) {
                inputArray = scanner.next().replaceAll("\\s+", "").toCharArray();
            }

        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println(badInput);
            System.exit(0);
        }
        catch (IOException e){
            System.out.println(badInput);
            System.exit(0);
        }

        descriptionFile = new fsmDescription(descriptionFileName);

        if(descriptionFile.wellFormed()) {

            //Checks that content can be read from the description file correctly, exits if not possible.
            try {
                descriptionFile.readContent();
            } catch (IOException e){
                System.out.println("Bad input");
                System.exit(0);
            }

            //Creates iterators for current state, input, next state and output for the lists created in the
            //fsmDescription class based off of the description provided to the program.
            ListIterator<Integer> csiterator = descriptionFile.getCurrentStateList().listIterator();
            ListIterator<String> iiterator = descriptionFile.getInputList().listIterator();
            ListIterator<Integer> nsiterator = descriptionFile.getNextStateList().listIterator();
            ListIterator<String> oiterator = descriptionFile.getOutputList().listIterator();
            distinctStates = new HashSet<>(descriptionFile.getCurrentStateList());
            initialState = descriptionFile.getCurrentStateList().get(0);


            //Creates all empty hash maps to occupy indices of distinct states in the map lists.
            //Maps are placed in index 0, even if there is no state zero for simplicity
            for(int i = 0; i <= distinctStates.size(); i++){
                inputOutputMaps.add(new HashMap<>());
                inputNextStateMaps.add(new HashMap<>());
            }

            //Inputs data into the maps
            while(iiterator.hasNext() && nsiterator.hasNext() && oiterator.hasNext() && csiterator.hasNext()){
                String inputInterpret = iiterator.next();
                String outputInterpret = oiterator.next();
                Integer nextStateInterpret = nsiterator.next();
                Integer currentStateInterpret = csiterator.next();

                inputOutputMaps.get(currentStateInterpret).put(inputInterpret, outputInterpret);
                inputNextStateMaps.get(currentStateInterpret).put(inputInterpret, nextStateInterpret);
            }

            //Debug printer
            for(int i = 1; i<inputOutputMaps.size(); i++){
                Iterator iterator = inputOutputMaps.get(i).keySet().iterator();
                Iterator iterator2 = inputNextStateMaps.get(i).keySet().iterator();
                System.out.println("State = " + i + ": ");
                while(iterator.hasNext()){
                    String item = (String) iterator.next();
                    String item2 = (String) iterator2.next();
                    System.out.println("Input = " + item + ", Output = " + inputOutputMaps.get(i).get(item) + ", Next State = " + inputNextStateMaps.get(i).get(item));
                }
                System.out.println();
            }
        }
        else {
            System.out.println(badInput);
            System.exit(0);
        }

        if(inputArray != null && initialState != -1){
            currentState = initialState;
            for(int i = 0; i < inputArray.length; i++){
                if(inputOutputMaps.get(currentState).containsKey(String.valueOf(inputArray[i])) && inputNextStateMaps.get(currentState).containsKey(String.valueOf(inputArray[i]))){
                    System.out.print(inputArray[i] +  ", ");
                    System.out.print(inputOutputMaps.get(currentState).get(String.valueOf(inputArray[i])));
                    currentState = inputNextStateMaps.get(currentState).get(String.valueOf(inputArray[i]));
                    System.out.println(", " + currentState);
                }
                else {
                    System.out.println("Invalid input");
                    System.exit(0);
                }

            }
        }
        else{
            System.out.println("Invalid input");
            System.exit(0);
        }
    }






}
