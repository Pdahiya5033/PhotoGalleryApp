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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {
    private static final String TAG="LoginFragment";
    private EditText mUserName,mPassword;
    private Button mLoginButton;
    private TextView mTextView;
    private UsersDataClass userLogin;
    public static LoginFragment newInstance(){
        return new LoginFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.login_fragment,container,false);
        mLoginButton=(Button) view.findViewById(R.id.loginButton);
        mUserName=(EditText) view.findViewById(R.id.userName_editText);
        mPassword=(EditText) view.findViewById(R.id.password_editText);
        mTextView=(TextView) view.findViewById(R.id.signUp_quote_text);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm= getActivity().getSupportFragmentManager();
                Fragment fragment=SignUpFragment.newInstance();
                fm.beginTransaction().replace(R.id.fragment_container_signUp,fragment).commit();
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin=new UsersDataClass();
                userLogin.setUserName(mUserName.getText().toString());
                userLogin.setPassword(mPassword.getText().toString());
                if(checkUser(userLogin)==1){
                    Intent i=new Intent(getActivity(),PhotoGallery.class);
                    startActivity(i);
                }
                else if(checkUser(userLogin)==0){
                    mPassword.setText(null);
                }
                else{
                    mUserName.setText(null);
                    mPassword.setText(null);
                }
            }
        });
        return view;
    }
    public int checkUser(UsersDataClass user){
        int passValue=-1,i=0;
        boolean param1,param2;
        List<UsersDataClass> listUsers;
        listUsers=UserDetailStore.get(getActivity()).getUsers();
        Log.i(TAG,"got "+listUsers.size()+" users");
        for(i=0;i<listUsers.size();i++){
            param1=user.getUserName().equals(listUsers.get(i).getUserName());
            param2=user.getPassword().equals(listUsers.get(i).getPassword());
            if(param1&&param2){
                passValue=1;
                user.setEmail(listUsers.get(i).getEmail());
                Toast.makeText(getActivity(),"Loading photos",Toast.LENGTH_SHORT).show();
                break;
            }
            else if(param1&&!param2){
                passValue=0;
                Toast.makeText(getActivity(),"Oops! incorrect password",Toast.LENGTH_SHORT).show();
                break;
            }
            if(!param1&&!param2){
                continue;
            }
        }
        if(i==listUsers.size()){
            Toast.makeText(getActivity(),"no such user registered",Toast.LENGTH_SHORT).show();
            return -1;
        }
        else{
            return passValue;
        }
    }
}
