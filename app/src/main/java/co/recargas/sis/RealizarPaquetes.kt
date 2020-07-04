package co.recargas.sis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.recargas.sis.ui.products.ProductoFragmentClaroInt

class RealizarPaquetes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_paquetes)

        //var intent:Intent?=null
        var rec=intent.extras
       var tipo:String=rec?.get("tipo").toString()
        Log.i("INFO","probando "+tipo)

        var fragmentManager=supportFragmentManager

        when(tipo){
            "internet"->{
                var internet=ProductoFragmentClaroInt()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,internet).commit()

            }
            "voz"->{
                var voz = ProductoFragmentClaroVoz()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,voz).commit()

            }
        }
    }
}
