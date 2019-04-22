package br.com.mribeiro.marylimp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CustomAddressesRecyclerViewAdapter extends RecyclerView.Adapter<CustomAddressViewHolder> {
    private static final String TAG = "AddressesRecyclerViewAd";

    private ArrayList<Address> addresses = new ArrayList<>();
    private Activity mainActivity;

    public CustomAddressesRecyclerViewAdapter(ArrayList<Address> addresses, Activity mainActivity) {
        this.addresses = addresses;
        this.mainActivity = mainActivity;
    }


    @NonNull
    @Override
    public CustomAddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.address_recycler_item, viewGroup, false);

        return new CustomAddressViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAddressViewHolder customAddressViewHolder, int i) {
        customAddressViewHolder.label.setText(addresses.get(i).getLogradouro());
        customAddressViewHolder.uid = addresses.get(i).getUid();
        customAddressViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                ((BookVisitActivity)view.getContext()).addressSelected(position);
            }
        });
        switch (addresses.get(i).getIcon()) {
            case 0:
                customAddressViewHolder.icon.setImageResource(R.drawable.ic_home_black_24dp);
                break;
            case 1:
                customAddressViewHolder.icon.setImageResource(R.drawable.ic_business_black_24dp);
                break;
            default:
        }
    }


    @Override
    public int getItemCount() {
        int size = addresses.size();
        return size;
    }


}

