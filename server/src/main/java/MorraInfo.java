import java.io.Serializable;

class MorraInfo implements Serializable {

    int playernumber = 0; // one time var

    MorraInfo(int play, int guess) {
        if (firstPlayer) {
            p1Plays = play;
            p1Guess = guess;
        } else {
            p2Plays = play;
            p2Guess = guess;
        }
        firstPlayer = false;
    }

    MorraInfo() {}

    // if first player is true -> fill in p1 data else p2
    public boolean firstPlayer = true;
    // if have2players is true -> evaluate it to see who won
    boolean have2players;
    // max 2 to win
    int p1Points;
    int p2Points;
    // what each player played
    int p1Plays;
    int p2Plays;
    // what each player guessed
    int p1Guess;
    int p2Guess;   

    int getp1play() { return p1Plays; }
    int getp1guess() { return p1Guess; }
    int getp2play() { return p2Plays; }
    int getp2guess() { return p2Guess; }
}
