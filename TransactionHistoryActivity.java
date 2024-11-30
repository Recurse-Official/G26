package mobilepaymentapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        userPreferences = new UserPreferences(this);

        // Fetch the current user
        User currentUser = userPreferences.getCurrentUser();
        int userId = -1;  // Default value = no user found
        if (currentUser != null) {
            userId = currentUser.getId();
        }

        RecyclerView recyclerView = findViewById(R.id.transactionRecyclerView);
        TransactionHistoryDatabaseHelper dbHelper = new TransactionHistoryDatabaseHelper(this);

        List<Transaction> transactions;
        if (userId != -1) {
            transactions = dbHelper.getAllTransactionsForUser(userId);
        } else {
            transactions = new ArrayList<>(); // Handle accordingly if no user is found
        }

        TransactionHistoryRecyclerAdapter adapter = new TransactionHistoryRecyclerAdapter(transactions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
