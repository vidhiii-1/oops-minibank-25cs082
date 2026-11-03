package practical12;

import java.util.*;

public class WordFrequencyDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 12 - Task 1: Word Frequency Counter ===");
        String text = "java collections framework in java provides powerful collections data structures for java developers";
        String[] words = text.split("\\s+");

        Map<String, Integer> freqMap = new HashMap<>();
        for (String w : words) {
            freqMap.put(w, freqMap.getOrDefault(w, 0) + 1);
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(freqMap.entrySet());
        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        System.out.println("Words sorted by frequency (descending):");
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
