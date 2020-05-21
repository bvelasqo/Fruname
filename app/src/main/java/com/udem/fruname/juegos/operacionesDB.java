package com.udem.fruname.juegos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udem.fruname.dbresources.DBHelper;

import java.util.ArrayList;

public class operacionesDB {
    Context context;
    DBHelper helper;

    public operacionesDB(Context context,String nameDB){
        this.context=context;
        helper=new DBHelper(context,nameDB,"",null,1);
    }
    //Juego Adivina La Bandera
    public ArrayList<Pregunta> getPreguntaAB(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String SQL = "Select * from AdivinaLaBandera";
        Cursor c = db.rawQuery(SQL, null);
        ArrayList<Pregunta> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                String op1=c.getString(1),op2=c.getString(2),op3=c.getString(3),
                        op4=c.getString(4),correcta=c.getString(5);
                list.add(new Pregunta("",op1,op2,op3,op4,correcta));
            } while (c.moveToNext());
        }
        db.close();
        return list;
    }
}
