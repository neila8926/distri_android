package co.recargas.sis.ui.ultimasrecargas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.recargas.sis.local.RecargaRepository
import co.recargas.sis.local.modelo.Recargas

class UltimasRecargasViewModel(application: Application): AndroidViewModel(application) {

    private var recargasRepository:RecargaRepository
    private var ultimasRecargas:LiveData<List<Recargas>>
    init {
        recargasRepository= RecargaRepository(application)
        ultimasRecargas=recargasRepository.getRecargas()
    }
    fun getUltimasRecargas():LiveData<List<Recargas>>{
        return ultimasRecargas
    }
}