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
    //CLARO
    //Obtener Paquetes de Claro Internet
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE 'NAVEGA%' AND nombre LIKE '%MB'")
    fun getClaroInternet():LiveData<List<Producto>>
    //Obtener Paquetes de Claro Voz
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE 'PAQUETE%' AND nombre LIKE '%MIN%' AND nombre LIKE '%TAT'")
    fun getClaroVoz():LiveData<List<Producto>>
    //Obtener Paquetes de Claro Todo Incluido
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE '%TODOINCLUIDO%'")
    fun getClaroTodoIncl():LiveData<List<Producto>>
    //Obtenet paquetes Claro LDI
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE '%LDI%'")
    fun getClaroLdi():LiveData<List<Producto>>
    //Obtener Paquetes reventa
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE '%REVENTA%'")
    fun getClaroReventa():LiveData<List<Producto>>
    //Obtener Paquetes Claro APPS
    @Query("SELECT * FROM productos WHERE operadorId=1 AND (nombre LIKE '%INSTAGRAM%' or nombre LIKE '%WAZE%' OR nombre LIKE '%SNAPCHAT%' OR nombre LIKE '%YOUTUBE%' OR nombre LIKE '%WHATSAPP%' OR nombre LIKE '%APP%')")
    fun getClaroApps():LiveData<List<Producto>>
    //Obtener Paquetes Claro Inetrnet y Tv Prepago
    @Query("SELECT * FROM productos WHERE operadorId=1 AND ( nombre LIKE 'INALAMBRICO' OR nombre LIKE '%TV%' OR nombre LIKE '%PREPAGO%')")
    fun getClaroPrepago():LiveData<List<Producto>>
    //TIGO
    //Obtener paquetes de Combo de Tigo
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre LIKE '%CMB%'")
    fun getTigoCombo():LiveData<List<Producto>>
    //obtener paquetes de Internet de Tigo
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre LIKE '%_IN_%'")
    fun getTigoInter():LiveData<List<Producto>>
    //obtener paquetes de minutos y sms de Tigo
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre LIKE '%_MIN_%'  AND nombre LIKE '%_MS'")
    fun getTigoMin(): LiveData<List<Producto>>
    //obtener bolsas de tigo
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre LIKE 'B_INIT%'")
    fun getTigoBolsa(): LiveData<List<Producto>>
    //obtener paquetes de larga distancia de tigo
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre LIKE '%LDI'")
    fun getTigoLdi(): LiveData<List<Producto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProductos(producto: Producto)

}