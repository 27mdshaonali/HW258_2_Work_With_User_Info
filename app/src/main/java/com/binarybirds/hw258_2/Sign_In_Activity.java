package com.binarybirds.hw258_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sign_In_Activity extends AppCompatActivity {

    TextInputEditText userSignInUserName, userSignInPassword;
    AppCompatCheckBox checkBox;
    AppCompatTextView forgotPassword, signUp;
    AppCompatButton signInButton;

    String USER_INFO_URL = "https://dummyjson.com/users";
    ArrayList<JSONObject> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeView();
    }

    private void initializeView() {
        userSignInUserName = findViewById(R.id.userSignInUserName);
        userSignInPassword = findViewById(R.id.userSignInPassword);
        checkBox = findViewById(R.id.checkBox);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUp = findViewById(R.id.signUp);
        signInButton = findViewById(R.id.signInButton);

        signUp.setOnClickListener(v -> startActivity(new Intent(this, Sign_Up_Activity.class)));
        forgotPassword.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        signInButton.setOnClickListener(v -> {
            String username = userSignInUserName.getText().toString().trim();
            String password = userSignInPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            fetchUserList(username, password);
        });
    }

    private void fetchUserList(String username, String password) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, USER_INFO_URL, null,
                response -> {
                    try {
                        JSONArray users = response.getJSONArray("users");

                        boolean isUserFound = false;

                        for (int i = 0; i < users.length(); i++) {
                            JSONObject user = users.getJSONObject(i);
                            userList.add(user); // Add full user object to the list

                            String u = user.optString("username");
                            String p = user.optString("password");

                            if (username.equals(u) && password.equals(p)) {
                                isUserFound = true;

                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Sign_In_Activity.this, MainActivity.class);
                                intent.putExtra("userListJson", users.toString()); // send full array
                                intent.putExtra("loggedUsername", username);
                                startActivity(intent);
                                break;
                            }
                        }

                        if (!isUserFound) {
                            Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error reading user list", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Volley error occurred", Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }
}
