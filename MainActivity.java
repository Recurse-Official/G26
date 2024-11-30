package mobilepaymentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class    MainActivity extends AppCompatActivity {

    private Button PayButtonMainPage;
    private TextView welcomeTextView;
    private Button receiveButton;
    private ImageButton settingsButton;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPreferences = new UserPreferences(this);

        welcomeTextView = findViewById(R.id.HompageText);
        PayButtonMainPage = findViewById(R.id.PayButtonMainPage);
        receiveButton = findViewById(R.id.RequestButtonMainPage);
        settingsButton = findViewById(R.id.settingsButton);

        // Retrieve user info
        User currentUser = userPreferences.getCurrentUser();
        if (currentUser != null) {
            // Display welcome message with user's name
            welcomeTextView.setText("Welcome, " + currentUser.getName() + "!");
        } else {
            welcomeTextView.setText("Welcome!");
        }

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        receiveButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RequestActivity.class);
            startActivity(intent);
        });

        PayButtonMainPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PayActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent homeIntent = new Intent(this, SettingsActivity.class);
            startActivity(homeIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
