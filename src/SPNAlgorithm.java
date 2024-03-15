import java.util.HashMap;
import java.util.Map;


public class SPNAlgorithm {

    private String key;
    private int r = 4;
    private int m = 4;
    private int n = 4;

    private String[] Sboxval;
    private int[] positions;

    private Map<Integer, String> SBox;
    private static int[] keys;
    private Map<Integer, Integer> permutation;

    public SPNAlgorithm(String key, String[] Sboxval, int[] positions) {
        this.key = key;
        this.positions = positions;
        this.Sboxval = Sboxval;
        initKeys();
        initPermutation();
        initSBox();
    }

    private void initPermutation() {
        permutation = new HashMap<>();
        for(int i = 0; i < positions.length; i++) {
            permutation.put(i, positions[i]);
        }
    }

    private void initSBox() {
        SBox = new HashMap<>();
        int i = 0;
        for(String value: Sboxval) {
            int temp = Integer.parseInt(value, 16);
            String v = Integer.toBinaryString(temp);
            SBox.put(i, v);
            i++;
        }
    }

    private void initKeys() {
        keys = new int[r + 1];
        for(int i = 0; i < r + 1; i++) {
            String currentKey = key.substring(i *n, (i+m) * n);
            keys[i] = Integer.parseInt(currentKey, 2);
        }
    }

    private String permutateString(String bitString) {
        StringBuffer result = new StringBuffer(bitString);
        for(int i = 0; i < bitString.length(); i++){
            int position = permutation.get(i);
            result.setCharAt(position, bitString.charAt(i));
        }
        return result.toString();
    }

    public String encode(String text) {


        return null;
    }

    public String decode(String text) {
        return null;
    }

    private String algorithm(String text, Map<Integer, String> SBox, int[] keys) {
        return null;
    }

}   