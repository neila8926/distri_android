package co.recargas.sis.ui.paquetes.wings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import co.recargas.sis.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentTipoPaqueteWings : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tipo_paquete_wings, container, false)
    }

}
