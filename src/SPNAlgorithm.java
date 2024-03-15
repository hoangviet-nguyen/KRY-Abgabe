import java.util.HashMap;
import java.util.Map;


public class SPNAlgorithm {

    private String key;
    private int r = 4;
    private int m = 4;
    private int n = 4;

    private String[] Sboxval;
    private int[] positions;

    private Map<String, String> SBox;
    private Map<String, String> invSBox = new HashMap<>();

    private int[] keys;
    private int[] permutatedKeys;
    private Map<Integer, Integer> permutation;

    public SPNAlgorithm(String key, String[] Sboxval, int[] positions) {
        this.key = key;
        this.positions = positions;
        this.Sboxval = Sboxval;
        initKeys();
        permutatedKeys = keys;
        initPermutation();
        initSBox();
        initInvBox();
    }


    private String[] splitCode(String bitString, int lenght) {
        assert bitString.length() % lenght == 0;
        String[] splitted = new String[bitString.length() / lenght];
        for(int i = 0; i < bitString.length() / lenght; i++) {
            splitted[i] = bitString.substring(i * lenght, (i+ 1) * lenght);
        } 
        return splitted;
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
            SBox.put(Integer.toBinaryString(i), v);
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

    private String convertSBox(String bString) {
        String[] splitted = splitCode(bString, 4);
        String result = "";
        
        for (String code : splitted) {
            result += SBox.get(code);
        }
        
        return result;
    }

    public String decode(String text) {
        int i = 1;
        permutatedKeys[0] = permutatedKeys[r];
        permutatedKeys[r] = permutatedKeys[0];

        while(i < r) {
            String permutaded = permutateString(Integer.toBinaryString(permutatedKeys[r-i]));
            permutatedKeys[i] = Integer.parseInt(permutaded);
            i++;
        }

        String result = algorithm(text, invSBox, permutatedKeys);

        i = result.length();
        while (result.charAt(i) != '0') {
            i--;
        }

        result = result.substring(0, i);
        return  result;
    }

    public String encode(String text) {

        String ascii = "";

        for (char character: text.toCharArray()) {
            ascii += Integer.toBinaryString(character);   
        }

        ascii += "1";

        while (ascii.length() % 16 != 0) {
            ascii += "0";
        }

        if (text.contains("0") || text.contains("1")) {
            ascii = text;
        }

        String result = algorithm(ascii, this.SBox, this.keys);
        return result;
    }

    private String algorithm(String text, Map<String, String> SBox, int[] keys) {
        assert text.length() % 16 == 0;
        String result = "";
        String currentCode = "";
        int currenVal = 0;

        //splitting the text int n even parts of lenght 16
        String[] splitted = splitCode(text, 16);

        for (int i = 0; i < splitted.length; i++) {
            currentCode = splitted[i];

            // initial whitestep
            currenVal = Integer.parseInt(currentCode, 2) ^ keys[0];
            currentCode = convertSBox(Integer.toBinaryString(currenVal));

            // regulär rounds with r-1 keys
            for (int j = 1; j < r - 1; j++) {
                currentCode = permutateString(currentCode);
                currenVal =   Integer.parseInt(currentCode, 2) ^ keys[j];
                currentCode = convertSBox(Integer.toBinaryString(currenVal));
            }

            // last round without bit permutation
            currenVal = Integer.parseInt(currentCode, 2) ^ keys[r];
            result += Integer.toBinaryString(currenVal);
        }

        return result;
    }


    private void initInvBox() {
        for(Map.Entry<String, String> entry: SBox.entrySet()) {
            invSBox.put(entry.getValue(), entry.getKey());
        }
    }

}   