package mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.ui.buscar

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.Area
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.SubDepartamento
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.databinding.FragmentBuscarBinding
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.subDepartamento.SubDepartamento_Agregar
import mx.tecnm.ladm_u3_practica1_mapeoempresas_18401083.subDepartamento.SubDepartamento_Modificar
import java.lang.Error

class BuscarFragment : Fragment() {

    private var _binding: FragmentBuscarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var listaIdArea = ArrayList<Int>()
    var listaIdSubDepa = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(BuscarViewModel::class.java)

        _binding = FragmentBuscarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rbArea.isChecked = true
        ponerOpcionesArea()
        mostrarArea_Todo()


        binding.txtBusqueda.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(cambio: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(cambio: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (binding.rbArea.isChecked){
                    if (!binding.txtBusqueda.text.toString().equals("")) {
                        if (binding.rbOp2.isChecked) {mostrarArea_Por(binding.txtBusqueda.text.toString(), "DESCRIPCION")}
                        if (binding.rbOp3.isChecked) {mostrarArea_Por(binding.txtBusqueda.text.toString(), "DIVISION")}
                    }
                    else{binding.lvDatos.adapter = null}
                }
                if (binding.rbSubDep.isChecked){
                    if (!binding.txtBusqueda.text.toString().equals("")) {
                        if (binding.rbOp2.isChecked) {mostrarSubDepartamento_Por(binding.txtBusqueda.text.toString(), "ID_EDIFICIO")}
                        if (binding.rbOp3.isChecked) {mostrarSubDepartamento_Por(binding.txtBusqueda.text.toString(), "DESCRIPCION")}
                        if (binding.rbOp4.isChecked) {mostrarSubDepartamento_Por(binding.txtBusqueda.text.toString(), "DIVISION")}

                    }
                    else{binding.lvDatos.adapter = null}
                }

            }
            override fun afterTextChanged(cambio: Editable?) {
            }

        })
        binding.rbArea.setOnClickListener {
            ponerOpcionesArea()
            mostrarArea_Todo()
        }
        binding.rbSubDep.setOnClickListener {
            ponerOpcionesSubDep()
            mostrarSubDepartamento_Todo()
        }
        binding.rbOp1.setOnClickListener {
            bloqueaBusqueda()
            if (binding.rbArea.isChecked){mostrarArea_Todo()}
            if (binding.rbSubDep.isChecked){mostrarSubDepartamento_Todo()}
        }
        binding.rbOp2.setOnClickListener {
            activaBusqueda()
        }
        binding.rbOp3.setOnClickListener {
            activaBusqueda()
        }
        binding.rbOp4.setOnClickListener {
            activaBusqueda()
        }
        binding.lvDatos.setOnItemClickListener { adapterView, view, i, l ->
            var id = -1
            var datosArea:Area = Area(this.requireContext()).mostrarArea(id)
            var datosSubDepa:SubDepartamento = SubDepartamento(this.requireContext()).mostrarSubDepa(id)
            var mensaje = ""
            if (binding.rbArea.isChecked){
                id = listaIdArea.get(i)
                datosArea = Area(this.requireContext()).mostrarArea(id)
                mensaje =   "¿Que deseas hacer con ${datosArea.descripcion}, " +
                            "\nDivision :${datosArea.division}" +
                            "\nCant. Empleados: ${datosArea.cantidad_empleados}?"
                AlertDialog.Builder(this.requireContext())
                    .setTitle("Area")
                    .setMessage(mensaje)
                    .setNegativeButton("Eliminar"){d,i ->
                        AlertDialog.Builder(this.requireContext())
                            .setMessage("¿Estás seguro de eliminarlo?")
                            .setPositiveButton("Si, eliminar"){d,i->
                                if (binding.rbArea.isChecked){
                                    datosArea.eliminarArea(id)
                                    mostrarArea_Todo()
                                    binding.rbOp1.isChecked = true
                                }
                                if (binding.rbSubDep.isChecked){
                                    datosSubDepa.eliminarSubDepartamento(id)
                                    mostrarSubDepartamento_Todo()
                                    binding.rbOp1.isChecked = true
                                }

                            }
                            .setNegativeButton("No, cancelar"){r,i ->
                                Toast.makeText(this.requireContext(),"Operacion cancelada",Toast.LENGTH_LONG).show()
                            }
                            .show()
                    }
                    .setPositiveButton("Agrega SubDepartamento"){d,i->
                        var vtnAgregarSubDepa = Intent(this.requireContext(),SubDepartamento_Agregar::class.java)
                        vtnAgregarSubDepa.putExtra("idArea",datosArea.id_area)
                        vtnAgregarSubDepa.putExtra("descripcion",datosArea.descripcion)
                        startActivity(vtnAgregarSubDepa)
                    }
                    .setNeutralButton("Cancelar"){d,i->
                        Toast.makeText(this.requireContext(),"Operacion cancelada",Toast.LENGTH_LONG).show()
                    }

                    .show()
            }
            if (binding.rbSubDep.isChecked){
                id = listaIdSubDepa.get(i)
                datosSubDepa = SubDepartamento(this.requireContext()).mostrarSubDepa(id)
                mensaje =   "¿Que deseas hacer con el sub Depto: ${datosSubDepa.idEdificio}, " +
                        "\nPiso :${datosSubDepa.piso}?"
                AlertDialog.Builder(this.requireContext())
                    .setTitle("Area")
                    .setMessage(mensaje)
                    .setNegativeButton("Eliminar"){d,i ->
                        AlertDialog.Builder(this.requireContext())
                            .setMessage("¿Estás seguro de eliminarlo?")
                            .setPositiveButton("Si, eliminar"){d,i->
                                if (binding.rbArea.isChecked){
                                    datosArea.eliminarArea(id)
                                    mostrarArea_Todo()
                                    binding.rbOp1.isChecked = true
                                }
                                if (binding.rbSubDep.isChecked){
                                    datosSubDepa.eliminarSubDepartamento(id)
                                    mostrarSubDepartamento_Todo()
                                    binding.rbOp1.isChecked = true
                                }

                            }
                            .setNegativeButton("No, cancelar"){r,i ->
                                Toast.makeText(this.requireContext(),"Operacion cancelada",Toast.LENGTH_LONG).show()
                            }
                            .show()
                    }
                    .setPositiveButton("Modificar subDepartamento"){d,i->
                        var vtnModificarSubDepa = Intent(this.requireContext(),SubDepartamento_Modificar::class.java)
                        vtnModificarSubDepa.putExtra("idArea",datosSubDepa.idArea)
                        vtnModificarSubDepa.putExtra("descripcion",datosSubDepa.area_Descripcion)
                        vtnModificarSubDepa.putExtra("idSubDepto",datosSubDepa.idSubDepto)
                        vtnModificarSubDepa.putExtra("idEdificio",datosSubDepa.idEdificio)
                        vtnModificarSubDepa.putExtra("piso",datosSubDepa.piso)
                        startActivity(vtnModificarSubDepa)
                    }
                    .setNeutralButton("Cancelar"){d,i->
                        Toast.makeText(this.requireContext(),"Operacion cancelada",Toast.LENGTH_LONG).show()
                    }

                    .show()
            }

        }
        return root
    }

    private fun mostrarArea_Por(dato:String,tipo:String) {
        try {
            var listaArea = Area(this.requireContext()).mostrarAreaPor(dato, tipo)
            var descripcionAreas = ArrayList<String>()
            listaIdArea.clear()
            (0..listaArea.size - 1).forEach {
                var area = listaArea.get(it)
                var cad ="Descripcion: " + area.descripcion + "\nDivision: " + area.division + "\nCant. Empleados: " + area.cantidad_empleados + "\n"
                descripcionAreas.add(cad)
                listaIdArea.add(area.id_area)
            }
            binding.lvDatos.adapter = ArrayAdapter<String>(
                this.requireContext(),
                R.layout.simple_list_item_1, descripcionAreas
            )
        }catch (e: Error){
            Log.e("Error","Mensaje: ${e.message}")
        }
    }


    private fun mostrarArea_Todo() {
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
            binding.lvDatos.adapter = ArrayAdapter<String>(
                this.requireContext(),
                R.layout.simple_list_item_1,descripcionAreas
            )
        }catch (e: Error){
            Log.e("Error","Mensaje: ${e.message}")
        }
    }

    private fun mostrarSubDepartamento_Todo() {
        try {
            var listaSubDepa = SubDepartamento(this.requireContext()).mostrarTodo()
            var idsEdificios = ArrayList<String>()
            listaIdSubDepa.clear()
            (0..listaSubDepa.size-1).forEach {
                var subDepa = listaSubDepa.get(it)
                var cad = "Id Edificio: "+subDepa.idEdificio + "\nPiso: "+subDepa.piso+"\nDescipcion: "+subDepa.area_Descripcion+"\nDivision: "+subDepa.area_Division+"\n"
                idsEdificios.add(cad)
                listaIdSubDepa.add(subDepa.idSubDepto)
            }
            binding.lvDatos.adapter = ArrayAdapter<String>(
                this.requireContext(),
                R.layout.simple_list_item_1,idsEdificios
            )
        }catch (e: Error){
            Log.e("Error","Mensaje: ${e.message}")
        }
    }
    private fun mostrarSubDepartamento_Por(dato:String,tipo:String) {
        try {
            var listaSubDepa = SubDepartamento(this.requireContext()).mostrarSubDepaPor(dato,tipo)
            var idsEdificios = ArrayList<String>()
            listaIdSubDepa.clear()
            (0..listaSubDepa.size-1).forEach {
                var subDepa = listaSubDepa.get(it)
                var cad = "Id Edificio: "+subDepa.idEdificio + "\nPiso: "+subDepa.piso+"\nDescipcion: "+subDepa.area_Descripcion+"\nDivision: "+subDepa.area_Division+"\n"
                idsEdificios.add(cad)
                listaIdSubDepa.add(subDepa.idSubDepto)
            }
            binding.lvDatos.adapter = ArrayAdapter<String>(
                this.requireContext(),
                R.layout.simple_list_item_1,idsEdificios
            )
        }catch (e: Error){
            Log.e("Error","Mensaje: ${e.message}")
        }
    }

    private fun bloqueaBusqueda() {
        binding.txtBusqueda.setText("")
        binding.txtBusqueda.isEnabled = false
    }
    private fun activaBusqueda() {
        binding.txtBusqueda.setText("")
        binding.lvDatos.adapter = null
        binding.txtBusqueda.isEnabled = true
    }

    fun ponerOpcionesArea(){
        bloqueaBusqueda()
        binding.rbOp1.setText("Todos")
        binding.rbOp2.setText("Descripción")
        binding.rbOp3.setText("División")
        binding.rbOp4.isVisible = false
        binding.rbOp1.isChecked = true
    }
    fun ponerOpcionesSubDep(){
        bloqueaBusqueda()
        binding.rbOp1.setText("Todos")
        binding.rbOp2.setText("Id Edificio")
        binding.rbOp3.setText("Descripción")
        binding.rbOp4.setText("División")
        binding.rbOp4.isVisible = true
        binding.rbOp1.isChecked = true

    }

    override fun onStart() {
        super.onStart()
        binding.rbArea.isChecked = true
        ponerOpcionesArea()
        mostrarArea_Todo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}