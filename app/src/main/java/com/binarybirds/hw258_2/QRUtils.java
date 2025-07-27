package com.binarybirds.hw258_2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRUtils {

    public static void showQRCodeDialog(Context context, String name, String username, String position, String phone, String email) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.qr_code, null);

        ImageView qrCodeImage = dialogView.findViewById(R.id.qrCodeImage);
        Button btnDownload = dialogView.findViewById(R.id.btnDownload);
        Button btnShare = dialogView.findViewById(R.id.btnShare);

        String qrData = "Name: " + name + "\nUsername: " + username + "\nPosition: " + position + "\nPhone: " + phone + "\nEmail: " + email;

        final Bitmap[] qrBitmapHolder = new Bitmap[1];

        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            qrBitmapHolder[0] = encoder.encodeBitmap(qrData, BarcodeFormat.QR_CODE, 600, 600);
            qrCodeImage.setImageBitmap(qrBitmapHolder[0]);
        } catch (WriterException e) {
            Toast.makeText(context, "Error generating QR Code", Toast.LENGTH_SHORT).show();
            return;
        }

        btnDownload.setOnClickListener(v -> {
            if (qrBitmapHolder[0] != null) {
                if (checkStoragePermission(context)) {
                    saveImageToGallery(context, qrBitmapHolder[0]);
                }
            }
        });

        btnShare.setOnClickListener(v -> {
            if (qrBitmapHolder[0] != null) {
                shareQRCode(context, qrBitmapHolder[0]);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setCancelable(true);

        builder.show();

    }

    private static boolean checkStoragePermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((android.app.Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return false;
        }
        return true;
    }

    private static void saveImageToGallery(Context context, Bitmap bitmap) {
        try {
            File path = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "QRCode");
            if (!path.exists()) path.mkdirs();

            File file = new File(path, "qrcode_" + System.currentTimeMillis() + ".png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();

            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);

            Toast.makeText(context, "QR Code saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Failed to save QR Code!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private static void shareQRCode(Context context, Bitmap bitmap) {
        try {
            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs();
            File file = new File(cachePath, "shared_qr.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(intent, "Share QR Code"));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to share QR Code", Toast.LENGTH_SHORT).show();
        }
    }
}
