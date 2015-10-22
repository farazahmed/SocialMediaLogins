package com.socialmedialogins;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.socialmedialogins.FacebookCallBack;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by faraz.ahmedabbasi on 10/19/2015.
 */
public class MainActivityFacebook extends FragmentActivity{

    private CallbackManager callbackManager;
    private FacebookCallBack facebookCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logTokens();
        if (findViewById(R.id.headlines_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            GenericFacebookFragment genericFragment = new GenericFacebookFragment();
            genericFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.headlines_fragment, genericFragment).commit();
        }



        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult result) {
                //updateUI();
               /* mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        Log.v("facebook - profile", profile2.getFirstName());
                        updateUI(profile2);
                        mProfileTracker.stopTracking();
                    }
                };
                mProfileTracker.startTracking();*/


                if (null != result.getAccessToken()) {
                    if (Profile.getCurrentProfile() != null) {
                        GraphRequest.newMeRequest(
                                result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject me, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                        } else {
                                            String email = me.optString("email");
                                            String id = me.optString("id");
                                            // send email and id to your web server
                                            updateUI(Profile.getCurrentProfile(), email);
                                        }
                                    }
                                }).executeAsync();
                    }

                } else { // try again
                    loginWithFacebook();
                }
            }


            @Override
            public void onError(FacebookException error) {
                //updateUI();
              /*  mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        //Log.v("facebook - profile", profile2.getFirstName());
                        updateUI(profile2);
                        mProfileTracker.stopTracking();
                    }
                };
                mProfileTracker.startTracking();*/
                Toast.makeText(MainActivityFacebook.this, "Login failed, please try again", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivityFacebook.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void updateUI(Profile profile, String email) {

        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        if (enableButtons && profile != null) {
            Toast.makeText(MainActivityFacebook.this, profile.getFirstName(), Toast.LENGTH_LONG).show();
            if(facebookCallBack != null) {
                facebookCallBack.socialInfo(profile,true);
            }
        } else {
            // login false
            Toast.makeText(MainActivityFacebook.this, "Login failed, please try again", Toast.LENGTH_SHORT).show();
            facebookCallBack.socialInfo(null, false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void logTokens() {

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                Log.d("SHA1-KeyHash:::",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));

                md = MessageDigest.getInstance("MD5");
                md.update(signature.toByteArray());
                Log.d("MD5-KeyHash:::",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));

                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("SHA-Hex-From-KeyHash:::", bytesToHex(md.digest()));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    final protected static char[] hexArray = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public void loginWithFacebook(com.socialmedialogins.FacebookCallBack facebookCallBack) {
        this.facebookCallBack = facebookCallBack;
        LoginManager.getInstance().logInWithReadPermissions(MainActivityFacebook.this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    private void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(MainActivityFacebook.this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void logoutFb() {
        LoginManager.getInstance().logOut();
    }


}
