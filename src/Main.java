import fileio.File;

import java.util.Map;

public class Main {
    public static void main(String[] args) {

        File file = new File();
        String content = file.readFile("res/chiffre.txt");
        System.out.println(content);

        String key = "00111010100101001101011000111111";
        int[] positions = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15};
        String[] Sboxval = { "E", "4", "D", "1", 
                             "2", "F", "B", "8", 
                             "3", "A", "6", "C", 
                             "5", "9", "0", "7" };

        SPNAlgorithm snp = new SPNAlgorithm("0001001100110100010101110111100110011011101111001101111111110001", Sboxval, positions);
        
        String result = snp.decode(content);
        System.out.println(result.length() % 8);
        
    }
}
