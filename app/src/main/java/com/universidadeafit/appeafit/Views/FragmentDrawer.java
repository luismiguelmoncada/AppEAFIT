package com.universidadeafit.appeafit.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universidadeafit.appeafit.R;
import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterSolicitudes;

import java.util.ArrayList;

/**
 * Created by LUISM on 26/09/2016.
 */
public class FragmentDrawer extends Fragment {

    private RecyclerView mRecyclerView;
    private View view;
    private static final String KEY_SOLICITUDES = "solicitudes";

    public static FragmentDrawer newInstance(ArrayList  solicitudes) {
        FragmentDrawer fragment = new FragmentDrawer();
        Bundle args = new Bundle();
        args.putStringArrayList(KEY_SOLICITUDES, solicitudes);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList Solicitudes = getArguments().getStringArrayList(KEY_SOLICITUDES);

        view = inflater.inflate(R.layout.fragment_one, container, false); // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.my_recycler_view_vehiculos);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items

        RecyclerView.Adapter mAdapter = new MyRecyclerViewAdapterSolicitudes(Solicitudes);// recibe el arreglo de carros de  la clase vehiculos
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
