package co.recargas.sis.ui.paquetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.recargas.sis.ui.paquetes.virgin.FragmentTiposPaquetesVirgin
import co.recargas.sis.R
import co.recargas.sis.ui.paquetes.avantel.FragmentTipoPaqueteAvantel
import co.recargas.sis.ui.paquetes.claro.TiposPaquetesClaro
import co.recargas.sis.ui.paquetes.etb.FragmentTiposPaqueteEtb
import co.recargas.sis.ui.paquetes.kalley.FragmentTipoPaqueteKalley
import co.recargas.sis.ui.paquetes.movistar.FragmentTipoPaqueteMovistar
import co.recargas.sis.ui.paquetes.tigo.FragmentTipoPaqueteTigo
import co.recargas.sis.ui.paquetes.wings.FragmentTipoPaqueteWings

class PaquetesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paquetes)
        supportActionBar?.title="Claro Paquetes"



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
                    FragmentTipoPaqueteTigo()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.add(R.id.contenedor_paquetes,paqueteTigo).commit()

            }
            "virgin"->{
                var paqueteVirgin=
                    FragmentTiposPaquetesVirgin()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.add(R.id.contenedor_paquetes,paqueteVirgin).commit()
            }
            "etb"->{
                var paqueteEtb=FragmentTiposPaqueteEtb()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.add(R.id.contenedor_paquetes, paqueteEtb).commit()
            }
            "movistar"->{
                var paqueteMovistar=FragmentTipoPaqueteMovistar()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.add(R.id.contenedor_paquetes,paqueteMovistar).commit()
            }
            "avantel"->{
                var paqueteAvantel=FragmentTipoPaqueteAvantel()
                var fragmentTransation=managerFragmant.beginTransaction()
                fragmentTransation.add(R.id.contenedor_paquetes,paqueteAvantel).commit()
            }
            

        }

    }
}
