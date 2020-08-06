package co.recargas.sis.local.modelo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "recargas")
data class Recargas(
    @PrimaryKey val codigo:String,
    val estado:Int,
    val operador: String,
    val producto:String,
    val numero:String,
    val observacion :String,
    val valor: String,
    val fecha:String
    )