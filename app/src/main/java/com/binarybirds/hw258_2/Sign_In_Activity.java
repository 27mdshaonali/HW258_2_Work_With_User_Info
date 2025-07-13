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
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

        parseData();

        signInButton.setOnClickListener(v -> {
            String username = userSignInUserName.getText().toString();
            String password = userSignInPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            HashMap<String, String> loggedInUser = null;

            for (HashMap<String, String> user : arrayList) {
                if (username.equals(user.get("userSignInUserName")) && password.equals(user.get("userPassword"))) {
                    loggedInUser = user;
                    break;
                }
            }

            if (loggedInUser != null) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("userList", arrayList);
                intent.putExtra("loggedUsername", loggedInUser.get("userSignInUserName"));
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        signUp.setOnClickListener(v -> {
            startActivity(new Intent(this, Sign_Up_Activity.class));
        });

        forgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    public void parseData() {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, USER_INFO_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray users = jsonObject.optJSONArray("users");

                    for (int i = 0; i < users.length(); i++) {
                        JSONObject user = users.optJSONObject(i);

                        HashMap<String, String> hashMap = new HashMap<>();
                        int id = user.optInt("id");
                        String userSignInUserName = user.optString("username");
                        String userPassword = user.optString("password");

                        hashMap.put("id", String.valueOf(id));
                        hashMap.put("userSignInUserName", userSignInUserName);
                        hashMap.put("userPassword", userPassword);

                        String firstName = user.optString("firstName");
                        String lastName = user.optString("lastName");
                        String maidenName = user.optString("maidenName");
                        int age = user.optInt("age");
                        String gender = user.optString("gender");
                        String email = user.optString("email");
                        String phone = user.optString("phone");
                        String birthDate = user.optString("birthDate");
                        String image = user.optString("image");
                        String bloodGroup = user.optString("bloodGroup");
                        double height = user.optDouble("height");
                        double weight = user.optDouble("weight");
                        String eyeColor = user.optString("eyeColor");

                        JSONObject hair = user.optJSONObject("hair");
                        if (hair != null) {
                            hashMap.put("hairColor", hair.optString("color", "N/A"));
                            hashMap.put("hairType", hair.optString("type", "N/A"));
                        }

                        String ip = user.optString("ip");

                        JSONObject homeAddress = user.optJSONObject("address");
                        if (homeAddress != null) {
                            hashMap.put("homeAddress1", homeAddress.optString("address"));
                            hashMap.put("homeCity", homeAddress.optString("city"));
                            hashMap.put("homeState", homeAddress.optString("state"));
                            hashMap.put("homeStateCode", homeAddress.optString("stateCode"));
                            hashMap.put("homePostalCode", homeAddress.optString("postalCode"));
                            hashMap.put("homeCountry", homeAddress.optString("country"));
                        }

                        String macAddress = user.optString("macAddress");
                        String university = user.optString("university");

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
                            hashMap.put("department", company.optString("department"));
                            hashMap.put("companyName", company.optString("name"));
                            hashMap.put("title", company.optString("title"));

                            JSONObject companyAddress = company.optJSONObject("address");
                            if (companyAddress != null) {
                                hashMap.put("companyAddress2", companyAddress.optString("address"));
                                hashMap.put("companyCity", companyAddress.optString("city"));
                                hashMap.put("companyState", companyAddress.optString("state"));
                                hashMap.put("companyStateCode", companyAddress.optString("stateCode"));
                                hashMap.put("companyPostalCode", companyAddress.optString("postalCode"));
                                hashMap.put("companyCountry", companyAddress.optString("country"));

                                JSONObject coordinates = companyAddress.optJSONObject("coordinates");
                                if (coordinates != null) {
                                    hashMap.put("companyLat", coordinates.optString("lat"));
                                    hashMap.put("companyLng", coordinates.optString("lng"));
                                }
                            }
                        }

                        JSONObject crypto = user.optJSONObject("crypto");
                        if (crypto != null) {
                            hashMap.put("coin", crypto.optString("coin"));
                            hashMap.put("wallet", crypto.optString("wallet"));
                            hashMap.put("network", crypto.optString("network"));
                        }

                        hashMap.put("firstName", firstName);
                        hashMap.put("lastName", lastName);
                        hashMap.put("maidenName", maidenName);
                        hashMap.put("age", String.valueOf(age));
                        hashMap.put("gender", gender);
                        hashMap.put("email", email);
                        hashMap.put("phone", phone);
                        hashMap.put("birthDate", birthDate);
                        hashMap.put("image", image);
                        hashMap.put("bloodGroup", bloodGroup);
                        hashMap.put("height", String.valueOf(height));
                        hashMap.put("weight", String.valueOf(weight));
                        hashMap.put("eyeColor", eyeColor);
                        hashMap.put("ip", ip);
                        hashMap.put("macAddress", macAddress);
                        hashMap.put("university", university);
                        hashMap.put("ein", user.optString("ein"));
                        hashMap.put("ssn", user.optString("ssn"));
                        hashMap.put("userAgent", user.optString("userAgent"));
                        hashMap.put("role", user.optString("role"));

                        arrayList.add(hashMap);
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "JSON Parsing Error.....", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }
}
