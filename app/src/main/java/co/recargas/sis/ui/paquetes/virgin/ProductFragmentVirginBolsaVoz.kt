package co.recargas.sis.ui.paquetes.virgin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.recargas.sis.R
import co.recargas.sis.interfaces.DetallesPaquete
import co.recargas.sis.local.modelo.Producto
import co.recargas.sis.ui.paquetes.products.ProductViewModel
import co.recargas.sis.ui.paquetes.products.ProductoRecyclerViewAdapter
import java.lang.ClassCastException

class ProductFragmentVirginBolsaVoz: Fragment() {
    lateinit var productViewModel:ProductViewModel
    lateinit var productoAdapter: ProductoRecyclerViewAdapter
    var listener: DetallesPaquete?=null
    var columCount=1
    var productos:List<Producto> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_producto_list,container,false)

        //obtenemos el ViewModel
        productViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)
        //instanciamos el adaptador
        productoAdapter= ProductoRecyclerViewAdapter()

        productoAdapter.setOnclicListener(View.OnClickListener {
            var paquete=it.tag as Producto
            var nombre=paquete.nombre
            var precio=paquete.valor
            var descripcion=paquete.observacion
            var id=paquete.id

            listener?.obtenerDatosPaquetes(nombre!!,precio!!,descripcion!!,id!!)
        })

        if(view is RecyclerView){
            with(view){
                layoutManager=when{
                    columCount<=1 -> LinearLayoutManager(context)
                    else->GridLayoutManager(context,columCount)
                }
                adapter=productoAdapter
            }
        }
        productViewModel.getProductVozVirgin().observe(viewLifecycleOwner, Observer {
            productos=it
            productoAdapter.setData(productos)

        })
        return view
    }
    companion object{
        const val ARG_COLUMN_COUNT = "column-count"
        fun newInstance(columnCount: Int)=ProductFragmentVirginBolsaVoz().apply {
            val args = Bundle().apply {
                putInt(ARG_COLUMN_COUNT,columnCount)
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener=context as DetallesPaquete
        }catch (e:ClassCastException){
            throw ClassCastException(context.toString()+ " Debes Implementar la Interfaz")
        }
    }
}