package co.recargas.sis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.recargas.sis.ui.TiposPaquetesClaro

class PaquetesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paquetes)

        var inte=intent.extras
       var tipo:String= inte?.get("paquete").toString()
        val managerFragmant=supportFragmentManager

        when(tipo){
            "claro"->{
                var paqueteClaro=TiposPaquetesClaro()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.add(R.id.contenedor_paquetes,paqueteClaro).commit()

            }
            "tigo"->{
                var paqueteTigo=TiposPaquetesTigo()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.add(R.id.contenedor_paquetes,paqueteTigo).commit()

            }
        }

    }
}
