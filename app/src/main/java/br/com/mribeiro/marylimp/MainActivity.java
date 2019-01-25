package br.com.mribeiro.marylimp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    RecyclerView addressesRecyclerView, visitsRecyclerView;
    RecyclerView.LayoutManager  addressesManager, visitsManager;
    ArrayList<Address> addresses = new ArrayList<>();
    AddressesRecyclerViewAdapter addressesAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            visitsRecyclerView = findViewById(R.id.visitsRecyclerView);
            addressesRecyclerView = findViewById(R.id.AddressessRecyclerView);
            visitsRecyclerView.setHasFixedSize(true);
            addressesRecyclerView.setHasFixedSize(true);
            addressesManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            visitsManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            visitsRecyclerView.setLayoutManager(visitsManager);
            addressesRecyclerView.setLayoutManager(addressesManager);
            addressesAdapter = new AddressesRecyclerViewAdapter(addresses, this);


            db.collection("users").document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void loadData() {


        setupAddressesRecyclerView();
    }

    private void setupAddressesRecyclerView() {
        db.collection("users").document(auth.getUid()).collection("enderecos").orderBy("created ").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (addresses.size() > 0) {
                    addresses.clear();
                }

                for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {
                    Address item = new Address(document.getString("logradouro"),document.getDouble("type").intValue(), document.getId());
                    addresses.add(item);
                }
                addressesAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void setupVisitsRecyclerView() {

    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
