package com.universidadeafit.appeafit.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.R;

import java.util.ArrayList;

/**
 * Created by HP on 01/03/2016.
 */
public class MyRecyclerViewAdapterPerfil extends RecyclerView
        .Adapter<MyRecyclerViewAdapterPerfil
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Solicitud> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView item;
        TextView valoritem;
        ImageView imagen;

        public DataObjectHolder(View itemView) {
            super(itemView);

            item = (TextView) itemView.findViewById(R.id.item_user);
            valoritem = (TextView) itemView.findViewById(R.id.item_user_value);
            imagen = (ImageView) itemView.findViewById(R.id.icon);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           // myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
/*
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
*/
    public MyRecyclerViewAdapterPerfil(ArrayList<Solicitud> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_usuario, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.item.setText(mDataset.get(position).getPregunta());
        holder.valoritem.setText(mDataset.get(position).getMotivo());
        holder.imagen.setBackgroundResource(mDataset.get(position).getPhoto());
    }

    public void addItem(Solicitud dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public Solicitud getObjeto(int position) {
        return mDataset.get(position);
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
