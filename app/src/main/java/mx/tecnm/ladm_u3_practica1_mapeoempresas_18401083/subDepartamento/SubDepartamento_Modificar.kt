package mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.subDepartamento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.SubDepartamento
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.databinding.ActivitySubDepartamentoModificarBinding

class SubDepartamento_Modificar : AppCompatActivity() {
    lateinit var binding: ActivitySubDepartamentoModificarBinding
    var idArea = -1
    var descripcion = ""
    var idSubDepto = -1
    var idEdificio = ""
    var piso = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubDepartamentoModificarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idArea = this.intent.extras!!.getInt("idArea")!!
        descripcion = this.intent.extras!!.getString("descripcion")!!
        idSubDepto = this.intent.extras!!.getInt("idSubDepto")!!
        idEdificio = this.intent.extras!!.getString("idEdificio")!!
        piso = this.intent.extras!!.getString("piso")!!

        this.setTitle("Modificando: ${descripcion} \n" +
                "Id: ${idSubDepto}")
        binding.txtIdEdificio.setText(idEdificio)
        binding.txtIdEdificio.requestFocus()
        binding.txtPiso.setText(piso)

        binding.btnRegresar.setOnClickListener {
            finish()
        }
        binding.btnModificar.setOnClickListener {

            if (txtEstaVacio(binding.txtIdEdificio)){return@setOnClickListener}
            if (txtEstaVacio(binding.txtPiso)){return@setOnClickListener}

            var subDepa = SubDepartamento(this)

            subDepa.idSubDepto = idSubDepto

            subDepa.idEdificio = binding.txtIdEdificio.text.toString()
            subDepa.piso = binding.txtPiso.text.toString()
            subDepa.idArea = idArea

            val respuesta = subDepa.actualizar()
            if (respuesta){
                Toast.makeText(this,"Se actualizó", Toast.LENGTH_LONG).show()
            }
            else{
                AlertDialog.Builder(this)
                    .setMessage("Error, no se actualizó")
                    .show()
            }
            limpiarCampos()
            binding.txtIdEdificio.requestFocus()
        }

    }
    private fun limpiarCampos() {
        binding.txtIdEdificio.setText("")
        binding.txtPiso.setText("")
    }
    private fun txtEstaVacio(txt: EditText):Boolean {
        if (txt.getText().toString().trim().equals("")) {
            txt.setError("Required!");
            return true
        }
        return false
    }

}