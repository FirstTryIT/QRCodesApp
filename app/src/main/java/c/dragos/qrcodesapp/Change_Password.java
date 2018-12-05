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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Change_Password extends AppCompatActivity {

    private ImageButton imageButtonCurrentPassword;
    private ImageButton imageButtonNewPassword;
    private ImageButton imageButtonConfirmPassword;

    private EditText CurrentPasswordEditText;
    private EditText NewPasswordEditText;
    private EditText ConfirmPasswordEditText;

    private Button SavePasswordButton;

    //Next counter is used for counting the times when the image Button for Current password field (for setting the visibility of password ON or OFF) was pressed
    private int CurrentPasswordCnt = 0;

    //Next counter is used for counting the times when the image Button for new password field (for setting the visibility of password ON or OFF) was pressed
    private int NewPasswordCnt = 0;

    //Next counter is used for counting the times when the image Button for confirm password field (for setting the visibility of password ON or OFF) was pressed
    private int ConfirmPasswordCnt = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        imageButtonCurrentPassword = findViewById(R.id.imageButtonCurrentPassword);
        imageButtonNewPassword = findViewById(R.id.imageButtonNewPassword);
        imageButtonConfirmPassword = findViewById(R.id.imageButtonConfirmPassword);

        CurrentPasswordEditText  =findViewById(R.id.CurrentPasswordTextView);
        NewPasswordEditText = findViewById(R.id.NewPasswordTextView);
        ConfirmPasswordEditText = findViewById(R.id.ConfirmPasswordTextView);

        SavePasswordButton  = findViewById(R.id.SavePasswordButton);

        imageButtonCurrentPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the button is never pressed or is pressed with an even number times, the password is Transformed into *
                //else, the password is visible
                if ((CurrentPasswordCnt % 2) == 0) {

                    imageButtonCurrentPassword.setBackgroundResource(R.mipmap.visibility_off_photo);
                    CurrentPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }


                else {
                    imageButtonCurrentPassword.setBackgroundResource(R.mipmap.visibility_on_photo);
                    CurrentPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                CurrentPasswordCnt++;
            }
        });

        imageButtonNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the button is never pressed or is pressed with an even number times, the password is Transformed into *
                //else, the password is visible
                if ((NewPasswordCnt % 2) == 0) {

                    imageButtonNewPassword.setBackgroundResource(R.mipmap.visibility_off_photo);
                    NewPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }


                else {
                    imageButtonNewPassword.setBackgroundResource(R.mipmap.visibility_on_photo);
                    NewPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                NewPasswordCnt++;
            }

        });

        imageButtonConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the button is never pressed or is pressed with an even number times, the password is Transformed into *
                //else, the password is visible
                if ((ConfirmPasswordCnt % 2) == 0) {

                    imageButtonConfirmPassword.setBackgroundResource(R.mipmap.visibility_off_photo);
                    ConfirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }


                else {
                    imageButtonConfirmPassword.setBackgroundResource(R.mipmap.visibility_on_photo);
                    ConfirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                ConfirmPasswordCnt++;
            }

        });

        SavePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Now verify if all the fields are filled
                if ((!CurrentPasswordEditText.getText().toString().equals("")) && (!NewPasswordEditText.getText().toString().equals("")) && (!ConfirmPasswordEditText.getText().toString().equals(""))) {

                    //Now verify if new password and confirm password fields match
                    if (NewPasswordEditText.getText().toString().equals(ConfirmPasswordEditText.getText().toString())) {

                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


                        AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(),CurrentPasswordEditText.getText().toString());
                        currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    currentUser.updatePassword(NewPasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                toastMessage("Password updated successful!");
                                                startActivity(new Intent(Change_Password.this, Main_Page.class));

                                            } else {
                                                toastMessage("Error.Password was not updated!");
                                            }
                                        }
                                    });
                                } else {
                                    toastMessage("Current password is not correct");
                                }

                            }
                        });
                    }

                    else {

                        toastMessage("New password and confirm password fields don't match!");
                    }
                }
                else {

                    toastMessage("ERROR. You didn't fill all the fields!");
                }
            }
        });
    }

    //Next function is used for showing a message down on the phone, like a notification
    private void toastMessage(String text) {
        Toast.makeText(Change_Password.this, text,
                Toast.LENGTH_SHORT).show();
    }

}
