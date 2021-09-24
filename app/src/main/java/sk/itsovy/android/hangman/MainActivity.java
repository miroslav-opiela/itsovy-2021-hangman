package sk.itsovy.android.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private EditText editText;

    private Game game;

    private final int[] gallowsIds = {
            R.drawable.gallows0,
            R.drawable.gallows1,
            R.drawable.gallows2,
            R.drawable.gallows3,
            R.drawable.gallows4,
            R.drawable.gallows5,
            R.drawable.gallows6
    };

    private static final ColorFilter WON_GAME = new LightingColorFilter(Color.GREEN, Color.BLACK);
    private static final ColorFilter LOST_GAME = new LightingColorFilter(Color.RED, Color.BLACK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageViewGallows);
        textView = findViewById(R.id.textViewGuessedWord);
        editText = findViewById(R.id.editTextLetter);

        restartGame();

    }

    private void restartGame() {
        String[] words = getResources().getStringArray(R.array.dictionary);
        game = new HangmanGame(words, new Random());
        updateText();
        updateImage();
        imageView.setColorFilter(null);
    }

    public void onImageClick(View view) {
        // je vyhra alebo prehra
        if (game.getAttemptsLeft() == 0 || game.isWon()) {
            restartGame();
            return;
        }

        CharSequence text = editText.getText();
        // ak nebolo zadane pismeno na hadanie
        if (text == null || text.length() == 0) {
            Toast.makeText(this, R.string.toast_insert_a_letter, Toast.LENGTH_SHORT).show();
            return;
        }

        char letter = Character.toLowerCase(text.charAt(0));

        if (letter >= 'a' && letter <= 'z') {
            boolean success = game.guess(letter);
            if (success) {
                updateText();
                // ak sme uhadli a vyhrali hru
                if (game.isWon()){
                    imageView.setColorFilter(WON_GAME);
                }
            } else {
                updateImage();
                // ak sme neuhadli a prehrali hru
                if (game.getAttemptsLeft() == 0) {
                    imageView.setColorFilter(LOST_GAME);
                    textView.setText(game.getChallengeWord());
                }
            }
        } else {
           // nie je to pismeno
            Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT).show();
        }
        editText.setText("");



    }

    // aktualizuje stav widgetu zobrazujuceho slovo
    private void updateText() {
        textView.setText(game.getGuessedCharacters());
    }

    private void updateImage() {
        int index = Game.DEFAULT_ATTEMPTS_LEFT - game.getAttemptsLeft();
        imageView.setImageResource(gallowsIds[index]);
    }
}