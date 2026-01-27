package fpoly.junit;

public class JUnitMessage {
    private String message;

    public JUnitMessage(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
//        int a = 1 / 0;
    }

    public String printHiMessage() {
        return "Hi!" + message;
    }
}