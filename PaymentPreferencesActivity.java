package mobilepaymentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentPreferencesActivity extends AppCompatActivity {

    private Button addPaymentMethod;
    private Button removePaymentMethod;
    private Button viewPaymentMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_preferences);

        addPaymentMethod = findViewById(R.id.addPaymentMethod);
        removePaymentMethod = findViewById(R.id.deletePaymentMethod);
        viewPaymentMethods = findViewById(R.id.viewPaymentMethods);

        addPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentPreferencesActivity.this, AddPaymentActivity.class);
                startActivity(intent);
            }
        });

        removePaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentPreferencesActivity.this, ExistingPaymentsActivity.class);
                startActivity(intent);
            }
        });

        viewPaymentMethods.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentPreferencesActivity.this, ExistingPaymentsActivity.class);
                startActivity(intent);
            }
        });
    }
}
