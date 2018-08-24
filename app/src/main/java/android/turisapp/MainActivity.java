package android.turisapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , InicioFragment.OnFragmentInteractionListener,HotelesFragment.OnFragmentInteractionListener,
                    Restaurantes.OnFragmentInteractionListener,SitiosFragment.OnFragmentInteractionListener,DetalleSitio.OnFragmentInteractionListener{


    MenuItem itemCambiar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment=new InicioFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();

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
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemCambiar= menu.findItem(R.id.cambiarEstilo);
        if (Utilidades.verBotonCambio==false) {
            itemCambiar.setVisible(false);
        }else{
            itemCambiar.setVisible(true);
        }

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

        Utilidades.verBotonCambio=false;

        int id = item.getItemId();
        Fragment fragment=null;
        boolean fragmentSeleccionado=false;
        if (id == R.id.nav_camera) {
            fragment=new InicioFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_gallery) {
            Utilidades.verBotonCambio=true;
            fragment=new SitiosFragment();
            fragmentSeleccionado=true;

        } else if (id == R.id.nav_slideshow) {
            fragment=new HotelesFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_manage) {
            fragment = new Restaurantes();
            fragmentSeleccionado = true;
        }


        if (fragmentSeleccionado==true){
            invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
