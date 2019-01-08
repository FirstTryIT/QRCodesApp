package c.dragos.qrcodesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Modify the layout, too for finding the group of item

public class Add_New_Item_On_Database extends AppCompatActivity {

    private Button AddNewItem;
    private EditText ReadItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_item_on_database);

        AddNewItem = findViewById(R.id.AddNewItemInDatabaseButton);
        ReadItemName = findViewById(R.id.ReadItemNameTextView);

        //next statement is used for hiding the soft keyboard when is clicked outside from Edit Text boxes.
        findViewById(R.id.addNewItemLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;

            }
        });

        AddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ItemName = ReadItemName.getText().toString();
                String ItemGroup;

                if (ItemName.contains("DEM") || ItemName.contains("Dem") || ItemName.contains("dem")) {
                    ItemGroup = "DEM";
                }

                else
                    if (ItemName.contains("Monitor") || ItemName.contains("MONITOR") || ItemName.contains("monitor")) {
                      ItemGroup = "Monitor";
                     }

                    else
                        if (ItemName.contains("BDC") || ItemName.contains("bdc") || ItemName.contains("Bdc")) {
                          ItemGroup = "BDC";
                        }

                        else
                            if (ItemName.contains("CAM") || ItemName.contains("Cam") || ItemName.contains("cam")) {
                              ItemGroup = "CAM";
                              }
                            else
                                if (ItemName.contains("can") || ItemName.contains("CAN") || ItemName.contains("Can")) {
                                  ItemGroup = "CANoe";
                               }
                                else
                                    if (ItemName.contains("Lauterbach") || ItemName.contains("lauterbach") || ItemName.contains("LAUTERBACH")) {
                                        ItemGroup = "Lauterbach";
                                     }
                                    else
                                        if (ItemName.contains("Phone") || ItemName.contains("phone") || ItemName.contains("PHONE")) {
                                         ItemGroup = "Phone";
                                         }
                                        else {
                                            ItemGroup = "Others";
                                        }




                if (!ItemName.equals("")) {

                    DatabaseReference myDatabaseRef;
                    myDatabaseRef = FirebaseDatabase.getInstance().getReference();

                    Items currentItem;

                    currentItem = new Items(ItemName,true," "," "," ","",ItemGroup);
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

