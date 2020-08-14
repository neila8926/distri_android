package co.recargas.sis.ui.pines

import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.recargas.sis.HomeActivity
import co.recargas.sis.R
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import co.recargas.sis.common.SharedPreferenceManager
import co.recargas.sis.common.ValidacionDato
import co.recargas.sis.interfaces.DetallesPaquete
import co.recargas.sis.ui.pines.imvu.ProductFragmentImvu
import co.recargas.sis.ui.pines.minecraft.ProductFragmentMinecraft
import co.recargas.sis.ui.pines.netflix.ProductFragmentNetflix
import co.recargas.sis.ui.pines.spotify.ProductFragmentSpotify
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class RealizarPines:AppCompatActivity(),DetallesPaquete{
    var parametros:String="";
    val version= Constantes.VERSION_CODE;
    var progressBar: ProgressBar?=null
    // private var recargaRepository: RecargaRepository = RecargaRepository(application)

    lateinit var nombrePaquete: TextView
    lateinit var valorPaquete: TextView
    lateinit var txTipoPaquete:TextView
    lateinit var descripcionPaquete: TextView
    var btnRealizarPin: Button?=null
    lateinit var numero: EditText
    var fechaActual:String?=null
    var horaActual:String?=null
    var idPaquete:Int?=null
    var btnRegresar:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_paquetes)


        nombrePaquete=findViewById(R.id.nombrePaquete)
        valorPaquete=findViewById(R.id.valorPaquete)
        txTipoPaquete=findViewById(R.id.txTipoPaquete)
        descripcionPaquete=findViewById(R.id.descripcion)
        btnRealizarPin=findViewById(R.id.btnRealizarPaquete)
        numero=findViewById(R.id.editNumero)
        progressBar=findViewById(R.id.progressBarPaq)
        btnRegresar=findViewById(R.id.btnRegresar)

        txTipoPaquete.text="Seleccione el Pin"

        btnRegresar?.setOnClickListener {
            var intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

        var rec=intent.extras
        var tipo:String=rec?.get("pin").toString()
        Log.i("INFO","probando "+tipo)

        var fragmentManager=supportFragmentManager

        when(tipo){
            "netflix"->{
                var netflix=
                    ProductFragmentNetflix()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,netflix).commit()
            }
            "imvu"->{
                var imvu=ProductFragmentImvu()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,imvu).commit()
            }
            "spotify"->{
                var spotify=ProductFragmentSpotify()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,spotify).commit()
            }
            "minecraft"->{
                var minecraft=ProductFragmentMinecraft()
                var fragmentTransation=fragmentManager.beginTransaction()
                fragmentTransation.add(R.id.contenedorTipoPaquete,minecraft).commit()
            }
        }

        btnRealizarPin?.setOnClickListener {

            if(numero.text.isEmpty() || ValidacionDato.validarCelular(numero.text.toString())==false){
                numero.setError("Digite un numero de celular Valido")

            }
            else {
                var celular=numero.text
                var nombrePaquete =nombrePaquete.text
                var valorPaquete=valorPaquete.text
                var idCliente= SharedPreferenceManager.getSomeStringValue("ID")
                //Se obtiene la fecha y la hora actual
                fechaActual= SimpleDateFormat("yyyy-MM-dd ").format(Date());
                horaActual= SimpleDateFormat("HH:mm:ss").format(Date());



                if(nombrePaquete.isNotEmpty()==true || valorPaquete.isNotEmpty()==true) {
                    //Se envian los datos al metodo que va a generar la Key de tipo Hexadecimal para ser enviada a Distrirecarga
                    val hmac = calculateRFC2104HMAC(fechaActual + horaActual, "android123*")
                    //Parametros que van a hacer enviados en la peticion Socket en el Inicio de Sesion
                    Log.i("INFO", "NOMBRE P "+nombrePaquete)
                    parametros = "mov|rec|"+horaActual+"|"+hmac +"|"+idCliente+"|"+celular+"|"+valorPaquete+"|"+idPaquete+"|"+version;

                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Confirmar Recarga")
                    alertDialog.setMessage("Numero: ${numero?.text.toString()}\nPaquete: ${nombrePaquete}\nValor: ${valorPaquete.toString()}")
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
                    Toast.makeText(this,"Todos los campos son requeridos", Toast.LENGTH_SHORT).show()
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

    override fun obtenerDatosPaquetes(nombre: String, valor: Int, descripcion: String, id: Int) {
        nombrePaquete.visibility= View.VISIBLE
        valorPaquete.visibility= View.VISIBLE
        descripcionPaquete.visibility= View.VISIBLE
        nombrePaquete?.text=nombre
        valorPaquete?.text=valor.toString()
        descripcionPaquete?.text=descripcion
        idPaquete=id
    }

    inner class EnviarPaquete: AsyncTask<Void, Int, Boolean>(){
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
                response= ConexionSocket().ClSocket(parametros)
                Log.i("INFO", response)
            }catch (ex: Exception){
                ex.printStackTrace()
            }
            if(response.equals("Error de Conexión")==false){
                var reqJson: JSONObject = JSONObject(response);
                respuesta=reqJson.getString("respuesta")

                if(respuesta.equals("ok")){
                    //var recargas:Recargas= Recargas(2,numero.toString(),descripcionPaquete.toString(),valorPaquete.toString().toInt(),fechaActual!! )
                    // recargaRepository.insertRecargas(recargas)
                    saldo=reqJson.getString("saldo")
                    publishProgress()
                    return true


                }
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
                Toast.makeText(this@RealizarPines,"Recarga Exitosa ${saldo}", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this@RealizarPines)
                builder.setTitle("Confirmación")
                builder.setMessage(respuesta)
                    .setPositiveButton("Aceptar",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                    .setNegativeButton("Imprimir",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })

                builder.show()
            }else{
                Toast.makeText(this@RealizarPines, "Recargar fallida ${respuesta}",Toast.LENGTH_SHORT).show()

                val builder = AlertDialog.Builder(this@RealizarPines)
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