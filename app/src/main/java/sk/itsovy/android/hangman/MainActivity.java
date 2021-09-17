package sk.itsovy.android.hangman;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageViewGallows);
        textView = findViewById(R.id.textViewGuessedWord);
        editText = findViewById(R.id.editTextLetter);

        String[] words = getResources().getStringArray(R.array.dictionary);

        game = new HangmanGame(words, new Random());

    }

    public void onImageClick(View view) {
        CharSequence text = editText.getText();
        // ak nebolo zadane pismeno na hadanie
        if (text == null || text.length() == 0) {
            Toast.makeText(this, R.string.toast_insert_a_letter, Toast.LENGTH_SHORT).show();
            return;
        }

        char letter = Character.toLowerCase(text.charAt(0));

        if (letter >= 'a' && letter <= 'z') {
            game.guess(letter);
        } else {
           // nie je to pismeno
            Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT).show();
        }
        editText.setText("");



    }
}