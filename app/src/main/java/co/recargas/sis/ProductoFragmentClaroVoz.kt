package co.recargas.sis

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.recargas.sis.local.modelo.Producto
import co.recargas.sis.ui.products.ProductViewModel
import co.recargas.sis.ui.products.ProductoRecyclerViewAdapter

/**
 * A simple [Fragment] subclass.
 */
class ProductoFragmentClaroVoz : Fragment() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductoRecyclerViewAdapter
    private var productos: List<Producto> = ArrayList()

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ProductoFragmentClaroVoz.ARG_COLUMN_COUNT)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_producto_list, container, false)

        //Obtenemos el ViewModel
        productViewModel= ViewModelProvider(this).get(ProductViewModel::class.java)


        //cuando se instanciara el adapter
        productAdapter= ProductoRecyclerViewAdapter()

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = productAdapter
            }
        }
        //Observer de los productos
        productViewModel.getProductsClaroVoz().observe(viewLifecycleOwner, Observer {
            productos= it
            Log.i("INFO", " Prueba "+productos)
            productAdapter.setData(productos)
        })
        return view
    }


    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            ProductoFragmentClaroVoz().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}
