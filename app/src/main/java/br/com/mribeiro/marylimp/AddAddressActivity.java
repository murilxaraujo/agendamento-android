package br.com.mribeiro.marylimp;

import android.content.DialogInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;
import com.stepstone.stepper.StepperLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddAddressActivity extends AppCompatActivity {
    ConstraintLayout firstPage, secondPage, thirdPage, fourthPage, fifthPage, sixthPage;
    Button firstNextButton, secondNextButton;
    Button firstBackButton, secondBackButton, thirdBackButton , fouthBackButton, fifthBackButton;
    ProgressDialog dialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ProgressDialog dialog1;
    //SecondPageVariables
    Integer tipoDeImovel;

    //ThirdPageVariables
    Integer metragemDoImovel;

    //FourthPageVariables
    Integer quantidadeDeBanheiros;

    //FifthPageVariables
    String cepDoImovel, logradouroDoImovel, numeroDoImovel, complementoDoImovel, bairroDoImovel, cidadeDoImovel, estadoDoImovel;
    TextInputLayout cepTextInputLayout, logradouroTextInputLayout, numeroTextInputLayout, complementoTextInputLayout, bairroTextInputLayout, cidadeTextInputLayout, estadoTextInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        setupViewVariables();
    }

    public void goToPage(int page) {
        hideAllPages();
        showPage(page);
    }

    private void hideAllPages() {
        firstPage.setVisibility(View.GONE);
        secondPage.setVisibility(View.GONE);
        thirdPage.setVisibility(View.GONE);
        fourthPage.setVisibility(View.GONE);
        fifthPage.setVisibility(View.GONE);
        sixthPage.setVisibility(View.GONE);
    }

    private void showPage(int page) {
        switch (page) {
            case 1:
                firstPage.setVisibility(View.VISIBLE);
                break;
            case 2:
                secondPage.setVisibility(View.VISIBLE);
                break;
            case 3:
                thirdPage.setVisibility(View.VISIBLE);
                break;
            case 4:
                fourthPage.setVisibility(View.VISIBLE);
                break;
            case 5:
                fifthPage.setVisibility(View.VISIBLE);
                break;
            case 6:
                sixthPage.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setupViewVariables() {
        firstPage = findViewById(R.id.addAddressFirstPage);
        secondPage = findViewById(R.id.addAddressSecondPage);
        thirdPage = findViewById(R.id.addAddressThirdPage);
        fourthPage = findViewById(R.id.addAddressFourthPage);
        fifthPage = findViewById(R.id.addAddressFithPage);

        dialog = new ProgressDialog(AddAddressActivity.this, "");
        dialog1 = new ProgressDialog(AddAddressActivity.this, "Buscando");
        firstBackButton = findViewById(R.id.firstBackButton);
        firstBackButton.setOnClickListener(v -> finish());

        firstNextButton = findViewById(R.id.firstNextButton);
        firstNextButton.setOnClickListener(v -> goToPage(2));

        secondBackButton = findViewById(R.id.secondBackButton);
        secondBackButton.setOnClickListener(v -> goToPage(1));

        secondNextButton = findViewById(R.id.secondNextButton);
        secondNextButton.setOnClickListener(v -> goToPage(3));

        thirdBackButton = findViewById(R.id.thirdBackButton);
        thirdBackButton.setOnClickListener(v -> goToPage(2));

        fouthBackButton = findViewById(R.id.fourthPageBackButton);
        fouthBackButton.setOnClickListener(v -> goToPage(3));

        fifthBackButton = findViewById(R.id.fifthPageBackButton);
        fifthBackButton.setOnClickListener(v -> goToPage(4));

        //Sets up second page

        Button residenciabutton, escritorioButton, outrosButton;
        residenciabutton = findViewById(R.id.secondPageResidenciaButton);
        escritorioButton = findViewById(R.id.secondPageEscritorioButton);
        outrosButton = findViewById(R.id.secondPageOthersButton);
        residenciabutton.setOnClickListener(v -> {
            tipoDeImovel = 0;
            if (canGoToPage(3)) {
                goToPage(3);
            } else {

            }
        });
        escritorioButton.setOnClickListener(v -> {
            tipoDeImovel = 1;
            if (canGoToPage(3)) {
                goToPage(3);
            } else {

            }
        });
        outrosButton.setOnClickListener(v -> {
            // TODO: 15/04/19 Add function to contact

        });

        //Sets up third Page

        final TextInputLayout metragemTextInputLayout;
        SeekBar metragemSeekBar;
        metragemTextInputLayout = findViewById(R.id.thirdPageMetragemTextInput);
        metragemSeekBar = findViewById(R.id.thirdPageMetragemSeekBar);
        metragemSeekBar.setMax(300);
        metragemSeekBar.setVisibility(View.GONE);
        metragemSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                metragemDoImovel = progress;
                metragemTextInputLayout.getEditText().setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button thirdPageNextButton = findViewById(R.id.thirdPageNextButton);
        thirdPageNextButton.setOnClickListener(v -> {
            metragemDoImovel = Integer.valueOf(metragemTextInputLayout.getEditText().getText().toString());
            if (canGoToPage(4)) {
                goToPage(4);
            } else {
                Toast.makeText(getApplicationContext(), "Você precisa inserir uma metragem válida para prosseguir", Toast.LENGTH_LONG).show();
            }
        });

        //Sets up fourth Page

        Button oneButton, twoButton, threeButton, fourButton;
        oneButton = findViewById(R.id.fourthPageOneButton);
        twoButton = findViewById(R.id.fourthPageTwoButton);
        threeButton = findViewById(R.id.fourthPageThirdButton);
        fourButton = findViewById(R.id.fourthPageFourPlusButton);
        oneButton.setOnClickListener(v -> {
            quantidadeDeBanheiros = 1;
            if (canGoToPage(5)) {
                goToPage(5);
            } else {

            }
        });
        twoButton.setOnClickListener(v -> {
            quantidadeDeBanheiros = 2;
            if (canGoToPage(5)) {
                goToPage(5);
            } else {
                Toast.makeText(getApplicationContext(), "Você precisa preencher todos os dados obrigatórios corretamente para prosseguir", Toast.LENGTH_LONG).show();
            }

        });
        threeButton.setOnClickListener(v -> {
            quantidadeDeBanheiros = 3;
            if (canGoToPage(5)) {
                goToPage(5);
            } else {
                Toast.makeText(getApplicationContext(), "Você precisa preencher todos os dados obrigatórios corretamente para prosseguir", Toast.LENGTH_LONG).show();
            }
        });
        fourButton.setOnClickListener(v -> {
            quantidadeDeBanheiros = 4;
            if (canGoToPage(5)) {
                goToPage(5);
            } else {
                Toast.makeText(getApplicationContext(), "Você precisa preencher todos os dados obrigatórios corretamente para prosseguir", Toast.LENGTH_LONG).show();
            }
        });
        Button fourthBackButton;
        fourthBackButton = findViewById(R.id.fourthPageBackButton);
        fourthBackButton.setOnClickListener(v -> goToPage(3));

        //Sets up fifth page

        Button fifthBackButton;
        fifthBackButton = findViewById(R.id.fifthPageBackButton);
        fifthBackButton.setOnClickListener(v -> goToPage(4));
        cepTextInputLayout = findViewById(R.id.fifthPageCEPTextView);
        logradouroTextInputLayout = findViewById(R.id.fifthPageLogradouroTextView);
        numeroTextInputLayout = findViewById(R.id.fifthPageNumberTextView);
        complementoTextInputLayout = findViewById(R.id.fifthPageComplementoTextView);
        bairroTextInputLayout = findViewById(R.id.fifthPageBairroTextView);
        cidadeTextInputLayout = findViewById(R.id.fifthPageCidadeTextView);
        estadoTextInputLayout = findViewById(R.id.fifthPageEstadoTextView);

        cepTextInputLayout.setOnFocusChangeListener((v, hasFocus) -> {
            if (cepTextInputLayout.getEditText().getText().length() != 8) {
                Toast.makeText(getApplicationContext(), "O CEP precisa ter 8 digitos, apenas numeros", Toast.LENGTH_LONG).show();
            } else {
                getStringByCEP(cepTextInputLayout.getEditText().getText().toString());
                dialog.setTitle("Buscando CEP");
                dialog.show();
            }
        });

        ImageButton searchIB = findViewById(R.id.fifthPageSearchButton);
        searchIB.setOnClickListener(v -> {
            if (cepTextInputLayout.getEditText().getText().length() != 8) {
                Toast.makeText(getApplicationContext(), "O CEP precisa ter 8 digitos, apenas numeros", Toast.LENGTH_LONG).show();
            } else {
                getStringByCEP(cepTextInputLayout.getEditText().getText().toString());

                dialog1.show();
            }
        });

        Button fifthPageNextButton = findViewById(R.id.fifthPageNextButton);
        fifthPageNextButton.setOnClickListener(v -> saveInfoToDataBase());

        // Calls the view

        hideAllPages();
        showPage(1);
    }

    private boolean canGoToPage(int page) {
        switch (page) {
            case 1:
                return true;

            case 2:
                return true;

            case 3:
                if (tipoDeImovel == null || tipoDeImovel < 0 || tipoDeImovel > 2) {
                    return false;
                } else {
                    return true;
                }
            case 4:
                if (metragemDoImovel == null || metragemDoImovel < 20 || metragemDoImovel > 300 ) {
                    return false;
                } else {
                    return true;
                }
            case 5:
                if (quantidadeDeBanheiros == null || quantidadeDeBanheiros < 1 || quantidadeDeBanheiros > 4) {
                    return false;
                } else {
                    return true;
                }
            case 6:
                if (cepDoImovel == null || cepDoImovel == "" || logradouroDoImovel == null || logradouroDoImovel == "" || bairroDoImovel == null || bairroDoImovel == "" || estadoDoImovel == null || estadoDoImovel == "" || cidadeDoImovel == null || cidadeDoImovel == "") {
                    return false;
                } else {
                    return true;
                }

        }
        return false;
    }

    private void getStringByCEP(String cep) {
        String url = "https://webmaniabr.com/api/1/cep/"+cep+"/?app_key=isGR3dBUvtMjYuKo6YTZcsXRydQfAvJp&app_secret=kLpoxqQc8spkJg3OMRMPa3DSsJzs9yiffvTF8ifKEuZKO5Y3";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Thread thread = new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                transformStringToJson(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
                dialog1.dismiss();
            }
        });
        thread.start();
    }

    private void transformStringToJson(String objectString) {

        try {
            JSONObject obj = new JSONObject(objectString);
            Looper.prepare();
            logradouroTextInputLayout.setHintAnimationEnabled(false);
            bairroTextInputLayout.setHintAnimationEnabled(false);
            cidadeTextInputLayout.setHintAnimationEnabled(false);
            estadoTextInputLayout.setHintAnimationEnabled(false);
            logradouroTextInputLayout.getEditText().setText(obj.getString("endereco"));
            bairroTextInputLayout.getEditText().setText(obj.getString("bairro"));
            cidadeTextInputLayout.getEditText().setText(obj.getString("cidade"));
            estadoTextInputLayout.getEditText().setText(obj.getString("uf"));



            dialog1.dismiss();
        } catch (Throwable t) {
            dialog1.dismiss();
            Log.d("erro", "transformStringToJson: "+t);
        }
    }

    private void saveInfoToDataBase() {
        if (cepTextInputLayout.getEditText().getText().toString().isEmpty() || bairroTextInputLayout.getEditText().getText().toString().isEmpty() || cidadeTextInputLayout.getEditText().getText().toString().isEmpty() || estadoTextInputLayout.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Você precisa preencher todos os campos obrigatórios", Toast.LENGTH_LONG).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(AddAddressActivity.this, "Enviando dados");
        progressDialog.show();

        Map<String, Object> data = new HashMap<>();
        data.put("type", tipoDeImovel);
        data.put("msqr", metragemDoImovel);
        data.put("banheiros", quantidadeDeBanheiros);
        data.put("cep", cepTextInputLayout.getEditText().getText().toString());
        data.put("logradouro", logradouroTextInputLayout.getEditText().getText().toString());
        data.put("numero", numeroTextInputLayout.getEditText().getText().toString());
        data.put("complemento", complementoTextInputLayout.getEditText().getText().toString());
        data.put("bairro", bairroTextInputLayout.getEditText().getText().toString());
        data.put("cidade", cidadeTextInputLayout.getEditText().getText().toString());
        data.put("estado", estadoTextInputLayout.getEditText().getText().toString());
        data.put("created", new Date());


        db.collection("users").document(auth.getUid()).collection("enderecos").add(data).addOnSuccessListener(documentReference -> {
            progressDialog.dismiss();

            Toast.makeText(getApplicationContext(), "Endereço adicionado com sucesso", Toast.LENGTH_LONG).show();
            finish();

        }).addOnFailureListener((@NonNull Exception e) -> {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Erro ao enviar dados", Toast.LENGTH_LONG).show();
        });
    }
}
