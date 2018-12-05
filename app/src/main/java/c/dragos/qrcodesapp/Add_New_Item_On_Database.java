package c.dragos.qrcodesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Add_New_Item_On_Database extends AppCompatActivity {

    private Button AddNewItem;
    private EditText ReadItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_item_on_database);

        AddNewItem = findViewById(R.id.AddNewItemInDatabaseButton);
        ReadItemName = findViewById(R.id.ReadItemNameTextView);

        AddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ItemName = ReadItemName.getText().toString();

                if (!ItemName.equals("")) {

                    DatabaseReference myDatabaseRef;
                    myDatabaseRef = FirebaseDatabase.getInstance().getReference();

                    Items currentItem;

                    currentItem = new Items(ItemName,true," "," "," ");
                    myDatabaseRef.child("Available Items").push().setValue(currentItem);

                    toastMessage("Item was added with success!");
                    startActivity(new Intent(Add_New_Item_On_Database.this, Main_Page.class));

                }
                else {

                    toastMessage("You didn't fill the Item name's field!");

                }



            }
        });

    }

    //Next function is used for showing a message down on the phone, like a notification
    private void toastMessage(String text) {
        Toast.makeText(Add_New_Item_On_Database.this, text,
                Toast.LENGTH_SHORT).show();
    }

}

