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
    //RECARGAS
    @Query("SELECT * FROM productos WHERE nombre='RECARGA'")
    fun getRecargas():LiveData<List<Producto>>
    @Query("DELETE FROM productos")
    fun eliminarProductos()
    //CLARO
    //Obtener Paquetes de Claro Internet
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE 'NAVEGA%' AND nombre LIKE '%MB' AND (nombre NOT LIKE '%SNAPCHAT%' AND nombre NOT LIKE '%WAZE%' AND nombre NOT LIKE '%YOUTUBE%' AND nombre NOT LIKE '%INSTAGRAM%')")
    fun getClaroInternet():LiveData<List<Producto>>
    //Obtener Paquetes de Claro Voz
    @Query("SELECT * FROM productos WHERE operadorId=1 AND nombre LIKE 'PAQUETE%' AND nombre LIKE '%MIN%' AND nombre LIKE '%TAT'")
    fun getClaroVoz():LiveData<List<Producto>>
    //Obtener Paquetes de Claro Todo Incluido
    @Query("SELECT * FROM productos WHERE operadorId=1 AND( nombre LIKE '%TODOINCLUIDO%' OR nombre LIKE '%TODO_INCLUIDO%')")
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
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre LIKE '%CMB%' AND nombre LIKE '%TD'")
    fun getTigoCombo():LiveData<List<Producto>>
    //obtener paquetes de Internet de Tigo
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre  LIKE '%\\_IN\\_%' ESCAPE '\\'")
    fun getTigoInter():LiveData<List<Producto>>
    //obtener paquetes de minutos y sms de Tigo
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre LIKE '%_MIN_%'  AND nombre LIKE '%_MS'")
    fun getTigoMin(): LiveData<List<Producto>>
    //obtener bolsas de tigo
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre LIKE 'B_INT%'")
    fun getTigoBolsa(): LiveData<List<Producto>>
    //obtener paquetes de larga distancia de tigo
    @Query("SELECT * FROM productos WHERE operadorId=2 AND nombre LIKE '%LDI'")
    fun getTigoLdi(): LiveData<List<Producto>>
    //VIRGIN
    //obtener antiplanes de virgin
    @Query("SELECT * FROM productos WHERE operadorId=7 AND nombre LIKE 'ANTIPLAN%'")
    fun getVirginAntiplan():LiveData<List<Producto>>
    //obtener bolsas de datos de virgin
    @Query("SELECT * FROM productos WHERE operadorId=7 AND nombre LIKE 'BOLSA_DATOS%'")
    fun getVirginBolsaDato():LiveData<List<Producto>>
    //obtener Bolsa de Voz de Virgin
    @Query("SELECT * FROM productos WHERE operadorId=7 AND nombre LIKE 'BOLSA_VOZ%'")
    fun getVirginBolsaVoz(): LiveData<List<Producto>>
    //obtener bolsa de Whatsapp de Virgin
    @Query("SELECT * FROM productos WHERE operadorId=7 AND nombre LIKE 'BOLSA_WA%'")
    fun getVirginBolsaWhatsapp():LiveData<List<Producto>>
    //ETB
    //obtener paquetes todo incluido de ETB
    @Query("SELECT * FROM productos WHERE operadorId=9 AND nombre LIKE '%_COMBOS_%'")
    fun getEtbCombo():LiveData<List<Producto>>
    //obtener paquetes larga distancia de ETB
    @Query("SELECT * FROM productos WHERE operadorId=9 AND ( nombre LIKE '%VENEZUELA%'  OR nombre LIKE '%LDI%')")
    fun getEtbLdi():LiveData<List<Producto>>
    //AVANTEL
    @Query("SELECT * FROM productos WHERE operadorId=4 AND nombre LIKE '%_TI'")
    fun getAvantelTodoIncluido():LiveData<List<Producto>>
    @Query("SELECT * FROM productos WHERE operadorId=4 AND (nombre LIKE '%MIN%' AND nombre LIKE '%M' )")
    fun getAvantelVoz():LiveData<List<Producto>>
    @Query("SELECT * FROM productos WHERE operadorId=4 AND(  nombre LIKE '%I' AND nombre NOT LIKE '%T_' )")
    fun getAvantelInternet():LiveData<List<Producto>>
    @Query("SELECT * FROM productos WHERE operadorId=4 AND  nombre LIKE '%W' ")
    fun getAvantelWhatsapp():LiveData<List<Producto>>
    //MOVISTAR
    //Obtener todos los paquetes todo Incluido de Movistar
    @Query("SELECT * FROM productos WHERE operadorId=3 AND nombre LIKE '%TI'")
    fun getMovistarTodoInc():LiveData<List<Producto>>
    //Obtener todos los paquetes de Minutos
    @Query("SELECT * FROM productos WHERE operadorId=3 AND nombre LIKE '%M'")
    fun getMovistarVoz():LiveData<List<Producto>>
    //obtener todos los paquetes de Internet
    @Query("SELECT * FROM productos WHERE operadorId=3 AND ( nombre LIKE '%I' AND nombre NOT LIKE '%T_')")
    fun getMovistarInternet(): LiveData<List<Producto>>
    //obtener todos los paquetes larga distancia
    @Query("SELECT * FROM productos WHERE operadorId=3 AND nombre LIKE '%LDI%'")
    fun getMovistarLdi():LiveData<List<Producto>>
    //Todos los paquetes de EXITO
    @Query("SELECT * FROM productos WHERE operadorId=10 AND nombre != 'RECARGA'" )
    fun getExitoAllPaquetes():LiveData<List<Producto>>
    //Todos los paquetes de Kalley
    @Query("SELECT * FROM productos WHERE operadorId=20 AND nombre != 'RECARGA'" )
    fun getKalleyAllPaquetes():LiveData<List<Producto>>
    //Todos los paquetes de Wings
    @Query("SELECT * FROM productos WHERE operadorId=21 AND nombre != 'RECARGA'" )
    fun getWingsAllPaquetes():LiveData<List<Producto>>
    //PINES NETFLIX
    @Query("SELECT * FROM productos WHERE operadorId=11 AND nombre LIKE '%NETFLIX%'")
    fun getPinesNetflix():LiveData<List<Producto>>
    //PINES SPOTIFY
    @Query("SELECT * FROM productos WHERE operadorId=11 AND nombre LIKE '%SPOTIFY%'")
    fun getPinesSpotify():LiveData<List<Producto>>
    //PINES IMVU
    @Query("SELECT * FROM productos WHERE operadorId=11 AND nombre LIKE '%IMVU%'")
    fun getPinesImvu():LiveData<List<Producto>>
    //PINES MINECRAFT
    @Query("SELECT * FROM productos WHERE operadorId=11 AND nombre LIKE '%MINECRAFT%'")
    fun getPinesMinecraft():LiveData<List<Producto>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProductos(producto: Producto)

}