package com.binarybirds.hw258_2;

import static android.view.View.GONE;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UserDetailsActivity extends AppCompatActivity {
    RoundedImageView imageCall, imageMessage, imageWhatsapp, imageEmail;
    ImageView ppImage;
    TextView fullUserName, userName, userBloodGroup, userCompanyName, userPosition, userEmail, userNumber;
    TextView firstNameResult, LastNameResult, maidenNameResult, universityNameResult, genderResult, bloodResult, birthDateResult, ageResult, emailInfoResult, phoneInfoResult;
    ImageView copyEmail, copyPhone;
    TextView heightInfoResult, weightInfoResult, eyeColorResult, hairInfoResult;
    TextView streetInfoResult, locationInfoResult, latitudeInfoResult, longitudeInfoResult;
    TextView companyNameResult, companyTitleResult, companyDepartmentResult, companyStreetResult, companyLocationResult;
    TextView cardNumberInfoResult, cardTypeInfoResult, bankCurrencyInfoResult, cardExpiresInfoResult, cardIBANInfoResult, coinInfoResult, cryptoWalletInfoResult, cryptoNetworkInfoResult;
    TextView quickShare,additionalInfoContainer, showLess;
    TextView credentialsPasswordInfoResult, credentialsEINInfoResult, credentialsSSNInfoResult, credentialsRoleInfoResult, iPInfoResult, mACAddressInfoResult, userAgentInfoResult;
    String loggedEmails;
    CardView cardAdditional;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //============================ 🔐 Security and Technical ====================================

        credentialsPasswordInfoResult = findViewById(R.id.credentialsPasswordInfoResult);
        credentialsEINInfoResult = findViewById(R.id.credentialsEINInfoResult);
        credentialsSSNInfoResult = findViewById(R.id.credentialsSSNInfoResult);
        credentialsRoleInfoResult = findViewById(R.id.credentialsRoleInfoResult);
        iPInfoResult = findViewById(R.id.iPInfoResult);
        mACAddressInfoResult = findViewById(R.id.mACAddressInfoResult);
        userAgentInfoResult = findViewById(R.id.userAgentInfoResult);

        //============================ Additional Info Visibility ====================================

        quickShare = findViewById(R.id.quickShare);
        additionalInfoContainer = findViewById(R.id.additionalInfoContainer);
        showLess = findViewById(R.id.showLess);
        cardAdditional = findViewById(R.id.cardAdditional);

        //============================ 💳 Financial ====================================

        cardNumberInfoResult = findViewById(R.id.cardNumberInfoResult);
        cardTypeInfoResult = findViewById(R.id.cardTypeInfoResult);
        bankCurrencyInfoResult = findViewById(R.id.bankCurrencyInfoResult);
        cardExpiresInfoResult = findViewById(R.id.cardExpiresInfoResult);
        cardIBANInfoResult = findViewById(R.id.cardIBANInfoResult);
        coinInfoResult = findViewById(R.id.coinInfoResult);
        cryptoWalletInfoResult = findViewById(R.id.cryptoWalletInfoResult);
        cryptoNetworkInfoResult = findViewById(R.id.cryptoNetworkInfoResult);

        //============================ 🏢 Employment ====================================

        companyNameResult = findViewById(R.id.companyNameResult);
        companyTitleResult = findViewById(R.id.companyTitleResult);
        companyDepartmentResult = findViewById(R.id.companyDepartmentResult);
        companyStreetResult = findViewById(R.id.companyStreetResult);
        companyLocationResult = findViewById(R.id.companyLocationResult);


        //============================ Residential Address 📍 ====================================

        streetInfoResult = findViewById(R.id.streetInfoResult);
        locationInfoResult = findViewById(R.id.locationInfoResult);
        latitudeInfoResult = findViewById(R.id.latitudeInfoResult);
        longitudeInfoResult = findViewById(R.id.longitudeInfoResult);

        //============================ Physical Attributes ====================================

        heightInfoResult = findViewById(R.id.heightInfoResult);
        weightInfoResult = findViewById(R.id.weightInfoResult);
        eyeColorResult = findViewById(R.id.eyeColorResult);
        hairInfoResult = findViewById(R.id.hairInfoResult);

        //============================ Personal Info & Contact ====================================

        firstNameResult = findViewById(R.id.firstNameResult);
        LastNameResult = findViewById(R.id.LastNameResult);
        maidenNameResult = findViewById(R.id.maidenNameResult);
        universityNameResult = findViewById(R.id.universityNameResult);
        genderResult = findViewById(R.id.genderResult);
        bloodResult = findViewById(R.id.bloodResult);
        birthDateResult = findViewById(R.id.birthDateResult);
        ageResult = findViewById(R.id.ageResult);
        emailInfoResult = findViewById(R.id.emailInfoResult);
        phoneInfoResult = findViewById(R.id.phoneInfoResult);

        imageCall = findViewById(R.id.imageCall);
        imageMessage = findViewById(R.id.imageMessage);
        imageWhatsapp = findViewById(R.id.imageWhatsapp);
        imageEmail = findViewById(R.id.imageEmail);

        //========================== Overview ====================================

        ppImage = findViewById(R.id.ppImage);
        fullUserName = findViewById(R.id.fullUserName);
        userName = findViewById(R.id.userName);
        userBloodGroup = findViewById(R.id.userBloodGroup);
        userCompanyName = findViewById(R.id.userCompanyName);
        userPosition = findViewById(R.id.userPosition);
        userEmail = findViewById(R.id.userEmail);
        copyEmail = findViewById(R.id.copyEmail);
        copyPhone = findViewById(R.id.copyPhone);
        userNumber = findViewById(R.id.userNumber);


        String userJsonString = getIntent().getStringExtra("userDataJson");
        String loggedEmail = getIntent().getStringExtra("loggedEmail");
        this.loggedEmails = loggedEmail;

        cardAdditional.setVisibility(GONE);

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
                    //Toast.makeText(this, "Hair Color: " + hairColor + ", Type: " + hairType, Toast.LENGTH_SHORT).show();
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

                            //Toast.makeText(this, "Company Coordinates: (" + companyLat + ", " + companyLng + ")", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                String cardNumberText = cardNumber;
                String ibanNumberText = iban;
                String passwordText = user.optString("password");
                //String cryptoWalletText = wallet;

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


                //================================= Overviews ====================================

                fullUserName.setText(firstName + " " + lastName);
                userName.setText("@" + username);
                userBloodGroup.setText(bloodGroup);
                userCompanyName.setText(companyName);
                userPosition.setText(title);
                userEmail.setText(email);
                userNumber.setText(phone);

                userBloodGroup.setOnClickListener(v -> {
                    startActivity(new Intent(UserDetailsActivity.this, QRGeneratorActivity.class));
                });


                //================================= Personal info & Contacts ====================================

                firstNameResult.setText(firstName);
                LastNameResult.setText(lastName);

                if (maidenName.isEmpty()) {
                    maidenNameResult.setText("N/A");
                } else {
                    maidenNameResult.setText(maidenName);
                }

                universityNameResult.setText(university);
                genderResult.setText(gender);
                bloodResult.setText(bloodGroup);

                String formatedDate = formatDate(birthDate);
                birthDateResult.setText(formatedDate);

                ageResult.setText(age);

                emailInfoResult.setText(email);
                phoneInfoResult.setText(phone);

                copyEmail.setOnClickListener(v -> {

                    String textToCopyEmail = email;

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Text", textToCopyEmail);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();

                });

                copyPhone.setOnClickListener(v -> {

                    String textToCopyPhone = phone;

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Text", textToCopyPhone);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();

                });


                imageCall.setOnClickListener(v -> {

                    String callNumber = phone;
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + callNumber));
                    startActivity(callIntent);

                });

                imageMessage.setOnClickListener(v -> {

                    String message = phone;
                    Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
                    messageIntent.setData(Uri.parse("sms:" + message));
                    startActivity(messageIntent);

                });

                imageWhatsapp.setOnClickListener(v -> {
                    String whatsappNumber = phone;
                    Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                    whatsappIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + whatsappNumber));
                    startActivity(whatsappIntent);
                });

                imageEmail.setOnClickListener(v -> {
                    sendEmail(email);
                });

                //Toast.makeText(this, "Logged Email: " + loggedEmails, Toast.LENGTH_SHORT).show();


                //================================ Physical Attributes ====================================

                heightInfoResult.setText(height);
                weightInfoResult.setText(weight);
                eyeColorResult.setText(eyeColor);
                hairInfoResult.setText(hairColor + " (" + hairType + ")");


                //================================= Residential Address ====================================

                streetInfoResult.setText(street);
                locationInfoResult.setText(city + ", " + state + " (" + stateCode + "), " + country + " - " + postalCode);
                latitudeInfoResult.setText(String.valueOf(lat));
                longitudeInfoResult.setText(String.valueOf(lng));

                //================================= Employee ====================================

                companyNameResult.setText(companyName);
                companyTitleResult.setText(title);
                companyDepartmentResult.setText(department);
                companyStreetResult.setText(companyStreet);
                companyLocationResult.setText(companyCity + ", " + companyState + " (" + companyPostalCode + "), " + companyCountry);

                //================================= Financial ====================================

                cardTypeInfoResult.setText(cardType);
                bankCurrencyInfoResult.setText(currency);
                cardExpiresInfoResult.setText(cardExpire);

                coinInfoResult.setText(coin);
                cryptoNetworkInfoResult.setText(network);


                //=============================== Masking Area Starts ====================================


                MaskingUtils.applyMaskToggle(cardNumberInfoResult, cardNumberText, 3, 3);
                MaskingUtils.applyMaskToggle(cardIBANInfoResult, ibanNumberText, 3, 3);
                MaskingUtils.applyMaskToggle(credentialsPasswordInfoResult, passwordText, 0, 0);
                MaskingUtils.applyMaskToggle(cryptoWalletInfoResult, wallet, 3, 3);

                additionalInfoContainer.setOnClickListener(v -> {

                    if (cardAdditional.getVisibility() == GONE) {
                        cardAdditional.setVisibility(View.VISIBLE);
                        additionalInfoContainer.setVisibility(GONE);
                    }

                });

                showLess.setOnClickListener(v -> {

                    if (cardAdditional.getVisibility() == View.VISIBLE) {
                        cardAdditional.setVisibility(GONE);
                        additionalInfoContainer.setVisibility(View.VISIBLE);
                    }

                });

                //================================== Security ====================================

//                credentialsPasswordInfoResult = findViewById(R.id.credentialsPasswordInfoResult);
//                credentialsEINInfoResult = findViewById(R.id.credentialsEINInfoResult);
//                credentialsSSNInfoResult = findViewById(R.id.credentialsSSNInfoResult);
//                credentialsRoleInfoResult = findViewById(R.id.credentialsRoleInfoResult);
//                iPInfoResult = findViewById(R.id.iPInfoResult);
//                mACAddressInfoResult = findViewById(R.id.mACAddressInfoResult);
//                userAgentInfoResult = findViewById(R.id.userAgentInfoResult);

                credentialsEINInfoResult.setText(ein);
                credentialsSSNInfoResult.setText(ssn);
                credentialsRoleInfoResult.setText(role);
                iPInfoResult.setText(ip);
                mACAddressInfoResult.setText(macAddress);
                //userAgentInfoResult.setText(userAgent);

                /*

                userAgentInfoResult.setSingleLine(true);
                userAgentInfoResult.setEllipsize(null);
                userAgentInfoResult.setHorizontallyScrolling(true);
                userAgentInfoResult.setMovementMethod(new ScrollingMovementMethod());

                 */

                userAgentInfoResult.setText(userAgent);


                //=============================== Masking Area Ends ====================================


                Picasso.get().load(image).into(ppImage);

                checkStoragePermission();

                String titles = title;


                quickShare.setOnClickListener(v -> {
                    QRUtils.showQRCodeDialog(

                            UserDetailsActivity.this, ""+firstName+" "+lastName, "@"+username, ""+titles, ""+phone, ""+email);
                });


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error parsing JSON data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No user data available", Toast.LENGTH_SHORT).show();
        }


    }

    private void sendEmail(String recipientEmail) {

        Uri uri = Uri.parse("mailto:" + Uri.encode(recipientEmail));
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject text here");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "This mail is from \n" + loggedEmails);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));


        try {
            startActivity(Intent.createChooser(emailIntent, "Send email...")); // ✅ Use activity context directly
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show(); // ✅ Use 'this'
        }
    }


    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }


    private String formatDate(String isoDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Make sure it's UTC if needed
            Date date = inputFormat.parse(isoDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
            outputFormat.setTimeZone(TimeZone.getDefault()); // Local timezone
            return outputFormat.format(date);
        } catch (Exception e) {
            return isoDate; // Fallback to raw if parsing fails
        }
    }

}
