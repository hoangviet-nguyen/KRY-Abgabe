/*
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SPNAlgorithmTest {

    @Test
    public void testEncode() {
        // Instanz der SPNAlgorithm Klasse erstellen
        String key = "00010001001010001000110000000000";
        String[] Sboxval = {"1110", "100", "1101", "1", "10", "1111", "1011", "1000", "11", "1010", "110", "1100", "101", "1001", "0", "111"};
        int[] positions = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15};
        SPNAlgorithm spn = new SPNAlgorithm(key, Sboxval, positions);

        // Text der verschlüsselt werden soll
        String text = "0001001010001111";

        // Text der erwartet wird
        String expectedEncodedText = "1010111010110100";

        // Text verschlüsseln
        String encodedText = spn.encode(text);
        assertEquals(expectedEncodedText, encodedText);
    }
}
*/
