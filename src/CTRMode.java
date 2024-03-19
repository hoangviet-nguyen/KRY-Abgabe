// This class implements the Counter (CTR) mode of operation for block ciphers.
public class CTRMode {

    // An instance of the SPNAlgorithm class
    private final SPNAlgorithm algorithm;

    // Constructor for the CTRMode class
    public CTRMode() {
        // Define the substitution box values and the key
        String[] SBoxval = {"E", "4", "D", "1", "2", "F", "B", "8", "3", "A", "6", "C", "5", "9", "0", "7"};
        String key = "00111010100101001101011000111111";
        int[] positions = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15};

        // Initialize the SPNAlgorithm with the defined values
        algorithm = new SPNAlgorithm(key, SBoxval, positions, 4, 4, 4);
    }

    // Method to encrypt or decrypt a message
    public String encryptDecrypt(String message, boolean encrypt) {
        StringBuilder result = new StringBuilder();

        // Split the message into the random bit and the rest of the message
        String rndBit = message.substring(0, 16);
        int  y = Integer.parseInt(rndBit, 2);
        String toDecodString = message.substring(16);

        // For each block of the message
        for (int offset = 0; offset < (toDecodString.length() / 16) ; offset++) {
            // Generate the keystream block
            String keystreamBlock = String.format("%16s",Integer.toBinaryString(y)).replace(' ', '0');
            keystreamBlock = algorithm.encode(keystreamBlock);

            // Get the current block of the message
            String msg = toDecodString.substring(offset * 16, Math.min((offset + 1) * 16, message.length()));

            // XOR the message block with the keystream block and append the result
            result.append(XOR(msg, keystreamBlock));

            // Increment y modulo 2^16
            y = (y + 1) % (1 << 16);
        }

        // Return the result, prepending the random bit if encrypting
        return encrypt ? rndBit + result : result.toString();
    }

    // Method to XOR a bitstring with a key
    private String XOR(String bitstring, String key) {
        // Convert the bitstring and the key to integers
        int btemp = Integer.parseInt(bitstring, 2);
        int ktemp = Integer.parseInt(key, 2);

        // XOR the bitstring and the key, and format the result as a 16-bit string
        String result = Integer.toBinaryString(ktemp ^ btemp);
        result = String.format("%16s", result).replace(' ', '0');

        // Return the result
        return result;
    }
}