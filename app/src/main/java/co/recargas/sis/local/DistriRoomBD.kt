package co.recargas.sis.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import co.recargas.sis.local.dao.ProductoDao
import co.recargas.sis.local.dao.RecargaDao
import co.recargas.sis.local.modelo.Producto
import co.recargas.sis.local.modelo.Recargas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Producto::class,Recargas::class),version = 1, exportSchema = false)
abstract class DistriRoomBD: RoomDatabase() {
    abstract fun productoDao():ProductoDao
    abstract fun recargaDao():RecargaDao



    companion object{
        @Volatile
        private var INSTANCE: DistriRoomBD? = null

        fun getDatabase(context: Context):DistriRoomBD{
            val temInstance = INSTANCE
            if(temInstance!=null){
                return temInstance
            }
            synchronized(this){
                val instance =Room.databaseBuilder(
                    context.applicationContext,
                    DistriRoomBD::class.java,
                    "distri_database"
                )  .build()
                INSTANCE=instance
                return instance
            }

        }
    }

}