package co.recargas.sis.ui.paquetes.claro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.recargas.sis.R
import co.recargas.sis.ui.paquetes.products.ProductViewModel
import co.recargas.sis.ui.paquetes.products.ProductoRecyclerViewAdapter

class ProductFragmentClaroReventa : Fragment() {

    lateinit var productViewModel: ProductViewModel
    lateinit var produductAdapter: ProductoRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TextView(activity).apply {
            setText(R.string.hello_blank_fragment)
        }
    }

}
