package android.turisapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetalleSitio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetalleSitio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleSitio extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String longitud;
    String latitud;
    String nombre;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetalleSitio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleSitio.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleSitio newInstance(String param1, String param2) {
        DetalleSitio fragment = new DetalleSitio();
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

    View viewDetalle;
    View viewMain;

    public TextView txtNombres,txtDescripcion;
    public ImageView imagenView;
    FloatingActionButton fabu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewDetalle= inflater.inflate(R.layout.fragment_detalle_sitio, container, false);
        viewMain= inflater.inflate(R.layout.activity_main, container, false);

        txtNombres=(TextView) viewDetalle.findViewById(R.id.nombreDetalle);
        txtDescripcion=(TextView) viewDetalle.findViewById(R.id.descripcionDetalle);
        imagenView=(ImageView) viewDetalle.findViewById(R.id.imagenDetalle);


        String idSitio= Utilidades.buscarSitioID;
        String cadenaSQL="select * from sitios where id="+idSitio;
        Conexion conexion=new Conexion(getContext());
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor=db.rawQuery(cadenaSQL,null);

        cursor.moveToNext();
        if (cursor!=null){
            try{
                String imagen=cursor.getString(7);
                txtNombres.setText(cursor.getString(1).toString());
                imagenView.setImageResource(Integer.parseInt(imagen));
                txtDescripcion.setText(cursor.getString(4).toString());
                nombre =cursor.getString(1).toString();
                latitud =cursor.getString(5).toString();
                longitud =cursor.getString(6).toString();
            }catch (Exception e){
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        fabu = (FloatingActionButton) viewDetalle.findViewById(R.id.f);
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewDetalle.getContext(),MapsActivity.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("latitud",latitud);
                intent.putExtra("longitud",longitud);
                startActivity(intent);

            }
        });





        return viewDetalle;
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
