package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.universidadeafit.appeafit.Model.DataObject;
import com.universidadeafit.appeafit.R;
import com.universidadeafit.appeafit.Views.Adapters.MyRecyclerViewAdapterVehiculo;
import com.universidadeafit.appeafit.Views.Adapters.ViewPagerAdapter;

import java.util.ArrayList;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView.Adapter mAdapter;
    private static String LOG_TAG = "CardViewActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private int[] tabIcons = {
            R.drawable.ic_user_white,
            R.drawable.ic_user_general_white,
            R.drawable.ic_email
    };

    ArrayList<DataObject> Perfil = new ArrayList<>();
    ArrayList<DataObject> Noticias = new ArrayList<>();
    ArrayList<DataObject> Mas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("APP EAFIT");

        Perfil.add(new DataObject("Mi Perfil", " Usuario", R.drawable.ic_menu_camera, " ", " "));
        Perfil.add(new DataObject("Materias", " 1", R.drawable.ic_menu_manage, " ", " "));

        Noticias.add(new DataObject("Lo Nuevo", " Noticia 1", R.drawable.ic_menu_slideshow, " ", " "));
        Mas.add(new DataObject("Nuevo", " Nuevo 1", R.drawable.ic_menu_share, " ", " "));


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        //Log.e("TAG", "TAB1");
                        onResume(Perfil);
                        break;
                    case 1:
                        //Log.e("TAG", "TAB2");
                        onResume(Noticias);
                        break;
                    case 2:
                        //Log.e("TAG", "TAB3");
                        onResume(Mas);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(OneFragment.newInstance(Perfil), "PERFIL"); //se pasan los carros para el fragment
        adapter.addFragment(OneFragment.newInstance(Noticias), "NOTICIAS");
        adapter.addFragment(OneFragment.newInstance(Mas), "MAS");
        viewPager.setAdapter(adapter);
        onResume(Perfil);// se debe ingresar el primer arreglo para el primer fragment, los otros se asignan cada vez que se mueve el tablayout
    }//es necesario para que el metodo que devuelve la posicion lo haga del fragment cvorrecto


    protected void onResume(ArrayList  carros) {
        super.onResume();

        mAdapter = new MyRecyclerViewAdapterVehiculo(carros);
        ((MyRecyclerViewAdapterVehiculo) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapterVehiculo
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(NavigationDrawerActivity.this, DetalleVehiculos.class);
                i.putExtra("placa",((MyRecyclerViewAdapterVehiculo) mAdapter).getObjeto(position).getPlaca());
                startActivity(i);
                //finish(); // Es necesario que las clases objetos o entidades que se usan en esta clase
                //se implementen como Serializable (public class xxx implments Serializable)
                //Toast.makeText(NavigationDrawerActivity.this, " Clicked on  " + ((MyRecyclerViewAdapterVehiculo) mAdapter).getObjeto(position).getPlaca(), Toast.LENGTH_LONG).show();
                //Log.i(LOG_TAG, " Clicked on  " + ((MyRecyclerViewAdapterVehiculo) mAdapter).getObjeto(position).getPlaca());
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            Intent i = new Intent(NavigationDrawerActivity.this, PerfilActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_solicitud) {
            Intent i = new Intent(NavigationDrawerActivity.this, SolicitudActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
