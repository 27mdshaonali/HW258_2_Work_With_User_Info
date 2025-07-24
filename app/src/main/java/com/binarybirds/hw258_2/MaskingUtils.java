package com.binarybirds.hw258_2;

import android.view.View;
import android.widget.TextView;

public class MaskingUtils {

    // Static method to mask middle characters
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

    // Static method to apply toggle masking on TextView
    public static void applyMaskToggle(final TextView textView, final String originalText, final int unmaskedStart, final int unmaskedEnd) {
        final String maskedText = maskMiddle(originalText, unmaskedStart, unmaskedEnd);
        textView.setText(maskedText); // Set masked text initially

        textView.setOnClickListener(new View.OnClickListener() {
            private boolean isMasked = true;

            @Override
            public void onClick(View v) {
                textView.setText(isMasked ? originalText : maskedText);
                isMasked = !isMasked;
            }
        });
    }
}
