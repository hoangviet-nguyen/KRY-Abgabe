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
    private Map<Integer, String> invSBox = new HashMap<>();

    private static int[] keys;
    private int[] permutatedKeys = keys;
    private Map<Integer, Integer> permutation;

    public SPNAlgorithm(String key, String[] Sboxval, int[] positions) {
        this.key = key;
        this.positions = positions;
        this.Sboxval = Sboxval;
        initKeys();
        initPermutation();
        initSBox();
        initInvBox();
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
        int i = 0;
        while(i<=r) {
            if (i == 0) {
                permutatedKeys[i] = keys[r];
            } else if (i == r) {
                permutatedKeys[i] = keys[0];
            } else {
                permutatedKeys[i] = Integer.parseInt(permutateString(String.valueOf(permutatedKeys[r-i])));
            }
            i++;
        }
        return algorithm(text, SBox, permutatedKeys);
    }

    public String decode(String text) {
        return null;
    }

    private String algorithm(String text, Map<Integer, String> SBox, int[] keys) {
        return null;
    }


    private void initInvBox() {
        for(Map.Entry<Integer, String> entry: SBox.entrySet()) {
            invSBox.put(Integer.parseInt(entry.getValue(), 2), Integer.toBinaryString(entry.getKey()));
        }
    }

}   