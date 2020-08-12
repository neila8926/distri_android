package co.recargas.sis.ui.pines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.recargas.sis.local.ProductRepository
import co.recargas.sis.local.modelo.Producto

class PinesViewModel(application: Application): AndroidViewModel(application) {
    private var repository:ProductRepository
    private var listadoProductosNetflix:LiveData<List<Producto>>
    private var listadoProductosSpotify:LiveData<List<Producto>>
    private var listadoProductosImvu:LiveData<List<Producto>>
    private var listadoProductoMinecraft:LiveData<List<Producto>>

    init {
        repository= ProductRepository(application)
        listadoProductosNetflix=repository.getPinesNetflix()
        listadoProductosSpotify=repository.getPinesSpotify()
        listadoProductosImvu=repository.getPinesImvu()
        listadoProductoMinecraft=repository.getPinesMinecraft()
    }

    fun getProductNetflix():LiveData<List<Producto>>{
        return listadoProductosNetflix
    }
    fun getProductSpotify():LiveData<List<Producto>>{
        return listadoProductosSpotify
    }
    fun getProductImvu():LiveData<List<Producto>>{
        return listadoProductosImvu
    }
    fun getProductMinecraft():LiveData<List<Producto>>{
        return listadoProductoMinecraft
    }
}