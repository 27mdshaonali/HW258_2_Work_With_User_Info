package com.binarybirds.hw258_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRGeneratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);

        // Sample user info from server (you can replace with dynamic data)
        String name = "John Doe";
        String username = "john123";
        String position = "Developer";
        String phone = "01700000000";
        String email = "john@example.com";

//        qrData = "Name: " + name +
//                "\nUsername: " + username +
//                "\nPosition: " + position +
//                "\nPhone: " + phone +
//                "\nEmail: " + email;
//
//        btnGenerate.setOnClickListener(v -> generateQRCode());
//
//        btnDownload.setOnClickListener(v -> {
//            if (qrBitmap != null) {
//                saveImageToGallery(qrBitmap);
//            } else {
//                Toast.makeText(this, "Please generate the QR code first!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnShare.setOnClickListener(v -> {
//            if (qrBitmap != null) {
//                shareQRCode(qrBitmap);
//            } else {
//                Toast.makeText(this, "Please generate the QR code first!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        checkStoragePermission();
//    }
//
//    private void generateQRCode() {
//        BarcodeEncoder encoder = new BarcodeEncoder();
//        try {
//            qrBitmap = encoder.encodeBitmap(qrData, BarcodeFormat.QR_CODE, 600, 600);
//            qrCodeImage.setImageBitmap(qrBitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void saveImageToGallery(Bitmap bitmap) {
//        try {
//            File path = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "QRCode");
//            if (!path.exists()) path.mkdirs();
//
//            File file = new File(path, "qrcode_" + System.currentTimeMillis() + ".png");
//            FileOutputStream stream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            stream.flush();
//            stream.close();
//
//            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), null);
//            Toast.makeText(this, "QR Code saved successfully!", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            Toast.makeText(this, "Failed to save QR Code!", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }
//
//    private void shareQRCode(Bitmap bitmap) {
//        try {
//            File cachePath = new File(getCacheDir(), "images");
//            cachePath.mkdirs();
//            File file = new File(cachePath, "shared_qr.png");
//            FileOutputStream stream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            stream.close();
//
//            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
//
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("image/png");
//            intent.putExtra(Intent.EXTRA_STREAM, uri);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            startActivity(Intent.createChooser(intent, "Share QR Code"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Failed to share QR Code", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void checkStoragePermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
//        }
    }
}
