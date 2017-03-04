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


    private static final String DATABASE_NAME = "Usuario";// Database Name
    private static final int DATABASE_VERSION = 1;   // Database Version"

    // para tabla REGISTRO
    private static final String KEY_ID = "IdAux";
    private static final String KEY_USUARIO = "Usuario";
    private static final String KEY_NAME = "Nombres";
    private static final String KEY_APELLIDOS = "Apellidos";
    private static final String KEY_CONTRA = "Contra";
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

        String TABLE_REGISTRO = "Registro";
        String TABLE_VEHICULOS = "Vehiculos";

    }

    interface Referencias{

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Tablas.TABLE_REGISTRO + "("
                + KEY_ID + " INTEGER ," + KEY_USUARIO + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_APELLIDOS + " TEXT,"
                + KEY_CONTRA + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";

        String CREATE_VEHICULOS_TABLE = "CREATE TABLE " + Tablas.TABLE_VEHICULOS + "("
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
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_REGISTRO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TABLE_VEHICULOS);
        // Creating tables again
        onCreate(db);
    }

    public void AgregarUsuario(Integer id, String usuario, String NombreCompleto, String apellidos, String pass, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_USUARIO, usuario); // Shop Name
        values.put(KEY_NAME, NombreCompleto); // Shop Name
        values.put(KEY_APELLIDOS, apellidos);
        values.put(KEY_CONTRA, pass);// Shop Name
        values.put(KEY_EMAIL, email); // Shop Phone Number
        // Inserting Row
        db.insert(Tablas.TABLE_REGISTRO, null, values);
        db.close(); // Closing database connection
    }

    public Cursor ObtenerDatos(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();

       Cursor cursor = db.query(Tablas.TABLE_REGISTRO, new String[]{KEY_ID,
                       KEY_USUARIO, KEY_NAME, KEY_APELLIDOS, KEY_CONTRA, KEY_EMAIL}, KEY_ID + "=?",
               new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
        // return shop

    }
    public boolean UsuarioNuevo(Integer fieldValue) {
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + Tablas.TABLE_REGISTRO + " where " + KEY_ID + " = " + fieldValue;
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
        db.insert(Tablas.TABLE_VEHICULOS, null, values);
        db.close(); // Closing database connection
    }

    public Cursor ObtenerVehiculos(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(Tablas.TABLE_VEHICULOS, new String[]{KEY_IDVEHI,
                        KEY_PLACA, KEY_TIPO, KEY_MARCA, KEY_REFERENCIA, KEY_SOAT, KEY_TECNO, KEY_COLOR, KEY_USER}, KEY_IDVEHI + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);


        if (cursor != null)
            cursor.moveToFirst();


        return cursor;
// return shop
    }

    public boolean HayCarros() {                    // devuelve si hay por lo menos un registro en la tabla
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + Tablas.TABLE_VEHICULOS ;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            return false;
        }
        return true;
    }

    public boolean CLean(Integer numeroVehiculos) {
        SQLiteDatabase sqldb = this.getReadableDatabase();

        //revisar ... es bueno pasar un parametro como user para poder eliminar tabn todos los vehiculos
       for (int i = 0; i < numeroVehiculos;i++) {
            String consulta = KEY_IDVEHI + "=" + i ;
            sqldb.delete(Tablas.TABLE_VEHICULOS, consulta, null);
        }
        sqldb.delete(Tablas.TABLE_REGISTRO, "IdAux=1", null);
        return true;
    }

    public boolean CLeanUsers() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        sqldb.delete(Tablas.TABLE_REGISTRO, "IdAux=1", null);
        return true;
    }

    public int HayCaarros() {                    // devuelve si hay por lo menos un registro en la tabla
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + Tablas.TABLE_VEHICULOS ;
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
