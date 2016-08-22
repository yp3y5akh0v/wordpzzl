package com.yp3y5akh0v.games.wordpzzl;

import com.yp3y5akh0v.games.wordpzzl.actor.ActorState;
import com.yp3y5akh0v.games.wordpzzl.actor.Letter;

import java.util.Stack;

public class GameLogic {

    private Stack<Letter> sequence = new Stack<>();
    private int[] letterScore = {
            1,  // a
            3,  // b
            3,  // c
            2,  // d
            1,  // e
            4,  // f
            2,  // g
            4,  // h
            1,  // i
            8,  // j
            5,  // k
            1,  // l
            3,  // m
            1,  // n
            1,  // o
            3,  // p
            10, // q
            1,  // r
            1,  // s
            1,  // t
            1,  // u
            4,  // v
            4,  // w
            8,  // x
            4,  // y
            10  // z
    };

    private Stats stats = new Stats();

    private static final GameLogic instance = new GameLogic();

    private GameLogic() {
    }

    public static GameLogic getInstance() {
        return instance;
    }

    public void pushToSequence(Letter letter) {
        if (sequence.contains(letter))
            rollBackSequenceTo(letter);
        else {
            if (!isNeighborForSequence(letter))
                clearSequence();
            for (Letter anotherLetter : sequence)
                anotherLetter.setState(ActorState.GOLD);
            letter.setState(ActorState.ORANGE);
            sequence.push(letter);
            ResourceManager.getInstance().getSelectSound().play();
            stats.setMoves(stats.getMoves() + 1);
        }
        if (sequence.size() >= 3 && checkSequenceForWord()) {
            for (Letter anotherLetter : sequence)
                anotherLetter.setState(ActorState.GREEN_LIGHT);
            sequence.peek().setState(ActorState.GREEN_DARK);
        }
    }

    public void clearSequence() {
        for (Letter letter : sequence)
            letter.setState(ActorState.GREY);
        sequence.clear();
    }

    public String getSequenceAsString() {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : sequence)
            sb.append(letter.getCharacter());
        return sb.toString();
    }

    public Stats getStats() {
        return stats;
    }

    public boolean checkSequenceForWord() {
        return ResourceManager
                .getInstance()
                .getWords()
                .contains(getSequenceAsString());
    }

    public void rollBackSequenceTo(Letter letter) {
        while (!sequence.isEmpty() && sequence.peek() != letter) {
            sequence.pop().setState(ActorState.GREY);
            ResourceManager.getInstance().getSelectSound().play();
        }
        letter.setState(ActorState.ORANGE);
        for (Letter anotherLetter : sequence)
            if (anotherLetter != letter)
                anotherLetter.setState(ActorState.GOLD);
            else
                break;
    }

    public boolean isNeighborForSequence(Letter letter) {
        return sequence.isEmpty() ||
                (Math.abs(letter.getX() - sequence.peek().getX()) <= 64 &&
                        Math.abs(letter.getY() - sequence.peek().getY()) <= 64 &&
                        letter != sequence.peek());
    }

    public Stack<Letter> getSequence() {
        return sequence;
    }

    public int sizeOfSequence() {
        return sequence.size();
    }

    public Letter peekFromSequence() {
        return sequence.peek();
    }

    public void calculateScore() {
        int res = 0;
        for (int i = 0; i < sequence.size(); i++)
            res += letterScore[sequence.get(i).getValue()];
        stats.setScore(res + stats.getScore());
        String oldWord = stats.getBestWord(), newWord = getSequenceAsString();
        if (oldWord.length() <= newWord.length())
            stats.setBestWord(newWord);
    }

}
