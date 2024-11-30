package mobilepaymentapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UserPreferences {
    private SharedPreferences sharedPreferences;
    private List<User> users;
    private static int userIdCounter = 1;

    public UserPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        users = new ArrayList<>();
        loadUsersFromSharedPreferences();
    }

    public boolean saveUser(User updatedUser) {
        if (users == null) {
            Log.e("UserPreferences", "Users list is null! Cannot save or update user.");
            return false;
        }

        for (User user : users) {
            // Check if the email is already in use by another user
            if (user.getEmail().equalsIgnoreCase(updatedUser.getEmail()) && user.getId() != updatedUser.getId()) {
                Log.d("UserPreferences", "Email already in use: " + updatedUser.getEmail());
                return false;
            }
        }

        // Check if updating an existing user or adding a new user
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == updatedUser.getId()) {
                users.set(i, updatedUser); // Update the existing user
                saveUsersToSharedPreferences();
                Log.d("UserPreferences", "Updated user: " + updatedUser);
                return true;
            }
        }

        // If the user is new, assign a unique ID
        if (updatedUser.getId() == -1) {
            updatedUser.setId(userIdCounter++);
        }
        users.add(updatedUser);
        saveUsersToSharedPreferences();
        Log.d("UserPreferences", "Saved new user: " + updatedUser);
        return true;
    }



    public void updateLoggedInUserEmail(String newEmail) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LOGGED_IN_USER_EMAIL", newEmail);
        editor.apply();
        Log.d("UserPreferences", "Updated logged-in user email in SharedPreferences");
    }

    private void loadUsersFromSharedPreferences() {
        String jsonString = sharedPreferences.getString("USERS", "");
        if (jsonString != null && !jsonString.isEmpty()) {
            try {
                users = new Gson().fromJson(jsonString, new TypeToken<List<User>>(){}.getType());
                Log.d("UserPreferences", "Loaded users: " + users);
            } catch (Exception e) {
                Log.e("UserPreferences", "Error loading users: " + e.getMessage());
                users = new ArrayList<>();
            }
        } else {
            Log.d("UserPreferences", "No user data found in shared preferences");
            users = new ArrayList<>();
        }
        Log.d("UserPreferences", "Users after loading from prefs: " + users);
    }

    public User getCurrentUser() {
        String loggedInUserEmail = sharedPreferences.getString("LOGGED_IN_USER_EMAIL", "");
        Log.d("UserPreferences", "Current logged-in user email: " + loggedInUserEmail);

        if (!loggedInUserEmail.isEmpty()) {
            for (User user : users) {
                Log.d("UserPreferences", "Checking user: " + user.getEmail());
                if (user.getEmail().equals(loggedInUserEmail)) {
                    Log.d("UserPreferences", "Match found: " + user.getEmail());
                    return user;
                }
            }
            Log.d("UserPreferences", "No user found with email: " + loggedInUserEmail);
        } else {
            Log.d("UserPreferences", "No logged-in user email found");
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }


    private void saveUsersToSharedPreferences() {
        if (users != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // Convert users list to JSON string
            String jsonString = new Gson().toJson(users);
            editor.putString("USERS", jsonString);
            editor.apply();
            Log.d("UserPreferences", "Saved users to shared preferences: " + users.size());
        } else {
            Log.e("UserPreferences", "Users list is null! Cannot save to shared preferences.");
        }
    }

    public void resetUsers() {
        Log.d("UserPreferences", "Resetting users");
        users.clear();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("USERS");
        editor.apply();
        Log.d("UserPreferences", "Users reset complete");
    }



}
