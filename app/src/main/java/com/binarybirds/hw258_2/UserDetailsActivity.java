package com.binarybirds.hw258_2;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class UserDetailsActivity extends AppCompatActivity {

    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        tvOutput = findViewById(R.id.tvOutput);

        String userJsonString = getIntent().getStringExtra("userDataJson");

        if (userJsonString != null) {
            try {
                JSONObject user = new JSONObject(userJsonString);

                String id = user.optString("id");
                String firstName = user.optString("firstName");
                String lastName = user.optString("lastName");
                String maidenName = user.optString("maidenName");
                String age = user.optString("age");
                String gender = user.optString("gender");
                String email = user.optString("email");
                String phone = user.optString("phone");
                String username = user.optString("username");
                String password = user.optString("password");
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

                // Hair
                String hairColor = "", hairType = "";
                JSONObject hair = user.optJSONObject("hair");
                if (hair != null) {
                    hairColor = hair.optString("color");
                    hairType = hair.optString("type");


                }

                Toast.makeText(this, "Hair color is: " + hairColor, Toast.LENGTH_SHORT).show();

                // Address
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

                // Bank
                String cardNumber = "", cardType = "", cardExpire = "", currency = "", iban = "";
                JSONObject bank = user.optJSONObject("bank");
                if (bank != null) {
                    cardNumber = bank.optString("cardNumber");
                    cardType = bank.optString("cardType");
                    cardExpire = bank.optString("cardExpire");
                    currency = bank.optString("currency");
                    iban = bank.optString("iban");
                }

                // Company
                String department = "", companyName = "", title = "";
                String companyStreet = "", companyCity = "", companyState = "", companyPostal = "", companyCountry = "";
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
                        companyPostal = companyAddress.optString("postalCode");
                        companyCountry = companyAddress.optString("country");
                    }
                }

                // Crypto
                String coin = "", wallet = "", network = "";
                JSONObject crypto = user.optJSONObject("crypto");
                if (crypto != null) {
                    coin = crypto.optString("coin");
                    wallet = crypto.optString("wallet");
                    network = crypto.optString("network");
                }

                // Display output
                String output = "ID: " + id +
                        "\nName: " + firstName + " " + lastName +
                        "\nMaiden Name: " + maidenName +
                        "\nUsername: " + username +
                        "\nPassword: " + password +
                        "\nGender: " + gender +
                        "\nAge: " + age +
                        "\nEmail: " + email +
                        "\nPhone: " + phone +
                        "\nBirth Date: " + birthDate +
                        "\nImage URL: " + image +
                        "\nBlood Group: " + bloodGroup +
                        "\nHeight: " + height +
                        "\nWeight: " + weight +
                        "\nEye Color: " + eyeColor +
                        "\nHair: " + hairColor + " (" + hairType + ")" +
                        "\nIP: " + ip +
                        "\nMAC Address: " + macAddress +
                        "\nUniversity: " + university +
                        "\nEIN: " + ein +
                        "\nSSN: " + ssn +
                        "\nUser Agent: " + userAgent +
                        "\nRole: " + role +

                        "\n\n📍 Address:\n" +
                        street + ", " + city + ", " + state + " (" + stateCode + "), " +
                        country + " - " + postalCode +
                        "\nCoordinates: (" + lat + ", " + lng + ")" +

                        "\n\n🏦 Bank:\nCard: " + cardNumber + " (" + cardType + "), Exp: " + cardExpire +
                        "\nCurrency: " + currency + ", IBAN: " + iban +

                        "\n\n🏢 Company:\n" +
                        title + " at " + companyName +
                        "\nDepartment: " + department +
                        "\nAddress: " + companyStreet + ", " + companyCity + ", " +
                        companyState + ", " + companyCountry + " - " + companyPostal +

                        "\n\n🪙 Crypto:\n" +
                        "Coin: " + coin + ", Wallet: " + wallet + ", Network: " + network;

                tvOutput.setText(output);

                Toast.makeText(this, "Username is: " + username, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                tvOutput.setText("Error parsing user data.");
            }
        } else {
            tvOutput.setText("No user data received.");
        }
    }
}
