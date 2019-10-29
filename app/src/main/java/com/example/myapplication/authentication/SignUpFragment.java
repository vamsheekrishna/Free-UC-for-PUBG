package com.example.myapplication.authentication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSignUpBinding;
import com.example.myapplication.fragments.BaseFragment;
import com.example.myapplication.models.UserViewModel;
import com.example.myapplication.models.Utilities;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class SignUpFragment extends BaseFragment implements View.OnClickListener {

    private static final int RC_SIGN_IN = 1001;
    //https://firebase.google.com/docs/auth/android/google-signin?utm_source=studio
    private UserViewModel mViewModel = new UserViewModel();

    private OnAuthenticationInteractionListener mListener;
    private GoogleSignInClient mGoogleSignInClient;
    CheckBox privacyClick;
    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //mListener.setText(String.valueOf(this.getTag()));
        FragmentSignUpBinding FragmentGoogleAuthBindingImpl = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up,container,false);
        View view = FragmentGoogleAuthBindingImpl.getRoot();//inflater.inflate(R.layout.registration_fragment, container, false);

        privacyClick = view.findViewById(R.id.privacy_click);
        TextView textView = view.findViewById(R.id.privacy_text);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView.setOnClickListener(this);
        view.findViewById(R.id.submit).setOnClickListener(this);

        // google sign in start
        FragmentGoogleAuthBindingImpl.setRegistration(mViewModel);

        SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        updateUI(account);

        // Google sign-in end

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sign_in_button:
                if(privacyClick.isChecked()) {
                    signIn();
                } else {
                    Toast.makeText(getActivity(), "Please Selected the Privacy text.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.submit:
                if(!TextUtils.isEmpty(mViewModel.getName()) && !TextUtils.isEmpty(mViewModel.getMobile())) {
                    if(privacyClick.isChecked()) {
                        mobileVerification();
                    } else {
                        Toast.makeText(getActivity(), "Please Selected the Privacy text.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.privacy_text:
                mListener.goToPrivacyContent();
                break;
        }

        /*if(!TextUtils.isEmpty(mViewModel.getName()) && !TextUtils.isEmpty(mViewModel.getMobile())) {
            Toast.makeText(getActivity(), mViewModel.getName()+" ,"+mViewModel.getMobile(), Toast.LENGTH_SHORT).show();
            //Utilities.generateProfile(getActivity(), mViewModel);
            //mListener.gotoHomeFragment();



        } else {
            showToast();
        }*/
    }

    private void mobileVerification() {
        UserViewModel userViewModel  = new UserViewModel();
        mListener.goToValidateOTPFragment(userViewModel);
        Toast.makeText(getActivity(), "mobileVerification", Toast.LENGTH_SHORT).show();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(GoogleSignInAccount account) {
        if(null != account) {
            UserViewModel userViewModel  = new UserViewModel();
            userViewModel.setName(account.getDisplayName());
            userViewModel.setEmail(account.getEmail());
            Utilities.generateProfile(getActivity(), userViewModel);
            mListener.gotoHome();
            Log.d("account: ", account.toString());
            //Toast.makeText(getActivity(), "Name: "+account.getDisplayName(), Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    private void showToast() {
        Toast.makeText(getActivity(), "Please fill all fields",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAuthenticationInteractionListener) {
            mListener = (OnAuthenticationInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("google", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}
