package mobilepaymentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddPaymentActivity extends AppCompatActivity {

    private Button addCreditButton;
    private Button addDebitButton;
    private Button addBankAccountButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        Button addCreditButton = findViewById(R.id.addCreditButton);
        Button addDebitButton = findViewById(R.id.addDebitButton);
        Button addBankAccountButton = findViewById(R.id.addBankAccountButton);


        addCreditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPaymentActivity.this, AddCreditInfoActivity.class);
                startActivity(intent);
            }
        });

        addDebitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPaymentActivity.this, AddDebitInfoActivity.class);
                startActivity(intent);
            }
        });

        addBankAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPaymentActivity.this, AddBankInfoActivity.class);
                startActivity(intent);
            }
        });


    }
}