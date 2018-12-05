package c.dragos.qrcodesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Bogdan on 18.09.2018.
 */

public class View_Informations_about_User extends AppCompatActivity {

    private Button SaveButton;
    private Button ChangePasswordButton;

    private EditText Name;
    private EditText Email;
    private EditText PhoneNumber;

    private DatabaseReference myDatabaseRef;
    private FirebaseAuth myAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_informations_about_user);


        SaveButton = findViewById(R.id.SaveButton);
        ChangePasswordButton = findViewById(R.id.ChangePasswordButton);

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        PhoneNumber = findViewById(R.id.PhoneNumber);

        myDatabaseRef  = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        currentUser = myAuth.getCurrentUser();


        if (currentUser != null) {
            showInformationsFromFirebase();
        }

        //next statement is used for hiding the soft keyboard when is clicked outside from Edit Text boxes.
        findViewById(R.id.infoAboutUserLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;

            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = Name.getText().toString();
                String userEmail = Email.getText().toString();
                String userPhoneNumber = PhoneNumber.getText().toString();

                WriteNewUserOnDatabase(currentUser.getUid(),userName, userPhoneNumber, userEmail);
            }
        });

        ChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(View_Informations_about_User.this, Change_Password.class));

            }
        });

    }

    private void showInformationsFromFirebase(){

        myDatabaseRef.child("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FevUser user = dataSnapshot.getValue(FevUser.class);
                Name.setText(user.Name);
                Email.setText(user.Email);
                PhoneNumber.setText(user.PhoneNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void WriteNewUserOnDatabase(String ID, String Name, String PhoneNumber, String Email) {

        FevUser user = new FevUser(Name, PhoneNumber,Email,false);

        myDatabaseRef.child("Users").child(ID).setValue(user);

        toastMessage("Informations saved successful!");
        startActivity(new Intent(View_Informations_about_User.this, Main_Page.class));//3

    }

    private void toastMessage(String text) {
        Toast.makeText(View_Informations_about_User.this, text,
                Toast.LENGTH_SHORT).show();
    }
}
