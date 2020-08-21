package co.recargas.sis.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.recargas.sis.R
import co.recargas.sis.local.modelo.Producto





import kotlinx.android.synthetic.main.fragment_producto.view.*
import java.text.NumberFormat


class ProductoRecyclerViewAdapter() : RecyclerView.Adapter<ProductoRecyclerViewAdapter.ViewHolder>(), View.OnClickListener {

    var mOnClickListener: View.OnClickListener
    private var productos : List<Producto> = ArrayList()

    var nombre:String?=null
    var valor:Int?=null

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Producto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_producto, parent, false)
        return ViewHolder(view)
    }
//Se encarga de ir dibujando cada elemento de la lista sobre el RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //Se obtiene el elemento actual de la lista
        val item = productos[position]
    //Se carga la información elemento por elemento al ViewComponet
        holder.nameProduct.text = item.nombre
        holder.descriptionProduct.text = item.observacion
    var numberFormat:NumberFormat= NumberFormat.getInstance()
    var precio=numberFormat.format(item.valor.toInt())
        holder.priceProduct.text = precio
    if(item.operadorNombre.toUpperCase()=="CLARO"){
        holder.imageLogo.setImageResource(R.drawable.recarga_claro)
    }
    if(item.operadorNombre.toUpperCase()=="MOVISTAR"){
        holder.imageLogo.setImageResource(R.drawable.recarga_movistar)
    }
    if(item.operadorNombre.toUpperCase()=="AVANTEL"){
        holder.imageLogo.setImageResource(R.drawable.recarga_avantel)
    }
    if(item.operadorNombre.toUpperCase()=="KALLEY_MOBILE"){
        holder.imageLogo.setImageResource(R.drawable.recrga_kalley)
    }
    if(item.operadorNombre.toUpperCase()=="VIRGIN MOBILE"){
        holder.imageLogo.setImageResource(R.drawable.virgin)
    }
    if(item.operadorNombre.toUpperCase()=="ETB"){
        holder.imageLogo.setImageResource(R.drawable.paquete_etb_n)
    }
    if(item.operadorNombre.toUpperCase()=="EXITO"){
        holder.imageLogo.setImageResource(R.drawable.recarga_exito)
    }
    if(item.operadorNombre.toUpperCase()=="TIGO"){
        holder.imageLogo.setImageResource(R.drawable.tigo)
    }
    if(item.operadorNombre.toUpperCase()=="WINGS_MOBILE"){
        holder.imageLogo.setImageResource(R.drawable.recarga_wings)
    }
    if(item.nombre.toUpperCase().indexOf("NETFLIX")!=-1){
        holder.imageLogo.setImageResource(R.drawable.pin_netflix)
    }
    if(item.nombre.toUpperCase().indexOf("SPOTIFY")!=-1){
        holder.imageLogo.setImageResource(R.drawable.spotify)
    }
    if(item.nombre.toUpperCase().indexOf("IMVU")!=-1){
        holder.imageLogo.setImageResource(R.drawable.imvu)
    }
    if(item.nombre.toUpperCase().indexOf("MINECRAFT")!=-1){
        holder.imageLogo.setImageResource(R.drawable.minecraft)
    }



        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)

        }
    }


    override fun getItemCount(): Int = productos.size

    fun setData(products: List<Producto>?) {
        productos=products!!
        notifyDataSetChanged()

    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val imageLogo: ImageView = mView.logoPaquetes
        val nameProduct: TextView = mView.nameProduct
        val descriptionProduct: TextView = mView.descriptionProduct
        val priceProduct : TextView = mView.priceProduct


    }
   public fun setOnclicListener(listener: View.OnClickListener){
       mOnClickListener=listener
   }

    override fun onClick(v: View?) {
        if(mOnClickListener!=null){
            mOnClickListener.onClick(v)
        }
    }


}
