package co.recargas.sis.common

import android.content.Context
import android.content.SharedPreferences
import co.recargas.sis.app.AppClase

class SharedPreferenceManager {




    private fun SharedPrerenceManager(){}
        companion object {
            private fun SharedPrerenceManager(){}
            private const val APP_SETTING_FILE="APP_SETTINGS"
            private fun  getSharedPreferences() :  SharedPreferences?{
                return AppClase.getContext()?.getSharedPreferences(APP_SETTING_FILE,Context.MODE_PRIVATE)


            }
            //editar preferencias
            fun setSomeStringValue(dataLabel:String, dataValue:String ) {
                var editor: SharedPreferences.Editor  = getSharedPreferences()!!.edit()
                editor.putString(dataLabel,dataValue)
                editor.commit()
                
            }
            fun setSomeBooleanValue(dataLabel: String,dataValue: Boolean){
                var editor:SharedPreferences.Editor= getSharedPreferences()!!.edit()
                editor.putBoolean(dataLabel,dataValue)
                editor.commit()
            }
            fun deleteSomeValue(){
                var editor:SharedPreferences.Editor= getSharedPreferences()!!.edit()
                editor.clear()
                editor.commit()
            }
            fun getSomeStringValue(dataLabel: String):String?{
                return getSharedPreferences()?.getString(dataLabel,"null")
            }

            fun getSomeBooleanValue(dataLabel: String):Boolean?{
                return getSharedPreferences()?.getBoolean(dataLabel,false)
            }
    }
}