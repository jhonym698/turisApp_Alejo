package android.turisapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC27 on 22/08/2018.
 */

public class Conexion extends SQLiteOpenHelper {
    public Conexion(Context context) {
        super(context, "turisapp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE sitios(id integer primary key AUTOINCREMENT, nombre text,descripcioncorta text,ubicacion text,descripcion text,latitud text, longitud text,foto text)");
        sqLiteDatabase.execSQL("insert into sitios(nombre,descripcioncorta,ubicacion,descripcion,latitud,longitud,foto) values" +
                "('Centro Comercial Portal del Quindío.', 'El Centro Comercial Portal del Quindio, ubicado en el norte de Armenia, es el centro comercial, más grande e importante del Quindío.'," +
                "'Dirección: Av Bolivar # 19 Norte 46 Armenia'," +
                "'El Centro Comercial Portal del Quindio, ubicado en el norte de Armenia, es el centro comercial, más grande e importante del Quindío. Cuenta con 100 locales donde se encuentran representadas las marcas comerciales nacionales e internacionales más importantes.\n" +
                "En su mall de comidas rápidas encontrará reconocidos restaurantes como Frisby, hamburguesas El Corral y Presto entre otras.\n" +
                "Todo esta oferta se complementa con una espectacular diverzona y cuatro (4) modernas salas de Cinecolombia.'," +
                "'4.545695136892776','-75.67256734597161','"+ R.drawable.portal_del_quindio+"')");

        sqLiteDatabase.execSQL("insert into sitios(nombre,descripcioncorta,ubicacion,descripcion,latitud,longitud,foto) values " +
                "('Unicentro - Armenia.'," +
                "'Unicentro Armenia inaugurado en Septiembre de 2.012,  ubicado en la  esquina de la Avenida Bolívar con la Calle 4B '," +
                "'Avenida Bolívar con Calle 4B'," +
                "'Unicentro Armenia inaugurado en Septiembre de 2.012,  ubicado en la  esquina de la Avenida Bolívar con la Calle 4B donde antiguamente funcionó la fabrica de Bavaria, a pocos minutos del Centro de Armenia.El centro comercial cuenta con 40 mil metros cuadrados, 129 locales comerciales y 410 parqueaderos.\n" +
                "Entre las marcas comerciales que ya confirmarón su presencia en Unicentro Armenia, se destacan: Almacenes Exito, Pepe Ganga, Recreatec, Arturo Calle, entre otros.'," +
                "'4.537481262607865','-75.66655919777826','"+ R.drawable.unicentro+"')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
