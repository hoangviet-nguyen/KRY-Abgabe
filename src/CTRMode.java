public class CTRMode {
    
    private String[] Sboxval = { "E", "4", "D", "1", 
                                 "2", "F", "B", "8", 
                                 "3", "A", "6", "C", 
                                 "5", "9", "0", "7" };

    private String key = "00111010100101001101011000111111";

    private int[] positions = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15};

    private SPNAlgorithm algorithm;

    public CTRMode() {
        algorithm = new SPNAlgorithm(key, Sboxval, positions, 4, 4, 4);
    }

    public String encryptDecrypt(String message) {
        String result = "";

        String rndBit = message.substring(0, 16);
        int  y = Integer.parseInt(rndBit, 2);
        System.out.println(y);
        String toDecodString = message.substring(16, message.length());

        for (int offset = 0; offset < (toDecodString.length() / 16) ; offset++) {
            String keystreamBlock = String.format("%16s",Integer.toBinaryString(y)).replace(' ', '0');
            keystreamBlock = algorithm.encode(keystreamBlock);
            String msg = toDecodString.substring(offset * 16, Math.min((offset + 1) * 16, message.length()));
            result += XOR(msg, keystreamBlock);
            y = (y + 1) % (1 << 16);
        }

        result = rndBit + result;
        return result;
    }

    private String XOR(String bitstring, String key) {
        int btemp = Integer.parseInt(bitstring, 2);
        int ktemp = Integer.parseInt(key, 2);
        String result = Integer.toBinaryString(ktemp ^ btemp);

        result = String.format("%16s", result).replace(' ', '0');
        return result;
    }

}
