import java.io.Serializable;

class MorraInfo implements Serializable {

    String testString;

    void setTestString(String s) {
        testString = s;
    }

    String getTestString() {
        return testString;
    }

    int p1Points;
    int p2Points;
    String p1Plays;
    String p2Plays;
    boolean have2players;
    int p1Guess;
    int p2Guess;
    
}