package game;

public class PlayerScore implements Comparable<PlayerScore>{
    private int score;
    private String name;

    public PlayerScore(int score, String name){
        this.score = score;
        this.name = name;
    }

    @Override
    public int compareTo(PlayerScore p) {
        return -Integer.compare(this.score, p.score);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
