package co.recargas.sis.ui.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.recargas.sis.R
import co.recargas.sis.local.modelo.Producto


class ProductoFragmentClaroInt : Fragment() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductoRecyclerViewAdapter
    private var productos: List<Producto> = ArrayList()


    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
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
        productViewModel.getProductsClaroInt().observe(viewLifecycleOwner, Observer {
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
            ProductoFragmentClaroInt().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
