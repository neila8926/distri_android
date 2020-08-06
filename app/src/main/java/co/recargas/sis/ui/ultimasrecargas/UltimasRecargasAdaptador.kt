package co.recargas.sis.ui.ultimasrecargas

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.recargas.sis.R
import co.recargas.sis.local.modelo.Producto
import co.recargas.sis.local.modelo.Recargas
import kotlinx.android.synthetic.main.item_recarga.*

class UltimasRecargasAdaptador(var contexto:Context,item:List<Recargas>):RecyclerView.Adapter<UltimasRecargasAdaptador.ViewHolder>() {
    private var recargas : List<Recargas> = ArrayList()
    var item:List<Recargas>? =null
    init {
        this.item=item
        Log.i("item",item.get(1).numero)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UltimasRecargasAdaptador.ViewHolder {
        val vista=LayoutInflater.from(contexto).inflate(R.layout.item_recarga,parent,false)
        var viewHolder=ViewHolder(vista)
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ite= item?.get(position)
        Log.i("itema",ite?.valor!!)
        holder.numero?.text=ite?.numero!!
        holder.valor?.text=ite?.valor!!
        holder.respuesta?.text=ite?.observacion!!
        holder.producto?.text=ite?.producto!!
        holder.fecha?.text=ite?.fecha!!
        holder.operador?.text=ite?.operador!!

    }

    override fun getItemCount(): Int =item!!.size




    class ViewHolder(vista:View):RecyclerView.ViewHolder(vista){
        //var vista=vista
        var numero:TextView?=null
        var operador:TextView?=null
        var producto:TextView?=null
        var valor:TextView?=null
        var respuesta:TextView?=null
        var fecha:TextView?=null

        init {
            numero=vista.findViewById(R.id.idNumeroR)
            operador=vista.findViewById(R.id.idOperadorR)
            producto=vista.findViewById(R.id.idProductoR)
            valor=vista.findViewById(R.id.idValorR)
            respuesta=vista.findViewById(R.id.idObservacionR)
            fecha=vista.findViewById(R.id.idFechaRecarga)

        }
    }
}