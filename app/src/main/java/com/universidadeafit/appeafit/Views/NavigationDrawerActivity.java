package com.universidadeafit.appeafit.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import android.widget.Toast;

import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterSolicitudes;
import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;
import com.universidadeafit.appeafit.Adapters.ViewPagerAdapter;

import java.util.ArrayList;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView.Adapter mAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    int id = 1;
    String nombres;
    String apellidos;
    String email;

    private UsuariosSQLiteHelper mydb ;

    ArrayList<Solicitud> resumen = new ArrayList<>();
    ArrayList<Solicitud> frecuentes = new ArrayList<>();
    ArrayList<Solicitud> sinresponder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        mydb = new UsuariosSQLiteHelper(this);
        boolean aux;
        aux = mydb.HayUsuarios(id);
        if (aux) {
            Cursor rs = mydb.ObtenerDatos(id);
            nombres = rs.getString(2);
            apellidos = rs.getString(3);
            email = rs.getString(5);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EAFIT INTERACTIVA");

        resumen.add(new Solicitud("Galeria", " Biblioteca", R.drawable.ic_menu_camera, " Fotos", " Enero 2017"));
        resumen.add(new Solicitud("Materias", " Registradas: 1", R.drawable.ic_menu_manage, " Semestre: 2017-1", " Creditos: 4"));
        frecuentes.add(new Solicitud("Lo Nuevo", " Noticia 1", R.drawable.ic_menu_slideshow, " ", " "));
        sinresponder.add(new Solicitud("Nuevo", " Nuevo 1", R.drawable.ic_menu_share, " ", " "));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        //Log.e("TAG", "TAB1");
                        onResume(resumen);
                        break;
                    case 1:
                        //Log.e("TAG", "TAB2");
                        onResume(frecuentes);
                        break;
                    case 2:
                        //Log.e("TAG", "TAB3");
                        onResume(sinresponder);
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

        com.github.clans.fab.FloatingActionButton fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(NavigationDrawerActivity.this, WatsonActivity.class);
               startActivity(i);
            }
        });

        com.github.clans.fab.FloatingActionButton fab1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NavigationDrawerActivity.this, MainActivity.class);
                startActivity(i);
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentDrawer.newInstance(resumen), "INICIO"); //se pasan los carros para el fragment
        adapter.addFragment(FragmentDrawer.newInstance(frecuentes), "FRECUENTE");
        adapter.addFragment(FragmentDrawer.newInstance(sinresponder), "MAS");
        viewPager.setAdapter(adapter);
        onResume(resumen);// se debe ingresar el primer arreglo para el primer fragment, los otros se asignan cada vez que se mueve el tablayout
    }//es necesario para que el metodo que devuelve la posicion lo haga del fragment cvorrecto

    protected void onResume(ArrayList  solicitudes) {
        super.onResume();

        mAdapter = new MyRecyclerViewAdapterSolicitudes(solicitudes);
        ((MyRecyclerViewAdapterSolicitudes) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapterSolicitudes
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(NavigationDrawerActivity.this, DetalleSolicitud.class);
                i.putExtra("placa",((MyRecyclerViewAdapterSolicitudes) mAdapter).getObjeto(position).getPlaca());
                startActivity(i);
                //finish(); // Es necesario que las clases objetos o entidades que se usan en esta clase
                //se implementen como Serializable (public class xxx implments Serializable)
                //Toast.makeText(NavigationDrawerActivity.this, " Clicked on  " + ((MyRecyclerViewAdapterSolicitudes) mAdapter).getObjeto(position).getPlaca(), Toast.LENGTH_LONG).show();
                //Log.i(LOG_TAG, " Clicked on  " + ((MyRecyclerViewAdapterSolicitudes) mAdapter).getObjeto(position).getPlaca());
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
            Intent i = new Intent(NavigationDrawerActivity.this, MisSolicitudesActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_informacion) {
            Toast.makeText(NavigationDrawerActivity.this, " about icons8 https://icons8.com/web-app/5764/Message", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_contacto) {

        } else if (id == R.id.nav_mas) {

        } else if (id == R.id.nav_config) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_cerrarsesion) {
            // Validacion para cerrar sesion usando la base de datos y el registro de usuaio
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Saliendo...")
                    .setMessage("¿" + nombres + ", segur@ que deseas cerrar tu Sesión? ")
                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        //se obtiene el numero de vehiculos ingresados por el usuario
                            boolean aux;
                            aux=mydb.HayCarros();
                            //aux es un vallor de tipo boolean y devuelve si hay vehiculos registrados o no
                            if(aux) {
                                int numero=mydb.HayCaarros();
                                boolean numVehiculos = mydb.CLean(numero);
                                if (numVehiculos) {
                                    Toast.makeText(getApplicationContext(), "¡Bye "+nombres+", Hasta pronto!", Toast.LENGTH_SHORT).show();
                                }
                            }else{//para borrar solo el usuario si no tiene vehiculos
                                boolean user = mydb.CLeanUsers();
                                if (user) {
                                    Toast.makeText(getApplicationContext(), "¡Bye "+nombres+", Hasta pronto!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            finish();
                        }

                    })
                    .setNegativeButton("Volver", null)
                    .show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
