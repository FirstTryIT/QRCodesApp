package c.dragos.qrcodesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private Button SignInButton;
    private Button SignOutButton;
    private Button ForgotPass;
    private FirebaseAuth.AuthStateListener myAuthStateListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        SignInButton = findViewById(R.id.SignInButton);
        SignOutButton = findViewById(R.id.SignOutButton);
        ForgotPass = findViewById(R.id.forgotPass);
        setupFirebaseListener();


        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                toastMessage("You have successfully Sign Out");

            }
        });



        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    toastMessage("You are successfully LOG IN with USER: " + user.getEmail());
                    startActivity(new Intent(MainActivity.this, Main_Page.class));//3
                } else {
                    toastMessage("You have to SIGN IN with a correct Email and Password");
                    startActivity(new Intent(MainActivity.this, Log_In_Informations.class));//2
                }
            }
        });

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Reset_Password.class));
            }
        });

    }

    private void setupFirebaseListener() {
        myAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(myAuthStateListener);
    }

    //Next function is used for showing a message down on the phone, like a notification
    private void toastMessage(String text) {
        Toast.makeText(MainActivity.this, text,
                Toast.LENGTH_SHORT).show();
    }

}