package co.recargas.sis.modelo

data class Productos (
    val id: Int,
    val nombre:String,
    val valor: Int,
    val observacion:String,
    val operadorId: Int,
    val operadorNombre:String

)