package co.recargas.sis.ui.paquetes.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.recargas.sis.local.ProductRepository
import co.recargas.sis.local.modelo.Producto

class ProductViewModel (application: Application): AndroidViewModel(application) {
//la que se conecta al repositorio para poder obtener la peticion de las peliculas populares
    private var productRepository:ProductRepository
    private var listadoProductosClaroInt: LiveData<List<Producto>>
    private var listadoProductosClaroVoz: LiveData<List<Producto>>
    private var listadoProductosClaroTodoIncluido: LiveData<List<Producto>>

    init {
        productRepository= ProductRepository(application)
        listadoProductosClaroInt=productRepository.getPaquetesClaroInt()
        listadoProductosClaroVoz= productRepository.getPaquetesClaroVoz()
        listadoProductosClaroTodoIncluido=productRepository.getPaquetesClaroTodoIncluido()
    }
    fun saveProducts(product: Producto){
        productRepository.insertProductos(product)
    }
    fun getProductsClaroInt(): LiveData<List<Producto>>{
        return listadoProductosClaroInt
    }
    fun getProductsClaroVoz(): LiveData<List<Producto>>{
         return listadoProductosClaroVoz
    }
    fun getProductsClaroTodoIncluido(): LiveData<List<Producto>>{
        return listadoProductosClaroTodoIncluido
    }

}