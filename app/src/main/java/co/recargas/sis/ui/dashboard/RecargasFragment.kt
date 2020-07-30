package co.recargas.sis.ui.dashboard

import android.app.Dialog
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.recargas.sis.R
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import co.recargas.sis.common.SharedPreferenceManager
import co.recargas.sis.common.ValidacionDato
import co.recargas.sis.local.modelo.Producto
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject
import java.lang.Exception
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList

class RecargasFragment:Fragment() {
    var parametros:String="";
    val version= Constantes.VERSION_CODE;
    var progressBar: ProgressBar?=null
    var fechaActual:String?=null
    var horaActual:String?=null

    private lateinit var recargasViewModel: RecargasViewModel
    private var operadores:List<Producto> = ArrayList()
    private var recargas:TextInputLayout?=null
    var lista:MutableList<String> =ArrayList()
    var btnMil:Button?=null
    var btnDosMil:Button?=null
    var btnTresMil:Button?=null
    var btnCincoMil:Button?=null
    var btnDiezMil:Button?=null
    var btnVeinteMil:Button?=null
    var btnRecargar:Button?=null
    var nombreOperador:String?=null
    var operadorId:Int?=null
    private lateinit var editNumero:TextInputLayout
    private lateinit var edtValor:TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View =
            inflater.inflate(R.layout.fragment_dashboard, container, false)
        recargas=root.findViewById(R.id.recargas)
        editNumero=root.findViewById(R.id.editTextNumber2)
        edtValor=root.findViewById(R.id.editTextNumber)
        btnRecargar=root.findViewById(R.id.btnRecargas)
        progressBar=root.findViewById(R.id.progressBar2)
        btnMil=root.findViewById(R.id.btnMil)
        btnDosMil=root.findViewById(R.id.btnDosMil)
        btnTresMil=root.findViewById(R.id.btnTresMil)
        btnCincoMil=root.findViewById(R.id.btnCincoMil)
        btnDiezMil=root.findViewById(R.id.btnDiezMil)
        btnVeinteMil=root.findViewById(R.id.btnVeinteMil)

        recargasViewModel=ViewModelProvider(this).get(RecargasViewModel::class.java)
        
        btnMil?.setOnClickListener {
            edtValor.editText?.setText("1000")
        }
        btnDosMil?.setOnClickListener {
            edtValor.editText?.setText("2000")
        }
        btnTresMil?.setOnClickListener {
            edtValor.editText?.setText("3000")
        }
        btnCincoMil?.setOnClickListener {
            edtValor.editText?.setText("5000")
        }
        btnDiezMil?.setOnClickListener {
            edtValor.editText?.setText("10000")
        }
        btnVeinteMil?.setOnClickListener {
            edtValor.editText?.setText("20000")
        }

        recargasViewModel.recargas().observe(viewLifecycleOwner, Observer {
            operadores=it

           operadores.forEach {
               lista.add(it.operadorNombre)
           }
           val adaptador=ArrayAdapter(requireContext(),R.layout.list_item,lista)
            (recargas?.editText as? AutoCompleteTextView )?.setAdapter(adaptador)

            (recargas?.editText as? AutoCompleteTextView )?.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
               nombreOperador= lista[position]        

            })
           })
        btnRecargar?.setOnClickListener {
            if(nombreOperador==null) {
                recargas?.error="Seleccione un operador"
            }else{
            operadores.forEach {
                if(nombreOperador.equals(it.operadorNombre)==true){
                    operadorId=it.operadorId
                    recargas?.error=null
                    Log.i("INFO",operadorId.toString())
                }
             }

            if(editNumero.editText?.text?.isEmpty()==true || ValidacionDato.validarCelular(editNumero.editText?.text.toString())==false ){
                editNumero.setError("Digite un numero de celular Valido")

            }else{
                editNumero.error=null
            }
            if(edtValor.editText?.text?.isEmpty()==true ){
                edtValor.setError("Digite un valor")


            }else{
                try {
                if(edtValor.editText!!.text!!.toString()!!.toInt()<1000 || edtValor.editText!!.text!!.toString()!!.toInt()%1000!=0 || edtValor.editText!!.text!!.toString()!!.toInt()>100000){

                    edtValor.setError("Digite un valor valido, debe ser mayor a 1.000 y menor a 100.000")

            }else{
                    edtValor.error=null
                    var idCliente= SharedPreferenceManager.getSomeStringValue("ID")
                    //Se obtiene la fecha y la hora actual
                    fechaActual= SimpleDateFormat("yyyy-MM-dd ").format(Date());
                    horaActual= SimpleDateFormat("HH:mm:ss").format(Date());
                    var celular=editNumero.editText!!.text.toString()
                    var valor=edtValor.editText!!.text!!.toString()

                    //Se envian los datos al metodo que va a generar la Key de tipo Hexadecimal para ser enviada a Distrirecarga
                    val hmac = calculateRFC2104HMAC(fechaActual + horaActual, "android123*")
                    //Parametros que van a hacer enviados en la peticion Socket en el Inicio de Sesion
                   // Log.i("INFO", "NOMBRE P "+nombrePaquete)
                    parametros = "mov|rec|"+horaActual+"|"+hmac +"|"+idCliente+"|"+celular+"|"+valor+"|"+operadorId+"|"+version;

                    var alertDialog=AlertDialog.Builder(context!!)
                    alertDialog.setTitle("Confirmar Recargar")
                    alertDialog.setMessage("Numero: ${celular}\nPaquete: ${nombreOperador}\nValor: ${valor}")
                    alertDialog.apply {
                        setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id ->
                            RealizarRecarga().execute()
                        })
                        setNegativeButton("Cancelar",DialogInterface.OnClickListener { dialog, id ->

                        })
                    }
                    alertDialog.show()
                }

            }catch (e:NumberFormatException){
                    edtValor.setError("Digite un valor valido, debe ser mayor a 1.000 y menor a 100.000")
                    
                } }

        }
        }

       return root
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

    inner class RealizarRecarga: AsyncTask<Void, Int , Boolean>(){
        private lateinit var response:String
        private lateinit var respuesta:String
        lateinit var saldo:String

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar?.max=100
            progressBar?.progress=0
            progressBar?.visibility=View.VISIBLE
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
            super.onPostExecute(result)
            progressBar?.visibility=View.GONE

            if(result==true){
                Toast.makeText(context,"Recarga Exitosa ${saldo}", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(context!!)
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
                Toast.makeText(context, "Recargar fallida ${respuesta}",Toast.LENGTH_SHORT).show()

                val builder = AlertDialog.Builder(context!!)
                builder.setTitle("Confirmación")
                builder.setMessage(respuesta)
                    .setPositiveButton("Aceptar",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                builder.show()

            }
        }



        override fun onCancelled() {
            super.onCancelled()
        }


    }
}