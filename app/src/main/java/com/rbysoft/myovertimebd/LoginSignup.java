package com.rbysoft.myovertimebd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginSignup extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    SignInButton mysigninbutton;
    Button skipbutton;

    public static  final int RC_SIGN_IN=102;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);


        mysigninbutton=findViewById(R.id.signInButton);
        mAuth=FirebaseAuth.getInstance();
        skipbutton=findViewById(R.id.skipbutton);

        if (mAuth.getInstance().getCurrentUser()!=null){
            updateUI(mAuth.getInstance().getCurrentUser());
        }



       gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mysigninbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signIn();
            }
        });
        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginSignup.this,MyMainActivity.class);
                Saving.SaveABoolean(LoginSignup.this,"isFirstTime",true);

                startActivity(intent);
                finish();

            }
        });

    }

    private void signIn() {
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }


    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);


        } catch (ApiException e) {

        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);


                        } else {
                            Toast.makeText(LoginSignup.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
    private void updateUI(FirebaseUser currentUser) {
        //  Toast.makeText(this, currentUser.getUid(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,MyMainActivity.class);
        startActivity(intent);
        finish();
    }
}
