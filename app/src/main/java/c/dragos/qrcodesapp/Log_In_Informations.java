package c.dragos.qrcodesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Bogdan on 17.09.2018.
 */

public class Log_In_Informations extends AppCompatActivity {

    private EditText Password;
    private EditText Email;

    private ProgressBar progress;

    private FirebaseAuth myAuth;
    private ImageButton imageButton;
    //Next counter is used for counting the times when the image Button (for setting the visibility of password ON or OFF) was pressed
    private int cnt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_informations);

        Password = findViewById(R.id.Password);
        Email = findViewById(R.id.Email);

        Button SignInButton = findViewById(R.id.button);
        imageButton = findViewById(R.id.imageButton);

        progress=(ProgressBar)findViewById(+R.id.progressBar);

        myAuth = FirebaseAuth.getInstance();

        progress.setVisibility(View.INVISIBLE);

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = Password.getText().toString();
                String email = Email.getText().toString();

                if (!email.equals("") && !pass.equals("")) {
                    //if all fields are completed, verify if email and password is correct
                    checkInformationsForLogIn(email,pass);
                }
                else {
                    toastMessage("You didn't fill all the fields!");
                }


            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the button is never pressed or is pressed with an even number times, the password is Transformed into *
                //else, the password is visible
                if ((cnt % 2) == 0) {

                    imageButton.setBackgroundResource(R.mipmap.visibility_off_photo);
                    Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }


                else {
                    imageButton.setBackgroundResource(R.mipmap.visibility_on_photo);
                    Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                cnt++;
            }
        });


    }

    public void checkInformationsForLogIn(String email, String pass) {
        progress.setVisibility(View.VISIBLE);

        myAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()) {
                    toastMessage("Authentication failed. Check email,password and your network connection!");
                    progress.setVisibility(View.INVISIBLE);
                }
                else {
                    toastMessage("Authentication successful");
                    startActivity(new Intent(Log_In_Informations.this, Main_Page.class));//3
                    progress.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    //Next function is used for showing a message down on the phone, like a notification
    private void toastMessage(String text) {
        Toast.makeText(Log_In_Informations.this, text,
                Toast.LENGTH_SHORT).show();
    }
}
