package com.universidadeafit.appeafit.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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


    public UsuariosSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    interface Tablas{
        String TABLE_USUARIO = "PerfilUsuario";
        String TABLE_TIPO_USUARIO= "TipoUsuario";
        String TABLE_PREGUNTAS = "Preguntas";

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
                + KEY_IDPREGUNTA + " INTEGER ," + KEY_PREGUNTA + " TEXT PRIMARY KEY,"
                + KEY_MOTIVO + " TEXT,"
                + KEY_OBSERVACION + " TEXT,"
                + KEY_FECHA + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_PREGUNTAS_TABLE);
        db.execSQL(CREATE_TIPOUSUARIO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_TIPO_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_PREGUNTAS);
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

    public void InsertarPregunta(Integer idpregunta, String pregunta, String motivo, String oservacion, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IDPREGUNTA, idpregunta);
        values.put(KEY_PREGUNTA, pregunta); // Shop Name
        values.put(KEY_MOTIVO, motivo);
        values.put(KEY_OBSERVACION, oservacion);
        values.put(KEY_FECHA, fecha);
        // Inserting Row
        db.insert(Tablas.TABLE_PREGUNTAS, null, values);
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

    public int CantidadPreguntas() {                    // devuelve si hay por lo menos un registro en la tabla
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + Tablas.TABLE_PREGUNTAS;
        Cursor cursor = sqldb.rawQuery(Query, null);
        int valor=cursor.getCount();

        return valor;
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

    public boolean CLean(Integer numeroVehiculos) {
        SQLiteDatabase sqldb = this.getReadableDatabase();

        //revisar ... es bueno pasar un parametro como user para poder eliminar tabn todos los vehiculos
       for (int i = 0; i < numeroVehiculos;i++) {
            String consulta = KEY_IDPREGUNTA + "=" + i ;
            sqldb.delete(Tablas.TABLE_PREGUNTAS, consulta, null);
        }
        sqldb.delete(Tablas.TABLE_USUARIO, "IdAux=1", null);
        return true;
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
