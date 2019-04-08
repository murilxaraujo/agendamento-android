package br.com.mribeiro.marylimp;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    ImageView icon;
    TextView label;
    String uid;
    ConstraintLayout constraintLayout;
    private ItemClickListener itemClickListener;

    public AddressViewHolder(View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.icon);
        label = itemView.findViewById(R.id.label);
        constraintLayout = itemView.findViewById(R.id.constraintLayout);

        constraintLayout.setOnClickListener(this);
        constraintLayout.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return false;
    }
}
