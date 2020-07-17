package co.recargas.sis.ui.paquetes.tigo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import co.recargas.sis.R
import co.recargas.sis.interfaces.DetallesPaquete

class RealizarPaquetesTigo:AppCompatActivity(),DetallesPaquete {
    private lateinit var nombrePaquete:TextView
    private lateinit var valorPaquete:TextView
    private lateinit var editNumero:TextView
    private lateinit var descripcion: TextView
    private lateinit var btnRealizarPaquete:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_paquetes)
        nombrePaquete=findViewById(R.id.nombrePaquete)
        valorPaquete=findViewById(R.id.valorPaquete)
        editNumero=findViewById(R.id.editNumero)
        descripcion=findViewById(R.id.descripcion)
        btnRealizarPaquete=findViewById(R.id.btnRealizarPaquete)

        var intentTigo=intent.extras
        var tipo:String=intentTigo?.get("tigo").toString()
        var fragmentManager=supportFragmentManager

        when(tipo){
            "combo"->{
                var combo=ProductFragmentTigoCombo()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,combo).commit()
            }
        }

        btnRealizarPaquete.setOnClickListener {
            Toast.makeText(this,"Probando Paquete de Tigo", Toast.LENGTH_SHORT).show()
        }

    }

    override fun obtenerDatosPaquetes(nombre: String, valor: Int, descripcion: String) {

        nombrePaquete.text=nombre
        valorPaquete.text=valor.toString()
        this.descripcion.text=descripcion
    }
}