package com.socialmedialogins;


import com.facebook.Profile;

/**
 * Created by faraz.ahmedabbasi on 10/19/2015.
 */
public interface FacebookCallBack {
    public void socialInfo(Profile user, boolean success);
}
