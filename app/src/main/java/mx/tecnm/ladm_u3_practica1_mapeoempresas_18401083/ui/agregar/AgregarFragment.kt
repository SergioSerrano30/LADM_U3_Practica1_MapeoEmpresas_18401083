package mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.ui.agregar

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.Area
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.R
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.databinding.FragmentAgregarBinding
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.subDepartamento.SubDepartamento_Agregar
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.ui.modificar.ModificarFragment
import java.lang.Error

class AgregarFragment() : Fragment() {

    private var _binding: FragmentAgregarBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var listaIdArea = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(AgregarViewModel::class.java)

        _binding = FragmentAgregarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mostrarDatosLV()

        binding.btnAgregar.setOnClickListener {

            if (txtEstaVacio(binding.txtDescripcion)){return@setOnClickListener}
            if (txtEstaVacio(binding.txtDivision)){return@setOnClickListener}
            if (txtEstaVacio(binding.txtCantUsuarios)){return@setOnClickListener}

            var area = Area(this.requireContext())
            area.descripcion = binding.txtDescripcion.text.toString()
            area.division = binding.txtDivision.text.toString()
            area.cantidad_empleados = binding.txtCantUsuarios.text.toString().toInt()
            val resultado = area.insertar()
            if (resultado){
                Toast.makeText(this.requireContext(),"Se insertó correctamente el area",Toast.LENGTH_LONG).show()
                mostrarDatosLV()
            }
            else{
                AlertDialog.Builder(this.requireContext())
                    .setTitle("¡Error!")
                    .setMessage("No se pudo insertar")
                    .show()
            }
            limpiarCampos()
        }
        binding.lvArea.setOnItemClickListener { adapterView, view, i, l ->
            val idArea = listaIdArea.get(i)
                val area = Area(this.requireContext()).mostrarArea(idArea)

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Area")
                    .setMessage("¿Que deseas hacer con ${area.descripcion}, " +
                            "\nDivision :${area.division}" +
                            "\nCant. Empleados: ${area.cantidad_empleados}?")
                    .setNegativeButton("Eliminar"){d,i->
                        AlertDialog.Builder(this.requireContext())
                            .setMessage("¿Estás seguro de eliminarlo?")
                            .setPositiveButton("Si, eliminar"){d,i->
                                area.eliminarArea(idArea)
                                mostrarDatosLV()
                            }
                            .setNegativeButton("No, cancelar"){r,i ->
                                Toast.makeText(this.requireContext(),"Operacion cancelada",Toast.LENGTH_LONG).show()
                            }
                            .show()

                    }
                    .setPositiveButton("Agrega SubDepartamento"){d,i->
                        var vtnAgregarSubDepa = Intent(this.requireContext(),SubDepartamento_Agregar::class.java)
                        vtnAgregarSubDepa.putExtra("idArea",area.id_area)
                        vtnAgregarSubDepa.putExtra("descripcion",area.descripcion)
                        startActivity(vtnAgregarSubDepa)
                    }
                    .setNeutralButton("Cancelar"){d,i->}

                    .show()
        }

        return root
    }

    private fun txtEstaVacio(txt:EditText):Boolean {
        if (txt.getText().toString().trim().equals("")) {
            txt.setError("Required!");
            return true
        }
        return false
    }

    private fun limpiarCampos() {
        binding.txtDescripcion.setText("")
        binding.txtDivision.setText("")
        binding.txtCantUsuarios.setText("")
    }

    private fun mostrarDatosLV() {
        try {
            var listaArea = Area(this.requireContext()).mostrarTodo()
            var descripcionAreas = ArrayList<String>()
            listaIdArea.clear()
            (0..listaArea.size-1).forEach {
                var area = listaArea.get(it)
                var cad = "Descripcion: "+area.descripcion + "\nDivision: "+area.division+"\nCant. Empleados: "+area.cantidad_empleados+"\n"
                descripcionAreas.add(cad)
                listaIdArea.add(area.id_area)
            }
            binding.lvArea.adapter = ArrayAdapter<String>(this.requireContext(),android.R.layout.simple_list_item_1,descripcionAreas)

        }catch (e:Error){
            Log.e("Error","Mensaje: ${e.message}")
        }

    }

    override fun onStart() {
        super.onStart()
        mostrarDatosLV()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}