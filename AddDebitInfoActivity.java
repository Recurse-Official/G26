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

public class AddDebitInfoActivity extends AppCompatActivity {

    private Button debitButton;
    private EditText debitCardNumberEditText, debitCardHolderNameEditText, debitExpirationDateEditText, debitCVVEditText;

    private UserPreferences userPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_debit_info);

        userPreferences = new UserPreferences(this);

        PaymentOptionsDatabaseHelper db = new PaymentOptionsDatabaseHelper(this);

        debitButton = findViewById(R.id.saveDebitInfoButton);
        debitCardNumberEditText = findViewById(R.id.debitCardNumberEditText);
        debitCardHolderNameEditText = findViewById(R.id.debitCardHolderNameEditText);
        debitExpirationDateEditText = findViewById(R.id.debitExpirationDateEditText);
        debitCVVEditText = findViewById(R.id.debitCVVEditText);

        debitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber = debitCardNumberEditText.getText().toString();
                String cardName = debitCardHolderNameEditText.getText().toString();
                String expiryDate = debitExpirationDateEditText.getText().toString();
                String cvv = debitCVVEditText.getText().toString();

                if (cardNumber.isEmpty() || cardName.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                    Toast.makeText(AddDebitInfoActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                } else if (cardNumber.length() != 16) {
                    Toast.makeText(AddDebitInfoActivity.this, "Enter a valid card number", Toast.LENGTH_SHORT).show();
                } else if (isDebitCardAlreadyAdded(cardNumber)) {
                    Toast.makeText(AddDebitInfoActivity.this, "This card already exists", Toast.LENGTH_SHORT).show();
                } else {
                    CreditCard creditCard = new CreditCard(userPreferences.getCurrentUser().getId(), cardName, cardNumber, expiryDate, cvv);
                    db.addCard(creditCard, userPreferences.getCurrentUser().getId());
                    Toast.makeText(AddDebitInfoActivity.this, "Debit card added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddDebitInfoActivity.this, ExistingPaymentsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isDebitCardAlreadyAdded(String cardNumber) {
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
            String selectQuery = "SELECT COUNT(*) FROM " + PaymentOptionsDatabaseHelper.TABLE_CARDS
                    + " WHERE " + PaymentOptionsDatabaseHelper.KEY_CARD_NUMBER + " = ?"
                    + " AND " + PaymentOptionsDatabaseHelper.KEY_USER_ID + " = ?";
            cursor = readableDb.rawQuery(selectQuery, new String[]{cardNumber, String.valueOf(currentUserId)});

            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                cursor.close();
                return count > 0; // Card already exists for the current user
            } else {
                // Card not found for the current user
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Card addition failed
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

}
