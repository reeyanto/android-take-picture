package com.riyanto.androidtakepicture;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivImage = findViewById(R.id.iv_image);
        Button btnTakePicture = findViewById(R.id.btn_takepicture);

        btnTakePicture.setOnClickListener(view -> takePicture());

        takePictureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent intentData = result.getData();
                    Bundle extra = intentData.getExtras();

                    if (extra != null) {
                        Bitmap imageBitmap = (Bitmap) extra.get("data");
                        ivImage.setImageBitmap(imageBitmap);
                    }
                }
            }
        );
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            takePictureLauncher.launch(intent);
        }
    }
}