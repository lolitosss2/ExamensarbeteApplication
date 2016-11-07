package db.hfad.com.healthapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Lolita on 2016-10-17.
 */
public class UserProfile extends AppCompatActivity{
    private EditText userName;
    private EditText userAge;
    private Spinner userGender;
    private Button applyProfileSettings;

    private FirebaseDatabase database;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);

        userName = (EditText)findViewById(R.id.userNameFieldEditText);
        userAge = (EditText)findViewById(R.id.AgeFieldEditText);
        userGender = (Spinner)findViewById(R.id.GenderSpinnerField);

        database = FirebaseDatabase.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        mProgress = new ProgressDialog(this);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_edittext, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        userGender.setAdapter(adapter);

        applyProfileSettings = (Button)findViewById(R.id.DoneBtnField);
        applyProfileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserExist();
            }
        });
    }

    private void checkUserExist() {
        final String name = userName.getText().toString().trim();
        final String age = userAge.getText().toString().trim();
        final String gender = userGender.getSelectedItem().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(gender)) {
            mProgress.setMessage("Applying changes...");
            mProgress.show();
            if (mAuth.getCurrentUser() != null) {
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference cureent_user_db = mDatabaseUsers.child(user_id);

                cureent_user_db.child("name").setValue(name);
                cureent_user_db.child("age").setValue(age);
                cureent_user_db.child("gender").setValue(gender);

                mProgress.dismiss();

                startActivity(new Intent(UserProfile.this, HealthApp.class));

            }
        } else {
            mProgress.dismiss();
            Toast.makeText(UserProfile.this,"Please fill all fields",Toast.LENGTH_LONG).show();
        }
    }
    //save userProfile
    //saveData
    public void saveInfo(View view){
        SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userName",userName.getText().toString());
        editor.putString("userAge",userAge.getText().toString());
        editor.putString("userGender",userGender.getSelectedItem().toString());
        editor.apply();

        Toast.makeText(this,"Saved!",Toast.LENGTH_LONG).show();
    }



}
