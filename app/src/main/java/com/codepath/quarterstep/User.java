package com.codepath.quarterstep;

import com.parse.ParseUser;

public class User extends ParseUser {

    private static final String KEY_FIRST = "firstName";
    private static final String KEY_LAST = "lastName";

    private ParseUser user;

    public User(ParseUser user) {
        this.user = user;
    }

    public String getFirstName() {
        return getString(KEY_FIRST);
    }

    public void setFirstName(String firstName) {
        put(KEY_FIRST, firstName);
    }

    public String getLastName() {
        return getString(KEY_LAST);
    }

    public void setLastName(String lastName) {
        put(KEY_LAST, lastName);
    }
}
