package com.universidadeafit.appeafit.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universidadeafit.appeafit.R;
import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterVehiculo;
import com.universidadeafit.appeafit.Adapters.ViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by LUISM on 26/09/2016.
 */
public class OneFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private View view;
    private static String LOG_TAG = "CardViewActivity";
    private static final String KEY_CARROS = "carros";
    private ViewPagerAdapter viewPagerAdapter;

    public static OneFragment newInstance(ArrayList  vehiculos) {

        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(KEY_CARROS, vehiculos);
        fragment.setArguments(args);
        return fragment;
    }

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList Carros = getArguments().getStringArrayList(KEY_CARROS);

        view = inflater.inflate(R.layout.fragment_one, container, false); // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.my_recycler_view_vehiculos);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items

        RecyclerView.Adapter mAdapter = new MyRecyclerViewAdapterVehiculo(Carros);// recibe el arreglo de carros de  la clase vehiculos
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

}
