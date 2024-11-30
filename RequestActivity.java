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

public class RequestActivity extends AppCompatActivity {

    private Spinner requestMethodSpinner;

    private Button requestButton;

    private UserPreferences userPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        userPreferences = new UserPreferences(this);

        requestMethodSpinner = findViewById(R.id.request_method_spinner);
        populatePaymentMethodSpinner();

        requestButton = findViewById(R.id.request_button);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestMade(); // Process the payment
                startActivity(new Intent(RequestActivity.this, TransactionHistoryActivity.class));
            }
        });
    }

    private void onRequestMade() {
        String paymentMethod = requestMethodSpinner.getSelectedItem().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(new Date());
        // Get the amount from the amount input field

        EditText requestamountInput = findViewById(R.id.receive_amount_input);
        String requestamountString = requestamountInput.getText().toString();

        double amount = 0.0;
        try {
            amount = Double.parseDouble(requestamountString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

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
        List<String> requestOptions = new ArrayList<>();

        for (CreditCard card : cards) {
            requestOptions.add("Credit/Debit Card: " + card.getCardNumber());
        }

        for (BankAccount account : bankAccounts) {
            requestOptions.add("Bank Account: " + account.getAccountNumber());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, requestOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestMethodSpinner.setAdapter(adapter);
    }

}