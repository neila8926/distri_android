package co.recargas.sis.ui.paquetes.products

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.recargas.sis.R
import co.recargas.sis.local.modelo.Producto





import kotlinx.android.synthetic.main.fragment_producto.view.*


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
        holder.priceProduct.text = item.valor.toString()


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
        //val imageLogo: ImageView = mView.imageView_logo
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
