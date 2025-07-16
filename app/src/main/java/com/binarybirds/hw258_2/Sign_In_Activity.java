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
import java.util.HashMap;

public class Sign_In_Activity extends AppCompatActivity {

    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList;
    TextInputEditText userSignInUserName, userSignInPassword;
    AppCompatCheckBox checkBox;
    AppCompatTextView forgotPassword, signUp;
    AppCompatButton signInButton;
    String USER_INFO_URL = "https://dummyjson.com/users";

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

    public void initializeView() {
        userSignInUserName = findViewById(R.id.userSignInUserName);
        userSignInPassword = findViewById(R.id.userSignInPassword);
        checkBox = findViewById(R.id.checkBox);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUp = findViewById(R.id.signUp);
        signInButton = findViewById(R.id.signInButton);
        arrayList = new ArrayList<>();

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

    public void fetchUserList(String username, String password) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, USER_INFO_URL, null,
                jsonObject -> {
                    try {
                        JSONArray users = jsonObject.getJSONArray("users");

                        for (int i = 0; i < users.length(); i++) {
                            JSONObject user = users.getJSONObject(i);
                            hashMap = new HashMap<>();

                            hashMap.put("userSignInUserName", user.optString("username"));
                            hashMap.put("userPassword", user.optString("password"));

                            hashMap.put("id", String.valueOf(user.optInt("id")));
                            hashMap.put("firstName", user.optString("firstName"));
                            hashMap.put("lastName", user.optString("lastName"));
                            hashMap.put("maidenName", user.optString("maidenName"));
                            hashMap.put("age", String.valueOf(user.optInt("age")));
                            hashMap.put("gender", user.optString("gender"));
                            hashMap.put("email", user.optString("email"));
                            hashMap.put("phone", user.optString("phone"));
                            hashMap.put("birthDate", user.optString("birthDate"));
                            hashMap.put("image", user.optString("image"));
                            hashMap.put("bloodGroup", user.optString("bloodGroup"));
                            hashMap.put("height", String.valueOf(user.optDouble("height")));
                            hashMap.put("weight", String.valueOf(user.optDouble("weight")));
                            hashMap.put("eyeColor", user.optString("eyeColor"));
                            hashMap.put("ip", user.optString("ip"));
                            hashMap.put("macAddress", user.optString("macAddress"));
                            hashMap.put("university", user.optString("university"));
                            hashMap.put("ein", user.optString("ein"));
                            hashMap.put("ssn", user.optString("ssn"));
                            hashMap.put("userAgent", user.optString("userAgent"));
                            hashMap.put("role", user.optString("role"));

                            JSONObject hair = user.optJSONObject("hair");
                            if (hair != null) {
                                hashMap.put("hairColor", hair.optString("color"));
                                hashMap.put("hairType", hair.optString("type"));
                            }

                            JSONObject address = user.optJSONObject("address");
                            if (address != null) {
                                hashMap.put("address", address.optString("address"));
                                hashMap.put("city", address.optString("city"));
                                hashMap.put("state", address.optString("state"));
                                hashMap.put("stateCode", address.optString("stateCode"));
                                hashMap.put("postalCode", address.optString("postalCode"));
                                hashMap.put("country", address.optString("country"));
                            }

                            JSONObject bank = user.optJSONObject("bank");
                            if (bank != null) {
                                hashMap.put("cardExpire", bank.optString("cardExpire"));
                                hashMap.put("cardNumber", bank.optString("cardNumber"));
                                hashMap.put("cardType", bank.optString("cardType"));
                                hashMap.put("currency", bank.optString("currency"));
                                hashMap.put("iban", bank.optString("iban"));
                            }

                            JSONObject company = user.optJSONObject("company");
                            if (company != null) {
                                hashMap.put("companyName", company.optString("name"));
                                hashMap.put("department", company.optString("department"));
                                hashMap.put("title", company.optString("title"));

                                JSONObject companyAddress = company.optJSONObject("address");
                                if (companyAddress != null) {
                                    hashMap.put("companyAddress", companyAddress.optString("address"));
                                    hashMap.put("companyCity", companyAddress.optString("city"));
                                    hashMap.put("companyState", companyAddress.optString("state"));
                                    hashMap.put("companyPostalCode", companyAddress.optString("postalCode"));
                                    hashMap.put("companyCountry", companyAddress.optString("country"));
                                }
                            }

                            JSONObject crypto = user.optJSONObject("crypto");
                            if (crypto != null) {
                                hashMap.put("coin", crypto.optString("coin"));
                                hashMap.put("wallet", crypto.optString("wallet"));
                                hashMap.put("network", crypto.optString("network"));
                            }

                            arrayList.add(hashMap);
                        }

                        boolean isUserFound = false;
                        for (HashMap<String, String> user : arrayList) {
                            if (username.equals(user.get("userSignInUserName")) && password.equals(user.get("userPassword"))) {
                                isUserFound = true;

                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("userList", arrayList);
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
                volleyError -> Toast.makeText(getApplicationContext(), "Volley error occurred", Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }
}
