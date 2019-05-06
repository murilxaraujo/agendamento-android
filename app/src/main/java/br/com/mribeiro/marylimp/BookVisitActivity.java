package br.com.mribeiro.marylimp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookVisitActivity extends AppCompatActivity {

    Button firstBackButton;
    ConstraintLayout firstPage, secondPage, thirdPage, fourthPage, fifthPage, sixthPage;
    RecyclerView addressesRecyclerView;
    RecyclerView.LayoutManager addressesManager;
    FirebaseFirestore db;
    ArrayList<Address> addresses = new ArrayList<>();
    FirebaseAuth auth;
    DatePickerDialog datePickerDialog;
    TextView dateTextView;

    //Second page variables;
    int selectedAddressIndex;
    String selectedAddressUID;

    //Third page variables
    int selectedTipoDeLimpeza;

    //FourthPage Variables
    int inityear, initmonth, initdayOfMonth;
    Calendar calendar;
    int selectedYear, selectedMonth, selectedDay;

    TextView fourthPageInformationTextView;
    Button manhaButton, tardeButton, noiteButton;
    int selectedTurno;

    //FifthPage Variables
    int finalprice;
    boolean hasCupon;
    String comment;
    String cuponCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_visit);

        setupView();
    }

    @SuppressLint("SetTextI18n")
    void setupView() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //Setting up first page
        firstPage = findViewById(R.id.firstBookVisitPage);
        firstBackButton = findViewById(R.id.firstBackButton);
        firstBackButton.setOnClickListener(v -> finish());
        Button firstnextbutton = findViewById(R.id.firstNextButton);
        firstnextbutton.setOnClickListener(v -> goToPage(2));

        //Setting up second page
        secondPage = findViewById(R.id.secondBookVisitPage);
        addressesRecyclerView = findViewById(R.id.AddressessRecyclerView);
        addressesRecyclerView.setHasFixedSize(true);
        addressesManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addressesRecyclerView.setLayoutManager(addressesManager);
        setupAddressesRecyclerView();
        Button secondBackButton = findViewById(R.id.secondPageBackButton);
        secondBackButton.setOnClickListener(v -> goToPage(1));

        //Setting up third page
        thirdPage = findViewById(R.id.thirdBookVisitPage);
        Button expressaButton = findViewById(R.id.thirdPageExpressaButton);
        Button detalhadaButton = findViewById(R.id.thirdPageDetalhadaButton);
        Button passadoriaButton = findViewById(R.id.thirdPagePassadoriaButton);
        Button outrosButton = findViewById(R.id.thidPageOutrosButton);
        Button thirdBackButton = findViewById(R.id.thirdPageBackButton);
        thirdBackButton.setOnClickListener(v -> goToPage(2));
        expressaButton.setOnClickListener(v -> {
            selectedTipoDeLimpeza = 0;
            goToPage(4);
        });

        detalhadaButton.setOnClickListener(v -> {
            selectedTipoDeLimpeza = 1;
            goToPage(4);
        });
        
        passadoriaButton.setOnClickListener(v -> {
            selectedTipoDeLimpeza = 2;
            goToPage(4);
        });

        outrosButton.setOnClickListener(v -> {
            String url = "http://marylimpbrasil.com.br/services.html";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        //Setting up fourth page
        fourthPage = findViewById(R.id.fourthBookVisitPage);
        Button selectDateBtn = findViewById(R.id.fourthPageSelectDateButton);
        dateTextView = findViewById(R.id.fourthPageDateTextView);
        Button fourthBackButton = findViewById(R.id.fourthPageBackButton);
        fourthBackButton.setOnClickListener(v -> goToPage(3));
        selectDateBtn.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            inityear = calendar.get(Calendar.YEAR);
            initmonth = calendar.get(Calendar.MONTH);
            initdayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(BookVisitActivity.this, (view, year, month, dayOfMonth) -> {
                dateTextView.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                selectedDay = dayOfMonth;
                selectedMonth = month+1;
                selectedYear = year;
                searchForDate(selectedDay,selectedMonth,selectedYear);
            }, inityear, initmonth ,initdayOfMonth);
            datePickerDialog.show();
        });

        fourthPageInformationTextView = findViewById(R.id.fourthPageInformationTextView);
        manhaButton = findViewById(R.id.fourthPageManhaButton);
        tardeButton = findViewById(R.id.fourthPageManhaTarde);
        noiteButton = findViewById(R.id.fourthPageManhaNoite);
        fourthPageInformationTextView.setVisibility(View.GONE);
        manhaButton.setVisibility(View.GONE);
        tardeButton.setVisibility(View.GONE);
        noiteButton.setVisibility(View.GONE);
        manhaButton.setOnClickListener(v -> {
            selectedTurno = 0;
            goToPage(5);
            updateServicesLabel();
            getFinalPrice();
        });
        tardeButton.setOnClickListener(v -> {
            selectedTurno = 1;
            goToPage(5);
            updateServicesLabel();
            getFinalPrice();
        });
        noiteButton.setOnClickListener(v -> {
            selectedTurno = 2;
            goToPage(5);
            updateServicesLabel();
            getFinalPrice();
        });

        //Setting up fifth page
        fifthPage = findViewById(R.id.fifthBookVisitPage);
        Button fifthBackButton = findViewById(R.id.fifthPageBackButton);
        fifthBackButton.setOnClickListener(v -> goToPage(4));
        
        Button contractButton = findViewById(R.id.viewContractButton);
        contractButton.setOnClickListener(v -> {
            // TODO: 2019-04-22 Add contract link intent
        });
        Button addCuponButton = findViewById(R.id.adicionarCupomButton);
        addCuponButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(BookVisitActivity.this);
            builder.setTitle("Adicionar cupom");
            final EditText input = new EditText(BookVisitActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Digite o cupom aqui");
            input.setPadding(10, 10, 10, 10);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                cuponCode = input.getText().toString();
                appyCupon(input.getText().toString());
            });
            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        Button agendarButton = findViewById(R.id.agendarButton);
        agendarButton.setOnClickListener(v -> agendar());
        //Setting up sixth page
        sixthPage = findViewById(R.id.sixthBookVisitPage);

        hideAllPages();
        showPage(1);
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

    private void appyCupon(String cupon) {
        final ProgressDialog dialog = new ProgressDialog(BookVisitActivity.this, "Aplicando cupom");
        dialog.show();
        db.collection("cupom")
                .document(cupon)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                if (Objects.requireNonNull(documentSnapshot.getLong("usos")).intValue() == 0) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Cupom expirado", Toast.LENGTH_LONG).show();
                } else {
                    int discount = Objects.requireNonNull(documentSnapshot.getLong("value")).intValue();
                    finalprice = finalprice - discount;
                    updatePriceLabel(finalprice);
                    hasCupon = true;

                    cuponCode = cupon;
                    Toast.makeText(getApplicationContext(), "Cupom aplicado com sucesso", Toast.LENGTH_LONG).show();
                }
            } else {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Cupom não foi encontrado", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Erro ao aplicar cupom", Toast.LENGTH_LONG).show();
        });
    }

    private void setupAddressesRecyclerView() {
        db.collection("users")
                .document(Objects.requireNonNull(auth.getUid()))
                .collection("enderecos")
                .orderBy("created")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (addresses.size() > 0) {
                        addresses.clear();
                    }

                    for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {
                        Address item = new Address(document.getString("logradouro"), Objects.requireNonNull(document.getDouble("type")).intValue(), document.getId());
                        addresses.add(item);
                    }
                    CustomAddressesRecyclerViewAdapter adapter = new CustomAddressesRecyclerViewAdapter(addresses, BookVisitActivity.this);

                    addressesRecyclerView.setAdapter(adapter);

                }).addOnFailureListener(e -> Toast.makeText(BookVisitActivity.this, "Erro ao buscar endereços", Toast.LENGTH_LONG).show());
    }

    public void addressSelected(int index) {
        selectedAddressIndex = index;
        selectedAddressUID = addresses.get(index).getUid();
        goToPage(3);
    }

    @SuppressLint("SetTextI18n")
    private void searchForDate(int day, int month, int year) {
        Calendar innerCalendar = Calendar.getInstance();
        int currentYear = innerCalendar.get(Calendar.YEAR);
        int currentMonth = innerCalendar.get(Calendar.MONTH)+1;
        int currentDay = innerCalendar.get(Calendar.DAY_OF_MONTH);

        if (year < currentYear) {
            //Ano passado
            fourthPageInformationTextView.setText("A data selecionada é invalida, por favor selecione uma data no futuro");
            fourthPageInformationTextView.setVisibility(View.VISIBLE);
            manhaButton.setVisibility(View.GONE);
            tardeButton.setVisibility(View.GONE);
            noiteButton.setVisibility(View.GONE);
        } else if (year == currentYear && month < currentMonth) {
            //Mesmo ano, mes passado
            fourthPageInformationTextView.setText("A data selecionada é invalida, por favor selecione uma data no futuro");
            fourthPageInformationTextView.setVisibility(View.VISIBLE);
            manhaButton.setVisibility(View.GONE);
            tardeButton.setVisibility(View.GONE);
            noiteButton.setVisibility(View.GONE);
        } else if (year == currentYear && month == currentMonth && day <= currentDay) {
            //Mesmo ano, mesmo mes, dia passado
            fourthPageInformationTextView.setText("A data selecionada é invalida, por favor selecione uma data no futuro");
            fourthPageInformationTextView.setVisibility(View.VISIBLE);
            manhaButton.setVisibility(View.GONE);
            tardeButton.setVisibility(View.GONE);
            noiteButton.setVisibility(View.GONE);
        } else {
            final ProgressDialog dialog = new ProgressDialog(BookVisitActivity.this, "Buscando dia");
            dialog.show();
            String clearYear = String.valueOf(year);
            String clearMonth, clearDay;
            if (month < 10) {
                clearMonth = "0"+month;
            } else {
                clearMonth = String.valueOf(month);
            }
            if (day < 10) {
                clearDay = "0"+day;
            } else {
                clearDay = String.valueOf(day);
            }
            db.collection("workers")
                    .document(clearYear)
                    .collection(clearMonth)
                    .document(clearDay)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        dialog.dismiss();
                        Log.d("getting document", "onSuccess: "+documentSnapshot);
                        if (documentSnapshot.exists()) {
                            fourthPageInformationTextView.setText(documentSnapshot.getReference().toString());
                            populateDisponibilidadeInformation(Objects.requireNonNull(documentSnapshot.getLong("manha")).intValue(), documentSnapshot.getLong("tarde").intValue(), documentSnapshot.getLong("noite").intValue());
                        } else {
                            fourthPageInformationTextView.setText("documento não existe");
                            populateDisponibilidadeInformation(0, 0, 0);

                        }
                    }).addOnFailureListener(e -> {
                        dialog.dismiss();
                        fourthPageInformationTextView.setText("Erro ao buscar dia, tente novamente mais tarde");
                    });
        }
    }

    @SuppressLint("SetTextI18n")
    private void populateDisponibilidadeInformation(int manha, int tarde, int noite) {
        manhaButton.setVisibility(View.GONE);
        tardeButton.setVisibility(View.GONE);
        noiteButton.setVisibility(View.GONE);
        if (manha == 0 && tarde == 0 && noite == 0) {
            fourthPageInformationTextView.setText("Não há turnos disponíveis no dia selecionado, tente outra data.");
            fourthPageInformationTextView.setVisibility(View.VISIBLE);
        } else {
            fourthPageInformationTextView.setText("Temos os seguintes turnos disponíveis, selecione um para continuar");
            fourthPageInformationTextView.setVisibility(View.VISIBLE);
            if (manha >= 1) {
                manhaButton.setVisibility(View.VISIBLE);
            }

            if (tarde >= 1) {
                tardeButton.setVisibility(View.VISIBLE);
            }

            if (noite >= 1) {
                noiteButton.setVisibility(View.VISIBLE);
            }

        }
    }

    private void getFinalPrice() {
        final ProgressDialog dialog = new ProgressDialog(BookVisitActivity.this, "Calculando preço");
        dialog.show();

        db.collection("users")
                .document(Objects.requireNonNull(auth.getUid()))
                .collection("enderecos")
                .document(selectedAddressUID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        int metragem = Objects.requireNonNull(documentSnapshot.getLong("msqr")).intValue();

                        if (metragem < 40) {
                            if (selectedTipoDeLimpeza == 0) {
                                finalprice = 7500;
                            } else {
                                finalprice = 15000;
                            }
                        } else if (metragem < 70) {
                            if (selectedTipoDeLimpeza == 0) {
                                finalprice = 9000;
                            } else {
                                finalprice = 18000;
                            }
                        } else if (metragem < 100) {
                            if (selectedTipoDeLimpeza == 0) {
                                finalprice = 12000;
                            } else {
                                finalprice = 24000;
                            }
                        } else if (metragem < 120) {
                            if (selectedTipoDeLimpeza == 0) {
                                finalprice = 17000;
                            } else {
                                finalprice = 34000;
                            }
                        } else if (metragem < 160) {
                            if (selectedTipoDeLimpeza == 0) {
                                finalprice = 22000;
                            } else {
                                finalprice = 44000;
                            }
                        } else if (metragem < 220) {
                            if (selectedTipoDeLimpeza == 0) {
                                finalprice = 30000;
                            } else {
                                finalprice = 60000;
                            }
                        } else if (metragem < 300) {
                            if (selectedTipoDeLimpeza == 0) {
                                finalprice = 40000;
                            } else {
                                finalprice = 80000;
                            }
                        }
                        updatePriceLabel(finalprice);
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Endereço selecionado não encontrado", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Erro ao buscar endereço", Toast.LENGTH_LONG).show();
                });
    }

    @SuppressLint("SetTextI18n")
    private void updatePriceLabel(int valor) {
        finalprice = valor;

        long finalprice = (long) (valor / 100);
        TextView priceTV = findViewById(R.id.fifthPagePriceTextView);
        priceTV.setText("R$ "+finalprice);
    }

    @SuppressLint("SetTextI18n")
    private void updateServicesLabel() {
        TextView infoTV = findViewById(R.id.fifthPageInfoTextView);
        switch (selectedTipoDeLimpeza) {
            case 0:
                infoTV.setText("Limpeza expressa dia "+selectedDay+"/"+selectedMonth+"/"+selectedYear);
                break;
            case 1:
                infoTV.setText("Limpeza detalhada dia "+selectedDay+"/"+selectedMonth+"/"+selectedYear);
                break;
            case 2:
                infoTV.setText("Passadoria dia "+selectedDay+"/"+selectedMonth+"/"+selectedYear);
                break;
        }
    }

    private void agendar() {
        comment = "";
        TextInputLayout textInputLayout = findViewById(R.id.commentTextInputlayout);
        if (!Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().isEmpty()) {
            comment = textInputLayout.getEditText().getText().toString();
        }
        gerarLinkDePagamento(finalprice);
    }

    private void gerarLinkDePagamento(int finalprice) {
        OkHttpClient httpClient = new OkHttpClient();
        String json = "{price: " + finalprice + ", email: " + Objects.requireNonNull(auth.getCurrentUser()).getEmail() + ", }";
        String url = "";
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset-utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            transformStringToJson(Objects.requireNonNull(response.body()).string());
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Erro ao criar pagamento, tente novamente mais tarde", Toast.LENGTH_LONG).show();
        }
    }

    private void savePaymentInfoToDB(String paymentLink) {
        Map<String, Object> data = new HashMap<>();

        data.put("ano", selectedYear);
        data.put("confirmada", false);
        data.put("dia", selectedDay);
        data.put("mes", selectedMonth);
        data.put("timestamp", new Date());
        data.put("turno", selectedTurno);
        data.put("user", Objects.requireNonNull(auth.getUid()));
        data.put("link", paymentLink);
        data.put("serviço", selectedTipoDeLimpeza);
        data.put("observacoes", comment);

        db.collection("agendamentos").add(data).addOnSuccessListener(documentReference -> {

        }).addOnFailureListener(e -> {

        });
    }

    private void transformStringToJson(String objectString) {
        try {
            JSONObject obj = new JSONObject(objectString);
            savePaymentInfoToDB(obj.getString("payment link"));
        } catch (Throwable t) {
            Log.d("erro", "transformStringToJson: "+t);
        }
    }

}
