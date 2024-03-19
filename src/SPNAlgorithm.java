import java.util.HashMap;
import java.util.Map;


// This class implements the SPN (Substitution-Permutation Network) algorithm.
public class SPNAlgorithm {

    // Class variables
    private final String key; // The key used for encryption and decryption
    private final int r; // The number of rounds in the SPN
    private final int m; // The block size in bits
    private final int n; // The number of blocks

    private final String[] SBoxval; // The substitution box values
    private final int[] positions; // The positions for the permutation

    private Map<String, String> SBox; // The substitution box
    private final Map<String, String> invSBox = new HashMap<>(); // The inverse substitution box

    private int[] keys; // The keys for each round
    private final int[] permutedKeys; // The permuted keys
    private Map<Integer, Integer> permutation; // The permutation map

    // Constructor for the SPNAlgorithm class
    public SPNAlgorithm(String key, String[] SBoxval, int[] positions, int n, int m, int r) {
        this.key = key;
        this.positions = positions;
        this.SBoxval = SBoxval;
        this.m = m;
        this.n = n;
        this.r = r;
        permutedKeys = new int[r + 1];
        initKeys();
        initPermutation();
        initSBox();
        initInvBox();
        initPermuKeys();
    }

    // Method to initialize the permutation map
    private void initPermutation() {
        permutation = new HashMap<>();
        for(int i = 0; i < positions.length; i++) {
            permutation.put(i, positions[i]);
        }
    }

    // Method to initialize the substitution box
    private void initSBox() {
        SBox = new HashMap<>();
        int index = 0;
        for(String value: SBoxval) {
            int temp = Integer.parseInt(value, 16);
            String v = Integer.toBinaryString(temp);
            String indexValue = Integer.toBinaryString(index);
            indexValue = String.format("%4s", indexValue).replace(' ', '0');
            v = String.format("%4s", v).replace(' ', '0');
            SBox.put(indexValue, v);
            index++;
        }
    }

    // Method to initialize the keys
    private void initKeys() {
        keys = new int[r + 1];
        for(int i = 0; i < r + 1; i++) {
            String currentKey = key.substring(i * n, (i+m) * n);
            keys[i] = Integer.parseInt(currentKey, 2);
        }
    }

    // Method to initialize the permuted keys
    private void initPermuKeys() {
        int i = 1;
        permutedKeys[0] = keys[r];
        permutedKeys[r] = keys[0];

        while(i < r ) {
            int value = keys[r-i];
            String permute = Integer.toBinaryString(value);
            permute = permutateString(permute);
            permutedKeys[i] = Integer.parseInt(permute, 2);
            i++;
        }

    }

    // Method to initialize the inverse substitution box
    private void initInvBox() {
        for(Map.Entry<String, String> entry: SBox.entrySet()) {
            invSBox.put(entry.getValue(), entry.getKey());
        }
    }

    // Method to split a binary string into equal parts
    private String[] splitCode(String bitString, int length) {
        assert bitString.length() % length == 0;
        String[] splitted = new String[bitString.length() / length];
        for(int i = 0; i < bitString.length() / length; i++) {
            splitted[i] = bitString.substring(i * length, (i+ 1) * length);
        }
        return splitted;
    }

    // Method to permute a binary string
    private String permutateString(String bitString) {
        StringBuilder bitStringBuilder = new StringBuilder(bitString);
        while (bitStringBuilder.length() % (n * m) != 0) {
            bitStringBuilder.insert(0, "0");
        }
        bitString = bitStringBuilder.toString();

        StringBuilder result = new StringBuilder(bitString);
        for(int i = 0; i < bitString.length(); i++) {
            int position = permutation.get(i);
            result.setCharAt(position, bitString.charAt(i));
        }
        return result.toString();
    }

    // Method to convert a binary string using a substitution box
    private String convertSBox(String bString, Map<String, String> SBox) {

        StringBuilder bStringBuilder = new StringBuilder(bString);
        while(bStringBuilder.length() % (n * m) != 0) {
            bStringBuilder.insert(0, "0");
        }
        bString = bStringBuilder.toString();
        String[] splitted = splitCode(bString, 4);
        StringBuilder result = new StringBuilder();

        for (String code : splitted) {
            result.append(SBox.get(code));
        }
        return result.toString();
    }

    // Method to decode a text
    public String decode(String text) {
        if ((text.length() % (n * m)) != 0) {
            throw new IllegalArgumentException("The text must be a multiple of n * m");
        }
        return algorithm(text, invSBox, permutedKeys);
    }

    // Method to encode a text
    public String encode(String text) {
        if ((text.length() % (n * m)) != 0) {
            throw new IllegalArgumentException("The text must be a multiple of n * m");
        }
        return algorithm(text, SBox, keys);
    }

    // The main algorithm for encoding and decoding
    private String algorithm(String text, Map<String, String> SBox, int[] keys) {
        StringBuilder result = new StringBuilder();
        String currentCode;
        int currenVal;

        //splitting the text int n even parts of length n * m
        String[] splitted = splitCode(text, n * m);

        for (String s : splitted) {
            currentCode = s;

            // Regular rounds with r-1 keys
            for (int j = 0; j < r; j++) {
                currenVal = Integer.parseInt(currentCode, 2) ^ keys[j];
                currentCode = Integer.toBinaryString(currenVal);
                currentCode = convertSBox(currentCode, SBox);
                currentCode = j < r - 1 ? permutateString(currentCode) : currentCode;
            }

            // Last round without bit permutation
            currenVal = Integer.parseInt(currentCode, 2) ^ keys[r];
            currentCode = Integer.toBinaryString(currenVal);
            result.append(String.format("%" + (n * m) + "s", currentCode).replace(' ', '0'));
        }
        return result.toString();
    }
}