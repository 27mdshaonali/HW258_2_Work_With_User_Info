package com.binarybirds.hw258_2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ModeratorUserDetailsActivity extends AppCompatActivity {

    TextView cardNumberTV, cardIBANInfoResult;
    String cardNumberText = "1234567890123456"; // Example, ideally passed from Intent
    String ibanNumberText = "BD12345678901234567890"; // Example, ideally passed from Intent
    String phoneNumberText = "01796470921"; // Example, ideally passed from Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moderator_user_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TextViews from layout
        cardNumberTV = findViewById(R.id.cardNumberInfoResult);
        cardIBANInfoResult = findViewById(R.id.cardIBANInfoResult);
        TextView phoneNumberInfoResult = findViewById(R.id.userNumber);

        //=============================== Masking Area Starts ====================================
        /*
        applyMaskToggle(cardNumberTV, cardNumberText, 3, 3);
        applyMaskToggle(cardIBANInfoResult, ibanNumberText, 3, 3);
        applyMaskToggle(phoneNumberInfoResult, phoneNumberText, 0, 3);

         */
        //=============================== Masking Area Ends ====================================
    }

    //========================== Masking Methods ====================================

    /*
    public static String maskMiddle(String input, int unmaskedStart, int unmaskedEnd) {
        if (input == null || input.length() <= (unmaskedStart + unmaskedEnd)) {
            return input; // Not enough characters to mask
        }

        int maskLength = input.length() - unmaskedStart - unmaskedEnd;
        StringBuilder masked = new StringBuilder();

        masked.append(input.substring(0, unmaskedStart)); // First visible part
        for (int i = 0; i < maskLength; i++) {
            masked.append("*");
        }
        masked.append(input.substring(input.length() - unmaskedEnd)); // Last visible part

        return masked.toString();
    }

    public void applyMaskToggle(final TextView textView, final String originalText, final int unmaskedStart, final int unmaskedEnd) {
        final String maskedText = maskMiddle(originalText, unmaskedStart, unmaskedEnd);
        textView.setText(maskedText); // Set masked text initially

        textView.setOnClickListener(new android.view.View.OnClickListener() {
            private boolean isMasked = true;

            @Override
            public void onClick(android.view.View v) {
                textView.setText(isMasked ? originalText : maskedText);
                isMasked = !isMasked;
            }
        });
    }

     */
}
