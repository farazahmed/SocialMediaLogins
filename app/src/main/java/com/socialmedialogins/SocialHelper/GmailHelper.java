/*
package com.socialmedialogins.SocialHelper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.socialmedialogins.R;
import com.socialmedialogins.SocialUser;

*/
/**
 * Created by faraz.ahmedabbasi on 10/19/2015.
 *//*

public class GmailHelper implements  View.OnClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    com.socialmedialogins.SocialCallBack socialCallBack = null;
    private static final String TAG = "MainActivity";
    */
/* RequestCode for resolutions involving sign-in *//*

    private static final int RC_SIGN_IN = 1;
    */
/* RequestCode for resolutions to get GET_ACCOUNTS permission on M *//*

    private static final int RC_PERM_GET_ACCOUNTS = 2;
    */
/* Keys for persisting instance variables in savedInstanceState *//*

    private static final String KEY_IS_RESOLVING = "is_resolving";
    private static final String KEY_SHOULD_RESOLVE = "should_resolve";
    */
/* Client for accessing Google APIs *//*

    private GoogleApiClient mGoogleApiClient;


    // [START resolution_variables]
    */
/* Is there a ConnectionResult resolution in progress? *//*

    private boolean mIsResolving = false;

    */
/* Should we automatically resolve ConnectionResults when possible? *//*

    private boolean mShouldResolve = false;
    // [END resolution_variables]



    Context context;
    GmailHelper(Context context){
        this.context = context;

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .build();


    }

    private void showSignedOutUI() {
        updateUI(false);
    }


    private boolean checkAccountsPermission() {
        final String perm = Manifest.permission.GET_ACCOUNTS;
        int permissionCheck = ContextCompat.checkSelfPermission(this, perm);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // We have the permission
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
            // Need to show permission rationale, display a snackbar and then request
            // the permission again when the snackbar is dismissed.
            Snackbar.make(findViewById(R.id.main_layout),
                    "Required permissions",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Request the permission again.
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{perm},
                                    RC_PERM_GET_ACCOUNTS);
                        }
                    }).show();
            return false;
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{perm},
                    RC_PERM_GET_ACCOUNTS);
            return false;
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            if (currentPerson != null) {
                // Show signed-in user's name
                String name = currentPerson.getDisplayName();
                //mStatus.setText(getString(R.string.signed_in_fmt, name));

                showMessage(name);

                // Show users' email address (which requires GET_ACCOUNTS permission)
                if (checkAccountsPermission()) {
                    String currentAccount = Plus.AccountApi.getAccountName(mGoogleApiClient);
                    //((TextView) findViewById(R.id.email)).setText(currentAccount);
                    showMessage(currentAccount);
                    if(socialCallBack != null){
                        socialCallBack.socialInfo(new SocialUser(currentAccount,name),true);
                    }

                }
            } else {
                // If getCurrentPerson returns null there is generally some error with the
                // configuration of the application (invalid Client ID, Plus API not enabled, etc).
                Log.w(TAG, "Null person");
                //mStatus.setText(getString(R.string.signed_in_err));
                showMessage("Sign in error");
                socialCallBack.socialInfo(null, false);
            }

            // Set button visibility
            //findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            // Show signed-out message and clear email field
            //mStatus.setText(R.string.signed_out);
            showMessage("Signed out");
            */
/*((TextView) findViewById(R.id.email)).setText("");

            // Set button visibility
            findViewById(R.id.sign_in_button).setEnabled(true);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);*//*

        }
    }



    // [START on_sign_out_clicked]
    public void onSignOutClicked() {
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

        showSignedOutUI();
    }

    // [START on_disconnect_clicked]
    private void onDisconnectClicked() {
        // Revoke all granted permissions and clear the default account.  The user will have
        // to pass the consent screen to sign in again.
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

        showSignedOutUI();
    }


    // [START on_sign_in_clicked]
    public void onSignInClicked(com.socialmedialogins.SocialCallBack socialCallBack) {
        this.socialCallBack = socialCallBack;
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        mShouldResolve = true;
        mGoogleApiClient.connect();
        // Show a message to the user that we are signing in.
        //mStatus.setText(R.string.signing_in);
        showMessage("Signing In");
    }
    private void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

}
*/
