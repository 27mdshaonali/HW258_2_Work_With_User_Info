package com.binarybirds.hw258_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class UserDetailsActivity extends AppCompatActivity {
    TextView tvOutput, cardNumberTV;
    ImageView coverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        tvOutput = findViewById(R.id.tvOutput);
        cardNumberTV = findViewById(R.id.cardNumber);
        coverImage = findViewById(R.id.coverImage);

        String userJsonString = getIntent().getStringExtra("userDataJson");

        if (userJsonString != null) {
            try {
                JSONObject user = new JSONObject(userJsonString);

                String id = user.optString("id");
                String firstName = user.optString("firstName");
                String lastName = user.optString("lastName");
                String maidenName = user.optString("maidenName");
                String username = user.optString("username");
                String password = user.optString("password");
                String age = user.optString("age");
                String gender = user.optString("gender");
                String email = user.optString("email");
                String phone = user.optString("phone");
                String birthDate = user.optString("birthDate");
                String image = user.optString("image");
                String bloodGroup = user.optString("bloodGroup");
                String height = user.optString("height");
                String weight = user.optString("weight");
                String eyeColor = user.optString("eyeColor");
                String ip = user.optString("ip");
                String macAddress = user.optString("macAddress");
                String university = user.optString("university");
                String ein = user.optString("ein");
                String ssn = user.optString("ssn");
                String userAgent = user.optString("userAgent");
                String role = user.optString("role");

                // Hair (nested object)
                JSONObject hair = user.optJSONObject("hair");
                String hairColor = "", hairType = "";
                if (hair != null) {
                    hairColor = hair.optString("color");
                    hairType = hair.optString("type");

                    if (!hairColor.isEmpty()) {
                        Toast.makeText(this, "Hair Color: " + hairColor, Toast.LENGTH_SHORT).show();
                    }

                    if (!hairType.isEmpty()) {
                        Toast.makeText(this, "Hair Type: " + hairType, Toast.LENGTH_SHORT).show();
                    }
                }

                // Address (nested object with nested coordinates)
                String street = "", city = "", state = "", stateCode = "", postalCode = "", country = "";
                double lat = 0, lng = 0;

                JSONObject address = user.optJSONObject("address");
                if (address != null) {
                    street = address.optString("address");
                    city = address.optString("city");
                    state = address.optString("state");
                    stateCode = address.optString("stateCode");
                    postalCode = address.optString("postalCode");
                    country = address.optString("country");

                    JSONObject coordinates = address.optJSONObject("coordinates");
                    if (coordinates != null) {
                        lat = coordinates.optDouble("lat");
                        lng = coordinates.optDouble("lng");
                    }
                }

                // Bank (nested)
                String cardNumber = "", cardType = "", cardExpire = "", currency = "", iban = "";
                JSONObject bank = user.optJSONObject("bank");
                if (bank != null) {
                    cardNumber = bank.optString("cardNumber");
                    cardType = bank.optString("cardType");
                    cardExpire = bank.optString("cardExpire");
                    currency = bank.optString("currency");
                    iban = bank.optString("iban");
                }

                // Company (nested with nested address and coordinates)
                String department = "", companyName = "", title = "";
                String companyStreet = "", companyCity = "", companyState = "", companyPostalCode = "", companyCountry = "";

                JSONObject company = user.optJSONObject("company");
                if (company != null) {
                    department = company.optString("department");
                    companyName = company.optString("name");
                    title = company.optString("title");

                    JSONObject companyAddress = company.optJSONObject("address");
                    if (companyAddress != null) {
                        companyStreet = companyAddress.optString("address");
                        companyCity = companyAddress.optString("city");
                        companyState = companyAddress.optString("state");
                        companyPostalCode = companyAddress.optString("postalCode");
                        companyCountry = companyAddress.optString("country");

                        JSONObject companyCoordinates = companyAddress.optJSONObject("coordinates");
                        if (companyCoordinates != null) {
                            double companyLat = companyCoordinates.optDouble("lat");
                            double companyLng = companyCoordinates.optDouble("lng");

                            Toast.makeText(this, "Company Coordinates: (" + companyLat + ", " + companyLng + ")", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                String cardNumberText = "Card Number: " + cardNumber;

                // Crypto (nested)
                String coin = "", wallet = "", network = "";
                JSONObject crypto = user.optJSONObject("crypto");
                if (crypto != null) {
                    coin = crypto.optString("coin");
                    wallet = crypto.optString("wallet");
                    network = crypto.optString("network");
                }

                // ✅ Output
                String output = "ID: " + id + "\nName: " + firstName + " " + lastName + "\nMaiden Name: " + maidenName + "\nUsername: " + username + "\nPassword: " + password + "\nGender: " + gender + "\nAge: " + age + "\nEmail: " + email + "\nPhone: " + phone + "\nBirth Date: " + birthDate + "\nImage: " + image + "\nBlood Group: " + bloodGroup + "\nHeight: " + height + "\nWeight: " + weight + "\nEye Color: " + eyeColor + "\nHair: " + hairColor + " (" + hairType + ")" + "\nIP: " + ip + "\nMAC Address: " + macAddress + "\nUniversity: " + university + "\nEIN: " + ein + "\nSSN: " + ssn + "\nUser Agent: " + userAgent + "\nRole: " + role +

                        "\n\n📍 Address:\n" + street + ", " + city + ", " + state + " (" + stateCode + "), " + country + " - " + postalCode + "\nCoordinates: (" + lat + ", " + lng + ")" +

                        "\n\n🏦 Bank:\nCard: " + cardNumber + " (" + cardType + "), Exp: " + cardExpire + "\nCurrency: " + currency + ", IBAN: " + iban +

                        "\n\n🏢 Company:\n" + title + " at " + companyName + "\nDepartment: " + department + "\nAddress: " + companyStreet + ", " + companyCity + ", " + companyState + ", " + companyCountry + " - " + companyPostalCode +

                        "\n\n🪙 Crypto:\nCoin: " + coin + ", Wallet: " + wallet + ", Network: " + network;

                tvOutput.setText(output);


                // Step 1: Mask all but the last 3 digits
                String maskedCardNumber = cardNumberText.replaceAll(".(?=...)", "*");

                // Step 2: Set the masked card number initially
                cardNumberTV.setText(maskedCardNumber);

                // Step 3: Toggle full/masked card number on click
                cardNumberTV.setOnClickListener(new View.OnClickListener() {
                    private boolean isMasked = true;

                    @Override
                    public void onClick(View v) {
                        if (isMasked) {
                            cardNumberTV.setText(cardNumberText); // Show full number
                        } else {
                            cardNumberTV.setText(maskedCardNumber); // Show masked
                        }
                        isMasked = !isMasked;
                    }
                });


                Picasso.get().load(image).into(coverImage);

            } catch (Exception e) {
                e.printStackTrace();
                tvOutput.setText("Error parsing user data.");
            }
        } else {
            tvOutput.setText("No user data received.");
        }
    }
}
