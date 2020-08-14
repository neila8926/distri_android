package co.recargas.sis

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import co.recargas.sis.common.ConexionSocket
import co.recargas.sis.common.Constantes
import co.recargas.sis.common.ValidacionDato
import co.recargas.sis.local.ProductRepository
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class RegistroActivity : AppCompatActivity() {
    var parametros:String="";
    val version= Constantes.VERSION_CODE;
    private lateinit var progressBarInicio: ProgressDialog

    var toolbar:Toolbar?=null
    var idIdentificacion:TextInputLayout?=null
    var idNombre:TextInputLayout?=null
    var idUsuarioR:TextInputLayout?=null
    var idDireccion:TextInputLayout?=null
    var idCelular:TextInputLayout?=null
    var idCorreoE:TextInputLayout?=null
    var btnRegistrase: Button?=null
    var btnLimpiarR:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        toolbar=findViewById(R.id.toolbarR)
        idIdentificacion=findViewById(R.id.idIdentificacion)
        idNombre=findViewById(R.id.idNombre)
        idUsuarioR=findViewById(R.id.idUsuarioR)
        idDireccion=findViewById(R.id.idDireccion)
        idCelular=findViewById(R.id.idCelular)
        idCorreoE=findViewById(R.id.idCorreoE)
        btnRegistrase=findViewById(R.id.btnRegistrase)
        toolbar?.setTitle(R.string.tituloToolbar)
        btnLimpiarR=findViewById(R.id.btnLimpiarR)


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var  identificacion=idIdentificacion?.editText?.text
        var nombre=idNombre?.editText?.text
        var usuario=idUsuarioR?.editText?.text
        var direccion=idDireccion?.editText?.text
        var celular=idCelular?.editText?.text
        var correo=idCorreoE?.editText?.text

        btnLimpiarR?.setOnClickListener {
            idIdentificacion?.editText?.setText("")
            idNombre?.editText?.setText("")
            idUsuarioR?.editText?.setText("")
            idDireccion?.editText?.setText("")
            idCelular?.editText?.setText("")
            idCorreoE?.editText?.setText("")
        }


        btnRegistrase?.setOnClickListener {
            idIdentificacion?.error=""
            idNombre?.error=""

            if(identificacion?.isEmpty()==true && nombre?.isEmpty()==true && usuario?.isEmpty()==true && direccion?.isEmpty()==true && celular?.isEmpty()==true && correo?.isEmpty()==true){
                Toast.makeText(this,"Todos los campos son requeridos",Toast.LENGTH_SHORT).show()
            }else{
                if(nombre?.isEmpty()==true || nombre.toString().length<=6 || nombre.toString().length>90 || ValidacionDato.validarNombreCompleto(nombre.toString())==false){
                    idNombre?.error="Nombre Invalido"

                }else  if(identificacion?.isEmpty()==true || (ValidacionDato.validarIdentificacion(identificacion.toString())==false && ValidacionDato.validarNit(identificacion.toString())==false) ){
                    idIdentificacion?.error="Identificación Invalida"
                } else if(usuario?.isEmpty()==true || ValidacionDato.validarUsuario(usuario.toString())==false){
                    idUsuarioR?.error="Usuario Invalido"
                } else if(direccion?.isEmpty()==true  || direccion.toString().length<6  || direccion.toString().length>99 ){
                    idDireccion?.error="Dirección Invalida"
                }else if(celular?.isEmpty()==true || ValidacionDato.validarCelular(celular.toString())==false){
                    idCelular?.error="Celular Invalido"
                }else if(correo?.isEmpty()==true || ValidacionDato.validarCorreo(correo.toString())==false){
                    idCorreoE?.error="Dirección de Correo Invalida"
                }
                else {
                    try {

                        //Se obtiene la fecha y la hora actual
                        val fechaActual= SimpleDateFormat("yyyy-MM-dd ").format(Date());
                        val horaActual= SimpleDateFormat("HH:mm:ss").format(Date());

                        val hmac = calculateRFC2104HMAC(fechaActual + horaActual, "android123*")
                        //Parametros que van a hacer enviados en la peticion Socket en el Inicio de Sesion
                        parametros = "mov|reg|"+ horaActual.toString()+"|" + hmac +"|" +nombre.toString()+ "|" +identificacion.toString()+"|"+usuario.toString()+"|"+celular.toString()+"|"+direccion.toString()+"|"+correo.toString()+"|"+ version

                        var dialogo=AlertDialog.Builder(this@RegistroActivity)
                        dialogo.setTitle("Confirmar Registro")
                        dialogo.setMessage("Nombre:\t\t\t\t${nombre.toString()}"+
                                         "\nIdentificación:\t${identificacion.toString()}"+
                                         "\nUsuario:\t\t\t\t${usuario.toString()}"+
                                         "\nCelular:\t\t\t\t${celular.toString()}"+
                                         "\nDirección:\t\t\t${direccion.toString()}"+
                                         "\nCorreo Electronico:\t ${correo.toString()}")
                            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->  Registro().execute() })
                            .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->  })
                            .show()

                  }catch (e: Exception){
                        Toast.makeText(this,"Error de Conexión",Toast.LENGTH_SHORT).show()
                    }
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
    inner class Registro:AsyncTask<Void,Int,Boolean>(){
        private var productRepository: ProductRepository = ProductRepository(application)
        private lateinit var response:String
        private  var respuesta:String="Error de Conexión"
        private var usuario:String?=null

        override fun onPreExecute() {
            super.onPreExecute()
            response=""
            progressBarInicio= ProgressDialog(this@RegistroActivity)
            progressBarInicio.setTitle("Iniciando Sesión")
            progressBarInicio.setMessage("Procesando...")
            progressBarInicio.setCancelable(false)
            progressBarInicio.show()
        }

        override fun doInBackground(vararg params: Void?): Boolean {
           if(params.isNotEmpty()){

           }else{
               try {
                   response= ConexionSocket().ClSocket(parametros)
                   Log.i("INFO", response)

               if(response.equals("Error de Conexión")==false){
                   var reqJson: JSONObject = JSONObject(response);
                   respuesta=reqJson.getString("respuesta")
                   if(respuesta.equals("ok")){
                       usuario=reqJson.getString("Usuario")
                       publishProgress(100)
                       return true
                   }

               }
               }catch (ex:Exception){
                   ex.printStackTrace()
                   return false
               }
               return false
               }

               return false

        }


        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            progressBarInicio.progress= values[0]!!
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            progressBarInicio.dismiss()
            if(result==true){
            var dialogo=AlertDialog.Builder(this@RegistroActivity)
                dialogo.setTitle("Información Registro")
                dialogo.setMessage("USUARIO REGISTRADO EXITOSAMENTE REVISE SU EMAIL\n"+
                        "\"HAY ENCONTRARA SUS DATOS DE ACCESO = ${usuario}")
                    .setPositiveButton("Ok",DialogInterface.OnClickListener { dialog, which -> this@RegistroActivity.finish() })
                    .show()


            }else{
                    if(respuesta.equals("Esta NIT/Identificacion ya se encuentra registrada")){
                        idIdentificacion?.error="Se encuentra registrado un usuario con esta identificación"
                    }else if(respuesta.equals("Este usuario ya se encuentra registrado")){
                        idUsuarioR?.error="Usuario en uso"
                    }
                Toast.makeText(this@RegistroActivity,respuesta,Toast.LENGTH_SHORT).show()
            }

        }
    }
}