package br.com.mribeiro.marylimp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.mribeiro.marylimp.AddAddressActivity;
import br.com.mribeiro.marylimp.ItemClickListener;
import br.com.mribeiro.marylimp.MainActivity;
import br.com.mribeiro.marylimp.R;
import br.com.mribeiro.marylimp.Visit;
import br.com.mribeiro.marylimp.VisitsViewHolder;

public class VisitsRecyclerViewAdapter extends RecyclerView.Adapter<VisitsViewHolder>{

    private ArrayList<Visit> visits = new ArrayList<>();
    private MainActivity mainActivity;

    public VisitsRecyclerViewAdapter(ArrayList<Visit> visits, MainActivity mainActivity) {
        this.visits = visits;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public VisitsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.visits_recycler_item, viewGroup, false);

        return new VisitsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitsViewHolder visitsViewHolder, int i) {
        if (i == 0) {
            visitsViewHolder.label.setText("Agendar visita");
            visitsViewHolder.icon.setImageResource(R.drawable.ic_add_circle_black_24dp);
            visitsViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent intent = new Intent(mainActivity, AddVisitActivity.class);
                    mainActivity.startActivity(intent);
                }
            });
        } else {

        }
    }

    @Override
    public int getItemCount() {
       return visits.size()+1;
    }
}
