package br.com.mribeiro.marylimp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateAccoutActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_accout);

        setupView();
    }

    private void setupView() {
        Button pessoaFisicaButton = findViewById(R.id.pfSelectButton);
        Button pessoaJuridicaButton = findViewById(R.id.pjSelectButton);
        final ScrollView pJScrollView = findViewById(R.id.pjScrollView);
        final ScrollView pfScrollView = findViewById(R.id.pfScrollView);
        pessoaFisicaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pJScrollView.setVisibility(View.GONE);
                pfScrollView.setVisibility(View.VISIBLE);
            }
        });
        pessoaJuridicaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pfScrollView.setVisibility(View.GONE);
                pJScrollView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void pFSubmitForm(View view) {
        TextInputLayout nome, sobrenome, cpf, telefone, senha, senha2, email;
        nome = findViewById(R.id.pfNomeTextInput);
        sobrenome = findViewById(R.id.pfSobrenomeTextInput);
        cpf = findViewById(R.id.pfCPFTextInput);
        telefone = findViewById(R.id.pfTelefoneTextInput);
        senha = findViewById(R.id.pfSenhaTextInput);
        senha2 = findViewById(R.id.pfSenhaRepeatTextInput);
        email = findViewById(R.id.pfEmailTextInput);

        final ProgressDialog dialog = new ProgressDialog(CreateAccoutActivity.this, "Criando conta");
        final Map<String, Object> user = new HashMap<>();

        if (nome.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (sobrenome.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (cpf.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (telefone.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (senha.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (senha2.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (false) {
            Toast.makeText(getApplicationContext(), "As senhas devem ser iguais", Toast.LENGTH_LONG).show();
        } else if (email.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else {

            user.put("cpf", cpf.getEditText().getText().toString());
            user.put("email", email.getEditText().getText().toString());
            user.put("fname", nome.getEditText().getText().toString());
            user.put("sname", sobrenome.getEditText().getText().toString());
            user.put("type", 0);
            user.put("phone", telefone.getEditText().getText().toString());
            user.put("creatd", new Date());

            dialog.show();
            auth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), senha.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    db.collection("users").document(authResult.getUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Erro ao subir dados de usuário para a núvem", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Erro ao criar conta, tente novamente mais tarde", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void pJSubmitForm(View view) {
        TextInputLayout cnpj, razao, cpf, representante, telefone, email, senha, senha2;
        cnpj = findViewById(R.id.pjCNPJTextInput);
        razao = findViewById(R.id.pjRazaoTextInput);
        cpf = findViewById(R.id.pjCPFTextInput);
        representante = findViewById(R.id.pjNomeTextInput);
        telefone = findViewById(R.id.pjTelefoneTextInput);
        email = findViewById(R.id.pjEmailTextInput);
        senha = findViewById(R.id.pjSenhaTextInput);
        senha2 = findViewById(R.id.pjSenhaRepeatTextInput);

        final ProgressDialog dialog = new ProgressDialog(CreateAccoutActivity.this, "Criando conta");
        final Map<String, Object> user = new HashMap<>();

        if (cnpj.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (razao.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (cpf.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (telefone.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (senha.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (senha2.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (senha.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "As senhas devem ser iguais", Toast.LENGTH_LONG).show();
        } else if (email.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else if (representante.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preecha todos os campos corretamente", Toast.LENGTH_LONG).show();
        } else {
            user.put("cpf", cpf.getEditText().getText().toString());
            user.put("email", email.getEditText().getText().toString());
            user.put("cnpj", cnpj.getEditText().getText().toString());
            user.put("razao", razao.getEditText().getText().toString());
            user.put("type", 1);
            user.put("phone", telefone.getEditText().getText().toString());
            user.put("creatd", new Date());
            user.put("rep", representante.getEditText().getText().toString());

            dialog.show();
            auth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), senha.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    db.collection("users").document(authResult.getUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Erro ao subir dados de usuário para a núvem", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Erro ao criar conta, tente novamente mais tarde", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
