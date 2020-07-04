package co.recargas.sis.local.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "recargas")
data class Recargas(
    @PrimaryKey(autoGenerate = true)
    val estado:Int,
    val operador: String,
    val numero:String,
    val observacion :String,
    val valor: Int,
    val fecha:String
)