package mobilepaymentapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBankInfoActivity extends AppCompatActivity {

    private Button bankButton;

    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bank_info);

        userPreferences = new UserPreferences(this);

        bankButton = findViewById(R.id.saveBankInfoButton);

        bankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText accountHolderNameEditText = findViewById(R.id.bankAccountHolderNameEditText);
                EditText accountNumberEditText = findViewById(R.id.bankAccountNumberEditText);
                EditText routingNumberEditText = findViewById(R.id.bankRoutingNumberEditText);

                String accountHolderName = accountHolderNameEditText.getText().toString();
                String accountNumber = accountNumberEditText.getText().toString();
                String routingNumber = routingNumberEditText.getText().toString();

                if (accountHolderName.isEmpty() || accountNumber.isEmpty() || routingNumber.isEmpty()) {
                    Toast.makeText(AddBankInfoActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                } else if (accountNumber.length() != 12) {
                    Toast.makeText(AddBankInfoActivity.this, "Enter a valid account number", Toast.LENGTH_SHORT).show();
                } else if (routingNumber.length() != 9) {
                    Toast.makeText(AddBankInfoActivity.this, "Enter a valid routing number", Toast.LENGTH_SHORT).show();
                } else if (isBankAccountAlreadyAdded(accountNumber)) {
                    Toast.makeText(AddBankInfoActivity.this, "This Bank account already exists", Toast.LENGTH_SHORT).show();
                } else {
                    BankAccount account = new BankAccount(userPreferences.getCurrentUser().getId(),accountHolderName, accountNumber, routingNumber);
                    PaymentOptionsDatabaseHelper db = new PaymentOptionsDatabaseHelper(AddBankInfoActivity.this);
                    db.addBankAccount(account, userPreferences.getCurrentUser().getId());
                    Toast.makeText(AddBankInfoActivity.this, "Bank account added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddBankInfoActivity.this, ExistingPaymentsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isBankAccountAlreadyAdded(String accountNumber) {
        PaymentOptionsDatabaseHelper db = new PaymentOptionsDatabaseHelper(this);
        User currentUser = userPreferences.getCurrentUser();

        if (currentUser == null) {
            // Handle case where no user is logged in
            return false;
        }

        int currentUserId = currentUser.getId();

        Cursor cursor = null;
        try {
            SQLiteDatabase readableDb = db.getReadableDatabase();
            String selectQuery = "SELECT COUNT(*) FROM " + PaymentOptionsDatabaseHelper.TABLE_BANK_ACCOUNTS
                    + " WHERE " + PaymentOptionsDatabaseHelper.KEY_ACCOUNT_NUMBER + " = ?"
                    + " AND " + PaymentOptionsDatabaseHelper.KEY_USER_ID + " = ?";
            cursor = readableDb.rawQuery(selectQuery, new String[]{accountNumber, String.valueOf(currentUserId)});

            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                cursor.close();
                return count > 0; // Account already exists for the current user
            } else {
                // Account not found for the current user
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Account addition failed
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }


}
