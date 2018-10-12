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

        ArrayList<HashMap<String, String>> inputOutputMaps = new ArrayList();
        ArrayList<HashMap<String, Integer>> inputNextStateMaps = new ArrayList();
        int currentInterpret;
        int nextInterpret;
        int currentState = 0;

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

            //Sets the current state being 'interpreted' to the first state in the current state list
            currentInterpret = descriptionFile.getCurrentStateList().get(0);

            //Iterates the csiterator as the 'current' state being interpreted has already been given an intial value
            csiterator.next();

            //Creates two default hash maps to occupy index 0 in the map lists.
            inputOutputMaps.add(new HashMap<>());
            inputNextStateMaps.add(new HashMap<>());

            //Creates the maps from input to output and input to next state and places them in lists where the index of
            //the list represents the current state being considered, and the maps show the expected behavhiour with
            //respect to output and next state for a given input.
            while(csiterator.hasNext()) {

                //Ensures that if the fsm description is 'out of order' such that the current states are not passed
                //from 1 onwards, the lists have placeholder maps to avoid index out of bounds exceptions.
                for(int i = 0; i < currentInterpret; i++){
                    if(inputOutputMaps.size()<i-1){
                        inputOutputMaps.add(new HashMap<>());
                    }
                    if(inputNextStateMaps.size()<i-1){
                        inputNextStateMaps.add(new HashMap<>());
                    }
                }

                //Creates new maps for the current state being considered
                inputOutputMaps.add(currentInterpret, new HashMap<>());
                inputNextStateMaps.add(currentInterpret, new HashMap<>());

                //Keeps track of the next 'current state' to be considered, so that the loop can have a condition for
                //when to stop.
                nextInterpret = csiterator.next();

                //Inputs data into the maps provided that the relevant data is there, and the current state being
                //considered hasn't changed
                while(iiterator.hasNext() && nsiterator.hasNext() && oiterator.hasNext() && nextInterpret==currentInterpret){
                    String input = iiterator.next();
                    inputOutputMaps.get(currentInterpret).put(input,oiterator.next());
                    inputNextStateMaps.get(currentInterpret).put(input,nsiterator.next());

                    //Only updates next interpret if possible
                    if(csiterator.hasNext()) {
                        nextInterpret = csiterator.next();

                        //if next interpret has 'just' updated, place the key value pairs into the map, as this is
                        //the final line for the given current state. This assumes that the input tables are organised
                        //normally.
                        if(nextInterpret != currentInterpret){
                            String input1 = iiterator.next();
                            inputOutputMaps.get(currentInterpret).put(input1,oiterator.next());
                            inputNextStateMaps.get(currentInterpret).put(input1,nsiterator.next());
                        }
                    }
                }

                //Update the current interpret.
                currentInterpret = nextInterpret;
            }


            //Debug printer
            for(int i = 1; i<inputOutputMaps.size(); i++){
                Iterator iterator = inputOutputMaps.get(i).keySet().iterator();
                System.out.println("State = " + i + ": ");
                while(iterator.hasNext()){
                    String item = (String) iterator.next();
                    System.out.println("Input = " + item + ", Output = " + inputOutputMaps.get(i).get(item));
                }
            }
            for(int i = 1; i<inputNextStateMaps.size(); i++){
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
