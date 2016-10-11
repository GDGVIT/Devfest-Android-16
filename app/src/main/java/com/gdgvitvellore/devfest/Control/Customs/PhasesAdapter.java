package com.gdgvitvellore.devfest.Control.Customs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdgvitvellore.devfest.Entity.Actors.Phase;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.List;

/**
 * Created by AravindRaj on 11-10-2016.
 */

public class PhasesAdapter extends RecyclerView.Adapter<PhasesAdapter.MyViewHolder> {

    private List<Phase> phasesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, time;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.phase_name);
            time = (TextView) view.findViewById(R.id.phase_time);
        }
    }

    public PhasesAdapter(List<Phase> phasesList) {
        this.phasesList = phasesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_timeline_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Phase phase = phasesList.get(position);
        holder.name.setText(phase.getName());
        holder.time.setText(phase.getTime());
    }

    @Override
    public int getItemCount() {
        return phasesList.size();
    }
}
