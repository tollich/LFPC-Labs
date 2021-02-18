import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

class Value{
    public String TerminalValue;
    public String NonTerminalValue;

    public Value(String terminal, String nonTerminal) {
        TerminalValue = terminal;
        NonTerminalValue = nonTerminal;
    }

    public Value(String terminal) {
        TerminalValue = terminal;
        NonTerminalValue = "#";
    }
}


public class Main {
    public static void main(String[] args) throws IOException {

        String regularGrammar = "VN={S, D, E, F, L}, VT={a, b, c, d},\n" +
                "P={\n" +
                "1. S-aD\n" +
                "2. D-bE\n" +
                "3. E-cF\n" +
                "4. F-dD\n" +
                "5. E-dL\n" +
                "6. L-aL\n" +
                "7. L-bL\n" +
                "8. L-c }";
        HashMap<String, Value[]> GrammarHashMap = ConvertToHash(regularGrammar);

        PrintFA(GrammarHashMap);

        String input = "abdc";
        String nonTerminal = "S";

        for (int i = 0; i < input.length(); i++) {
            nonTerminal = ParseValue(nonTerminal,input.charAt(i),GrammarHashMap);
            if (nonTerminal == "#" && (input.length()-1)-i!=0){
                nonTerminal = null;
                break;
            }
        }
        if(nonTerminal == null || !nonTerminal.equals("#")){
            System.out.println("FALSE");
        }
        else{
            System.out.println("TRUE");
        }
    }

    static String ParseValue(String NonTerminal, char terminal, HashMap<String, Value[]> hmap){
        if (NonTerminal == null){
            return null;
        }
        for (Value value: hmap.get(NonTerminal)) {
            if(value.TerminalValue.charAt(0) == terminal){
                return value.NonTerminalValue;
            }
        }
        return null;
    }


    static HashMap<String, Value[]> ConvertToHash(String input){

        HashMap<String, Value[]> hmap = new HashMap<String, Value[]>();
        int pos = input.indexOf("P");
        String[] productions = input.substring(pos).split("\\.");
        for (int i = 1; i < productions.length; i++) {
            productions[i]= productions[i].replaceAll("\\s|[0-9]|\\{|}", "");

            if(hmap.containsKey(productions[i].charAt(0)+"")){
                int len = hmap.get(productions[i].charAt(0)+"").length;
                Value[] array = hmap.get(productions[i].charAt(0)+"").clone();
                Value[] temp = new Value[len+1];
                for (int j = 0; j < len; j++) {
                    temp[j] = hmap.get(productions[i].charAt(0)+"")[j];
                }

                temp[len] = new Value(productions[i].charAt(2)+"", productions[i].length()==4?productions[i].charAt(3)+"":"#" );
                hmap.put(productions[i].charAt(0)+"",temp);
            }
            else{
                hmap.put(productions[i].charAt(0)+"", new Value[]{new Value(productions[i].charAt(2)+"",productions[i].length()==4?productions[i].charAt(3)+"":"#")});
            }

        }

        return hmap;
    }
    static void PrintFA(HashMap<String, Value[]> hmap) throws IOException {
        String graph = "";

        for (String key: hmap.keySet()){
            for (Value value: hmap.get(key)) {
                graph+= key+ " -> "+value.TerminalValue+(value.NonTerminalValue.equals("#")? " " : value.NonTerminalValue)+"\n";
            }
        }
        System.out.println(graph);
    }
}