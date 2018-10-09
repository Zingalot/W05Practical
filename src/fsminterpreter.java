import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class fsminterpreter {


    public static void main(String args[]){
        String badInput = "Bad input";

        String descriptionFileName = "";
        fsmDescription descriptionFile;

        /*ArrayList<Long> currentStateList;
        ArrayList<String> inputList;
        ArrayList<String> outputList;
        ArrayList<Long> nextStateList;*/

        ArrayList<HashMap<String, String>> inputOutputMaps = new ArrayList();
        ArrayList<HashMap<String, Integer>> inputNextStateMaps = new ArrayList();
        int currentinterpret;
        int currentState = 0;
        int initial;

        /*HashMap<String, String> outputMap = new HashMap<>();
        HashMap<String, String> nextStateMap = new HashMap<>();*/
        char[] inputArray = null;
        try {
            descriptionFileName = args[0];
            inputArray = args[1].replaceAll("\\s+","").toCharArray();
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println(badInput);
            System.exit(0);
        }

        descriptionFile = new fsmDescription(descriptionFileName);

        if(descriptionFile.wellFormed()) {
            //TODO Interpret FSM
            try {
                descriptionFile.readContent();
            } catch (IOException e){
                System.out.println("Bad input");
                System.exit(0);
            }

            /*currentStateList = descriptionFile.getCurrentStateList();
            inputList = descriptionFile.getInputList();
            outputList = descriptionFile.getOutputList();
            nextStateList = descriptionFile.getNextStateList();*/

            ListIterator<Integer> csiterator = descriptionFile.getCurrentStateList().listIterator();
            ListIterator<String> iiterator = descriptionFile.getInputList().listIterator();
            ListIterator<Integer> nsiterator = descriptionFile.getNextStateList().listIterator();
            ListIterator<String> oiterator = descriptionFile.getOutputList().listIterator();
            currentinterpret = descriptionFile.getCurrentStateList().get(0);
            initial = currentinterpret;

            while(csiterator.hasNext()) {
                inputOutputMaps.add(currentinterpret, new HashMap<>());
                inputNextStateMaps.add(currentinterpret, new HashMap<>());
                currentinterpret = csiterator.next();
                while(iiterator.hasNext() && nsiterator.hasNext() && oiterator.hasNext() && csiterator.next()!=currentinterpret){
                    inputOutputMaps.get(currentinterpret).put(iiterator.next(),oiterator.next());
                    inputNextStateMaps.get(currentinterpret).put(iiterator.next(),nsiterator.next());

                }
            }

            //Debug printer
            for(int i = 0; i<inputOutputMaps.size(); i++){
                Iterator iterator = inputOutputMaps.get(i).keySet().iterator();
                System.out.println("State = " + i + ": ");
                while(iterator.hasNext()){
                    String item = (String) iterator.next();
                    System.out.println("Input = " + item + ", Output = " + inputOutputMaps.get(i).get(item));
                }
            }
            for(int i = 0; i<inputNextStateMaps.size(); i++){
                Iterator iterator = inputNextStateMaps.get(i).keySet().iterator();
                System.out.println("State = " + i + ": ");
                while(iterator.hasNext()){
                    String item = (String) iterator.next();
                    System.out.println("Input = " + item + ", Next State = " + inputNextStateMaps.get(i).get(item));
                }
            }



            /*
            This creates a bodged map where state and input are combined into a single map key
            Better would be separate maps from input to output, each map representing a state
            while(csiterator.hasNext() && iiterator.hasNext() && nsiterator.hasNext() && oiterator.hasNext()) {
                String concatenate = csiterator.next().toString().concat(iiterator.next());
                outputMap.put(concatenate, oiterator.next());
                nextStateMap.put(concatenate, nsiterator.next().toString());
            }*/



        }
        else {
            System.out.println(badInput);
            System.exit(0);
        }

        if(inputArray!=null){
            for(int i = 0; i < inputArray.length; i++){
                if(inputOutputMaps.get(currentState).containsKey(inputArray[i]) && inputNextStateMaps.get(currentState).containsKey(inputArray[i])){
                    System.out.println(inputOutputMaps.get(currentState).get(inputArray[i]));
                    currentState = inputNextStateMaps.get(currentState).get(inputArray[i]);
                }
                else {
                    System.out.println("Invalid input");
                    System.exit(0);
                }

            }
        }
    }






}
