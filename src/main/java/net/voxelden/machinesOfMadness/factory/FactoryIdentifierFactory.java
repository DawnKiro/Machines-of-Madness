package net.voxelden.machinesOfMadness.factory;

import java.util.List;
import java.util.Random;

public class FactoryIdentifierFactory {
    private static final Random RANDOM = new Random();

    private static final List<String> WORDS = List.of(
            "quick", "lazy", "sleepy", "happy", "sad", "brave", "bright", "calm", "clever", "proud", "fox", "dog", "cat", "bear", "bird", "tree", "mountain", "river", "cloud", "sun", "jumps", "runs", "flies", "swims", "climbs", "sits", "dreams", "eats", "reads", "laughs"
    );

    public static String create() {
        String[] words = new String[4];
        words[0] = RANDOM.nextInt() + "-";
        for (int i = 1; i < words.length; i++) {
            words[i] = WORDS.get(RANDOM.nextInt(WORDS.size() - 1));
        }
        return String.join("-", words);
    }
}
