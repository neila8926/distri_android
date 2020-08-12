package co.recargas.sis.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.recargas.sis.R
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import co.recargas.sis.common.SharedPreferenceManager
import co.recargas.sis.interfaces.ClickListenerUltiRec
import co.recargas.sis.local.RecargaRepository
import co.recargas.sis.local.modelo.Recargas
import co.recargas.sis.ui.ultimasrecargas.UltimasRecargasAdaptador
import co.recargas.sis.ui.ultimasrecargas.UltimasRecargasViewModel
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.ui.ScanningActivity
import com.mazenrashed.printooth.utilities.Printing
import com.mazenrashed.printooth.utilities.PrintingCallback
import kotlinx.android.synthetic.main.item_recarga.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList

class UltimasRecargas : AppCompatActivity(), PrintingCallback {
    val version= Constantes.VERSION_CODE;
    lateinit var fechaActual:String
    lateinit var horaActual:String
    var idCliente:String?=null
    var hmac:String?=null
    var idRecargas:TextView?=null
    lateinit var parametros:String
    var toolbar3:Toolbar?=null
    internal var printing:Printing?=null
    var resp:String?=null
    private lateinit var progressBar:ProgressDialog
    var btnSincronizarImp:Button?=null

    var listaRecargas: RecyclerView?=null
    var layoutManager:RecyclerView.LayoutManager?=null
    lateinit var adaptador:UltimasRecargasAdaptador
    private lateinit var ultimasRecargasViewModel: UltimasRecargasViewModel
    private var ultimas:List<Recargas> = ArrayList()
    var emparejado:Boolean?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimas_recargas)
        toolbar3=findViewById(R.id.toolbar3)
        btnSincronizarImp=findViewById(R.id.btnSincronizar)
        setSupportActionBar(toolbar3)

        var actionBar=supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        //Se obtiene la fecha y la hora actual

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

        obtenerRecargas()

    }

    private fun obtenerRecargas() {
        if(printing!=null){
            printing!!.printingCallback=this
        }
        btnSincronizarImp?.setOnClickListener {
            if(Printooth.hasPairedPrinter()){
                Printooth.removeCurrentPrinter()
               Toast.makeText(this,"Desconectando Impresora",Toast.LENGTH_SHORT).show()
            }
            else {
                startActivityForResult(
                    Intent(
                        this@UltimasRecargas,
                        ScanningActivity::class.java
                    ), ScanningActivity.SCANNING_FOR_PRINTER
                )
                cambiar()
            }
        }

        ultimasRecargasViewModel.getUltimasRecargas().observe(this, androidx.lifecycle.Observer {
            ultimas=it
            adaptador=UltimasRecargasAdaptador(ultimas,object : ClickListenerUltiRec{
                override fun onClick(vista: View, index: Int) {
                    resp="Numero: ${vista.idNumeroR.text}\nValor: ${vista.idValorR.text}\nOperador: ${vista.idOperadorR.text}\nProducto: ${vista.idProductoR.text}\nRespuesta: ${vista.idObservacionR.text}\nFecha: ${vista.idFechaRecarga.text}\n"
                    var alerDialogo=AlertDialog.Builder(this@UltimasRecargas)
                    alerDialogo.setTitle("Imprimir")
                    alerDialogo.setMessage(resp)
                        .setPositiveButton("ok",DialogInterface.OnClickListener { dialog, which ->

                            if(!Printooth.hasPairedPrinter()) {
                                emparejado=true
                                        startActivityForResult(
                                            Intent(
                                                this@UltimasRecargas,
                                                ScanningActivity::class.java
                                            ), ScanningActivity.SCANNING_FOR_PRINTER
                                        )
                                    }
                            else
                                printFactura()

                     })
                        .show()
                }
            })
            listaRecargas?.adapter=adaptador

        })
    }

    private fun printFactura() {

        var printable=ArrayList<Printable>()
        printable.add(RawPrintable.Builder(byteArrayOf(27,100,4)).build())
        //custom text

        //agregar texto
        printable.add(TextPrintable.Builder()
            .setText("DISTRIRECARGA\n")
            .setLineSpacing(DefaultPrinter.LINE_SPACING_30)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
            .setNewLinesAfter(1)
            .build())
        printable.add(TextPrintable.Builder()
            .setText(resp!!)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .build())
        printable.add(TextPrintable.Builder()
            .setText("TICKET DE VENTA\n")
            .setLineSpacing(DefaultPrinter.LINE_SPACING_30)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
            .setFontSize(DefaultPrinter.FONT_SIZE_NORMAL)
            .setNewLinesAfter(0)
            .build())
        try {
            printing!!.print(printable)
        }catch (e:NullPointerException)
        {
            Toast.makeText(this,"seleccione una impresora",Toast.LENGTH_SHORT).show()

         }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==ScanningActivity.SCANNING_FOR_PRINTER && resultCode==Activity.RESULT_OK){
            initPronting()
            }
        cambiar()
    }

    private fun initPronting() {
        if(Printooth.hasPairedPrinter())
            printing=Printooth.printer()
        if(printing!=null)
            printing!!.printingCallback=this
    }

    private fun cambiar() {
        //verifica si la impresora guardada
        if(Printooth.hasPairedPrinter())
            Toast.makeText(this,"Sincronizando Impresora ${Printooth.getPairedPrinter()?.name}",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this,"Emparejando Impresora",Toast.LENGTH_SHORT).show()

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

            var reqJson: JSONObject = JSONObject(response);

                Log.i("recargaaaaas",reqJson.toString())
            if(reqJson.getString("respuesta").equals("ok")){
                recargas= reqJson.getJSONArray("rec")


                var size=recargas.length()

               for(i in 0 until size){
                 val data:JSONObject=recargas.getJSONObject(i)
                   Log.i("recargas",data.toString())

                   var reca=Recargas(data.getString("cod"),data.getString("est").toInt(),data.getString("ope"),data.getString("pro"),data.getString("num"),data.getString("obs"),data.getString("val"),data.getString("fec"))
                   recargasRepository.deleteRecargas()
                   recargasRepository.insertRecargas(reca)
                   publishProgress(i*10)

                }
                return true
            }
                if(reqJson.getString("respuesta").equals("sin recargas")){
                recargasRepository.deleteRecargas()
                return true
            }
            return true

            }catch (e: Exception){
                return false

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


            }else{
                Toast.makeText(this@UltimasRecargas,"Error de conexión",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun connectingWithPrinter() {
        Toast.makeText(this,"Conectando Impresora",Toast.LENGTH_SHORT).show()
    }

    override fun connectionFailed(error: String) {
        Toast.makeText(this,"Fallida $error",Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: String) {
        Toast.makeText(this,"Error $error",Toast.LENGTH_SHORT).show()
    }

    override fun onMessage(message: String) {
        Toast.makeText(this,"Mensaje $message",Toast.LENGTH_SHORT).show()
    }

    override fun printingOrderSentSuccessfully() {
        Toast.makeText(this,"Enviando ",Toast.LENGTH_SHORT).show()
    }


}