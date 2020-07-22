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
    //Paquetes Virgin
    private var listadoProductosVirginDato: LiveData<List<Producto>>
    private var listadoProductosVirginVoz:LiveData<List<Producto>>
    private var listadoProductosVirginAntiplan:LiveData<List<Producto>>
    private var listadoProductoVirginWhatsapp:LiveData<List<Producto>>
    //ETB
    private var listadoProductoEtbCombo:LiveData<List<Producto>>
    private var listadoProductoEtbLdi:LiveData<List<Producto>>
    //AVANTEL
    private var listadoProductoAvantelTodoInc:LiveData<List<Producto>>
    private var listadoProductoAvantelVoz:LiveData<List<Producto>>
    private var listadoProductoAvantelInternet:LiveData<List<Producto>>
    private var listadoProductoAvantelWhatsapp:LiveData<List<Producto>>


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
        //PAQUETES VIRGIN
        listadoProductosVirginAntiplan=productRepository.getPaqueteVirginAntiplan()
        listadoProductosVirginDato=productRepository.getPaqueteVirginBolsaDato()
        listadoProductosVirginVoz=productRepository.getPaqueteVirginBolsaVoz()
        listadoProductoVirginWhatsapp=productRepository.getPaqueteVirginBolsaWhatsapp()
        //ETB
        listadoProductoEtbCombo=productRepository.getPaqueteEtbCombo()
        listadoProductoEtbLdi=productRepository.getPaqueteEtbLdi()
        //AVANTEL
        listadoProductoAvantelTodoInc=productRepository.getPaqueteAvantelTodoInc()
        listadoProductoAvantelVoz=productRepository.getPaqueteAvantelVoz()
        listadoProductoAvantelInternet=productRepository.getPaqueteAvantelInternet()
        listadoProductoAvantelWhatsapp=productRepository.getPaqueteAvantelWhatsapp()


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
    //VIRGIN
    fun getProductAtiplanVirgin():LiveData<List<Producto>>{
        return listadoProductosVirginAntiplan
    }
    fun getProductDatoVirgin():LiveData<List<Producto>>{
        return listadoProductosVirginDato
    }
    fun getProductVozVirgin(): LiveData<List<Producto>>{
        return listadoProductosVirginVoz
    }
    fun getProductWhatsapp():LiveData<List<Producto>>{
        return listadoProductoVirginWhatsapp
    }
    //ETB
    fun getProductComboEtb():LiveData<List<Producto>>{
        return listadoProductoEtbCombo
    }
    fun getProductLdiEtb():LiveData<List<Producto>>{
        return listadoProductoEtbLdi
    }
    //AVANTEL
    fun getProductTodoInclAvantel():LiveData<List<Producto>>{
        return  listadoProductoAvantelTodoInc
    }
    fun getProductIntAvantel():LiveData<List<Producto>>{
        return listadoProductoAvantelInternet
    }
    fun getProductVozAvantel():LiveData<List<Producto>>{
        return listadoProductoAvantelVoz
    }
    fun getProductWhatAvantel():LiveData<List<Producto>>{
        return listadoProductoAvantelWhatsapp
    }



}