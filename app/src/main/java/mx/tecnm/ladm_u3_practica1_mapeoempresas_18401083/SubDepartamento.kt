package mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log

class SubDepartamento(ventana:Context) {
    var idSubDepto = -1
    var idEdificio = ""
    var piso = ""
    var idArea = -1
    var area_Descripcion = ""
    var area_Division = ""
    var ventana = ventana
    var err = ""

    fun mostrarTodasdeArea(idA: Int):ArrayList<SubDepartamento>{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        var subDepartamentos = ArrayList<SubDepartamento>()
        err= ""
        var idA = idA
        try {
            var tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM SUBDEPARTAMENTO S " +
                    "INNER JOIN AREA A " +
                    "ON A.ID_AREA = S.ID_AREA " +
                    "WHERE S.ID_AREA='${idA}'"
            Log.e("ID Area: ${idA}","Consulta: \n${SQL_SELECT}")
            var cursor = tabla.rawQuery(SQL_SELECT, null)
            if (cursor.moveToFirst()){
                do{
                    var subDepartamento = SubDepartamento(ventana)
                    subDepartamento.idSubDepto = cursor.getInt(0)
                    subDepartamento.idEdificio = cursor.getString(1)
                    subDepartamento.piso = cursor.getString(2)
                    subDepartamento.idArea = cursor.getInt(3)
                    subDepartamentos.add(subDepartamento)
                }while (cursor.moveToNext())
            }
        } catch (err:SQLiteException){
            this.err = err.message!!
            Log.e("Error","No se pudo mostrar :(")
        }finally {
            baseDatos.close()
        }
        return subDepartamentos
    }
    fun mostrarTodo():ArrayList<SubDepartamento>{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        var subDepas = ArrayList<SubDepartamento>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT S.ID_SUBDEPTO, S.ID_EDIFICIO, S.PISO, A.DESCRIPCION, A.DIVISION, A.ID_AREA " +
                    "FROM SUBDEPARTAMENTO S " +
                    "INNER JOIN AREA A " +
                    "ON A.ID_AREA = S.ID_AREA"
            var cursor = tabla.rawQuery(SQL_SELECT, null)
            if (cursor.moveToFirst()){
                do {
                    var subDepa = SubDepartamento(ventana)
                    subDepa.idSubDepto = cursor.getInt(0)
                    subDepa.idEdificio = cursor.getString(1)
                    subDepa.piso = cursor.getString(2)
                    subDepa.area_Descripcion = cursor.getString(3)
                    subDepa.area_Division = cursor.getString(4)
                    subDepa.idArea = cursor.getInt(5)

                    subDepas.add(subDepa)
                }while (cursor.moveToNext())
            }

        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return subDepas
    }
    fun mostrarSubDepa(id:Int):SubDepartamento{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        var subDepa = SubDepartamento(ventana)
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT S.ID_SUBDEPTO, S.ID_EDIFICIO, S.PISO, A.DESCRIPCION, A.DIVISION, A.ID_AREA " +
                    "FROM SUBDEPARTAMENTO S " +
                    "INNER JOIN AREA A " +
                    "ON A.ID_AREA = S.ID_AREA " +
                    "WHERE S.ID_SUBDEPTO = '${id}' "
            var cursor = tabla.rawQuery(SQL_SELECT, null)
            if (cursor.moveToFirst()){

                    subDepa.idSubDepto = cursor.getInt(0)
                    subDepa.idEdificio = cursor.getString(1)
                    subDepa.piso = cursor.getString(2)
                    subDepa.area_Descripcion = cursor.getString(3)
                    subDepa.area_Division = cursor.getString(4)
                    subDepa.idArea = cursor.getInt(5)
            }

        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return subDepa
    }
    fun mostrarSubDepaPor(dato:String,tipo:String):ArrayList<SubDepartamento>{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        var subDepas = ArrayList<SubDepartamento>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase

            if (tipo.equals("ID_EDIFICIO")){
                var SQL_SELECT1 = "SELECT S.ID_SUBDEPTO, S.ID_EDIFICIO, S.PISO, A.DESCRIPCION, A.DIVISION, A.ID_AREA " +
                        "FROM SUBDEPARTAMENTO S " +
                        "INNER JOIN AREA A " +
                        "ON A.ID_AREA = S.ID_AREA " +
                        "WHERE S.ID_EDIFICIO LIKE '${dato}%'"
                Log.e("Error","Consulta: ${SQL_SELECT1}")
                var cursor = tabla.rawQuery(SQL_SELECT1, null)

                if (cursor.moveToFirst()){
                    do {
                        var subDepa = SubDepartamento(ventana)
                        subDepa.idSubDepto = cursor.getInt(0)
                        subDepa.idEdificio = cursor.getString(1)
                        subDepa.piso = cursor.getString(2)
                        subDepa.area_Descripcion = cursor.getString(3)
                        subDepa.area_Division = cursor.getString(4)
                        subDepa.idArea = cursor.getInt(5)

                        subDepas.add(subDepa)
                    }while (cursor.moveToNext())

                }
            }
            if (tipo.equals("DESCRIPCION")){
                var SQL_SELECT2 = "SELECT S.ID_SUBDEPTO, S.ID_EDIFICIO, S.PISO, A.DESCRIPCION, A.DIVISION, A.ID_AREA " +
                        "FROM SUBDEPARTAMENTO S " +
                        "INNER JOIN AREA A " +
                        "ON A.ID_AREA = S.ID_AREA " +
                        "WHERE A.DESCRIPCION LIKE '${dato}%'"
                Log.e("Error","Consulta: ${SQL_SELECT2}")
                var cursor = tabla.rawQuery(SQL_SELECT2, null)

                if (cursor.moveToFirst()){
                    do {
                        var subDepa = SubDepartamento(ventana)
                        subDepa.idSubDepto = cursor.getInt(0)
                        subDepa.idEdificio = cursor.getString(1)
                        subDepa.piso = cursor.getString(2)
                        subDepa.area_Descripcion = cursor.getString(3)
                        subDepa.area_Division = cursor.getString(4)
                        subDepa.idArea = cursor.getInt(5)

                        subDepas.add(subDepa)
                    }while (cursor.moveToNext())

                }
            }
            if (tipo.equals("DIVISION")){
                var SQL_SELECT3 = "SELECT S.ID_SUBDEPTO, S.ID_EDIFICIO, S.PISO, A.DESCRIPCION, A.DIVISION, A.ID_AREA " +
                        "FROM SUBDEPARTAMENTO S " +
                        "INNER JOIN AREA A " +
                        "ON A.ID_AREA = S.ID_AREA " +
                        "WHERE A.DIVISION LIKE '${dato}%'"
                Log.e("Error","Consulta: ${SQL_SELECT3}")
                var cursor = tabla.rawQuery(SQL_SELECT3, null)

                if (cursor.moveToFirst()){
                    do {
                        var subDepa = SubDepartamento(ventana)
                        subDepa.idSubDepto = cursor.getInt(0)
                        subDepa.idEdificio = cursor.getString(1)
                        subDepa.piso = cursor.getString(2)
                        subDepa.area_Descripcion = cursor.getString(3)
                        subDepa.area_Division = cursor.getString(4)
                        subDepa.idArea = cursor.getInt(5)

                        subDepas.add(subDepa)
                    }while (cursor.moveToNext())

                }
            }


        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return subDepas
    }
    fun insertar():Boolean{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()
            datos.put("ID_EDIFICIO", idEdificio)
            datos.put("PISO", piso)
            datos.put("ID_AREA", idArea)
            var resultado = tabla.insert("SUBDEPARTAMENTO",null,datos)
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
    fun eliminarSubDepartamento(id:Int):Boolean{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("SUBDEPARTAMENTO","ID_SUBDEPTO=?", arrayOf(id.toString()))
            if (resultado == 0){return false} //No se borró
        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return true
    }
    fun actualizar():Boolean{
        var baseDatos = BaseDatos(ventana,"mapeo_empresas",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            var datosActualizados = ContentValues()
            datosActualizados.put("ID_EDIFICIO",idEdificio)
            datosActualizados.put("PISO",piso)
            val resultado = tabla.update("SUBDEPARTAMENTO",datosActualizados,"ID_SUBDEPTO=?", arrayOf(idSubDepto.toString()))
            Log.e("Resultado de actualizar",resultado.toString())
            if (resultado == 0 ){return false}//Sin actualización
        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return true
    }

}