package mobilepaymentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SplashPageActivity extends AppCompatActivity {

    private Button SplashPageContinueButton;
    private Button resetButton;

    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        resetButton = findViewById(R.id.resetButton);

        UserPreferences userPreferences = new UserPreferences(this);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferences.resetUsers();
                Toast.makeText(SplashPageActivity.this, "User data reset", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.SplashPageContinueButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}