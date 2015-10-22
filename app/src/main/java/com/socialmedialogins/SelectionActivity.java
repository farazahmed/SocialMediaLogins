package com.socialmedialogins;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by faraz.ahmedabbasi on 10/22/2015.
 */
public class SelectionActivity extends FragmentActivity implements  View.OnClickListener {
    Button btnFacebook;
    Button btnGmail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        btnFacebook = (Button)findViewById(R.id.btnfblogin);
        btnGmail = (Button)findViewById(R.id.btngmaillogin);
        btnFacebook.setOnClickListener(this);
        btnGmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnfblogin:
                startActivity(new Intent(this,MainActivityFacebook.class));
                break;
            case R.id.btngmaillogin:
                startActivity(new Intent(this,MainActivity.class));
                break;
            default:
                break;

        }

    }
}
