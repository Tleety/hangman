package hangman;

import java.io.PrintStream;

public class Letter {
    final char letter;
    private boolean visable;

    public Letter(char letter) {
        this(letter, false);
    }

    public Letter(char letter, boolean visable) {
        this.letter = letter;
        this.visable = visable;
    }

    public boolean evaluate(Letter letter) {
        if(this.equals(letter)) {
            if(!visable) {
                visable = true;
                return true;
            } else {
                return false;
            }
        }
        return false;
        
    }

    public boolean isVisable(){
        return visable;
    }

    public void print(final PrintStream out){
        if(visable) {
            out.append(letter);
        } else {
            out.append('?');
        }
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if(o.getClass() == Character.class){
            return letter == (char) o;
        } else {
            Letter letter1 = (Letter) o;
            return letter == letter1.letter;
        }
    }
}
