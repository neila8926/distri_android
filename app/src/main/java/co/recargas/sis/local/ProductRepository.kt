package co.recargas.sis.local

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.recargas.sis.local.dao.ProductoDao
import co.recargas.sis.local.modelo.Producto

class ProductRepository(application: Application){
   private val productoDao: ProductoDao?=DistriRoomBD.getDatabase(application)?.productoDao()

    fun insertProductos(producto: Producto){
       if (productoDao != null ) InsertAsyncTask(productoDao).execute(producto);
    }
    //PAQUETES DE CLARO
    fun getPaquetesClaroInt(): LiveData<List<Producto>>{
       return productoDao?.getClaroInternet() ?: MutableLiveData<List<Producto>>()
    }
    fun getPaquetesClaroVoz(): LiveData<List<Producto>>{
        return productoDao?.getClaroVoz() ?: MutableLiveData<List<Producto>>()
    }
    fun getPaquetesClaroTodoIncluido(): LiveData<List<Producto>>{
        return productoDao?.getClaroTodoIncl() ?: MutableLiveData<List<Producto>>()
    }
    fun getPaqueteClaroLdi():LiveData<List<Producto>>{
        return productoDao?.getClaroLdi() ?: MutableLiveData<List<Producto>>()
    }
    fun getPaqueteClaroReventa():LiveData<List<Producto>>{
        return productoDao?.getClaroReventa() ?: MutableLiveData<List<Producto>>()
    }
    fun getPaqueteClaroApps():LiveData<List<Producto>>{
        return productoDao?.getClaroApps() ?: MutableLiveData<List<Producto>>()
    }
    fun getPaqueteClaroPrepago(): LiveData<List<Producto>>{
        return productoDao?.getClaroPrepago() ?: MutableLiveData<List<Producto>>()
    }
    //PAQUETES DE TIGO
    fun getPaqueteTigoCombo():LiveData<List<Producto>>{
        return productoDao?.getTigoCombo() ?: MutableLiveData<List<Producto>>()
    }
    fun getPaqueteTigoInternet(): LiveData<List<Producto>>{
        return  productoDao?.getTigoInter() ?:MutableLiveData<List<Producto>>()
    }
    fun getPaquetesTigoMinutos(): LiveData<List<Producto>>{
        return productoDao?.getTigoMin()?: MutableLiveData<List<Producto>>()
    }
    fun getBolsasTigo(): LiveData<List<Producto>>{
        return productoDao?.getTigoBolsa()?: MutableLiveData<List<Producto>>()
    }
    fun getPaqueteLdiTigo(): LiveData<List<Producto>>{
        return productoDao?.getTigoLdi()?:MutableLiveData<List<Producto>>()
    }
    //VIRGIN
    fun getPaqueteVirginAntiplan():LiveData<List<Producto>>{
        return productoDao?.getVirginAntiplan()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteVirginBolsaDato(): LiveData<List<Producto>>{
        return productoDao?.getVirginBolsaDato()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteVirginBolsaVoz():LiveData<List<Producto>>{
        return productoDao?.getVirginBolsaVoz()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteVirginBolsaWhatsapp(): LiveData<List<Producto>>{
        return productoDao?.getVirginBolsaWhatsapp()?:MutableLiveData<List<Producto>>()
    }
    //ETB
    fun getPaqueteEtbCombo():LiveData<List<Producto>>{
        return productoDao?.getEtbCombo()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteEtbLdi():LiveData<List<Producto>>{
        return productoDao?.getEtbLdi()?: MutableLiveData<List<Producto>>()
    }
    //AVANTEL
    fun getPaqueteAvantelTodoInc():LiveData<List<Producto>>{
        return productoDao?.getAvantelTodoIncluido()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteAvantelVoz():LiveData<List<Producto>>{
        return productoDao?.getAvantelVoz()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteAvantelInternet():LiveData<List<Producto>>{
        return  productoDao?.getAvantelInternet()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteAvantelWhatsapp():LiveData<List<Producto>>{
        return productoDao?.getAvantelWhatsapp()?:MutableLiveData<List<Producto>>()
    }
    //MOVISTAR
    fun getPaqueteMovistarTodoInc():LiveData<List<Producto>>{
        return productoDao?.getMovistarTodoInc()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteMovistarVoz():LiveData<List<Producto>>{
        return productoDao?.getMovistarVoz()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteMovistarInternet():LiveData<List<Producto>>{
        return productoDao?.getMovistarInternet()?:MutableLiveData<List<Producto>>()
    }
    fun getPaqueteMovistarLdi():LiveData<List<Producto>>{
        return productoDao?.getMovistarLdi()?:MutableLiveData<List<Producto>>()
    }
    //EXITO
    fun getPaqueteExitoAll():LiveData<List<Producto>>{
        return productoDao?.getExitoAllPaquetes()?:MutableLiveData<List<Producto>>()
    }
    //KALLEY
    fun getPaquetesKalley():LiveData<List<Producto>>{
        return productoDao?.getKalleyAllPaquetes()?:MutableLiveData<List<Producto>>()
    }
    //WINGS
    fun getPaquetesWigs():LiveData<List<Producto>>{
        return productoDao?.getWingsAllPaquetes()?:MutableLiveData<List<Producto>>()
    }

    private class InsertAsyncTask(private val productoDao: ProductoDao): AsyncTask<Producto,Void,Void>() {
        override fun doInBackground(vararg productos: Producto?): Void? {
           for(product in productos ){
               if (product != null){
                   productoDao.insertAllProductos(product)
                   }
           }

            return null
        }

    }

}
