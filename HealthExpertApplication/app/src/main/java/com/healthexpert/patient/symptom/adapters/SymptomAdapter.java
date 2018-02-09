package com.healthexpert.patient.symptom.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.healthexpert.R;
import com.healthexpert.data.remote.models.response.Patient;
import com.healthexpert.data.remote.models.response.SymptomResponse;
import com.healthexpert.ui.widgets.BaseCheckbox;
import com.healthexpert.ui.widgets.BaseTextView;

import java.util.ArrayList;


/**
 * Created by Archish on 12/19/2016.
 */

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.NewsFeedViewHolder> {

    public ArrayList<SymptomResponse> data;

    public SymptomAdapter(ArrayList<SymptomResponse> data) {
        this.data = data;
    }
    public void filterList(ArrayList<SymptomResponse> filterdNames) {
        data = filterdNames;
        notifyDataSetChanged();
    }


    @Override
    public SymptomAdapter.NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_symptoms, parent, false);
        NewsFeedViewHolder holder = new NewsFeedViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SymptomAdapter.NewsFeedViewHolder holder, final int position) {
        holder.tvSname.setText(data.get(position).getSname());
        holder.cCheck.setChecked(data.get(position).isCheck());
        holder.cCheck.setTag(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        BaseTextView tvSname;
        BaseCheckbox cCheck;
        LinearLayout llItem;

        public NewsFeedViewHolder(final View itemView) {
            super(itemView);
            tvSname = (BaseTextView) itemView.findViewById(R.id.tvSname);
            cCheck = (BaseCheckbox) itemView.findViewById(R.id.cCheck);
            llItem = (LinearLayout) itemView.findViewById(R.id.llItem);
            llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data.get(getAdapterPosition()).isCheck()) {
                        cCheck.setChecked(false);
                        data.get(getAdapterPosition()).setCheck(false);
                    } else {
                        cCheck.setChecked(true);
                        data.get(getAdapterPosition()).setCheck(true);
                    }
                }
            });
            cCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.get(getAdapterPosition()).setCheck(isChecked);
                }
            });


        }

    }

    public ArrayList<SymptomResponse> getSymptoms(){
        return data;
    }

}