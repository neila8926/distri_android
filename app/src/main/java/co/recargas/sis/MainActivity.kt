package co.recargas.sis

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import co.recargas.sis.common.SharedPreferenceManager
import co.recargas.sis.local.ProductRepository
import co.recargas.sis.local.modelo.Producto
import co.recargas.sis.ui.paquetes.products.ProductViewModel
import org.json.JSONArray
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
        supportActionBar?.hide()
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
        private lateinit var productViewModel: ProductViewModel
        private var productRepository:ProductRepository=ProductRepository(application)
        private lateinit var response:String


        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val intent : Intent=Intent(this@MainActivity,HomeActivity::class.java)
            startActivity(intent)

        }

        override fun doInBackground(vararg params: Void?): Void? {
            if(params.isNotEmpty()){

            }
            try {
                response = ConexionSocket().ClSocket(parametros)
                Log.i("req", "Respuesta " + response)
                if(response.isEmpty()==false){
                    response = response.replace("\\n", "");

                    var reqJson:JSONObject= JSONObject(response);

                    val idCliente:String=reqJson.getString("id")
                    //Almacenamos en las preferencias el Id del Cliente
                    SharedPreferenceManager.setSomeStringValue("ID",idCliente)
                    Log.i("req", "probando el nuevo JSON $idCliente")
                    val producto: JSONArray = reqJson.getJSONArray("pr")
                    Log.i("INFO", " Pruebaaaaa "+producto.length())
                    var size=producto.length()


                    for(i in 0 .. size){

                        val data: JSONObject = producto.getJSONObject(i)
                        Log.i("INFO", " voy prebaaaaa "+data)
                        Log.i("INFO", " este es eñ id: "+data.getString("id").toInt())
                        var producto= Producto(data.getString("id").toInt(), data.getString("pr_n"),data.getString("val").toInt(),data.getString("obs"),data.getString("op_i").toInt(),data.getString("op_n"))
                       Log.i("INFO", " "+producto.toString());
                        productRepository.insertProductos(producto)

                        //productReposit.insertProductos(producto)



                       // Log.i("INFO", " Pruebaaaaa "+pro.nombre)


                    }




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



