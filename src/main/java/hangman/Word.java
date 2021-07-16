package hangman;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Word {
    final private List<Letter> word;

    public Word(final String word){
        List<Letter> letterWord = new ArrayList<>();
        for(char character : word.toCharArray()) {
            Letter letter = new Letter(character);
            letterWord.add(letter);
        }
        this.word = letterWord;
    }

    public void guessLetter(final Letter guessLetter, ResultListener listener){
        boolean letterFound = false;
        for(Letter letterInWord : word) {
            if(letterInWord.evaluate(guessLetter)){
                letterFound = true;
            }
        }
        listener.resultEvaluation(letterFound);
    }

    private boolean checkAllLettersVisable(){
        for(Letter letter : word){
            if(!letter.isVisable()){
                return false;
            }
        }
        return true;
    }

    public boolean Complete(){
        return checkAllLettersVisable();
    }

    public void printWord(final PrintStream out) {
        for(Letter letter : word) {
            letter.print(out);
        }
    }
}
