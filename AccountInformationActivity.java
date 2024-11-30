package com.group4.mobilepaymentapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccountInformationActivity extends AppCompatActivity {

    private EditText cardHolderName, cardNumber;
    private Button loginButton, googlePayButton, phonePayButton, otherButton;
    private TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information); // Update with your XML file name

        // Initialize views
        cardHolderName = findViewById(R.id.cardHolderName);
        cardNumber = findViewById(R.id.cardNumber);
        loginButton = findViewById(R.id.loginButton);
        googlePayButton = findViewById(R.id.googlePayButton);
        phonePayButton = findViewById(R.id.phonePayButton);
        otherButton = findViewById(R.id.otherButton);
        signUpText = findViewById(R.id.signUpText);

        // Set onClickListeners for buttons
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        googlePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGooglePay();
            }
        });

        phonePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePhonePay();
            }
        });

        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOtherPayment();
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });
    }

    private void handleLogin() {
        String name = cardHolderName.getText().toString().trim();
        String cardNum = cardNumber.getText().toString().trim();

        if (name.isEmpty() || cardNum.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            // Add your login logic here
        }
    }

    private void handleGooglePay() {
        Toast.makeText(this, "Google Pay option selected", Toast.LENGTH_SHORT).show();
        // Add Google Pay logic here
    }

    private void handlePhonePay() {
        Toast.makeText(this, "Phone Pay option selected", Toast.LENGTH_SHORT).show();
        // Add Phone Pay logic here
    }

    private void handleOtherPayment() {
        Toast.makeText(this, "Other payment option selected", Toast.LENGTH_SHORT).show();
        // Add other payment logic here
    }

    private void handleSignUp() {
        Toast.makeText(this, "Sign up clicked", Toast.LENGTH_SHORT).show();
        // Add sign-up navigation logic here
    }
}