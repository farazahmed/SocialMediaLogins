package com.socialmedialogins;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by faraz.ahmedabbasi on 10/19/2015.
 */
public class GenericFragment extends Fragment {
    View view;
    private TextView txt_info;
    Button btngmaillogin;
    Button btntwitterlogin;
    Button btnfblogin;
    MainActivity activity;
    boolean isGmailLogin = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.generic_fragment, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) getActivity();
        btnfblogin = (Button)view.findViewById(R.id.btnfblogin);
        btnfblogin.setVisibility(View.GONE);
        txt_info = (TextView)view.findViewById(R.id.txt_info);
        btngmaillogin = (Button)view.findViewById(R.id.btngmaillogin);
        btngmaillogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isGmailLogin) {
                    activity.onSignInClicked(new com.socialmedialogins.SocialCallBack() {
                        @Override
                        public void socialInfo(SocialUser user, boolean success) {
                            if(success) {
                                btngmaillogin.setText("Gmail logout");
                                isGmailLogin = true;
                                txt_info.setText(user.getEmail() + " " + user.getName());
                            }
                        }
                    });
                }else {
                    activity.onSignOutClicked();
                    btngmaillogin.setText("Gmail login");
                    txt_info.setText("info");
                    isGmailLogin = false;
                }
            }
        });




    }
}

