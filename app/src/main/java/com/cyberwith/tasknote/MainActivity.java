package com.cyberwith.tasknote;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView signUpText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.loginEmailID);
        passwordEditText = findViewById(R.id.loginPasswordID);
        loginButton = findViewById(R.id.loginButtonID);
        signUpText = findViewById(R.id.signUpTextID);
        auth = FirebaseAuth.getInstance();
        sharedPreferenceHome();

        loginButton.setOnClickListener(this);
        signUpText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButtonID:
                userLogin();
                break;
            case R.id.signUpTextID:
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userLogin() {
        final String userEmail = emailEditText.getText().toString().trim();
        final String userPassword = passwordEditText.getText().toString().trim();

        if(userEmail.isEmpty()){
            emailEditText.setError("Enter E-mail");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            emailEditText.setError("Enter valid email");
            emailEditText.requestFocus();
            return;
        }

        if(userPassword.isEmpty()){
            passwordEditText.setError("Enter password");
            passwordEditText.requestFocus();
            return;
        }
        if(userPassword.length()<6){
            passwordEditText.setError("Enter minimum 6 digit password");
            passwordEditText.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Store login data on sharedPreference
                    SharedPreferences sharedPreferences = getSharedPreferences("UserID", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("emailKey",userEmail);
                    editor.putString("passwordKey",userPassword);
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this," Incorrect account ",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void sharedPreferenceHome(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserID",Context.MODE_PRIVATE);
        if(sharedPreferences.contains("emailKey") && sharedPreferences.contains("passwordKey")){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
