import fileio.File;

// This is the main class where the program execution begins.
public class Main {
    public static void main(String[] args) {

        // Create a new File object
        File file = new File();
        // Read the content of the file "res/chiffre.txt"
        String content = file.readFile("res/chiffre.txt");
        // Create a new CTRMode object
        CTRMode algo = new CTRMode();
        // Initialize a StringBuilder with the binary representation of the number 4
        StringBuilder y = new StringBuilder(Integer.toBinaryString(4));

        // Prepend zeros to the binary string until its length is a multiple of 16
        while (y.length() % 16 != 0) {
            y.insert(0, "0");
        }

        System.out.println("The real message encryption");
        // Decrypt the content of the file
        String decodString = algo.encryptDecrypt(content, false);

        // Remove trailing zeros from the decrypted string
        decodString = shortMessage(decodString);
        // Convert the binary string to ASCII and print it
        System.out.println(bStringToAscii(decodString));

    }

    // Method to remove trailing zeros from a binary string
    public static String shortMessage(String message) {
        int index = message.length() -1;
        while (message.charAt(index) != '1') {
            index--;
        }

        return message.substring(0, index);
    }

    // Method to convert a binary string to an ASCII string
    public static String bStringToAscii(String biString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < biString.length(); i += 8) {
            String byteString = biString.substring(i, Math.min(i + 8, biString.length()));
            char character = (char) Integer.parseInt(byteString, 2);
            result.append(character);
        }
        return result.toString();
    }
}