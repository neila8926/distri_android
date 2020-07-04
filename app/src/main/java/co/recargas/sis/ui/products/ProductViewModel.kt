package co.recargas.sis.ui.products

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.recargas.sis.local.ProductRepository
import co.recargas.sis.local.modelo.Producto

class ProductViewModel (application: Application): AndroidViewModel(application) {
//la que se conecta al repositorio para poder obtener la peticion de las peliculas populares
    private var productRepository:ProductRepository
    private var listadoProductosClaroInt: LiveData<List<Producto>>
    private var listadoProductosClaroVoz: LiveData<List<Producto>>

    init {
        productRepository= ProductRepository(application)
        listadoProductosClaroInt=productRepository.getPaquetesClaroInt()
        listadoProductosClaroVoz= productRepository.getPaquetesClaroVoz()
    }
    fun saveProducts(product: Producto){
        Log.i("INFO", "ESTOY AQUI3 :)")
        productRepository.insertProductos(product)
    }
    fun getProductsClaroInt(): LiveData<List<Producto>>{
        Log.i("INFO", "ESTOY AQUI2 :)")
        Log.i("INFO", " Prueba "+listadoProductosClaroInt.value)
        return listadoProductosClaroInt
    }
    fun getProductsClaroVoz(): LiveData<List<Producto>>{
        Log.i("INFO", "ESTOY AQUI2 :)")
        Log.i("INFO", " Prueba "+listadoProductosClaroVoz.value)
        return listadoProductosClaroVoz
    }

}