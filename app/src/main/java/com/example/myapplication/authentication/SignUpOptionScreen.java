package com.example.myapplication.authentication;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpOptionScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpOptionScreen extends Fragment implements View.OnClickListener {


    private static final int RC_SIGN_IN = 1001;
    //https://firebase.google.com/docs/auth/android/google-signin?utm_source=studio

    private GoogleSignInClient mGoogleSignInClient;

    private CheckBox privacyClick;
    private OnAuthenticationInteractionListener onAuthenticationInteractionListener;
    public SignUpOptionScreen() {
        // Required empty public constructor
    }

    public static SignUpOptionScreen newInstance(String param1, String param2) {
        SignUpOptionScreen fragment = new SignUpOptionScreen();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  OnAuthenticationInteractionListener) {
            onAuthenticationInteractionListener = (OnAuthenticationInteractionListener) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onAuthenticationInteractionListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_option_screen, container, false);
        privacyClick = view.findViewById(R.id.privacy_click);
        view.findViewById(R.id.mobile).setOnClickListener(this);
        view.findViewById(R.id.sign_in_button).setOnClickListener(this);
        TextView textView = view.findViewById(R.id.privacy_text);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView.setOnClickListener(this);

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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
    private void updateUI(GoogleSignInAccount account) {
        if(null != account) {
            UserViewModel userViewModel  = new UserViewModel();
            userViewModel.setName(account.getDisplayName());
            userViewModel.setEmail(account.getEmail());
            Utilities.generateProfile(getActivity(), userViewModel);
            onAuthenticationInteractionListener.gotoHome();
            Log.d("account: ", account.toString());
            //Toast.makeText(getActivity(), "Name: "+account.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.privacy_text:
                onAuthenticationInteractionListener.goToPrivacyContent();
                break;
            case R.id.sign_in_button:
                if(privacyClick.isChecked()) {
                    signIn();
                } else {
                    Toast.makeText(getActivity(), "Please Selected the Privacy text.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mobile:
                if(privacyClick.isChecked()) {
                    onAuthenticationInteractionListener.goToSignUpFragment();
                } else  {
                    Toast.makeText(getActivity(), "Please Selected the Privacy text.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
