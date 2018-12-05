package c.dragos.qrcodesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_Password extends AppCompatActivity {

    private EditText email;
    private Button resetPassword;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initUI();
        onResetPasswordClick();
    }


    private void initUI() {
        email = (EditText) findViewById(+R.id.ResetPassEmail);
        resetPassword = (Button) findViewById(+R.id.ResetPassButton);
    }

    private void onResetPasswordClick(){
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail;

                userEmail = email.getText().toString();

                if (userEmail.isEmpty()) {
                    email.setError("Introduceti adresa de email!");
                    email.requestFocus();
                    return;
                }else{
                    auth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Emailul de resetare a parolei a fost trimis!", Toast.LENGTH_SHORT).show();
                                        Intent window = new Intent(Reset_Password.this,Log_In_Informations.class);
                                        startActivity(window);
                                        finish();
                                    } else{
                                        Toast.makeText(getApplicationContext(), "Emailul introdus nu exista in baza de date!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
