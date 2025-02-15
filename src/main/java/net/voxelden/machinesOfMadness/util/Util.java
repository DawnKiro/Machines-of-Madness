package net.voxelden.machinesOfMadness.util;

import java.util.HashMap;
import java.util.Map;

public class Util {
    public static <A, B> HashMap<B, A> flipMap(HashMap<A, B> map) {
        HashMap<B, A> flipped = new HashMap<>();
        for (Map.Entry<A, B> entry : map.entrySet()) {
            flipped.put(entry.getValue(), entry.getKey());
        }
        return flipped;
    }
}
