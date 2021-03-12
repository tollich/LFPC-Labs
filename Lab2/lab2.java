// LAB 2. Tolici Constantin FAF-193

import java.util.*;

public class lab2 {

        /** TASK №7
     * AF = (Q, E, d, q0, F);
     * Q = {q0, q1, q2, q3};
     * E = {a,b}, F = {q3}
     * d(q0,a)=q1,
     * d(q1,b)=q2,
     * d(q2,b)=q3,
     * d(q3,a)=q1,
     * d(q2,b)=q2,
     * d(q1,a)=q1.
     */

    static List<String> letters = List.of("a", "b");
    static Map<String, Map<String, String>> rules = new HashMap<>();

    public static void main(String[] args) {
        rules.put("q0", Map.of("a", "q1"));
        rules.put("q1", Map.of("b", "q2", "a", "q1"));
        rules.put("q2", Map.of("b", "q2 q3"));
        rules.put("q3", Map.of("a", "q1"));

        CreateDFA();
    }

    public static void CreateDFA() {
        var dfa = new HashMap<>(rules);

        for (var stR : rules.entrySet()) {
            for (var rule : stR.getValue().entrySet()) {
                if (!dfa.containsKey(rule.getValue())) {
                    String newState = rule.getValue();
                    Map<String, String> newRules = new HashMap<>();
                    for (String word : letters) {
                        StringBuilder endState = new StringBuilder();
                        for (String state : newState.split(" ")) {
                            var s = rules.get(state).get(word);
                            if (s != null) endState.append(" ").append(s);
                        }

                        var strState = endState.toString().trim();
                        if (!strState.isEmpty() && !strState.isBlank())
                            newRules.put(word, strState);
                    }
                    
                    dfa.put(newState, newRules);
                }
            }
        }

        for (var stR : dfa.entrySet()) {
            for (var rule : stR.getValue().entrySet()) {
                System.out.printf("([%s], %s) -> %s\n", stR.getKey(), rule.getKey(), rule.getValue());
            }
        }
    }
}