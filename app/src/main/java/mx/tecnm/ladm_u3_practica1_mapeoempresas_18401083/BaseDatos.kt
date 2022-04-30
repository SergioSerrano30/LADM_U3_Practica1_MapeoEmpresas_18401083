package mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
):SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE AREA (" +
            "ID_AREA INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DESCRIPCION VARCHAR(200), " +
            "DIVISION VARCHAR(50), " +
            "CANTIDAD_EMPLEADOS INTEGER" +
            ")"
        )
        db.execSQL("CREATE TABLE SUBDEPARTAMENTO (" +
                "ID_SUBDEPTO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID_EDIFICIO VARCHAR(20), " +
                "PISO VARCHAR(50), " +
                "ID_AREA INTEGER," +
                "FOREIGN KEY (ID_AREA) REFERENCES AREA(ID_AREA))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {

    }

}