package co.recargas.sis

import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import co.recargas.sis.common.SharedPreferenceManager
import co.recargas.sis.interfaces.FragmentCom
import co.recargas.sis.ui.paquetes.PaquetesActivity
import co.recargas.sis.ui.paquetes.exito.RealizarPaquetesExito
import co.recargas.sis.ui.paquetes.kalley.RealizarPaquetesKalley
import co.recargas.sis.ui.paquetes.wings.RealizarPaquetesWings
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class HomeActivity : AppCompatActivity(), FragmentCom {
    var toolbar:Toolbar?=null
    var parametros:String="";
    val version= Constantes.VERSION_CODE;
    lateinit var fechaActual:String
    lateinit var horaActual:String
    var idCliente:String?=null
    var hmac:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //supportActionBar?.hide()
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        toolbar=findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.tituloToolbar)
        setSupportActionBar(toolbar)




        //Se obtiene la fecha y la hora actual
        fechaActual= SimpleDateFormat("yyyy-MM-dd ").format(Date());
        horaActual= SimpleDateFormat("HH:mm:ss").format(Date());
        idCliente= SharedPreferenceManager.getSomeStringValue("ID")

        Toast.makeText(this,"Probando : $fechaActual $horaActual", Toast.LENGTH_LONG ).show()
        //Se envian los datos al metodo que va a generar la Key de tipo Hexadecimal para ser enviada a Distrirecarga
        hmac = calculateRFC2104HMAC(fechaActual + horaActual, "android123*")
        //Parametros que van a hacer enviados en la peticion Socket en el Inicio de Sesion






        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


    //    val appBarConfiguration = AppBarConfiguration.Builder(
      //      R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        //).build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
       NavigationUI.setupWithNavController(navView, navController)


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.cRecargas->{
                Toast.makeText(this,"Consultar Ultimas Recargas",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.cEfecty->{
                Toast.makeText(this,"Cosultar Codigo Efecty",Toast.LENGTH_SHORT).show()
                var alertDialog=AlertDialog.Builder(this)
                alertDialog.setTitle("Datos Para Consignar en Efecty")
                alertDialog.setMessage("Convenio: 110911\nReferencia: 110${idCliente}")
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->  })
                alertDialog.show()
                return true
            }
            R.id.cSaldo->{
                parametros = "mov|sal|" + horaActual.toString() + "|" + hmac + "|" +idCliente+ "|" + version
                Acciones().execute()
                return true
            }
            R.id.sesion->{
                Toast.makeText(this,"Cerrar Sesión",Toast.LENGTH_SHORT).show()
                return true
            }
            else->( return super.onOptionsItemSelected(item))

        }

    }


    override fun paquetesClaro() {

       Toast.makeText(this,"Probando ando ",Toast.LENGTH_LONG).show()
       var intent: Intent= Intent(this,
           PaquetesActivity::class.java)
       intent.putExtra("paquete","claro")
       startActivity(intent)

   }

    override fun paquetesTigo() {
        Toast.makeText(this,"Probando ando ",Toast.LENGTH_LONG).show()
        var intent: Intent= Intent(this,
            PaquetesActivity::class.java)
        intent.putExtra("paquete","tigo")
        startActivity(intent)

    }

    override fun paqueteVirgin() {
        Toast.makeText(this, "boton virgin", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, PaquetesActivity::class.java)
        intent.putExtra("paquete","virgin")
        startActivity(intent)

    }
    override fun paqueteEtb() {
        Toast.makeText(this, "boton etb", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, PaquetesActivity::class.java)
        intent.putExtra("paquete","etb")
        startActivity(intent)

    }

    override fun paqueteAvantel() {
        Toast.makeText(this, "boton avantel", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, PaquetesActivity::class.java)
        intent.putExtra("paquete","avantel")
        startActivity(intent)

    }

    override fun paqueteMovistar() {
        Toast.makeText(this, "boton movistar", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, PaquetesActivity::class.java)
        intent.putExtra("paquete","movistar")
        startActivity(intent)

    }

    override fun paqueteExito() {
        Toast.makeText(this, "boton exito", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, RealizarPaquetesExito::class.java)
        intent.putExtra("paquete","exito")
        startActivity(intent)

    }

    override fun paqueteKalley() {
        Toast.makeText(this, "boton kalley", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, RealizarPaquetesKalley::class.java)
        intent.putExtra("paquete","kalley")
        startActivity(intent)

    }

    override fun paqueteWings() {
        Toast.makeText(this, "boton wigs", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, RealizarPaquetesWings::class.java)
        intent.putExtra("paquete","wings")
        startActivity(intent)

    }

    inner class Acciones:AsyncTask<Void,Void,Boolean>(){

        private lateinit var response:String
        private lateinit var respuesta:String
        lateinit var saldo:String
        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            var alertDialog=AlertDialog.Builder(this@HomeActivity)
            alertDialog.setTitle("Saldo Actual")
            alertDialog.setMessage("Su saldo es: $"+saldo)
                .setPositiveButton("OK",DialogInterface.OnClickListener { dialog, id ->

                })
                alertDialog.show()
        }

        override fun doInBackground(vararg params: Void?): Boolean {
            if(params.isNotEmpty()){

            }
            try {
                response= ConexionSocket().ClSocket(parametros)
                Log.i("INFO", response)
            }catch (ex: Exception){
                ex.printStackTrace()
            }
            if(response.isNotEmpty()){
                var reqJson: JSONObject = JSONObject(response);
                respuesta=reqJson.getString("respuesta")

                if(respuesta.equals("ok")){

                    saldo= reqJson.getString("saldo")
                    publishProgress()
                    return true


                }
            }
            return false
        }

        override fun onCancelled(result: Boolean?) {
            super.onCancelled(result)
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }
    }






}