package co.recargas.sis.ui.ultimasrecargas

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.setActivated
import androidx.recyclerview.widget.RecyclerView
import co.recargas.sis.R
import co.recargas.sis.interfaces.ClickListenerUltiRec
import co.recargas.sis.local.modelo.Producto
import co.recargas.sis.local.modelo.Recargas
import kotlinx.android.synthetic.main.item_recarga.*
import java.text.NumberFormat

class UltimasRecargasAdaptador(item:List<Recargas>, var listener:ClickListenerUltiRec):RecyclerView.Adapter<UltimasRecargasAdaptador.ViewHolder>() {
    private var recargas : List<Recargas> = ArrayList()
    var item:List<Recargas>? =null
    init {
        this.item=item
        //Log.i("item",item.get(1).numero)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UltimasRecargasAdaptador.ViewHolder {
        val vista=LayoutInflater.from(parent?.context).inflate(R.layout.item_recarga,parent,false)
        var viewHolder=ViewHolder(vista,listener )
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ite= item?.get(position)
        Log.i("itema",ite?.valor!!)
        holder.numero?.text=ite?.numero!!.toString()
        var numberFormat:NumberFormat= NumberFormat.getNumberInstance()

        holder.valor?.text=numberFormat.format(ite?.valor.toFloat()).toString()
        holder.respuesta?.text=ite?.observacion!!.toString()
        holder.producto?.text=ite?.producto!!.toString()
        holder.fecha?.text=ite?.fecha!!.toString()
        holder.operador?.text=ite?.operador!!.toString()
        holder.numero
    }

    override fun getItemCount(): Int =item!!.size

    class ViewHolder(vista:View, listener: ClickListenerUltiRec):RecyclerView.ViewHolder(vista),View.OnClickListener{

        var vista=vista
        var numero:TextView?=null
        var operador:TextView?=null
        var producto:TextView?=null
        var valor:TextView?=null
        var respuesta:TextView?=null
        var fecha:TextView?=null
        var listener:ClickListenerUltiRec?=null

        init {
            numero=vista.findViewById(R.id.idNumeroR)
            operador=vista.findViewById(R.id.idOperadorR)
            producto=vista.findViewById(R.id.idProductoR)
            valor=vista.findViewById(R.id.idValorR)
            respuesta=vista.findViewById(R.id.idObservacionR)
            fecha=vista.findViewById(R.id.idFechaRecarga)
            this.listener=listener
            vista.setOnClickListener(this)

        }

        override fun onClick(v: View?) {

        this.listener?.onClick(v!!,adapterPosition)

        }
    }
}