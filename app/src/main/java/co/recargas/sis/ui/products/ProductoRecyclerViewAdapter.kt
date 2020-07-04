package co.recargas.sis.ui.products

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.recargas.sis.R
import co.recargas.sis.local.modelo.Producto





import kotlinx.android.synthetic.main.fragment_producto_claro_int.view.*


class ProductoRecyclerViewAdapter() : RecyclerView.Adapter<ProductoRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var productos : List<Producto> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Producto


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_producto_claro_int, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productos[position]
        holder.nameProduct.text = item.nombre
        holder.descriptionProduct.text = item.observacion
        holder.priceProduct.text = item.valor.toString()
        Log.i("INFO", " Prueba "+item.nombre)


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
}
