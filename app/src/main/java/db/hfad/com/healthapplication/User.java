package db.hfad.com.healthapplication;

import android.app.Activity;

/**
 * Created by Lolita on 2016-10-13.
 */

public class User {
    private String username;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() { return username;}

    public void setUsername(String username){this.username = username;}
}
