package com.example.sqlliteandrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DbAdaptorClass extends RecyclerView.Adapter<DbAdaptorClass.DbViewHolderClass> {

    private ArrayList<DbModelClass> DbModelClassArrayList;

    public DbAdaptorClass(ArrayList<DbModelClass> dbModelClassArrayList) {
        DbModelClassArrayList = dbModelClassArrayList;
    }

    @NonNull
    @Override
    public DbViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singlerow= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        DbViewHolderClass DbViewholderclass=new DbViewHolderClass(singlerow);
        return DbViewholderclass;
    }

    @Override
    public void onBindViewHolder(@NonNull DbViewHolderClass holder, int position) {

        holder.nameTV.setText(DbModelClassArrayList.get(position).getName());
        holder.addressTV.setText(DbModelClassArrayList.get(position).getAddress());


    }

    @Override
    public int getItemCount() {
        return DbModelClassArrayList.size();
    }

    class DbViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView nameTV;
        TextView addressTV;

        public DbViewHolderClass(@NonNull View itemView)
        {
            super(itemView);

            nameTV=itemView.findViewById(R.id.single_row_namaeTV);
            addressTV=itemView.findViewById(R.id.single_row_addressTV);

    }
    }
}
