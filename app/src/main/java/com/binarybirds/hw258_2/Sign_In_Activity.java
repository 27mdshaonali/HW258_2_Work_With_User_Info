package com.binarybirds.hw258_2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
        forgotPassword = findViewById(R.id.forgotPassword);
        signUp = findViewById(R.id.signUp);
        signInButton = findViewById(R.id.signInButton);

        if (!internetConnectivity()) {
            signInButton.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

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
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, USER_INFO_URL, null, response -> {
            try {
                JSONArray users = response.getJSONArray("users");

                boolean isUserFound = false;
                userList.clear();

                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    userList.add(user);

                    String u = user.optString("username");
                    String p = user.optString("password");
                    String gender = user.optString("gender");
                    String profileImage = user.optString("image");
                    String userRole = user.optString("role");

                    if (username.equals(u) && password.equals(p)) {
                        isUserFound = true;

                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Sign_In_Activity.this, MainActivity.class);
                        intent.putExtra("userListJson", users.toString());
                        intent.putExtra("loggedUsername", username);
                        intent.putExtra("loggedGender", gender);
                        intent.putExtra("loggedProfileImage", profileImage);
                        intent.putExtra("loggedRole", userRole);
                        startActivity(intent);
                        break;
                    }
                }

                if (!isUserFound) {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error reading user list", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), "Volley error occurred", Toast.LENGTH_SHORT).show();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }

    public boolean internetConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
