package co.recargas.sis.ui

import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.recargas.sis.R
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import co.recargas.sis.common.SharedPreferenceManager
import co.recargas.sis.local.RecargaRepository
import co.recargas.sis.local.modelo.Recargas
import co.recargas.sis.ui.ultimasrecargas.UltimasRecargasAdaptador
import co.recargas.sis.ui.ultimasrecargas.UltimasRecargasViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList

class UltimasRecargas : AppCompatActivity() {
    val version= Constantes.VERSION_CODE;
    lateinit var fechaActual:String
    lateinit var horaActual:String
    var idCliente:String?=null
    var hmac:String?=null
    var idRecargas:TextView?=null
    lateinit var parametros:String
    var toolbar3:Toolbar?=null
    private lateinit var progressBar:ProgressDialog

    var listaRecargas: RecyclerView?=null
    var layoutManager:RecyclerView.LayoutManager?=null
    lateinit var adaptador:UltimasRecargasAdaptador
    private lateinit var ultimasRecargasViewModel: UltimasRecargasViewModel
    private var ultimas:List<Recargas> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimas_recargas)
        var actionBar=supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        //Se obtiene la fecha y la hora actual
        toolbar3=findViewById(R.id.toolbar3)
        listaRecargas=findViewById(R.id.listaRecargas)
        toolbar3?.setTitle(R.string.tituloToolbar)
        //altura definida
        listaRecargas?.setHasFixedSize(true)
        layoutManager=LinearLayoutManager(this)
        listaRecargas?.layoutManager=layoutManager



        fechaActual= SimpleDateFormat("yyyy-MM-dd ").format(Date());
        horaActual= SimpleDateFormat("HH:mm:ss").format(Date());
        idCliente= SharedPreferenceManager.getSomeStringValue("ID")
        val hmac = calculateRFC2104HMAC(fechaActual + horaActual, "android123*")


        parametros=  "mov|las|" + horaActual.toString() + "|" + hmac + "|" +idCliente+ "|" + version
        ObtenerUltimasRecargas().execute()

        ultimasRecargasViewModel=ViewModelProvider(this).get(UltimasRecargasViewModel::class.java)




        var ultimas=ultimasRecargasViewModel.getUltimasRecargas()
        Log.i("recargas",ultimas.value?.get(1)?.numero.toString())
        var sds= ultimas.value?.get(1)?.numero
        idRecargas?.text=sds
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


    inner class ObtenerUltimasRecargas:AsyncTask<Void,Int,Boolean>(){


        private lateinit var response:String
        lateinit var recargas:JSONArray
        private  var respuesta:String="Error de Conexión"
        private var recargasRepository:RecargaRepository= RecargaRepository(application)
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar= ProgressDialog(this@UltimasRecargas)
            progressBar.setTitle("Cargando Recargas")
            progressBar.setMessage("Procesando...")
            progressBar.setCancelable(false)
            progressBar.show()
        }

        override fun doInBackground(vararg params: Void?): Boolean {
            if (params.isNotEmpty()){

            }
            try {
                response = ConexionSocket().ClSocket(parametros)
            }catch (e: Exception){

            }
            var reqJson: JSONObject = JSONObject(response);
            if(reqJson.getString("respuesta").equals("ok")){
                recargas= reqJson.getJSONArray("rec")
                var size=recargas.length()

               for(i in 0 until size){
                 val data:JSONObject=recargas.getJSONObject(i)
                   Log.i("recargas",data.toString())
                   var reca=Recargas(data.getString("cod"),data.getString("est").toInt(),data.getString("ope"),data.getString("pro"),data.getString("num"),data.getString("obs"),data.getString("val"),data.getString("fec"))
                   recargasRepository.insertRecargas(reca)
                   publishProgress(i*10)

                }
                return true
            }
            return true


        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            progressBar.progress=values[0]!!
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            progressBar.dismiss()
            if(result==true){
                ultimasRecargasViewModel.getUltimasRecargas().observe(this@UltimasRecargas, androidx.lifecycle.Observer {
                    ultimas=it
                    Log.i("estaas",ultimas.get(0).producto)
                    adaptador=UltimasRecargasAdaptador(this@UltimasRecargas,ultimas)
                    listaRecargas?.adapter=adaptador

                })

            }
        }
    }



}