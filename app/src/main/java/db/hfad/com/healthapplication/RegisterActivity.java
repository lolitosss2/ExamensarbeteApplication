package db.hfad.com.healthapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Lolita on 2016-10-12.
 */

public class RegisterActivity extends Activity{
    private EditText nameRegister;
    private EditText emailRegister;
    private EditText passwordRegister;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    private Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameRegister = (EditText)findViewById(R.id.UserNameField);
        emailRegister = (EditText)findViewById(R.id.EmailField);
        passwordRegister = (EditText)findViewById(R.id.PasswordField);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mProgress = new ProgressDialog(this);

        signUpButton = (Button)findViewById(R.id.SignupBtnField);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {

        final String name = nameRegister.getText().toString().trim();
        final String email = emailRegister.getText().toString().trim();
        final String password = passwordRegister.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgress.setMessage("Signing Up...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference cureent_user_db = mDatabase.child(user_id);

                            cureent_user_db.child("name").setValue(name);

                            User user= new User();
                            user.setUsername(name);
                            Log.i("Name",user.getUsername().toString());

                            mProgress.dismiss();

                            nameRegister.getText().clear();
                            emailRegister.getText().clear();
                            passwordRegister.getText().clear();

                            Intent registerIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                            registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(registerIntent);
                        }
                    }
                });
        }

    }
}
