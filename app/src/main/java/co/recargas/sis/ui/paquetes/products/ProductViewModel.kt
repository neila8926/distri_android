package co.recargas.sis.ui.paquetes.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.recargas.sis.local.ProductRepository
import co.recargas.sis.local.modelo.Producto

class ProductViewModel (application: Application): AndroidViewModel(application) {
//la que se conecta al repositorio para poder obtener la peticion de las peliculas populares
    private var productRepository:ProductRepository
    //Paquetes de Claro
    private var listadoProductosClaroInt: LiveData<List<Producto>>
    private var listadoProductosClaroVoz: LiveData<List<Producto>>
    private var listadoProductosClaroTodoIncluido: LiveData<List<Producto>>
    private var listadoProductoClaroLdi: LiveData<List<Producto>>
    private var listadoProductoClaroReventa: LiveData<List<Producto>>
    private var listadoProductoClaroApps: LiveData<List<Producto>>
    private var litadoProductoClaroPrepago: LiveData<List<Producto>>
    //Paquetes de Tigo
    private var listafoProductosTigoCombo: LiveData<List<Producto>>
    private var listadoProductosTigoInternet: LiveData<List<Producto>>
    private var listadoProductosTigoMinutos: LiveData<List<Producto>>
    private var listadoProductosTigoBolsas: LiveData<List<Producto>>
    private var listadoProductosTigoLdi: LiveData<List<Producto>>

    init {
        productRepository= ProductRepository(application)
        //PAQUETES DE CLARO
        listadoProductosClaroInt=productRepository.getPaquetesClaroInt()
        listadoProductosClaroVoz= productRepository.getPaquetesClaroVoz()
        listadoProductosClaroTodoIncluido=productRepository.getPaquetesClaroTodoIncluido()
        listadoProductoClaroLdi=productRepository.getPaqueteClaroLdi()
        listadoProductoClaroReventa=productRepository.getPaqueteClaroReventa()
        listadoProductoClaroApps=productRepository.getPaqueteClaroApps()
        litadoProductoClaroPrepago=productRepository.getPaqueteClaroPrepago()
        //PAQUETES DE TIGO
        listafoProductosTigoCombo=productRepository.getPaqueteTigoCombo()
        listadoProductosTigoInternet=productRepository.getPaqueteTigoInternet()
        listadoProductosTigoMinutos=productRepository.getPaquetesTigoMinutos()
        listadoProductosTigoBolsas=productRepository.getBolsasTigo()
        listadoProductosTigoLdi=productRepository.getPaqueteLdiTigo()
    }
    fun saveProducts(product: Producto){
        productRepository.insertProductos(product)
    }
    //PAQUETES DE CLARO
    fun getProductsClaroInt(): LiveData<List<Producto>>{
        return listadoProductosClaroInt
    }
    fun getProductsClaroVoz(): LiveData<List<Producto>>{
         return listadoProductosClaroVoz
    }
    fun getProductsClaroTodoIncluido(): LiveData<List<Producto>>{
        return listadoProductosClaroTodoIncluido
    }
    fun getProductsClaroLdi(): LiveData<List<Producto>>{
        return listadoProductoClaroLdi
    }
    fun getProductsReventa(): LiveData<List<Producto>>{
        return listadoProductoClaroReventa
    }
    fun getProductsApps(): LiveData<List<Producto>>{
        return listadoProductoClaroApps
    }
    fun getProductsPrepago(): LiveData<List<Producto>>{
        return litadoProductoClaroPrepago
    }
    //PAQUES TIGO
    fun getProductsCombo():LiveData<List<Producto>>{
        return listafoProductosTigoCombo
    }
    fun getProductsInternet():LiveData<List<Producto>>{
        return listadoProductosTigoInternet
    }
    fun getProductosMinutosTigo():LiveData<List<Producto>>{
        return  listadoProductosTigoMinutos
    }
    fun getProductosBolsasTigo():LiveData<List<Producto>>{
        return listadoProductosTigoBolsas
    }
    fun getProductosLdiTigo():LiveData<List<Producto>>{
        return listadoProductosTigoLdi
    }


}