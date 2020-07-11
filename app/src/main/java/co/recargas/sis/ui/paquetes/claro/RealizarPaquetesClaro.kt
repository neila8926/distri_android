package co.recargas.sis.ui.paquetes.claro

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import co.recargas.sis.R
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import co.recargas.sis.common.SharedPreferenceManager
import co.recargas.sis.interfaces.DetallesPaquete
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class RealizarPaquetesClaro : AppCompatActivity(), DetallesPaquete {
    var parametros:String="";
    val version=Constantes.VERSION_CODE;

    var nombrePaquete:TextView?=null
    var valorPaquete:TextView?=null
    var descripcionPaquete:TextView?=null
    var btnRealizarPaquete: Button?=null
    var numero:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_paquetes)
        nombrePaquete=findViewById(R.id.nombrePaquete)
        valorPaquete=findViewById(R.id.valorPaquete)
        descripcionPaquete=findViewById(R.id.descripcion)
        btnRealizarPaquete=findViewById(R.id.btnRealizarPaquete)
        numero=findViewById(R.id.editNumero)

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
            "todoInc"->{
                var todoIncluido=ProductoFragmentClaroTodoInc()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,todoIncluido).commit()

            }
            "ldi"->{
                var ldi= ProductFragmentClaroLdi()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,ldi).commit()

            }
        }
        btnRealizarPaquete?.setOnClickListener {
            var celular=numero?.text
            var nombrePaquete=nombrePaquete?.text
            var valorPaquete=valorPaquete?.text
            var idCliente=SharedPreferenceManager.getSomeStringValue("ID")
            //Se obtiene la fecha y la hora actual
            val fechaActual= SimpleDateFormat("yyyy-MM-dd ").format(Date());
            val horaActual= SimpleDateFormat("HH:mm:ss").format(Date());

            //Se envian los datos al metodo que va a generar la Key de tipo Hexadecimal para ser enviada a Distrirecarga
            val hmac = calculateRFC2104HMAC(fechaActual + horaActual, "android123*")
            //Parametros que van a hacer enviados en la peticion Socket en el Inicio de Sesion

            Toast.makeText(this,"me has pulsado "+idCliente, Toast.LENGTH_SHORT).show()
            parametros = "mov|rec|"+horaActual+"|"+hmac +"|"+idCliente+"|"+celular+"|"+valorPaquete+"|"+1+"|"+version;

            EnviarPaquete().execute()
        }

    }

    private fun toHexString(bytes: ByteArray): String? {
        val formatter = Formatter()
        for (b in bytes) {
            formatter.format("%02x", b)
        }
        return formatter.toString()
    }
    //Metodo que genera la Key de tipo Hexadecimal
    fun calculateRFC2104HMAC(data: String, key: String): String? {
        val signingKey = SecretKeySpec(key.toByteArray(), Constantes.HMAC_MD5_ALGORITHM)
        val mac: Mac = Mac.getInstance(Constantes.HMAC_MD5_ALGORITHM)
        mac.init(signingKey)
        return toHexString(mac.doFinal(data.toByteArray()))
    }

    override fun obtenerDatosPaquetes(nombre: String, valor: Int, descripcion:String) {

        nombrePaquete?.text=nombre
        valorPaquete?.text=valor.toString()
        descripcionPaquete?.text=descripcion

    }
    inner class EnviarPaquete:AsyncTask<Void,Void,Void>(){
        private lateinit var response:String
        override fun doInBackground(vararg params: Void?): Void? {
            if(params.isNotEmpty()){

            }
            try {
                response=ConexionSocket().ClSocket(parametros)
                Log.i("INFO", response)
            }catch (ex:Exception){
                ex.printStackTrace()
            }
            return null
        }

    }
}
