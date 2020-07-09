package co.recargas.sis.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.recargas.sis.local.modelo.Producto

//Se definen las operaciones que se pueden lleva a cabo
@Dao
interface ProductoDao {
    //Obtener Paquetes de Claro Internet
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE 'NAVEGA%' AND nombre LIKE '%MB'")
    fun getClaroInternet():LiveData<List<Producto>>
    //Obtener Paquetes de Claro Voz
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE 'PAQUETE%' AND nombre LIKE '%MIN%' AND nombre LIKE '%TAT'")
    fun getClaroVoz():LiveData<List<Producto>>
    //Obtener Paquetes de Claro Todo Incluido
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE '%TODOINCLUIDO%'")
    fun getClaroTodoIncl():LiveData<List<Producto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProductos(producto: Producto)

}