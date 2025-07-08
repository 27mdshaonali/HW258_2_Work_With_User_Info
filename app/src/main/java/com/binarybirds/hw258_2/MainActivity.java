package com.binarybirds.hw258_2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList;
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

        arrayList = new ArrayList<>();
        parseData();
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
                        String firstName = user.optString("firstName");
                        String lastName = user.optString("lastName");
                        String maidenName = user.optString("maidenName");
                        int age = user.optInt("age");
                        String gender = user.optString("gender");
                        String email = user.optString("email");
                        String phone = user.optString("phone");
                        String username = user.optString("username");
                        String password = user.optString("password");
                        String birthDate = user.optString("birthDate");
                        String image = user.optString("image");
                        String bloodGroup = user.optString("bloodGroup");
                        double height = user.optDouble("height");
                        double weight = user.optDouble("weight");
                        String eyeColor = user.optString("eyeColor");

                        JSONObject hair = user.optJSONObject("hair");
                        if (hair != null) {

                            String hairColor = hair.optString("color", "N/A");
                            String hairType = hair.optString("type", "N/A");

                            Toast.makeText(MainActivity.this, "Hair Color: " + hairColor + ", Hair Type: " + hairType, Toast.LENGTH_SHORT).show();

                            hashMap.put("hairColor", hairColor);
                            hashMap.put("hairType", hairType);
                        }

                        String ip = user.optString("ip");

                        JSONObject homeAddress = user.optJSONObject("address");
                        if (homeAddress != null) {

                            String homeAddress1 = homeAddress.optString("address");
                            String homeCity = homeAddress.optString("city");
                            String homeState = homeAddress.optString("state");
                            String homeStateCode = homeAddress.optString("stateCode");
                            String homePostalCode = homeAddress.optString("postalCode");
                            String homeCountry = homeAddress.optString("country");

                            hashMap.put("homeAddress1", homeAddress1);
                            hashMap.put("homeCity", homeCity);
                            hashMap.put("homeState", homeState);
                            hashMap.put("homeStateCode", homeStateCode);
                            hashMap.put("homePostalCode", homePostalCode);
                            hashMap.put("homeCountry", homeCountry);

                            String homeCoordinates = homeAddress.optString("coordinates");
                            if (homeCoordinates != null) {

                                String lat = homeAddress.optString("lat");
                                String lng = homeAddress.optString("lng");

                                Toast.makeText(MainActivity.this, "Home Latitude: " + lat + ", Home Longitude: " + lng, Toast.LENGTH_SHORT).show();
                            }
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

                                    String lat = coordinates.optString("lat");
                                    String lng = coordinates.optString("lng");

                                    hashMap.put("companyLat", lat);
                                    hashMap.put("companyLng", lng);

                                    Toast.makeText(MainActivity.this, "Company Latitude: " + lat + ", Company Longitude: " + lng, Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        JSONObject crypto = user.optJSONObject("crypto");
                        if (crypto != null) {
                            hashMap.put("coin", crypto.optString("coin"));
                            hashMap.put("wallet", crypto.optString("wallet"));
                            hashMap.put("network", crypto.optString("network"));
                        }

                        hashMap.put("id", String.valueOf(id));
                        hashMap.put("firstName", firstName);
                        hashMap.put("lastName", lastName);
                        hashMap.put("maidenName", maidenName);
                        hashMap.put("age", String.valueOf(age));
                        hashMap.put("gender", gender);
                        hashMap.put("email", email);
                        hashMap.put("phone", phone);
                        hashMap.put("username", username);
                        hashMap.put("password", password);
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

                    Toast.makeText(MainActivity.this, "Parsed " + arrayList.size() + " users.", Toast.LENGTH_SHORT).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "JSON Parsing Error.....", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(objectRequest);
    }
}
