package co.recargas.sis.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.recargas.sis.local.modelo.Recargas
@Dao
interface RecargaDao {
    @Query("SELECT * FROM recargas")
   fun getAllRecargas():List<Recargas>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecargas(recargas: Recargas)

}