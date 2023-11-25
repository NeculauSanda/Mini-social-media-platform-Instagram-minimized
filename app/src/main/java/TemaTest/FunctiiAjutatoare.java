package TemaTest;

public class FunctiiAjutatoare {
    public FunctiiAjutatoare() {
    }

    public static String extragerePN(String rowstring, int lungime) {
        String s = rowstring.replaceAll("'", "");
        return  s.substring(lungime);
    }
}
