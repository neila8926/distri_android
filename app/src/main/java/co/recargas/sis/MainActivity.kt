package co.recargas.sis

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class MainActivity : AppCompatActivity() {

    var parametros:String="";
    val version=Constantes.VERSION_CODE;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edtUsuario:EditText=findViewById(R.id.idUsername);
        val edtPassword:EditText=findViewById(R.id.idPassword);
        val btnIngresar:Button=findViewById(R.id.idIngresar);
        Toast.makeText(this,"Probando la version $version ",Toast.LENGTH_LONG).show()

        btnIngresar.setOnClickListener{view->
            //Se obtiene la fecha y la hora actual
            val fechaActual=SimpleDateFormat("yyyy-MM-dd ").format(Date());
            val horaActual=SimpleDateFormat("HH:mm:ss").format(Date());

            Toast.makeText(this,"Probando : $fechaActual $horaActual", Toast.LENGTH_LONG ).show()
            //Se envian los datos al metodo que va a generar la Key de tipo Hexadecimal para ser enviada a Distrirecarga
            val hmac = calculateRFC2104HMAC(fechaActual + horaActual, "android123*")
            //Parametros que van a hacer enviados en la peticion Socket en el Inicio de Sesion
            parametros = "mov|log|" + horaActual.toString() + "|" + hmac + "|" + edtUsuario.getText().toString().toString() + "|" + edtPassword.getText().toString().toString() + "|" + version

            Ingresar().execute()

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

    inner class Ingresar: AsyncTask<Void,Void,Void>(){

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg params: Void?): Void? {
            if(params.isNotEmpty()){

            }
            try {
                var req:String = ConexionSocket().ClSocket(parametros)
                Log.i("req", "Respuesta " + req)
                if(req.isEmpty()==false){
                    req = req.replace("\\n", "");

                    var reqJson:JSONObject= JSONObject(req);

                    val idCliente:String=reqJson.getString("id")
                    Log.i("req", "probando el nuevo JSON $idCliente")

                }

            }catch ( ex: Exception) {
                ex.printStackTrace()
            }


            return null




        }

        override fun onPreExecute() {
            super.onPreExecute()
        }
    }
}



