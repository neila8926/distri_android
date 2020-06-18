package co.recargas.sis.modelo

import java.util.*

data class Recargas(
    val estado:Int,
    val operador: String,
    val numero:String,
    val observacion :String,
    val valor: Int,
    val fecha:Date
)