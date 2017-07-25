package maintest;

public class MainTest {

    public static void main(String[] args) {
        String str = "  dfdsfd dfsd  ";
        System.out.println(str.trim());
        byte[] bytes = str.getBytes();
        for (byte b : bytes) {
            System.out.println(b);
        }
    }

}
