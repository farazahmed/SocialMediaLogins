package com.socialmedialogins;

/**
 * Created by faraz.ahmedabbasi on 10/19/2015.
 */
public class SocialUser {
    private String name;
    private   String email;

    public SocialUser(String currentAccount, String name) {
        setEmail(currentAccount);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
