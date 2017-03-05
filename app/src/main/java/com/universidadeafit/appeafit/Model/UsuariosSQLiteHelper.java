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

    // para tabla REGISTRO
    private static final String KEY_ID = "IdAux";
    private static final String KEY_USUARIO = "Usuario";
    private static final String KEY_NOMBRES = "Nombres";
    private static final String KEY_APELLIDOS = "Apellidos";
    private static final String KEY_PASSWORD = "Contra";
    private static final String KEY_EMAIL = "Email";

    //para tabla VEHICULOS
    private static final String KEY_IDVEHI = "IdVehiculo";
    private static final String KEY_PLACA = "Placa";
    private static final String KEY_TIPO = "Tipo";
    private static final String KEY_MARCA = "Marca";
    private static final String KEY_REFERENCIA = "Referencia";
    private static final String KEY_SOAT = "Soat";
    private static final String KEY_TECNO = "Tecno";
    private static final String KEY_COLOR = "Color";
    private static final String KEY_USER = "User";

    public UsuariosSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    interface Tablas{
        String TABLE_USUARIO = "PerfilUsuario";
        String TABLE_SOLICITUDES = "Vehiculos";
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

        String CREATE_VEHICULOS_TABLE = "CREATE TABLE " + Tablas.TABLE_SOLICITUDES + "("
                + KEY_IDVEHI + " INTEGER ," + KEY_PLACA + " TEXT PRIMARY KEY,"
                + KEY_TIPO + " TEXT,"
                + KEY_MARCA + " TEXT,"
                + KEY_REFERENCIA + " TEXT,"
                + KEY_SOAT + " TEXT,"
                + KEY_TECNO + " TEXT,"
                + KEY_COLOR + " TEXT,"
                + KEY_USER + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_VEHICULOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_SOLICITUDES);
        // Creating tables again
        onCreate(db);
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

    public boolean HayCarros() {
        // devuelve si hay por lo menos un registro en la tabla
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + Tablas.TABLE_SOLICITUDES;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            return false;
        }
        return true;
    }

    public void AgregarVehiculo(Integer i, String placa, String tipo, String marca, String referencia, String soat, String tecno, String color, String user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDVEHI, i);
        values.put(KEY_PLACA, placa); // Shop Name
        values.put(KEY_TIPO, tipo);
        values.put(KEY_MARCA, marca); // Shop Name
        values.put(KEY_REFERENCIA, referencia);
        values.put(KEY_SOAT, soat);// Shop Name
        values.put(KEY_TECNO, tecno); // Shop Phone Number
        values.put(KEY_COLOR, color);
        values.put(KEY_USER, user);

        // Inserting Row
        db.insert(Tablas.TABLE_SOLICITUDES, null, values);
        db.close(); // Closing database connection
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

    public Cursor ObtenerVehiculos(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Tablas.TABLE_SOLICITUDES, new String[]{KEY_IDVEHI,
                        KEY_PLACA, KEY_TIPO, KEY_MARCA, KEY_REFERENCIA, KEY_SOAT, KEY_TECNO, KEY_COLOR, KEY_USER}, KEY_IDVEHI + "=?",
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
            String consulta = KEY_IDVEHI + "=" + i ;
            sqldb.delete(Tablas.TABLE_SOLICITUDES, consulta, null);
        }
        sqldb.delete(Tablas.TABLE_USUARIO, "IdAux=1", null);
        return true;
    }

    public boolean CLeanUsers() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        sqldb.delete(Tablas.TABLE_USUARIO, "IdAux=1", null);
        return true;
    }

    public int HayCaarros() {                    // devuelve si hay por lo menos un registro en la tabla
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + Tablas.TABLE_SOLICITUDES;
        Cursor cursor = sqldb.rawQuery(Query, null);
        int valor=cursor.getCount();

        return valor;
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
