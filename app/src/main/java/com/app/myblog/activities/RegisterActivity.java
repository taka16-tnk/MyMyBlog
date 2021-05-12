package com.app.myblog.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.myblog.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView imgRegUserPhoto;
    static int IReqCode = 1;
    static int REQUEST_CODE = 1;
    Uri pickedImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgRegUserPhoto = findViewById(R.id.regUserPhoto);
        imgRegUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestPermission();
                } else {
                    openGallery();
                }
                
            }
        });
    }


    private void openGallery() {
        // TODO: open gallery intent and wait for user to pick an image

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/");
        startActivityForResult(galleryIntent, REQUEST_CODE);

    }


    private void checkAndRequestPermission() {

        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(RegisterActivity.this,
                        "Please accept for required permission",
                        Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        IReqCode);
            }
        } else {
            openGallery();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE &&
                data != null) {
            // 画像選択成功時, URI変数への参照を保存
            pickedImgUri = data.getData();
            imgRegUserPhoto.setImageURI(pickedImgUri);

        }
    }
}