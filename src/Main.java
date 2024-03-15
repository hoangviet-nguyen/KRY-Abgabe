public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        File file = new File();
        String content = file.readFile("res/chiffre.txt");
        System.out.println(content);
    }
}
