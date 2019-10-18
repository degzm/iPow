package dev.deg.ipow;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    //Version de la base de datos
    public static final int db_ver = 1;
    //Nombre de la base de datos
    public static final String db_nom = "GeoPoints";

    /*
     * -- Tablas de la base de datos --
     * Tabla GeoPuntos con columnas:
     * Columna nombre: Nombre del geo punto
     * Columna snippet: Descripcion del geo punto
     * Columna coord_x: Valor de x
     * Columna coord_y: Valor de y
     */
    public static final String TABLA_GEOPUNTOS = "geopuntos";
    public static final String COLUMNA_NOMBRE = "punto";
    public static final String COLUMNA_SNIPPET = "snippet";
    public static final String COLUMNA_X = null;
    public static final String COLUMNA_Y = null;

    //Creacion de la tabla en SQL
    public static final String SQL_CREATE = "create table "
            + TABLA_GEOPUNTOS + "(" + COLUMNA_NOMBRE
            + "integer primary key autoincrement, " + COLUMNA_SNIPPET
            + "text not null);";

    //Super constructor
    public Database(Context context) {
        super(context, db_nom, null, db_ver);
    }
    //Acciones al crear
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea la tabla geopuntos
        db.execSQL(SQL_CREATE);
    }

    //Metodo que agrega datos
    public void agregarGP(String nombre, String snippet, int x, int y){
        //Mandamos al SQL que lo usaremos
        SQLiteDatabase db = this.getWritableDatabase();
        //Contenido temporal
        ContentValues values = new ContentValues();
        //Valor del nombre
        values.put(COLUMNA_NOMBRE, nombre);
        //Valor del snippet
        values.put(COLUMNA_SNIPPET, snippet);
        //Valor de coord x
        values.put(COLUMNA_X,x);
        //Valor de coord y
        values.put(COLUMNA_Y,y);
        //Insertamos los datos
        db.insert(TABLA_GEOPUNTOS,null,values);
        db.close();
    }

    public void obtenerDts(){

    }


    //Acciones al actualizar
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

    }
}
