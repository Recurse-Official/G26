package mobilepaymentapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExistingPaymentsActivity extends AppCompatActivity {

    private ArrayList<Object> items; // Combined list of CreditCards and BankAccounts
    private RecyclerView recyclerView;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_payment_methods);

        recyclerView = findViewById(R.id.recyclerCardView);
        userPreferences = new UserPreferences(this);
        PaymentOptionsDatabaseHelper db = new PaymentOptionsDatabaseHelper(this);

        User currentUser = userPreferences.getCurrentUser();
        if (currentUser != null) {
            int userId = currentUser.getId(); // Use the user's ID to fetch their payment options
            Log.d("ExistingPaymentsActivity", "Current User ID: " + userId);

            items = new ArrayList<>();
            items.addAll(db.getAllCardsForUser(userId)); // Fetch cards for current user
            Log.d("ExistingPaymentsActivity", "Cards for user: " + items.size());

            items.addAll(db.getAllBankAccountsForUser(userId)); // Fetch bank accounts for current user
            Log.d("ExistingPaymentsActivity", "Total payment methods for user: " + items.size());
        } else {
            items = new ArrayList<>(); // Empty list if no user is logged in
            Log.d("ExistingPaymentsActivity", "No user logged in");
        }

        setAdapter();
    }

    private void setAdapter() {
        PaymentOptionsRecyclerAdapter adapter = new PaymentOptionsRecyclerAdapter(items, userPreferences);
        adapter.setOnItemClickListener(new PaymentOptionsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showDeleteConfirmationDialog(position);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this Payment Method?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        performDeletion(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void performDeletion(int position) {
        PaymentOptionsDatabaseHelper db = new PaymentOptionsDatabaseHelper(this);
        Object item = items.get(position);

        if (item instanceof CreditCard) {
            CreditCard card = (CreditCard) item;
            db.deleteCard(card.getCardNumber(), userPreferences.getCurrentUser().getId());
            Log.d("ExistingPaymentsActivity", "Deleting CreditCard, User ID: " + card.getUserId());
        } else if (item instanceof BankAccount) {
            BankAccount account = (BankAccount) item;
            db.deleteBankAccount(account.getAccountNumber(), userPreferences.getCurrentUser().getId());
            Log.d("ExistingPaymentsActivity", "Deleting BankAccount, User ID: " + account.getUserId());
        }

        items.remove(position);
        recyclerView.getAdapter().notifyItemRemoved(position);
        recyclerView.getAdapter().notifyItemRangeChanged(position, items.size());
    }
}
