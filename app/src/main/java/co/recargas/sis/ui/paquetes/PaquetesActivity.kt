package co.recargas.sis.ui.paquetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.recargas.sis.R
import co.recargas.sis.ui.paquetes.claro.TiposPaquetesClaro
import co.recargas.sis.ui.paquetes.tigo.TiposPaquetesTigo

class PaquetesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paquetes)

        var inte=intent.extras
       var tipo:String= inte?.get("paquete").toString()
        val managerFragmant=supportFragmentManager

        when(tipo){
            "claro"->{
                var paqueteClaro=
                    TiposPaquetesClaro()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.add(R.id.contenedor_paquetes,paqueteClaro).commit()

            }
            "tigo"->{
                var paqueteTigo=
                    TiposPaquetesTigo()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.add(R.id.contenedor_paquetes,paqueteTigo).commit()

            }
        }

    }
}
