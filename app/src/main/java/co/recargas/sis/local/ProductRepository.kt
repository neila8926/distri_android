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
    fun getPaquetesClaroInt(): LiveData<List<Producto>>{
       return productoDao?.getClaroInternet() ?: MutableLiveData<List<Producto>>()
    }
    fun getPaquetesClaroVoz(): LiveData<List<Producto>>{
        return productoDao?.getClaroVoz() ?: MutableLiveData<List<Producto>>()
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
