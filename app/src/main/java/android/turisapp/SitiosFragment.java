package android.turisapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SitiosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SitiosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SitiosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FloatingActionButton floatingActionButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SitiosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SitiosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SitiosFragment newInstance(String param1, String param2) {
        SitiosFragment fragment = new SitiosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    ArrayList<Sitios> listDatos;
    RecyclerView recycler;
    View rootView;
    Conexion conexion;

    Activity activity;
    IComunicaFragments interfaceComunicaFragment;
    View viewSitio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_sitios, container, false);
        conexion=new Conexion(getContext());
        setHasOptionsMenu(true);

        floatingActionButton  = (FloatingActionButton)rootView.findViewById(R.id.s);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(),MapsActivity.class);
                startActivity(intent);
            }
        });


        recycler=(RecyclerView) rootView.findViewById(R.id.recyclerId);
        //recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        //recycler.setLayoutManager(new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false));

        cargarDatosListaLinear();




        return rootView;
    }

    private void cargarDatosListaLinear() {

        listDatos=new ArrayList<>();
        try {
            if (Utilidades.seleccionado== Utilidades.list){
                recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            }else{
                recycler.setLayoutManager(new GridLayoutManager(getContext(),2));
            }

            consultarDatos();

            AdapterDatos adapter=new AdapterDatos(listDatos);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilidades.buscarSitioID=listDatos.get(recycler.getChildAdapterPosition(view)).getId();

                    Fragment fragment=new DetalleSitio();

                    getFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();

                    Utilidades.verBotonCambio=false;
                    getActivity().invalidateOptionsMenu();
                }
            });
            recycler.setAdapter(adapter);



        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void consultarDatos() {
        SQLiteDatabase db=conexion.getReadableDatabase();
        String sql="select * from sitios";
        Cursor cursor=db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            String id=cursor.getString(0);
            String nombre=cursor.getString(1);
            String descripcionCorta=cursor.getString(2);
            String ubicacion=cursor.getString(3);
            String descripcion=cursor.getString(4);
            String latitud=cursor.getString(5);
            String longitud=cursor.getString(6);
            String foto=cursor.getString(7);

            Sitios sitios=new Sitios(id,nombre,descripcionCorta,ubicacion,descripcion,latitud,longitud,foto);

            listDatos.add(sitios);

        }
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
        if (id == R.id.cambiarEstilo) {
            cambiarLista();
        }


        return super.onOptionsItemSelected(item);
    }

    private void cambiarLista() {
        if (Utilidades.seleccionado== Utilidades.list){
            Utilidades.seleccionado= Utilidades.grid;
        }else{
            Utilidades.seleccionado= Utilidades.list;
        }

        cargarDatosListaLinear();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
