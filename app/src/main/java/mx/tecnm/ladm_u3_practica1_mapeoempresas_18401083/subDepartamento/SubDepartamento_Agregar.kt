package mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.subDepartamento

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.Area
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.SubDepartamento
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.databinding.ActivitySubDepartamentoAgregarBinding

class SubDepartamento_Agregar:AppCompatActivity() {

    lateinit var binding: ActivitySubDepartamentoAgregarBinding
    var idArea = -1
    var descripcion = ""
    var listaIdSubDepa = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubDepartamentoAgregarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idArea = this.intent.extras!!.getInt("idArea")!!
        descripcion = this.intent.extras!!.getString("descripcion")!!

        val area = Area(this).mostrarArea(idArea)
        this.setTitle("Areas para: ${descripcion}")
        mostrarDatosLV()
        binding.btnAgregar.setOnClickListener {

            if (txtEstaVacio(binding.txtIdEdificio)){return@setOnClickListener}
            if (txtEstaVacio(binding.txtPiso)){return@setOnClickListener}

            var subDepa = SubDepartamento(this)
            subDepa.idEdificio = binding.txtIdEdificio.text.toString()
            subDepa.piso = binding.txtPiso.text.toString()
            subDepa.idArea = idArea
            val resultado = subDepa.insertar()
            if (resultado){
                Toast.makeText(this,"Se insertó correctamente el Subarea de ${descripcion}", Toast.LENGTH_LONG).show()
                mostrarDatosLV()
            }
            else{
                AlertDialog.Builder(this)
                    .setTitle("¡Error!")
                    .setMessage("No se pudo insertar")
                    .show()
            }
            limpiarCampos()
            binding.txtIdEdificio.requestFocus()

        }
        binding.lvSubDepartamento.setOnItemClickListener { adapterView, view, i, l ->
            val idSubDepartamento = listaIdSubDepa.get(i)
            Log.e("ID sub Departamento",idSubDepartamento.toString())
            val subDepa = SubDepartamento(this).mostrarSubDepa(idSubDepartamento)
            Log.e("Resultado subDepa","ID: ${subDepa.idSubDepto.toString()}\n" +
                    "IDEdificio: ${subDepa.idEdificio}")
            AlertDialog.Builder(this)
                .setTitle("Area")
                .setMessage("¿Que deseas hacer el Edificio: ${subDepa.idEdificio}, " +
                        "\nPiso :${subDepa.piso}?")
                .setNegativeButton("Eliminar"){d,i->
                    AlertDialog.Builder(this)
                        .setMessage("¿Estás seguro de eliminarlo?")
                        .setPositiveButton("Si, eliminar"){d,i->
                            subDepa.eliminarSubDepartamento(idSubDepartamento)
                            mostrarDatosLV()
                        }
                        .setNegativeButton("No, cancelar"){r,i ->
                            Toast.makeText(this,"Operacion cancelada",Toast.LENGTH_LONG).show()
                        }
                        .show()

                }
                .setPositiveButton("Modificar"){d,i->
                    var vtnModificarSubDepa = Intent(this,SubDepartamento_Modificar::class.java)
                    vtnModificarSubDepa.putExtra("idArea",idArea)
                    vtnModificarSubDepa.putExtra("descripcion",descripcion)
                    vtnModificarSubDepa.putExtra("idSubDepto",subDepa.idSubDepto)
                    vtnModificarSubDepa.putExtra("idEdificio",subDepa.idEdificio)
                    vtnModificarSubDepa.putExtra("piso",subDepa.piso)


                    startActivity(vtnModificarSubDepa)
                }
                .setNeutralButton("Cancelar"){d,i->}

                .show()
        }
        binding.btnRegresar.setOnClickListener {
            finish()
        }

    }

    private fun limpiarCampos() {
        binding.txtIdEdificio.setText("")
        binding.txtPiso.setText("")
    }

    private fun mostrarDatosLV(){
        try {
            var listaSubDepa = SubDepartamento(this).mostrarTodasdeArea(idArea)
            var idEdificio = ArrayList<String>()
            listaIdSubDepa.clear()
            (0..listaSubDepa.size-1).forEach {
                var subDepa = listaSubDepa.get(it)
                var cad = "Id Edificio: ${subDepa.idEdificio}" +
                        "\n Piso: ${subDepa.piso} \n"
                idEdificio.add(cad)
                listaIdSubDepa.add(subDepa.idSubDepto)
            }
            binding.lvSubDepartamento.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,idEdificio)

        }catch (e:Error){

        }
    }
    private fun txtEstaVacio(txt: EditText):Boolean {
        if (txt.getText().toString().trim().equals("")) {
            txt.setError("Required!");
            return true
        }
        return false
    }

    override fun onRestart() {
        super.onRestart()
        mostrarDatosLV()
    }
}