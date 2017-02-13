package com.universidadeafit.appeafit.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universidadeafit.appeafit.Model.DataObject;
import com.universidadeafit.appeafit.R;

import java.util.ArrayList;

/**
 * Created by HP on 01/03/2016.
 */
public class MyRecyclerViewAdapterVehiculo extends RecyclerView
        .Adapter<MyRecyclerViewAdapterVehiculo
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView placa;
        ImageView imagen;
        TextView marca;
        TextView tipo;
        TextView color;

        public DataObjectHolder(View itemView) {
            super(itemView);

            placa = (TextView) itemView.findViewById(R.id.vehiculo_name);
            imagen = (ImageView) itemView.findViewById(R.id.fondo);
            marca = (TextView) itemView.findViewById(R.id.marca_vehiculo);
            tipo = (TextView) itemView.findViewById(R.id.tipo_vehiculo);
            color = (TextView) itemView.findViewById(R.id.color_vehiculo);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }



    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapterVehiculo(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_vehiculos, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.placa.setText(mDataset.get(position).getPlaca());
        holder.imagen.setBackgroundResource(mDataset.get(position).getPhoto());
        holder.marca.setText(mDataset.get(position).getMarca());
        holder.tipo.setText(mDataset.get(position).getTipo());
        holder.color.setText(mDataset.get(position).getColor());
    }

    public void addItem(DataObject dataObj, int index) {
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

    public DataObject getObjeto(int position) {
        return mDataset.get(position);
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
