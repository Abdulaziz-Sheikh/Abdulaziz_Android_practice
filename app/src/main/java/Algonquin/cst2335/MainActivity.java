package Algonquin.cst2335;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    EditText editTextEmail;




    ImageView imgView;
    Switch sw;

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("MainActivity", "onStop: Activity is no longer visible to the user.");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("MainActivity", "onStart: Activity is becoming visible to the user.");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause: Activity is partially obscured or not in focus but still visible.");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume: Activity is in the foreground and user interaction is possible.");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity", "onDestroy: Activity is being destroyed, either by user or system.");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.w("MainActivity", "onCreate: Activity is being created.");
        //Log.w( "MainActivity", "In onCreate() - Loading Widgets" );

        Button loginButton = findViewById(R.id.buttonLogin); // Find the "Login" button by its ID
        EditText editTextEmail = findViewById(R.id.editTextEmail);



        // Retrieve the saved email address from SharedPreferences and set it in the EditText
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        editTextEmail.setText(emailAddress);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to start the second activity
                // Retrieve the email address from the EditText
                String emailAddress = editTextEmail.getText().toString();

                // Get a reference to SharedPreferences
                SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

                // Create a SharedPreferences.Editor
                SharedPreferences.Editor editor = prefs.edit();

                // Save the email address
                editor.putString("LoginName", emailAddress);

                // Apply the changes to persist them
                editor.apply();


                Intent intent = new Intent(MainActivity.this, SecondsActivity.class);
                intent.putExtra( "EmailAddress", editTextEmail.getText().toString() );
                startActivity(intent);

            }
        });


        ;
    }
}


