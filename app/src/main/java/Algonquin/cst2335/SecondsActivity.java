package Algonquin.cst2335;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.contract.ActivityResultContracts;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondsActivity extends AppCompatActivity {
    private ImageView profileImage;
    private Bitmap imageBitmap;
    private static final String IMAGE_FILENAME = "Picture.png";
    private EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconds);

        phoneEditText = findViewById(R.id.editTextPhone);
        profileImage = findViewById(R.id.imageView);
        Button callButton = findViewById(R.id.buttonCall);
        EditText phoneEditText = findViewById(R.id.editTextPhone);
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedPhoneNumber = prefs.getString("PhoneNumber", "");
        phoneEditText.setText(savedPhoneNumber);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        TextView emailTextView = findViewById(R.id.textViewEmailAddress);
        emailTextView.setText("Welcome Back " + emailAddress);
        String filename = IMAGE_FILENAME;
        File file = new File(getFilesDir(), filename);
        if (file.exists()) {
            // Load the image into a Bitmap
            imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath()); // Use getAbsolutePath() method
            // Set the ImageView's bitmap
            profileImage.setImageBitmap(imageBitmap);
        }



        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneEditText.getText().toString();

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));

                startActivity(callIntent);
            }
        });

        captureImage();
    }

    private void captureImage() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),

                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        FileOutputStream fOut = null;
                        try {
                            fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        profileImage.setImageBitmap(imageBitmap);
                    }
                }
        );

        cameraResult.launch(cameraIntent);

    }
    @Override
    protected void onPause() {
        super.onPause();

        // Save the current phone number to SharedPreferences
        String currentPhoneNumber = phoneEditText.getText().toString();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber", currentPhoneNumber);
        editor.apply();
    }
}

