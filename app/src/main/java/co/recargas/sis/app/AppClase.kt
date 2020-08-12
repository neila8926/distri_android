package co.recargas.sis.app

import android.app.Application
import android.content.Context
import com.mazenrashed.printooth.Printooth

class AppClase:Application() {

companion object {
    private lateinit var instance:AppClase
    //para poder recibir, nos permite recibir un objeto de este tipo, devuelve un objeto de tipo instance
    fun getInstance(): AppClase? {
        return instance
    }

    //Meodo que devuelve el contexto, referencia al especio de memoria
    fun getContext(): Context? {
        return instance
    }
}

    override fun onCreate() {
        super.onCreate()
        Printooth.init(this)
        instance=this

    }
}