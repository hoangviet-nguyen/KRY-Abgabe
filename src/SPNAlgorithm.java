import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class SPNAlgorithm {

    private String key;
    private int r;
    private int m;
    private int n;

    private String[] Sboxval;
    private int[] positions;

    private Map<String, String> SBox;
    private Map<String, String> invSBox = new HashMap<>();

    private int[] keys;
    private int[] permutatedKeys;
    private Map<Integer, Integer> permutation;

    public SPNAlgorithm(String key, String[] Sboxval, int[] positions, int n, int m, int r) {
        this.key = key;
        this.positions = positions;
        this.Sboxval = Sboxval;
        this.m = m;
        this.n = n;
        this.r = r;
        permutatedKeys = new int[r + 1];
        initKeys();
        initPermutation();
        initSBox();
        initInvBox();
        initPermuKeys();
    }

    private void initPermutation() {
        permutation = new HashMap<>();
        for(int i = 0; i < positions.length; i++) {
            permutation.put(i, positions[i]);
        }
    }

    private void initSBox() {
        SBox = new HashMap<>();
        int index = 0;
        for(String value: Sboxval) {
            int temp = Integer.parseInt(value, 16);
            String v = Integer.toBinaryString(temp);
            String key = Integer.toBinaryString(index);
            key = String.format("%4s", key).replace(' ', '0');
            v = String.format("%4s", v).replace(' ', '0');
            SBox.put(key, v);
            index++;
        }
    }

    private void initKeys() {
        keys = new int[r + 1];
        for(int i = 0; i < r + 1; i++) {
            String currentKey = key.substring(i * n, (i+m) * n);
            keys[i] = Integer.parseInt(currentKey, 2);
        }
    }

    private void initPermuKeys() {
        int i = 1;
        permutatedKeys[0] = keys[r];
        permutatedKeys[r] = keys[0];

        while(i < r ) {
            int key = keys[r-i];
            String permute = Integer.toBinaryString(key);
            permute = permutateString(permute);
            permutatedKeys[i] = Integer.parseInt(permute, 2);
            i++;
        }

    }

    private void initInvBox() {
        for(Map.Entry<String, String> entry: SBox.entrySet()) {
            invSBox.put(entry.getValue(), entry.getKey());
        }
    }

    private String[] splitCode(String bitString, int lenght) {
        assert bitString.length() % lenght == 0;
        String[] splitted = new String[bitString.length() / lenght];
        for(int i = 0; i < bitString.length() / lenght; i++) {
            splitted[i] = bitString.substring(i * lenght, (i+ 1) * lenght);
        } 
        return splitted;
    }

    private String permutateString(String bitString) {
        while (bitString.length() % (n * m) != 0) {
            bitString = "0" + bitString; 
        }

        StringBuffer result = new StringBuffer(bitString);
        for(int i = 0; i < bitString.length(); i++) {
            int position = permutation.get(i);
            result.setCharAt(position, bitString.charAt(i));
        }
        return result.toString();
    }

    private String convertSBox(String bString, Map<String, String> SBox) {

        while(bString.length() % (n * m) != 0) {
            bString = "0" + bString;
        }
        String[] splitted = splitCode(bString, 4);
        String result = "";

        for (String code : splitted) {
            result += SBox.get(code);
        }
        return result;
    }

    public String decode(String text) {
        assert text.length() % 16 == 0;
        return algorithm(text, invSBox, permutatedKeys);
    }

    public String encode(String text) {
        assert text.length() % 16 == 0;      
        return algorithm(text, SBox, keys);
    }

    private String algorithm(String text, Map<String, String> SBox, int[] keys) {
        String result = "";
        String currentCode = "";
        int currenVal = 0;

        //splitting the text int n even parts of lenght n * m
        String[] splitted = splitCode(text, n * m);

        for (int i = 0; i < splitted.length; i++) {
            currentCode = splitted[i];
            
            // regulär rounds with r-1 keys
            for (int j = 0; j < r  ;j++) {
                currenVal =   Integer.parseInt(currentCode, 2) ^ keys[j];
                currentCode = Integer.toBinaryString(currenVal);
                currentCode = convertSBox(currentCode, SBox);
                currentCode = j < r-1 ? permutateString(currentCode): currentCode;
            }

            // last round without bit permutation
            currenVal = Integer.parseInt(currentCode, 2) ^ keys[r];
            currentCode = Integer.toBinaryString(currenVal);
            result += String.format("%" + (n*m) + "s", currentCode).replace(' ', '0');
        }

        return result;
    }

}   