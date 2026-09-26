package com.example.doan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    Button btnlogin, btnregister, btnforgotpass,btnloginsdt;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    EditText STK, Pass;
    Button btngg;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



         STK = findViewById(R.id.STK);
         Pass= findViewById(R.id.Pass);

         Pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

         btnlogin = findViewById(R.id.btnlogin);
         btnlogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (STK.getText().toString().trim().isEmpty() || Pass.getText().toString().trim().isEmpty()) {
                     Toast.makeText(Login.this, "Please input your username and password!",
                             Toast.LENGTH_LONG).show();

                     return;
                 }
                 mAuth = FirebaseAuth.getInstance();
                 String email = STK.getText().toString();
                 String pass = Pass.getText().toString();
                 mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(Login.this,"login successful", Toast.LENGTH_SHORT).show();
                             Intent i = new Intent(Login.this, MainActivity.class);
                             i.putExtra("username", STK.getText());
                             i.putExtra("password", Pass.getText());
                             startActivity(i);
                         }
                         else {
                             Toast.makeText(Login.this,"login unsuccessful", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });


             }
         });

         btnregister = findViewById(R.id.btnregister);
         btnregister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Login.this, register.class);
                 startActivity(intent);
             }
         });

         btnloginsdt= findViewById(R.id.btnloginsdt);
         btnloginsdt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(Login.this,LoginSdtActivity.class);
                 startActivity(i);
             }
         });


         mAuth= FirebaseAuth.getInstance();
         database = FirebaseDatabase.getInstance();

         progressDialog =new ProgressDialog(Login.this);
         progressDialog.setTitle("Creating account");
         progressDialog.setMessage("We are creating account");

         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.default_web_client_id))
                 .requestEmail().build();

         mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

         btngg = findViewById(R.id.btnlogingg);
         btngg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 signIn();
             }
         });



    }

    int RC_SIGN_IN =40;
    private void signIn() {
        Intent intent =mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            }catch (ApiException e){
                throw  new RuntimeException(e);
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            Users users = new Users();
                            users.setUserID(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setProfile(user.getPhotoUrl().toString());

                            //database.getReference().child("Users").child(user.getUid()).setValue(users);
                            Intent i = new Intent(Login.this,MainActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(Login.this,"error",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }
}