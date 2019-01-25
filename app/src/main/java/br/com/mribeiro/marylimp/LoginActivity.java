package br.com.mribeiro.marylimp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText email, password;
    Intent recintent , suintent, mainintent;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    LottieAnimationView loadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailTextEdit);
        password = findViewById(R.id.passwordEditText);
        recintent = new Intent(this, RecoverPasswordActivity.class);
        suintent = new Intent(this, CreateAccoutActivity.class);
        mainintent = new Intent(this, MainActivity.class);
        loadingAnimation = findViewById(R.id.loginLoadingAnimation);
        loadingAnimation.setVisibility(View.GONE);
    }

    public void openForgPass(View view) {
        startActivity(recintent);
    }

    public void signIn(View view) {
        final Button signinbuton = findViewById(R.id.singinButton);
        if (TextUtils.isEmpty(email.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "Campo de e-mail vazio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "Campo de senha vazio", Toast.LENGTH_LONG).show();
        } else {
            loadingAnimation.setVisibility(View.VISIBLE);
            loadingAnimation.playAnimation();
            signinbuton.setEnabled(false);
            auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        loadingAnimation.cancelAnimation();
                    loadingAnimation.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    signinbuton.setEnabled(true);
                }
                }
            });
        }
    }

    public void openSignUp(View view) {
        startActivity(suintent);
    }

    @Override
    public void onBackPressed() { }
}
