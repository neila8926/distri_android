package co.recargas.sis.local.modelo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto (
    @PrimaryKey val id: Int,
    val nombre:String,
    val valor: Int,
    val observacion:String,
    val operadorId: Int,
    val operadorNombre:String

) {
    override fun toString(): String {
        return "Producto(id=$id, nombre='$nombre', valor=$valor, observacion='$observacion', operadorId=$operadorId, operadorNombre='$operadorNombre')"
    }
}