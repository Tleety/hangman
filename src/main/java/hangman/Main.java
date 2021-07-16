/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 */
package hangman;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Main implements ResultListener {

    private final InputStream input;
    private final PrintStream out;
    private final int maxMistakes;
    private int mistakes;

    private static final Word[] WORDS = {
        new Word("simplicity"), new Word("equality"), new Word("grandmother"),
        new Word("neighborhood"), new Word("relationship"), new Word("mathematics"),
        new Word("university"), new Word("explanation")
    };

    public Main(final InputStream in, final OutputStream out, final int m) {
        this.input = in;
        this.maxMistakes = m;
        this.out = new PrintStream(out);
    }

    public static void main(final String... args) {
        new Main(System.in, System.out, 5).exec();
    }
    
    public void exec() {
        Word word = WORDS[new Random().nextInt(WORDS.length)];
        final Iterator<String> scanner = new Scanner(this.input);
        play(scanner, word, out);
    }

    public void play(final Iterator<String> scanner, final Word word, final PrintStream out) {
        while (mistakes < this.maxMistakes && !word.Complete()) {
            out.print("Guess a letter: ");
            Letter playerInputCharacter = new Letter(scanner.next().charAt(0));

            word.guessLetter(playerInputCharacter, this);

            printWord(word);
        }
        gameOver(word.Complete(), out);
    }

    private void printGuessResult(final boolean inputCharacterCorrect, final PrintStream out){
        if (inputCharacterCorrect) {
            out.print("Hit!\n");
        } else {
            out.printf(
                "Missed, mistake #%d out of %d\n",
                mistakes, this.maxMistakes
            );
        }
    }

    private void printWord(Word word){
        out.append("The word: ");
        word.printWord(out);
        out.append("\n\n");
    }

    public void gameOver(boolean playerWon, final PrintStream out) {
        if (playerWon) {
            out.print("You won!\n");
        } else {
            out.print("You lost.\n");
        }
    }

    @Override
    public void resultEvaluation(boolean result) {
        if(!result) {
            mistakes++;
        }
        printGuessResult(result, out);
    }
}
