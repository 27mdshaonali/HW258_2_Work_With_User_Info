package com.binarybirds.hw258_2;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> userList;
    String loggedUsername;

    TextView username;
    String USER_INFO_URL = "https://dummyjson.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("userList");
        loggedUsername = getIntent().getStringExtra("loggedUsername");

        username = findViewById(R.id.username);

        if (userList != null) {
            // Show all users' names in log or toast (or use later in list view)
            for (HashMap<String, String> user : userList) {
                String fullName = user.get("firstName") + " " + user.get("lastName");
                String email = user.get("email");
                // You can log or toast here for testing if needed
                // Log.d("UserInfo", fullName + " | " + email);

                 Toast.makeText(this, fullName + " | " + email, Toast.LENGTH_SHORT).show();

            }
        }

        if (userList != null && loggedUsername != null) {
            for (HashMap<String, String> user : userList) {
                if (loggedUsername.equals(user.get("userSignInUserName"))) {
                    String fullName = user.get("firstName") + " " + user.get("lastName");
                    username.setText("Welcome " + fullName + "!");
                    Toast.makeText(this, "Welcome " + fullName + "!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } else {
            username.setText("User not found!");
            Toast.makeText(this, "Logged user not found", Toast.LENGTH_SHORT).show();
        }
    }
}
