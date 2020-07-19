package co.recargas.sis

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class MainActivity : AppCompatActivity() {

    var parametros:String="";
    val version=Constantes.VERSION_CODE;
    private lateinit var progressBarInicio:ProgressDialog
    private lateinit var edtUsuario:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnIngresar:Button

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        edtUsuario=findViewById(R.id.idUsername);
        edtPassword=findViewById(R.id.idPassword);
        btnIngresar=findViewById(R.id.idIngresar);
        //progressBarInicio=findViewById(R.id.progressBarInicio)


        Toast.makeText(this,"Probando la version $version ",Toast.LENGTH_LONG).show()

        btnIngresar.setOnClickListener{view->
            if(edtUsuario.text.isEmpty()){
                edtUsuario.setError("Digite Usuario")
            }
            if(edtPassword.text.isEmpty()){
                edtPassword.setError("Digite Contraseña")

        }else {
            if(edtUsuario.text.isNotEmpty() && edtPassword.text.isNotEmpty()) {

                var user=edtUsuario.text.toString().trim().replace("\\s","")

           //Se obtiene la fecha y la hora actual
            val fechaActual=SimpleDateFormat("yyyy-MM-dd ").format(Date());
            val horaActual=SimpleDateFormat("HH:mm:ss").format(Date());

            Toast.makeText(this,"Probando : $fechaActual $horaActual", Toast.LENGTH_LONG ).show()
            //Se envian los datos al metodo que va a generar la Key de tipo Hexadecimal para ser enviada a Distrirecarga
            val hmac = calculateRFC2104HMAC(fechaActual + horaActual, "android123*")
            //Parametros que van a hacer enviados en la peticion Socket en el Inicio de Sesion
            parametros = "mov|log|" + horaActual.toString() + "|" + hmac + "|" +user+ "|" + edtPassword.getText().toString().toString() + "|" + version

            Ingresar().execute()
            }
            else {
                Toast.makeText(this,"Digite usuario y contraseña",Toast.LENGTH_SHORT).show()
            }

        }}

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

    inner class Ingresar: AsyncTask<Void,Int,Boolean>(){
        private lateinit var productViewModel: ProductViewModel
        private var productRepository:ProductRepository=ProductRepository(application)
        private lateinit var response:String
        private lateinit var respuesta:String

        override fun onPreExecute() {
            super.onPreExecute()

            progressBarInicio= ProgressDialog(this@MainActivity)
            progressBarInicio.setTitle("Iniciando Sesión")
            progressBarInicio.setMessage("Procesando...")
            progressBarInicio.setCancelable(false)
            progressBarInicio.show()


        }


        override fun doInBackground(vararg params: Void?): Boolean? {
            if(params.isNotEmpty()){

            }
            try {
                response = ConexionSocket().ClSocket(parametros)
                Log.i("req", "Respuesta " + response)
                if(response.isEmpty()==false){
                    response = response.replace("\\n", "");

                    var reqJson:JSONObject= JSONObject(response);
                    respuesta=reqJson.getString("respuesta")
                    if(respuesta.equals("Login exitoso")) {

                        val idCliente:String=reqJson.getString("id")
                        //Almacenamos en las preferencias el Id del Cliente
                        SharedPreferenceManager.setSomeStringValue("ID",idCliente)
                        Log.i("req", "probando el nuevo JSON $idCliente")
                        val producto: JSONArray = reqJson.getJSONArray("pr")
                        Log.i("INFO", " Pruebaaaaa "+producto.length())
                        var size=producto.length()
                        var x=size*100


                        for(i in 0 .. size){

                            val data: JSONObject = producto.getJSONObject(i)

                            var producto= Producto(data.getString("id").toInt(), data.getString("pr_n"),data.getString("val").toInt(),data.getString("obs"),data.getString("op_i").toInt(),data.getString("op_n"))
                           Log.i("INFO", " "+producto.toString());
                            productRepository.insertProductos(producto)
                            publishProgress(x*size)
                            //productReposit.insertProductos(producto)
                            if(isCancelled){
                                break
                            }

                        }

                    }else{
                        return false
                    }
                    }

            }catch ( ex: Exception) {
                ex.printStackTrace()
            }
            return true
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            progressBarInicio.progress= values[0]!!

        }


        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)

            progressBarInicio.dismiss()

            if(result==true) {
            val intent : Intent=Intent(this@MainActivity,HomeActivity::class.java)
            startActivity(intent)
            }else{
                Toast.makeText(this@MainActivity,respuesta,Toast.LENGTH_LONG).show()
            }

        }

        override fun onCancelled() {
            super.onCancelled()
        }
    }
}



