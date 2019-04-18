package br.com.mribeiro.marylimp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddressesRecyclerViewAdapter extends RecyclerView.Adapter<AddressViewHolder> {
    private static final String TAG = "AddressesRecyclerViewAd";

    private ArrayList<Address> addresses = new ArrayList<>();
    private Activity mainActivity;

    public AddressesRecyclerViewAdapter(ArrayList<Address> addresses, Activity mainActivity) {
        this.addresses = addresses;
        this.mainActivity = mainActivity;
    }


    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.address_recycler_item, viewGroup, false);

        return new AddressViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final AddressViewHolder addressViewHolder, int i) {

        if (i == 0) {
            addressViewHolder.label.setText("Novo imóvel");
            addressViewHolder.icon.setImageResource(R.drawable.ic_add_circle_black_24dp);
            addressViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent intent = new Intent(mainActivity, AddAddressActivity.class);
                    mainActivity.startActivity(intent);
                }
            });
        } else {
            addressViewHolder.label.setText(addresses.get(i-1).getLogradouro());
            addressViewHolder.uid = addresses.get(i-1).getUid();
            addressViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {

                }
            });

            switch (addresses.get(i-1).getIcon()) {
                case 0:
                    addressViewHolder.icon.setImageResource(R.drawable.ic_home_black_24dp);
                    break;
                case 1:
                    addressViewHolder.icon.setImageResource(R.drawable.ic_business_black_24dp);
                    break;
                default:
            }
        }

    }

    @Override
    public int getItemCount() {
        int size = addresses.size() + 1;
        return size;
    }


}

