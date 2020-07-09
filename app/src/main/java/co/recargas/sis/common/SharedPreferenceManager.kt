package co.recargas.sis.common

import android.content.Context
import android.content.SharedPreferences
import co.recargas.sis.app.MyApp

class SharedPreferenceManager {



    private fun SharedPrerenceManager(){}
        companion object {
            private fun SharedPrerenceManager(){}
            private const val APP_SETTING_FILE="APP_SETTINGS"
            private fun  getSharedPreferences() :  SharedPreferences?{
                return MyApp.getContext()
                    .getSharedPreferences(APP_SETTING_FILE,Context.MODE_PRIVATE)


            }
            //editar preferencias
            fun setSomeStringValue(dataLabel:String, dataValue:String ) {
                var editor: SharedPreferences.Editor  = getSharedPreferences()!!.edit()
                editor.putString(dataLabel,dataValue)
                editor.commit()
                
            }
            fun getSomeStringValue(dataLabel: String):String?{
                return getSharedPreferences()?.getString(dataLabel,"null")


            }

    }
}