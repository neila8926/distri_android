package co.recargas.sis.ui.paquetes.movistar

import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import co.recargas.sis.R
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import co.recargas.sis.common.SharedPreferenceManager
import co.recargas.sis.common.ValidacionDato
import co.recargas.sis.interfaces.DetallesPaquete
import co.recargas.sis.local.ProductRepository
import co.recargas.sis.local.RecargaRepository
import co.recargas.sis.local.modelo.Recargas
import co.recargas.sis.ui.paquetes.PaquetesActivity
import co.recargas.sis.ui.paquetes.tigo.ProductFragmentTigoCombo
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class RealizarPaquetesMovistar : AppCompatActivity(), DetallesPaquete {
    var parametros:String="";
    val version=Constantes.VERSION_CODE;
    var progressBar: ProgressBar?=null
   // private var recargaRepository: RecargaRepository = RecargaRepository(application)

    lateinit var nombrePaquete:TextView
    lateinit var valorPaquete:TextView
    lateinit var descripcionPaquete:TextView
    lateinit var txTipoPaquete:TextView
    var btnRealizarPaquete: Button?=null
    lateinit var numero:EditText
    var fechaActual:String?=null
    var horaActual:String?=null
    var idPaquete:Int?=null
    var btnRegresar:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_paquetes)
        nombrePaquete=findViewById(R.id.nombrePaquete)
        txTipoPaquete=findViewById(R.id.txTipoPaquete)
        valorPaquete=findViewById(R.id.valorPaquete)
        descripcionPaquete=findViewById(R.id.descripcion)
        btnRealizarPaquete=findViewById(R.id.btnRealizarPaquete)
        numero=findViewById(R.id.editNumero)
        progressBar=findViewById(R.id.progressBarPaq)
        btnRegresar=findViewById(R.id.btnRegresar)

        btnRegresar?.setOnClickListener {
            var intent= Intent(this,PaquetesActivity::class.java)
            intent.putExtra("paquete","movistar")
            startActivity(intent)
        }

        var rec=intent.extras
        var tipo:String=rec?.get("movistar").toString()
        Log.i("INFO","probando "+tipo)

        var fragmentManager=supportFragmentManager

        when(tipo){
            "internet"->{
                txTipoPaquete.text="Paquetes de Internet,\nSeleccione el Paquete"
                var internet=ProductFragmentMovistarInternet()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,internet).commit()

            }
            "voz"->{
                txTipoPaquete.text="Paquetes de Voz\nSeleccione el Paquete"
                var voz = ProductFragmentMovistarVoz()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,voz).commit()
            }
            "todo"->{
                txTipoPaquete.text="Paquetes Todo Incluido\nSeleccione el paquete"
                var todoIncluido=ProductFragmentMovistarTodoInc()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,todoIncluido).commit()

            }
            "ldi"->{
                txTipoPaquete.text="Paquetes Larga Distancia Int\nSeleccion el paquete"
                var ldi= ProductFragmentMovistarLdi()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,ldi).commit()

            }

        }
        btnRealizarPaquete?.setOnClickListener {

            if(numero.text.isEmpty() || ValidacionDato.validarCelular(numero.text.toString())==false){
                numero.setError("Digite un numero de celular Valido")

            }
            else {
                var celular=numero.text
                var nombrePaquete =nombrePaquete.text
                var valorPaquete=valorPaquete.text
                var idCliente=SharedPreferenceManager.getSomeStringValue("ID")
                //Se obtiene la fecha y la hora actual
                fechaActual= SimpleDateFormat("yyyy-MM-dd ").format(Date());
                horaActual= SimpleDateFormat("HH:mm:ss").format(Date());



                if(nombrePaquete.isNotEmpty()==true || valorPaquete.isNotEmpty()==true) {
                    //Se envian los datos al metodo que va a generar la Key de tipo Hexadecimal para ser enviada a Distrirecarga
                    val hmac = calculateRFC2104HMAC(fechaActual + horaActual, "android123*")
                    //Parametros que van a hacer enviados en la peticion Socket en el Inicio de Sesion
                    Log.i("INFO", "NOMBRE P "+nombrePaquete)
                    parametros = "mov|rec|"+horaActual+"|"+hmac +"|"+idCliente+"|"+celular+"|"+valorPaquete.toString().replace(",","").replace(".","")+"|"+idPaquete+"|"+version;
                    Log.i("parametros",parametros)

                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Confirmar Recarga")
                    alertDialog.setMessage("Numero: ${numero?.text.toString()}\nPaquete: ${nombrePaquete}\nValor: ${valorPaquete}")
                    alertDialog.apply {
                        setPositiveButton("Aceptar",
                            DialogInterface.OnClickListener { dialog, id ->
                                EnviarPaquete().execute()
                            })
                        setNegativeButton("Cancelar",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                            })
                    }

                    alertDialog.show()

        }else {
                Toast.makeText(this,"Todos los campos son requeridos",Toast.LENGTH_SHORT).show()
            }
            }
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

    override fun obtenerDatosPaquetes(nombre: String, valor: Int, descripcion:String,id:Int) {

        nombrePaquete.visibility=View.VISIBLE
        valorPaquete.visibility=View.VISIBLE
        descripcionPaquete.visibility=View.VISIBLE

        nombrePaquete?.text=nombre
        var numberFormat: NumberFormat = NumberFormat.getInstance()

        valorPaquete?.text=numberFormat.format(valor)
        descripcionPaquete?.text=descripcion
        idPaquete=id

    }
    inner class EnviarPaquete:AsyncTask<Void,Int,Boolean>(){
        private lateinit var response:String
        private  var respuesta:String="Error de Conexión"
        lateinit var saldo:String
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar?.max=100
            progressBar?.progress=0
            progressBar?.visibility=View.VISIBLE


        }
        override fun doInBackground(vararg params: Void?): Boolean? {


            if(params.isNotEmpty()){

            }
            try {
                response=ConexionSocket().ClSocket(parametros)
                Log.i("INFO", response)

                if(response.equals("Error de Conexión")==false){
                    try {
                        var reqJson: JSONObject = JSONObject(response);
                        respuesta = reqJson.getString("respuesta")


                        if(respuesta.equals("ok")){
                            //var recargas:Recargas= Recargas(2,numero.toString(),descripcionPaquete.toString(),valorPaquete.toString().toInt(),fechaActual!! )
                            // recargaRepository.insertRecargas(recargas)
                            saldo=reqJson.getString("saldo")
                            publishProgress()
                            return true
                        }
                    }catch (e: JSONException){
                        respuesta=response
                    }
                }

            }catch (ex:Exception){

                ex.printStackTrace()
            }
            return false
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            progressBar?.visibility=View.GONE

            if(result==true){
                Toast.makeText(this@RealizarPaquetesMovistar,"Recarga Exitosa ${saldo}", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this@RealizarPaquetesMovistar)
                builder.setTitle("Confirmación")
                builder.setMessage(respuesta+": Recarga Exitosa\n${saldo}")
                    .setPositiveButton("Aceptar",
                        DialogInterface.OnClickListener { dialog, id ->
                            nombrePaquete.setText("")
                            valorPaquete.setText("")
                            descripcionPaquete.setText("")
                            numero.setText("")

                            nombrePaquete.visibility=View.GONE
                            valorPaquete.visibility=View.GONE
                            descripcionPaquete.visibility=View.GONE
                        })
                builder.show()
            }else{
                Toast.makeText(this@RealizarPaquetesMovistar, "Recargar fallida ${respuesta}",Toast.LENGTH_SHORT).show()

                val builder = AlertDialog.Builder(this@RealizarPaquetesMovistar)
                builder.setTitle("Confirmación")
                builder.setMessage(respuesta)
                    .setPositiveButton("Aceptar",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                    builder.show()

            }
        }

    }
}
