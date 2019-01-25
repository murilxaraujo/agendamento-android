package br.com.mribeiro.marylimp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddressesRecyclerViewAdapter extends RecyclerView.Adapter<AddressesRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "AddressesRecyclerViewAd";

    private ArrayList<Address> addresses = new ArrayList<>();
    private Context mContext;

    public AddressesRecyclerViewAdapter(ArrayList<Address> addresses, Context mContext) {
        this.addresses = addresses;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_recycler_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.label.setText(addresses.get(i-1).getLogradouro());
    }


    @Override
    public int getItemCount() {
        int size = addresses.size() + 1;
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView label;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            label = itemView.findViewById(R.id.label);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
