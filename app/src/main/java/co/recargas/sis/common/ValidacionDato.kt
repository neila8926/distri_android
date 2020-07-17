package co.recargas.sis.common

import android.util.Log
import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidacionDato {


    companion object {
        lateinit var matcher: Matcher
        lateinit var pattern: Pattern

        //El nombre puede contener solo letras y espacios en blanco
        val NOMBRE_COMPLETO:String="^[A-Z]+(\\s*[A-Z]+)+[A-Z]+$"
        //la idetificacion puede contener solo numeros del 0 al 9 y tener entre 8 y 11
        val IDENTIFICACION:String="^[0-9]{8,11}$"
        //El Nit puede contenet culaquier digito del 0 al 9, entre 8 y 11 caracteres luego un - y numeros de 1 o 2 digitod
        val NIT:String="^[0-9]{8,11}-[0-9]{1,2}\$"
        val EMAIL:String="^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$";
        val CELULAR:String="^[0-9]{10}+$";

        fun validarPassword(password:String):Boolean{
            var nuevoPass=password.trim().replace(" ","").replace("\n", "")
            if(nuevoPass.equals("")){
                return false
            }
            if(nuevoPass.length<6){
                return false
            }
            return true
        }
        fun validarUsuario(usuario: String):Boolean {

            var usuarioNuevo=usuario.trim().replace("\\s","")
            if(usuarioNuevo.equals("") || usuarioNuevo.length<5 || usuarioNuevo.length>40){
                return false
            }
            return true
        }
        fun validarNombreCompleto(nombre:String): Boolean{
            //compila la expresion regular y extrae los digitos
            pattern= Pattern.compile(NOMBRE_COMPLETO)
            //busca coincidencias del contenido de la variable nombre en la expresion regular
            matcher=pattern.matcher(nombre)
            //devuelve True si coincide
            return matcher.matches()
        }
        fun validarCelular(celular:String):Boolean{
            pattern= Pattern.compile(CELULAR)
            matcher=pattern.matcher(celular)
            Log.i("INFO"," VALIDA"+matcher.matches())
            return matcher.matches()
        }
        fun validarIdentificacion(identificacion: String):Boolean{
            pattern= Pattern.compile(IDENTIFICACION)
            matcher=pattern.matcher(identificacion)
            return matcher.matches()
        }
        fun validarNit(nit:String):Boolean{
            pattern= Pattern.compile(NIT)
            matcher=pattern.matcher(nit)
            return  matcher.matches()
        }
        fun validarCorreo(correo: String):Boolean {
            pattern= Pattern.compile(EMAIL)
            matcher=pattern.matcher(correo)
            return  matcher.matches()
        }

    }



}