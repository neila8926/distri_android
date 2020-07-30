package co.recargas.sis.ui.paquetes.claro

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

class ProductFragmentClaroReventa : Fragment() {

   private  lateinit var productViewModel: ProductViewModel
    private lateinit var produductAdapter: ProductoRecyclerViewAdapter
      var listener: DetallesPaquete?=null
    private  var productos:List<Producto> =ArrayList()

    private var columnCount = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_producto_list,container,false)
        //obtenemos el viewModel
        productViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)
        //se instancia el adaptador
        produductAdapter= ProductoRecyclerViewAdapter()


        produductAdapter.setOnclicListener(View.OnClickListener {
            var producto=it.tag as Producto

            var nombre=producto.nombre
            var precio=producto.valor
            var descripcion=producto.observacion
            var id=producto.id

            listener?.obtenerDatosPaquetes(nombre!!,precio!!,descripcion!!,id!!)
        })

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {

                    columnCount <= 1 -> LinearLayoutManager(context)

                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = produductAdapter
            }
        }

        //observer de los productos
        productViewModel.getProductsReventa().observe(viewLifecycleOwner, Observer {
            productos=it
            produductAdapter.setData(productos)
        })



        return view
        }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        fun newInstance(columnCount: Int)= ProductFragmentClaroReventa().apply{

            val args = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columnCount)
            }


        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener= context as DetallesPaquete
        }catch (e: ClassCastException){
            throw ClassCastException(context.toString()+"Debes Implementar la Interfaz")
        }
    }
}
