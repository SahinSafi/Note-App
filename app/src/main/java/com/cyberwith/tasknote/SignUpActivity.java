package com.cyberwith.tasknote;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signUpEmail, signUpPassword;
    private Button signUpButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpEmail = findViewById(R.id.signUpEmailID);
        signUpPassword = findViewById(R.id.signUpPasswordID);
        signUpButton = findViewById(R.id.signUpButtonID);
        auth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signUpButtonID){
            userRegister();
        }

    }

    private void userRegister() {
        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();

        //check email validity
        if(email.isEmpty()){
            signUpEmail.setError("Enter email");
            signUpEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpEmail.setError("Enter valid email");
            signUpEmail.requestFocus();
            return;
        }

        //check password validity
        if(password.isEmpty()){
            signUpPassword.setError("Enter password");
            signUpPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            signUpPassword.setError("Enter 6 digit password");
            signUpPassword.requestFocus();
            return;
        }

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete( Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Toast.makeText(SignUpActivity.this," Register is successful ",Toast.LENGTH_LONG).show();
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){

                        Toast.makeText(SignUpActivity.this," User already registered ",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this," Register is failed ",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });




    }





}
