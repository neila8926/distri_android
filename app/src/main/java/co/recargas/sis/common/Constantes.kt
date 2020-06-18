package co.recargas.sis.common

import android.os.Build
import co.recargas.sis.BuildConfig

class Constantes {
    companion object{
        const val HOST:String="distrirecarga.co"
        const val PUERTO:Int=8888
        const val HMAC_MD5_ALGORITHM = "HmacMD5"
        const val VERSION_CODE= BuildConfig.VERSION_CODE;
    }
}