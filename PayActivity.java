package mobilepaymentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayActivity extends AppCompatActivity {

    private Spinner paymentMethodSpinner;
    private Button payButton;

    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        userPreferences = new UserPreferences(this);

        // Initialize the payment method spinner
        paymentMethodSpinner = findViewById(R.id.payment_method_spinner);
        populatePaymentMethodSpinner();

        // Initialize the pay resetButton and set its click listener
        payButton = findViewById(R.id.send_button);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPaymentMade(); // Process the payment
                startActivity(new Intent(PayActivity.this, TransactionHistoryActivity.class));
            }
        });
    }

    private void onPaymentMade() {
        String paymentMethod = paymentMethodSpinner.getSelectedItem().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(new Date());

        // Get the amount from the amount input field
        EditText amountInput = findViewById(R.id.amount_input);
        String amountString = amountInput.getText().toString();

        double amount = 0.0;
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        amount = -amount;

        TransactionHistoryDatabaseHelper dbHelper = new TransactionHistoryDatabaseHelper(this);
        dbHelper.addTransaction(new Transaction(String.valueOf(amount), paymentMethod, currentDate), userPreferences.getCurrentUser().getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Adds items to the action bar
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == R.id.settings) {
            Intent homeIntent = new Intent(this, SettingsActivity.class);
            startActivity(homeIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populatePaymentMethodSpinner() {
        PaymentOptionsDatabaseHelper db = new PaymentOptionsDatabaseHelper(this);
        ArrayList<CreditCard> cards = db.getAllCardsForUser(userPreferences.getCurrentUser().getId());
        ArrayList<BankAccount> bankAccounts = db.getAllBankAccountsForUser(userPreferences.getCurrentUser().getId());
        List<String> paymentOptions = new ArrayList<>();

        for (CreditCard card : cards) {
            paymentOptions.add("Credit/Debit Card: " + card.getCardNumber());
        }

        for (BankAccount account : bankAccounts) {
            paymentOptions.add("Bank Account: " + account.getAccountNumber());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(adapter);
    }
}
