package com.yp3y5akh0v.games.wordpzzl;

public class Stats {

    private String bestWord;
    private int moves;
    private int score;

    public Stats() {
        bestWord = "";
        moves = 0;
        score = 0;
    }

    public String getBestWord() {
        return bestWord;
    }

    public void setBestWord(String bestWord) {
        this.bestWord = bestWord;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
