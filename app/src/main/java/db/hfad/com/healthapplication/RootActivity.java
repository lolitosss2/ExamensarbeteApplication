package db.hfad.com.healthapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Thread.sleep;

/**
 * Created by Lolita on 2016-11-02.
 */

public class RootActivity extends Activity{

    private FirebaseDatabase database;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;

    private int progress = 0;
    private final int pBarMax = 60;
    //private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);

        database = FirebaseDatabase.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();

        /*imageview = (ImageView)findViewById(R.id.imageViewFeatherField);
        TransitionDrawable drawable = (TransitionDrawable)imageview.getDrawable();
        drawable.startTransition(1000);*/


        final ProgressBar pBar = (ProgressBar) findViewById(R.id.progressBar1);
        pBar.setVisibility(View.VISIBLE);
        pBar.setMax(pBarMax);
        final Thread pBarThread = new Thread() {
            @Override
            public void run() {
                try {
                    while(progress<=pBarMax) {
                        pBar.setProgress(progress);
                        sleep(1000);
                        ++progress;
                    }
                }
                catch(InterruptedException e) {
                }
            }
        };

        pBarThread.start();

        /*imageview.setImageResource(R.drawable.trans);
        ((TransitionDrawable) imageview.getDrawable()).startTransition(2000);*/

        checkUserExist();
    }



    private void checkUserExist() {
        if(mAuth.getCurrentUser() != null) {
            final String user_id = mAuth.getCurrentUser().getUid();

            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild(user_id)) {

                        check();


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        else{
            Intent setupIntent = new Intent(this,MainActivity.class);
            setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(setupIntent);
        }
    }

    private void check(){
        Intent setupIntent = new Intent(this,HealthApp.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(setupIntent);
    }


}
