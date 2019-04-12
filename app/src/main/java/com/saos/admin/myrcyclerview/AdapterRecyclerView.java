package com.saos.admin.myrcyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 20/08/2018.
 */

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolderDatos> implements View.OnClickListener{

    //ArrayList<Model> lstData;

    ArrayList<ActivityModel> lstData;

    private View.OnClickListener listener;

    public AdapterRecyclerView(ArrayList<ActivityModel> lstData) {
        this.lstData = lstData;
    }





    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //enlaza el adapter con el item list

        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list,null,false);

        view.setOnClickListener(this);



        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        //establece comunicacion y viewholderdatos

        holder.asignarDatos(lstData.get(position));
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }


    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }



    @Override
    public void onClick(View v) {

        if(listener!=null)
        {
            listener.onClick(v);
        }


    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView dato,detalle;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            dato=itemView.findViewById(R.id.idData);
            detalle=itemView.findViewById(R.id.idDetails);
        }

        public void asignarDatos(ActivityModel s)
        {
            dato.setText(s.getNombre());
            detalle.setText(s.getDescripcion());
        }

    }
}
