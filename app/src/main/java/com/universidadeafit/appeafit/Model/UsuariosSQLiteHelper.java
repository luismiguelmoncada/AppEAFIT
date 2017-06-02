package com.universidadeafit.appeafit.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by LUISM on 06/09/2016.
 */
public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EAFITSolicitudes";// Database Name
    private static final int DATABASE_VERSION = 1;   // Database Version"

    // para tabla USUARIO Registro
    private static final String KEY_ID = "IdAux";
    private static final String KEY_USUARIO = "Usuario";
    private static final String KEY_NOMBRES = "Nombres";
    private static final String KEY_APELLIDOS = "Apellidos";
    private static final String KEY_PASSWORD = "Contra";
    private static final String KEY_EMAIL = "Email";

    // para tabla TIPO USUARIO
    private static final String KEY_IDTIPOU = "IdTipoUsuario";
    private static final String KEY_ROL = "Rol";
    private static final String KEY_CODIGO_ESTUDIANTE = "Codigo";
    private static final String KEY_IDENTIFICACION = "Identificacion";

    //para tabla PREGUNTAS, Ingreso de solicitudes
    private static final String KEY_IDPREGUNTA = "IdPregunta";
    private static final String KEY_PREGUNTA = "Pregunta";
    private static final String KEY_MOTIVO = "Motivo";
    private static final String KEY_OBSERVACION = "Observacion";
    private static final String KEY_FECHA = "Fecha";

    //para tabla CALIFICACIONES, Ingreso de Calificaciones
    private static final String KEY_IDCALIFICACION = "IdCalificacion";
    private static final String KEY_USUARIO1= "Usuario1";
    private static final String KEY_CALIFICACION = "Calificacion";
    private static final String KEY_SUGERENCIA = "Sugerencia";
    private static final String KEY_FECHA1 = "Fecha1";


    public UsuariosSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    interface Tablas{
        String TABLE_USUARIO = "PerfilUsuario";
        String TABLE_TIPO_USUARIO= "TipoUsuario";
        String TABLE_PREGUNTAS = "Preguntas";
        String TABLE_CALIFICACIONES = "Calificaciones";
    }

    interface Referencias{

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Tablas.TABLE_USUARIO + "("
                + KEY_ID + " INTEGER ," + KEY_USUARIO + " TEXT PRIMARY KEY,"
                + KEY_NOMBRES + " TEXT,"
                + KEY_APELLIDOS + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";

       String CREATE_TIPOUSUARIO_TABLE = "CREATE TABLE " + Tablas.TABLE_TIPO_USUARIO + "("
                + KEY_IDTIPOU + " INTEGER ," + KEY_ROL + " TEXT,"
                + KEY_CODIGO_ESTUDIANTE + " TEXT,"
                + KEY_IDENTIFICACION + " TEXT" + ")";

        String CREATE_PREGUNTAS_TABLE = "CREATE TABLE " + Tablas.TABLE_PREGUNTAS + "("
                + KEY_IDPREGUNTA + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PREGUNTA + " TEXT,"
                + KEY_MOTIVO + " TEXT,"
                + KEY_OBSERVACION + " TEXT,"
                + KEY_FECHA + " TEXT" + ")";

        String CREATE_CALIFICACIONES_TABLE = "CREATE TABLE " + Tablas.TABLE_CALIFICACIONES + "("
                + KEY_IDCALIFICACION + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USUARIO1 + " TEXT,"
                + KEY_CALIFICACION + " TEXT,"
                + KEY_SUGERENCIA + " TEXT,"
                + KEY_FECHA1 + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_PREGUNTAS_TABLE);
        db.execSQL(CREATE_TIPOUSUARIO_TABLE);
        db.execSQL(CREATE_CALIFICACIONES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_TIPO_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_PREGUNTAS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_CALIFICACIONES);
        // Creating tables again
        onCreate(db);
    }

    public void InsertarUsuario(Integer id, String usuario, String nombres, String apellidos, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_USUARIO, usuario); // Shop Name
        values.put(KEY_NOMBRES, nombres); // Shop Name
        values.put(KEY_APELLIDOS, apellidos);
        values.put(KEY_PASSWORD, password);// Shop Name
        values.put(KEY_EMAIL, email); // Shop Phone Number
        // Inserting Row
        db.insert(Tablas.TABLE_USUARIO, null, values);
        db.close(); // Closing database connection
    }

    public void InsertarTipoUsuario(Integer id, String rol, String codigo,String identificacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IDTIPOU, id);
        values.put(KEY_ROL, rol); // Shop Name
        values.put(KEY_CODIGO_ESTUDIANTE, codigo); // Shop Name
        values.put(KEY_IDENTIFICACION, identificacion);// Shop Name
        // Inserting Row
        db.insert(Tablas.TABLE_TIPO_USUARIO, null, values);
        db.close(); // Closing database connection
    }

    public void InsertarPregunta(String pregunta, String motivo, String oservacion, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_IDPREGUNTA, idpregunta);
        values.put(KEY_PREGUNTA, pregunta); // Shop Name
        values.put(KEY_MOTIVO, motivo);
        values.put(KEY_OBSERVACION, oservacion);
        values.put(KEY_FECHA, fecha);
        // Inserting Row
        db.insert(Tablas.TABLE_PREGUNTAS, null, values);
        db.close(); // Closing database connection
    }

    public void InsertarCalificacion(String usuario1, String calificacion, String sugerencia, String fecha1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_IDPREGUNTA, idpregunta);
        values.put(KEY_USUARIO1, usuario1); // Shop Name
        values.put(KEY_CALIFICACION, calificacion);
        values.put(KEY_SUGERENCIA, sugerencia);
        values.put(KEY_FECHA1, fecha1);
        // Inserting Row
        db.insert(Tablas.TABLE_CALIFICACIONES, null, values);
        db.close(); // Closing database connection
    }


    public boolean HayUsuarios(Integer fieldValue) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from " + Tablas.TABLE_USUARIO + " where " + KEY_ID + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            return false;
        }
        return true;
    }

    public boolean HayTipoUsuarios(Integer fieldValue) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from " + Tablas.TABLE_TIPO_USUARIO + " where " + KEY_IDTIPOU + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            return false;
        }
        return true;
    }
    public boolean CLeanUsers() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        sqldb.delete(Tablas.TABLE_USUARIO, "IdAux=1", null);
        return true;
    }

    public boolean CLeanTipoUsers() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        sqldb.delete(Tablas.TABLE_TIPO_USUARIO, "IdTipoUsuario=1", null);
        return true;
    }

    public boolean CLeanCalificaciones() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        sqldb.delete(Tablas.TABLE_CALIFICACIONES, null, null);
        return true;
    }

    public boolean HayPreguntas() {
        // devuelve si hay por lo menos un registro en la tabla
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + Tablas.TABLE_PREGUNTAS;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            return false;
        }
        return true;
    }

    public boolean HayCalificaciones() {
        // devuelve si hay por lo menos un registro en la tabla
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + Tablas.TABLE_CALIFICACIONES;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            return false;
        }
        return true;
    }

    public int CantidadPreguntas() {                    // devuelve si hay por lo menos un registro en la tabla
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + Tablas.TABLE_PREGUNTAS;
        Cursor cursor = sqldb.rawQuery(Query, null);
        int valor=cursor.getCount();

        return valor;
    }


    public boolean CLean() {
        SQLiteDatabase sqldb = this.getReadableDatabase();

        //revisar ... es bueno pasar un parametro como user para poder eliminar tabn todos los vehiculos
       /* for (int i = 1; i <= numeroVehiculos;i++) {
            String consulta = KEY_IDPREGUNTA + "=" + i ;
            sqldb.delete(Tablas.TABLE_PREGUNTAS, consulta, null);
        }
        */
        sqldb.delete(Tablas.TABLE_USUARIO, null, null);//borra los datos de id en caso de tener preguntas guardadas

        sqldb.delete(Tablas.TABLE_PREGUNTAS, null, null);

        return true;
    }


    public boolean PreguntaExiste(String pregunta) throws SQLException {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from " + Tablas.TABLE_PREGUNTAS + " where " + KEY_PREGUNTA + " = " + "'" +pregunta+ "'";
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            return false;
        }
        return true;
    }
    public Cursor ObtenerDatos(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();

       Cursor cursor = db.query(Tablas.TABLE_USUARIO, new String[]{KEY_ID,
                       KEY_USUARIO, KEY_NOMBRES, KEY_APELLIDOS, KEY_PASSWORD, KEY_EMAIL}, KEY_ID + "=?",
               new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
        // return shop
    }

    public Cursor ObtenerDatosTipoUsuario(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Tablas.TABLE_TIPO_USUARIO, new String[]{KEY_IDTIPOU,
                        KEY_ROL, KEY_CODIGO_ESTUDIANTE, KEY_IDENTIFICACION}, KEY_IDTIPOU + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
        // return shop
    }

    public Cursor ObtenerPreguntas(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Tablas.TABLE_PREGUNTAS, new String[]{KEY_IDPREGUNTA,
                        KEY_PREGUNTA, KEY_MOTIVO, KEY_OBSERVACION, KEY_FECHA}, KEY_IDPREGUNTA + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
// return shop
    }

    public ArrayList<Solicitud> getCartList() {

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Solicitud> cartList = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + Tablas.TABLE_PREGUNTAS + ";";

            Cursor cursor = db.rawQuery(query, null);

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                cartList.add(new Solicitud(
                        cursor.getString(cursor.getColumnIndex(KEY_PREGUNTA)),
                        cursor.getString(cursor.getColumnIndex(KEY_MOTIVO)),
                        0,
                        cursor.getString(cursor.getColumnIndex(KEY_OBSERVACION)),
                        cursor.getString(cursor.getColumnIndex(KEY_FECHA)),
                        ""
                        ));
            }

            db.close();

        } catch (SQLiteException e) {
            db.close();
        }
        return cartList;
    }

    public ArrayList<Solicitud> getCalificacionesList() {

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Solicitud> cartList = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + Tablas.TABLE_CALIFICACIONES + ";";

            Cursor cursor = db.rawQuery(query, null);

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                cartList.add(new Solicitud(
                        cursor.getString(cursor.getColumnIndex(KEY_USUARIO1)),
                        cursor.getString(cursor.getColumnIndex(KEY_CALIFICACION)),
                        0,
                        cursor.getString(cursor.getColumnIndex(KEY_SUGERENCIA)),
                        cursor.getString(cursor.getColumnIndex(KEY_FECHA1)),
                        ""
                ));
            }

            db.close();

        } catch (SQLiteException e) {
            db.close();
        }
        return cartList;
    }



/*
    public List<Vehiculos> getVehiculos(){

        SQLiteDatabase db = this.getReadableDatabase();
        List<Vehiculos> listaVehiculos = new ArrayList<Vehiculos>();

        Cursor c = db.query("contactos", valores_recuperar,
                null, null, null, null, null, null);
        c.moveToFirst();
        do {
            Vehiculos contactos = new Contactos(c.getInt(0), c.getString(1),
                    c.getInt(2), c.getString(3));
            listaVehiculos.add(contactos);
        } while (c.moveToNext());
        db.close();
        c.close();

        return listaVehiculos;

    }

    */
}
