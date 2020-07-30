package co.recargas.sis.ui.paquetes.tigo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import co.recargas.sis.R
import co.recargas.sis.interfaces.DetallesPaquete
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.recargas.sis.local.modelo.Producto
import co.recargas.sis.ui.paquetes.products.ProductViewModel
import co.recargas.sis.ui.paquetes.products.ProductoRecyclerViewAdapter
import java.lang.ClassCastException

class ProductFragmentTigoCombo :  Fragment(){
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductoRecyclerViewAdapter
    private var listener: DetallesPaquete?=null
    private var productos: List<Producto> = ArrayList()
    private var columnCount = 1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_producto_list,container,false)
        //obteemos del ViewModel
        productViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)
        //instanaciamos el adaptador
        productAdapter=ProductoRecyclerViewAdapter()

        productAdapter.setOnclicListener(View.OnClickListener {
            var producto=it.tag as Producto
            var nombre= producto.nombre
            var precio=producto.valor
            var descripcion=producto.observacion
            var id=producto.id

            listener?.obtenerDatosPaquetes(nombre!!,precio!!,descripcion!!,id!!)

        })
        if(view is RecyclerView){
            with(view){
                 layoutManager = when{
                    columnCount <= 1 -> LinearLayoutManager(context)

                    else -> GridLayoutManager(context, columnCount)

                }
                adapter=productAdapter
            }
        }

        //observador del ViewModel
        productViewModel.getProductsCombo().observe(viewLifecycleOwner, Observer {
            productos=it
            productAdapter.setData(productos)

        })
        return view

    }
    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        fun newInstance(columsCount:Int)= ProductFragmentTigoCombo().apply {
            val args = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columsCount)
                }
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        super.onAttach(context)
        try{
            listener= context as DetallesPaquete
        }catch (e: ClassCastException){
            throw ClassCastException(context.toString()+" Debes Implementar la Interfaz")
        }
    }
}