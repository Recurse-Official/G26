package mobilepaymentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccountInformationActivity extends AppCompatActivity {

    private EditText changeAccountNameEditText, changeAccountEmailEditText, changePasswordEditText;
    private Button saveChangesButton;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        changeAccountNameEditText = findViewById(R.id.change_account_name);
        changeAccountEmailEditText = findViewById(R.id.change_account_email);
        changePasswordEditText = findViewById(R.id.change_password);
        userPreferences = new UserPreferences(this);

        loadExistingData();

        saveChangesButton = findViewById(R.id.save_changes_button);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = userPreferences.getCurrentUser();

                String newName = changeAccountNameEditText.getText().toString().trim();
                String newEmail = changeAccountEmailEditText.getText().toString().trim();
                String newPassword = changePasswordEditText.getText().toString().trim();

                // Clone the current user to update details
                User updatedUser = new User(newName.isEmpty() ? currentUser.getName() : newName,
                        newEmail.isEmpty() ? currentUser.getEmail() : newEmail,
                        newPassword.isEmpty() ? currentUser.getPassword() : newPassword);
                updatedUser.setId(currentUser.getId());

                // Attempt to save the updated user
                if (!userPreferences.saveUser(updatedUser)) {
                    Toast.makeText(AccountInformationActivity.this, "Email already in use!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newEmail.isEmpty() && !newEmail.equals(currentUser.getEmail())) {
                    userPreferences.updateLoggedInUserEmail(newEmail);
                }

                Toast.makeText(AccountInformationActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccountInformationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadExistingData() {
        User currentUser = userPreferences.getCurrentUser();
        changeAccountNameEditText.setText(currentUser.getName());
        changeAccountEmailEditText.setText(currentUser.getEmail());
        changePasswordEditText.setText(currentUser.getPassword());
    }
}
