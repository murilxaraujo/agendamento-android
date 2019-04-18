package br.com.mribeiro.marylimp;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class bookVisitActivity extends AppCompatActivity {

    Button firstBackButton;
    ConstraintLayout firstPage, secondPage, thirdPage, fourthPage, fifthPage, sixthPage;
    RecyclerView addressesRecyclerView;
    RecyclerView.LayoutManager addressesManager;
    FirebaseFirestore db;
    ArrayList<Address> addresses = new ArrayList<>();
    FirebaseAuth auth;

    //Second page variables;
    int selectedAddressIndex;
    String selectedAddressUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_visit);

        setupView();
    }

    void setupView() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //Setting up first page
        firstPage = findViewById(R.id.firstBookVisitPage);
        firstBackButton = findViewById(R.id.firstBackButton);
        firstBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button firstnextbutton = findViewById(R.id.firstNextButton);
        firstnextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(2);
            }
        });

        //Setting up second page
        secondPage = findViewById(R.id.secondBookVisitPage);
        addressesRecyclerView = findViewById(R.id.AddressessRecyclerView);
        addressesRecyclerView.setHasFixedSize(true);
        addressesManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addressesRecyclerView.setLayoutManager(addressesManager);
        setupAddressesRecyclerView();

        //Setting up third page
        thirdPage = findViewById(R.id.thirdBookVisitPage);
        //Setting up fourth page
        fourthPage = findViewById(R.id.fourthBookVisitPage);
        //Setting up fifth page
        fifthPage = findViewById(R.id.fifthBookVisitPage);
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

    private void setupAddressesRecyclerView() {
        db.collection("users").document(auth.getUid()).collection("enderecos").orderBy("created").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (addresses.size() > 0) {
                    addresses.clear();
                }

                for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {
                    Address item = new Address(document.getString("logradouro"),document.getDouble("type").intValue(), document.getId());
                    addresses.add(item);
                }
                CustomAddressesRecyclerViewAdapter adapter = new CustomAddressesRecyclerViewAdapter(addresses, bookVisitActivity.this);

                addressesRecyclerView.setAdapter(adapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(bookVisitActivity.this, "Erro ao buscar endereços", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addressSelected(int index) {
        selectedAddressIndex = index;
        selectedAddressUID = addresses.get(index).getUid();
        goToPage(3);
    }
}
