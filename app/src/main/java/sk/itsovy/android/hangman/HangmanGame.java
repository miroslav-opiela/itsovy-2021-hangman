package sk.itsovy.android.hangman;

import java.util.Random;

public class HangmanGame implements Game {

    // slovo ktore chceme uhadnut, to sa nemeni
    private String word;

    // slovo ktore hadame - aktualny stav co vidi pouzivatel
    private StringBuilder uncoveredWord;

    private int attemptsLeft = DEFAULT_ATTEMPTS_LEFT;

    public HangmanGame(String[] words, Random random) {
        int index = random.nextInt(words.length);
        word = words[index];

        uncoveredWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            uncoveredWord.append(UNGUESSED_CHAR);
        }
    }

    @Override
    public boolean isWon() {
        // alternativne zistit ci uncoveredWord neobsahuje znak '_'
        // pozor - musim porovnavat string a string
        return word.equals(uncoveredWord.toString());
    }

    @Override
    public CharSequence getGuessedCharacters() {
        return uncoveredWord;
    }

    @Override
    public String getChallengeWord() {
        return word;
    }

    @Override
    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    @Override
    public boolean guess(char character) {
        boolean success = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == character) {
                // odhalim uhadnute pismeno
                uncoveredWord.setCharAt(i, character);
                success = true;
            }
        }
        if (!success) {
            attemptsLeft--;
        }
        return success;
    }
}
