// "Create inner class 'MyInteger'" "true"
public class Test {
    public static void main() {
        int xxx = 3;
        Integer i = new <caret>MyInteger(xxx);
    }
}