package co.recargas.sis.ui.recargas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.recargas.sis.local.ProductRepository
import co.recargas.sis.local.modelo.Producto

class RecargasViewModel(aplication: Application):AndroidViewModel(aplication) {
    private var productoRepository:ProductRepository
    private var recargas:LiveData<List<Producto>>


    init {
        productoRepository=ProductRepository(aplication)
        recargas=productoRepository. getRecargas()
    }
    fun recargas():LiveData<List<Producto>>{
        return recargas
    }
}