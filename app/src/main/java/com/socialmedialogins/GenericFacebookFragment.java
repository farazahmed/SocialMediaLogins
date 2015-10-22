package com.socialmedialogins;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Profile;

/**
 * Created by faraz.ahmedabbasi on 10/19/2015.
 */
public class GenericFacebookFragment extends Fragment {
    View view;
    private TextView txt_info;
    Button btngmaillogin;
    Button btntwitterlogin;
    Button btnfblogin;
    MainActivityFacebook activity;
    boolean isGmailLogin = false;
    boolean isFacebookLogin = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.generic_fragment, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivityFacebook) getActivity();

        txt_info = (TextView)view.findViewById(R.id.txt_info);
        btnfblogin= (Button)view.findViewById(R.id.btnfblogin);
        btngmaillogin = (Button)view.findViewById(R.id.btngmaillogin);
        btngmaillogin.setVisibility(View.GONE);
        btnfblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.loginWithFacebook(new FacebookCallBack() {
                    @Override
                    public void socialInfo(Profile user, boolean success) {
                        if(!isFacebookLogin) {
                            btnfblogin.setText("Logout");
                            isFacebookLogin = true;
                            if(success) {
                                txt_info.setText(user.getFirstName());
                            }
                        }else {
                            btnfblogin.setText("Login");
                            txt_info.setText("info");
                            isFacebookLogin = false;
                            activity.logoutFb();
                        }
                    }
                });
            }
        });


    }
}

