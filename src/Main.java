import fileio.File;

public class Main {
    public static void main(String[] args) {

        String[] Sboxval = { "E", "4", "D", "1", 
        "2", "F", "B", "8", 
        "3", "A", "6", "C", 
        "5", "9", "0", "7" };

        int[] positions = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15};

        String key = "00010001001010001000110000000000";

        File file = new File();
        String content = file.readFile("res/chiffre.txt");
        CTRMode algo = new CTRMode();
        String y = Integer.toBinaryString(4);

        while (y.length() % 16 != 0) {
            y = "0" + y;
        }

        String hello = "Hello World";
        hello = asciiToBItString(hello);
        hello = y + hello;
        hello += "1";
        while (hello.length() % 16 != 0) {
            hello += "0";
        }
        
        SPNAlgorithm spn = new SPNAlgorithm(key, Sboxval, positions, 4, 4, 4);

        String x = "0001001010001111";
        String yt = "1010111010110100";

        System.out.println("Testing the SPN Alg");
        String en = spn.encode(x);
        String de = spn.decode(en);

        System.out.println("The value x and decrypt are equal: " + x.equals(de));
        System.out.println("The value y end encrypt are equal: " + yt.equals(en));
        System.out.println("----------------------------------------------------");

        System.out.println("Testing with Hello World");
        String hencryp = algo.encryptDecrypt(hello);
        String hdecrypt = algo.encryptDecrypt(hencryp);
        System.out.println(hello);
        System.out.println(hencryp);
        System.out.println(hdecrypt);
        System.out.println(bStringToAscii(hdecrypt));
        System.out.println("---------------------------");

        System.out.println("The real message encryption");
        String decodString = algo.encryptDecrypt(content);
        System.out.println(decodString.length());
        System.out.println(bStringToAscii(decodString.substring(16, decodString.length())));

    }

    public static String asciiToBItString(String text) {
    
        StringBuffer result = new StringBuffer();

        for (char character : text.toCharArray()) {
            String bitString = Integer.toBinaryString(character);
            
            while (bitString.length() % 8 != 0) {
                bitString = "0" + bitString;
            }
            result.append(bitString);
        }


        return result.toString();
    }


    public static String bStringToAscii(String biString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < biString.length(); i += 8) {
            String byteString = biString.substring(i, Math.min(i + 8, biString.length()));
            char character = (char) Integer.parseInt(byteString, 2);
            result.append(character);
        }
        return result.toString();
    }

    private static String[] splitCode(String bitString, int lenght) {
        assert bitString.length() % lenght == 0;
        String[] splitted = new String[bitString.length() / lenght];
        for(int i = 0; i < bitString.length() / lenght; i++) {
            splitted[i] = bitString.substring(i * lenght, (i+ 1) * lenght);
        } 
        return splitted;
    }
}

