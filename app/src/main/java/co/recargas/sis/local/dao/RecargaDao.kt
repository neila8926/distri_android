package co.recargas.sis.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import co.recargas.sis.local.modelo.Recargas
@Dao
interface RecargaDao {
    @Query("SELECT * FROM recargas")
   fun getAllRecargas():LiveData<List<Recargas>>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertRecargas(recargas: Recargas)

    @Query("DELETE FROM recargas")
    fun deleteRecargas()

}