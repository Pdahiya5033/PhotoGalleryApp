package com.example.myphotogallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class SignUpFragment extends Fragment {
    private static final String TAG="SignUpFragment";
    private Button mSignUpButton;
    private UsersDataClass user;
    private EditText mUserName,mEmail,mPassword;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    public static SignUpFragment newInstance(){
        return new SignUpFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.sign_up_fragment,container,false);
        mEmail=(EditText) view.findViewById(R.id.signUp_email_editText);
        mUserName=(EditText) view.findViewById(R.id.signUp_userName_editText);
        mPassword=(EditText) view.findViewById(R.id.signUp_password_editText);
        mSignUpButton=(Button) view.findViewById(R.id.signUpButton);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=new UsersDataClass(mEmail.getText().toString());
                user.setPassword(mPassword.getText().toString());
                user.setUserName(mUserName.getText().toString());
                UserDetailStore.get(getActivity()).addUser(user);
                Intent i=new Intent(getActivity(),PhotoGallery.class);
                startActivity(i);
                Log.i(TAG,"got this user"+UserDetailStore.get(getActivity()).getUser(user.getUserName()).getEmail());
            }
        });
        return view;
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
//        setVisibility(mLinearLayout).setVisibility(View.VISIBLE);
    }
    public static LinearLayout setVisibility(LinearLayout linearLayout){
        return linearLayout;
    }

}
