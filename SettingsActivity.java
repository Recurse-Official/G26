package mobilepaymentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private Button logOutButton;
    private Button PaymentPreferencesButton;

    private Button TransactionHistoryButton;
    private Button AccountInformationButton;
    private Button HelpandSupportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        logOutButton = findViewById(R.id.LogOutButton);

        PaymentPreferencesButton = findViewById(R.id.PaymentPreferencesButton);

        TransactionHistoryButton = findViewById(R.id.TransactionHistoryButton);

        AccountInformationButton = findViewById(R.id.AccountInformationButton);

        HelpandSupportButton = findViewById(R.id.HelpandSupportButton);


        logOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        });

        PaymentPreferencesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PaymentPreferencesActivity.class);
                startActivity(intent);
            }

        });

        TransactionHistoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, TransactionHistoryActivity.class);
                startActivity(intent);
            }

        });

        AccountInformationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AccountInformationActivity.class);
                startActivity(intent);
            }

        });

        HelpandSupportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HelpandSupportActivity.class);
                startActivity(intent);
            }

        });
    }

}
