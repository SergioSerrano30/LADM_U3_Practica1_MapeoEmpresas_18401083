package mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.fragment.app.Fragment

class Area(ventana:Context) {
    var id_area = -1
    var descripcion = ""
    var division = ""
    var cantidad_empleados = -1
    val ventana = ventana
    var err = ""

    fun insertar():Boolean{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()

            datos.put("DESCRIPCION",descripcion)
            datos.put("DIVISION",division)
            datos.put("CANTIDAD_EMPLEADOS",cantidad_empleados)

            var resultado = tabla.insert("AREA",null,datos)
            if (resultado==-1L){
                return false
            }
        }catch (err:SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun mostrarTodo():ArrayList<Area>{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        var areas = ArrayList<Area>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM AREA"
            var cursor = tabla.rawQuery(SQL_SELECT, null)
            if (cursor.moveToFirst()){
                do {
                    var area = Area(ventana)
                    area.id_area = cursor.getInt(0)
                    area.descripcion = cursor.getString(1)
                    area.division = cursor.getString(2)
                    area.cantidad_empleados = cursor.getInt(3)
                    areas.add(area)
                }while (cursor.moveToNext())
            }

        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return areas
    }

    fun mostrarAreaPor(dato:String,tipo:String):ArrayList<Area>{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        var areas = ArrayList<Area>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase

            if (tipo.equals("DESCRIPCION")){
                var SQL_SELECT1 = "SELECT * FROM AREA WHERE DESCRIPCION LIKE'${dato}%'"
                Log.e("Error","Consulta: ${SQL_SELECT1}")
                var cursor = tabla.rawQuery(SQL_SELECT1, null)

                if (cursor.moveToFirst()){
                    do {
                        var area = Area(ventana)
                        area.id_area = cursor.getInt(0)
                        area.descripcion = cursor.getString(1)
                        area.division = cursor.getString(2)
                        area.cantidad_empleados = cursor.getInt(3)
                        areas.add(area)
                    }while (cursor.moveToNext())

                }
            }
            if (tipo.equals("DIVISION")){
                var SQL_SELECT2 = "SELECT * FROM AREA WHERE DIVISION LIKE'${dato}%'"
                Log.e("Error","Consulta: ${SQL_SELECT2}")
                var cursor = tabla.rawQuery(SQL_SELECT2, null)
                if (cursor.moveToFirst()){
                    do {
                        var area = Area(ventana)
                        area.id_area = cursor.getInt(0)
                        area.descripcion = cursor.getString(1)
                        area.division = cursor.getString(2)
                        area.cantidad_empleados = cursor.getInt(3)
                        areas.add(area)
                    }while (cursor.moveToNext())
                }
            }


        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return areas
    }

    fun mostrarArea(idArea:Int):Area{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        var area = Area(ventana)
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQL_SELECT = ""
            SQL_SELECT = "SELECT * FROM AREA WHERE ID_AREA=?"
            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(idArea.toString()))
            if (cursor.moveToFirst()){
                area.id_area = cursor.getInt(0)
                area.descripcion = cursor.getString(1)
                area.division = cursor.getString(2)
                area.cantidad_empleados = cursor.getInt(3)
            }
        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return area
    }

    fun modificar():Boolean{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            var datosActualizados = ContentValues()
            datosActualizados.put("DESCRIPCION",descripcion)
            datosActualizados.put("DIVISION",division)
            datosActualizados.put("CANTIDAD_EMPLEADOS",cantidad_empleados)
            val resultado = tabla.update("AREA",datosActualizados,"ID_AREA='${id_area}'", null)
            Log.e("Resultado",resultado.toString())
            if (resultado == 0 ){return false}//Sin actualización
        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun eliminarArea(idAreaEliminar:Int):Boolean{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("AREA","ID_AREA=?", arrayOf(idAreaEliminar.toString()))
            if (resultado == 0){return false} //No se borró
        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return true
    }


    /*var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
    err = ""
    try {

    }catch (err:SQLiteException){
        this.err = err.message!!
        return false
    }finally {
        baseDatos.close()
    }
    return true*/

}