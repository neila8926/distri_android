package co.recargas.sis.local

import android.app.Application
import android.os.AsyncTask
import co.recargas.sis.local.dao.RecargaDao
import co.recargas.sis.local.modelo.Recargas

class RecargaRepository(application: Application) {
    private val recargaDao: RecargaDao?=DistriRoomBD.getDatabase(application)?.recargaDao()

    fun insertRecargas(recarga:Recargas){
        if(recargaDao!=null) InsertAsyncTask(recargaDao).execute(recarga)
    }


    private class InsertAsyncTask(private val recargaDao: RecargaDao): AsyncTask<Recargas, Void, Void>() {
        override fun doInBackground(vararg recargas: Recargas?): Void? {
            for(recarga in recargas ){
                if (recarga != null){
                    recargaDao.insertRecargas(recarga)
                }
            }

            return null
        }

    }

}