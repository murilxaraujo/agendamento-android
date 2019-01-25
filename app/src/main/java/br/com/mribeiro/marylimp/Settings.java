package br.com.mribeiro.marylimp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Settings extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextInputLayout first, second, third, fourth, fifth, sixth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        first = findViewById(R.id.firstTIL);
        second = findViewById(R.id.secondTIL);
        third = findViewById(R.id.thirdTIL);
        fourth = findViewById(R.id.fourthTIL);
        fifth = findViewById(R.id.fifthTIL);
        sixth = findViewById(R.id.sixthTIL);

        setup();
    }

    private void setup() {
        if (auth.getCurrentUser() == null) {
            finish();
        } else {
            db.collection("users").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (task.getResult().getDouble("type").intValue() == 0) {
                            //Pessoa Física
                            first.setHint("Nome");
                            first.getEditText().setText(doc.getString("fname")+ " " + doc.getString("sname"));

                            second.setHint("CPF");
                            second.getEditText().setText(doc.getString("cpf"));

                            third.setHint("Telefone");
                            third.getEditText().setText(doc.getString("phone"));

                            fourth.setHint("E-mail");
                            fourth.getEditText().setText(doc.getString("email"));

                        } else {
                            //Pessoa Jurídica
                            first.setHint("Razão social");
                            first.getEditText().setText("razao");

                            second.setHint("CNPJ");
                            second.getEditText().setText("cnpj");

                            third.setHint("Telefone");
                            third.getEditText().setText("phone");

                            fourth.setHint("E-mail");
                            fourth.getEditText().setText(doc.getString("email"));

                            fifth.setHint("Representante legal");
                            fifth.getEditText().setText("rep");
                            fifth.setVisibility(View.VISIBLE);

                            sixth.setHint("CPF Representante Legal");
                            sixth.getEditText().setText("cpf");
                            sixth.setVisibility(View.VISIBLE);

                        }

                    } else {

                    }
                }
            });
        }
    }

    public void signOut(View view) {
        auth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
