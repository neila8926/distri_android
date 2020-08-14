package co.recargas.sis.ui.paquetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import co.recargas.sis.ui.paquetes.virgin.FragmentTiposPaquetesVirgin
import co.recargas.sis.R
import co.recargas.sis.ui.paquetes.avantel.FragmentTipoPaqueteAvantel
import co.recargas.sis.ui.paquetes.claro.TiposPaquetesClaro
import co.recargas.sis.ui.paquetes.etb.FragmentTiposPaqueteEtb
import co.recargas.sis.ui.paquetes.movistar.FragmentTipoPaqueteMovistar
import co.recargas.sis.ui.paquetes.tigo.FragmentTipoPaqueteTigo

class PaquetesActivity : AppCompatActivity() {
    var toolbar:Toolbar?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paquetes)
        toolbar=findViewById(R.id.toolbar2)
        toolbar?.setTitle(R.string.tituloToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)




        var inte=intent.extras
       var tipo:String= inte?.get("paquete").toString()
        val managerFragmant=supportFragmentManager

        when(tipo){
            "claro"->{

                var paqueteClaro=
                    TiposPaquetesClaro()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.replace(R.id.contenedor_paquetes,paqueteClaro) .commit()

            }
            "tigo"->{
                var paqueteTigo=
                    FragmentTipoPaqueteTigo()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.replace(R.id.contenedor_paquetes,paqueteTigo).commit()

            }
            "virgin"->{
                var paqueteVirgin=
                    FragmentTiposPaquetesVirgin()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.replace(R.id.contenedor_paquetes,paqueteVirgin).commit()
            }
            "etb"->{
                var paqueteEtb=FragmentTiposPaqueteEtb()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.replace(R.id.contenedor_paquetes, paqueteEtb).commit()
            }
            "movistar"->{
                var paqueteMovistar=FragmentTipoPaqueteMovistar()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.replace(R.id.contenedor_paquetes,paqueteMovistar).commit()
            }
            "avantel"->{
                var paqueteAvantel=FragmentTipoPaqueteAvantel()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.replace(R.id.contenedor_paquetes,paqueteAvantel)
                    .commit()
            }
            

        }

    }
}
