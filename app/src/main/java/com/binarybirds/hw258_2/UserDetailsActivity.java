package com.binarybirds.hw258_2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class UserDetailsActivity extends AppCompatActivity {
    TextView tvOutput, fullUserName, userName, userBloodGroup, userCompanyName, userPosition, userNumber, userEmail, firstNameResult, lastNameResult, maidenNameResult, ageResult, genderResult, emailResult, phoneResult, birthDateResult, bloodGroupResult, heightResult, weightResult, eyeColorResult, hairColorResult, ipResult, macAddressResult, userAgentResult, universityNameResult, credentialsPasswordInfoResult, einResult, ssnResult, roleResult, companyNameResult, departmentResult, titleResult, streetResult, streetInfoResult,
            locationInfoResult, companyStreetResult, companyLocationResult, latResult, lngResult, cardNumberInfoResult, coinResult, walletResult, networkResult, cardTypeResult, cardExpireResult, currencyResult, cryptoWalletInfoResult, cardIBANInfoResult, phoneInfoResult;
    RoundedImageView imageCall, imageMessage, imageWhatsapp, imageEmail;
    ImageView coverImage;


    //========================== Masking Methods ====================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        //tvOutput = findViewById(R.id.tvOutput);
        cardNumberInfoResult = findViewById(R.id.cardNumberInfoResult);
        coverImage = findViewById(R.id.coverImage);
        credentialsPasswordInfoResult = findViewById(R.id.credentialsPasswordInfoResult);
        cryptoWalletInfoResult = findViewById(R.id.cryptoWalletInfoResult);
        cardIBANInfoResult = findViewById(R.id.cardIBANInfoResult);
        phoneInfoResult = findViewById(R.id.phoneInfoResult);
        userNumber = findViewById(R.id.userNumber);

        fullUserName = findViewById(R.id.fullUserName);
        userName = findViewById(R.id.userName);
        userBloodGroup = findViewById(R.id.userBloodGroup);
        userCompanyName = findViewById(R.id.userCompanyName);
        userPosition = findViewById(R.id.userPosition);
        userEmail = findViewById(R.id.userEmail);
        firstNameResult = findViewById(R.id.firstNameResult);
        lastNameResult = findViewById(R.id.lastName);
        maidenNameResult = findViewById(R.id.maidenName);
        ageResult = findViewById(R.id.age);
        genderResult = findViewById(R.id.gender);
        emailResult = findViewById(R.id.emailInfoResult);
        birthDateResult = findViewById(R.id.birthDate);
        bloodGroupResult = findViewById(R.id.bloodResult);
        heightResult = findViewById(R.id.heightInfoResult);
        weightResult = findViewById(R.id.weightInfoResult);
        eyeColorResult = findViewById(R.id.eyeColorResult);
        hairColorResult = findViewById(R.id.hairInfoResult);
        ipResult = findViewById(R.id.iPInfoResult);
        macAddressResult = findViewById(R.id.mACAddressInfoResult);
        userAgentResult = findViewById(R.id.userAgentInfoResult);
        universityNameResult = findViewById(R.id.universityNameResult);
        einResult = findViewById(R.id.credentialsEINInfoResult);
        ssnResult = findViewById(R.id.credentialsSSNInfoResult);
        roleResult = findViewById(R.id.credentialsRoleInfoResult);
        companyNameResult = findViewById(R.id.companyNameResult);
        departmentResult = findViewById(R.id.companyDepartmentResult);
        titleResult = findViewById(R.id.companyTitleResult);
        streetInfoResult = findViewById(R.id.streetInfoResult);
        locationInfoResult = findViewById(R.id.locationInfoResult);
        latResult = findViewById(R.id.latitudeInfoResult);
        lngResult = findViewById(R.id.longitudeInfoResult);

        companyStreetResult = findViewById(R.id.companyStreetResult);
        companyLocationResult = findViewById(R.id.companyLocationResult);

        cardTypeResult = findViewById(R.id.cardTypeInfoResult);
        cardExpireResult = findViewById(R.id.cardExpiresInfoResult);
        currencyResult = findViewById(R.id.bankCurrencyInfoResult);

        coinResult = findViewById(R.id.coinInfoResult);
        networkResult = findViewById(R.id.cryptoNetworkInfoResult);

        imageCall = findViewById(R.id.imageCall);
        imageMessage = findViewById(R.id.imageMessage);
        imageWhatsapp = findViewById(R.id.imageWhatsapp);
        imageEmail = findViewById(R.id.imageEmail);


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

                String cardNumberText = cardNumber;
                String ibanNumberText = iban;
                String passwordText = user.optString("password");

                // Crypto (nested)
                String coin = "", wallet = "", network = "";
                JSONObject crypto = user.optJSONObject("crypto");
                if (crypto != null) {
                    coin = crypto.optString("coin");
                    wallet = crypto.optString("wallet");
                    network = crypto.optString("network");
                }

                /*
                // ✅ Output
                String output = "ID: " + id + "\nName: " + firstName + " " + lastName + "\nMaiden Name: " + maidenName + "\nUsername: " + username + "\nPassword: " + password + "\nGender: " + gender + "\nAge: " + age + "\nEmail: " + email + "\nPhone: " + phone + "\nBirth Date: " + birthDate + "\nImage: " + image + "\nBlood Group: " + bloodGroup + "\nHeight: " + height + "\nWeight: " + weight + "\nEye Color: " + eyeColor + "\nHair: " + hairColor + " (" + hairType + ")" + "\nIP: " + ip + "\nMAC Address: " + macAddress + "\nUniversity: " + university + "\nEIN: " + ein + "\nSSN: " + ssn + "\nUser Agent: " + userAgent + "\nRole: " + role +

                        "\n\n📍 Address:\n" + street + ", " + city + ", " + state + " (" + stateCode + "), " + country + " - " + postalCode + "\nCoordinates: (" + lat + ", " + lng + ")" +

                        "\n\n🏦 Bank:\nCard: " + cardNumber + " (" + cardType + "), Exp: " + cardExpire + "\nCurrency: " + currency + ", IBAN: " + iban +

                        "\n\n🏢 Company:\n" + title + " at " + companyName + "\nDepartment: " + department + "\nAddress: " + companyStreet + ", " + companyCity + ", " + companyState + ", " + companyCountry + " - " + companyPostalCode +

                        "\n\n🪙 Crypto:\nCoin: " + coin + ", Wallet: " + wallet + ", Network: " + network;

                tvOutput.setText(output);

                 */



                //================================= Setting Data Starts ====================================

                fullUserName.setText(firstName + " " + lastName);
                userName.setText("@"+username);
                userBloodGroup.setText(bloodGroup);
                userCompanyName.setText(companyName);
                userPosition.setText(title);
                userEmail.setText(email);
                userNumber.setText(phone);

                firstNameResult.setText(firstName);
                lastNameResult.setText(lastName);

                if (maidenNameResult.length() > 0) {
                    maidenNameResult.setText(maidenName);
                } else {
                    maidenNameResult.setText("N/A");
                }

                universityNameResult.setText(university);
                genderResult.setText(gender);
                bloodGroupResult.setText(bloodGroup);
                birthDateResult.setText(birthDate);
                ageResult.setText(age);

                emailResult.setText(email);
                phoneResult.setText(phone);

                heightResult.setText(height);
                weightResult.setText(weight);
                eyeColorResult.setText(eyeColor);
                hairColorResult.setText(hairColor+"("+hairType+")");

                streetInfoResult.setText(street);
                locationInfoResult.setText(street+", "+city+", "+state+" ("+stateCode+"), "+country+" - "+postalCode);
                latResult.setText(String.valueOf(lat));
                lngResult.setText(String.valueOf(lng));


                companyNameResult.setText(companyName);
                departmentResult.setText(department);
                titleResult.setText(title);
                companyStreetResult.setText(companyStreet);
                companyLocationResult.setText(companyStreet+", "+companyCity+", "+companyState + " "+companyCountry+" - "+companyPostalCode);

                cardTypeResult.setText(cardType);
                cardExpireResult.setText(cardExpire);
                currencyResult.setText(currency);

                coinResult.setText(coin);
                walletResult.setText(wallet);
                networkResult.setText(network);


                ipResult.setText(ip);
                macAddressResult.setText(macAddress);
                userAgentResult.setText(userAgent);

















                //================================= Setting Data Ends ====================================



                //=============================== Masking Area Starts ====================================


                MaskingUtils.applyMaskToggle(cardNumberInfoResult, cardNumberText, 3, 3);
                MaskingUtils.applyMaskToggle(cardIBANInfoResult, ibanNumberText, 3, 3);
                MaskingUtils.applyMaskToggle(credentialsPasswordInfoResult, passwordText, 0, 0);

                //=============================== Masking Area Ends ====================================


                Picasso.get().load(image).into(coverImage);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error parsing JSON data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No user data available", Toast.LENGTH_SHORT).show();
        }


    }
}
