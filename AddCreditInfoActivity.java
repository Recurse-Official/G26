package mobilepaymentapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCreditInfoActivity extends AppCompatActivity {
    private Button creditButton;
    private EditText cardNumberEditText, cardHolderNameEditText, expirationDateEditText, cvvEditText;

    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_credit_info);

        userPreferences = new UserPreferences(this);

        PaymentOptionsDatabaseHelper db = new PaymentOptionsDatabaseHelper(this);

        creditButton = findViewById(R.id.saveCreditInfoButton);
        cardNumberEditText = findViewById(R.id.creditCardNumberEditText);
        cardHolderNameEditText = findViewById(R.id.creditCardHolderNameEditText);
        expirationDateEditText = findViewById(R.id.creditExpirationDateEditText);
        cvvEditText = findViewById(R.id.creditCVVEditText);

        creditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber = cardNumberEditText.getText().toString();
                String cardName = cardHolderNameEditText.getText().toString();
                String expiryDate = expirationDateEditText.getText().toString();
                String cvv = cvvEditText.getText().toString();

                if (cardNumber.isEmpty() || cardName.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                    Toast.makeText(AddCreditInfoActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                } else if (cardNumber.length() != 16) {
                    Toast.makeText(AddCreditInfoActivity.this, "Enter a valid card number", Toast.LENGTH_SHORT).show();
                } else if (isCreditCardAlreadyAdded(cardNumber)) {
                    Toast.makeText(AddCreditInfoActivity.this, "This card already exists", Toast.LENGTH_SHORT).show();
                } else {
                    CreditCard creditCard = new CreditCard(userPreferences.getCurrentUser().getId(), cardName, cardNumber, expiryDate, cvv);
                    Log.d("AddCreditInfo", "Card created with name: " + creditCard.getName() + ", number: " + creditCard.getCardNumber());
                    db.addCard(creditCard, userPreferences.getCurrentUser().getId());
                    Toast.makeText(AddCreditInfoActivity.this, "Credit card added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCreditInfoActivity.this, ExistingPaymentsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isCreditCardAlreadyAdded(String cardNumber) {
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
