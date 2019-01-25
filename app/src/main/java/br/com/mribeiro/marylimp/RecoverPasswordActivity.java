package br.com.mribeiro.marylimp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class RecoverPasswordActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button btn;
    TextInputLayout emailtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        btn = findViewById(R.id.singinButton);
        emailtv = findViewById(R.id.textInputLayout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (emailtv.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo de email vazio", Toast.LENGTH_LONG).show();
                } else {
                    auth.sendPasswordResetEmail(emailtv.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Você receberá um e-mail com os dados para redefinir sua senha", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
    }

    public void dismiss(View view) {
        this.finish();
    }

    public void request(final View view) {

    }
}
