package co.recargas.sis.ui.paquetes.etb
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
import co.recargas.sis.ui.ProductoRecyclerViewAdapter
import java.lang.ClassCastException


class ProductFragmentetEtbLdi:Fragment() {
    private lateinit var productViewModel:ProductViewModel
    private lateinit var productoAdapter: ProductoRecyclerViewAdapter
    private var listener:DetallesPaquete?=null
    private var productos:List<Producto> = ArrayList()
    var columnCont=1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_producto_list,container,false)

        //se obtiene el ViewModel
        productViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)
        //se crea una instancia del adaptador
        productoAdapter= ProductoRecyclerViewAdapter()

        productoAdapter.setOnclicListener(View.OnClickListener {
            var producto=it.tag as Producto
            var nombre=producto.nombre
            var precio=producto.valor
            var descripcion=producto.observacion
            var id=producto.id

            listener?.obtenerDatosPaquetes(nombre!!,precio!!,descripcion!!,id)
        })
        if(view is RecyclerView){
            with(view){
                layoutManager=when{
                    columnCont<= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context,columnCont)
                }
                adapter=productoAdapter
            }
        }
        //observador de ViewModel
        productViewModel.getProductLdiEtb().observe(viewLifecycleOwner, Observer {
            productos=it
            productoAdapter.setData(productos)
        })
       return view
    }
    companion object{
        const val ARG_COLUMN_COUNT = "column-count"
        fun newInstance(columnCount:Int)=ProductFragmentetEtbLdi().apply {

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
            throw ClassCastException(context.toString()+" Debes Implementar la Interfaz")
        }
    }
}