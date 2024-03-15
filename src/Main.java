import fileio.File;

public class Main {
    public static void main(String[] args) {

        File file = new File();
        String content = file.readFile("res/chiffre.txt");
        System.out.println(content);
    }
}
