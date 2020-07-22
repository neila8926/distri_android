package co.recargas.sis.ui.paquetes.etb

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

import co.recargas.sis.R


class FragmentTiposPaqueteEtb : Fragment() {
    var comboEtb:CardView?=null
    var ldiEtb:CardView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_tipos_paquete_etb, container, false)
        comboEtb=view.findViewById(R.id.etbCombo)
        ldiEtb=view.findViewById(R.id.etbLdi )

        comboEtb?.setOnClickListener {
            var intent:Intent=Intent(context,RealizarPaquetesEtb::class.java)
            intent.putExtra("etb", "combo")
            startActivity(intent)
        }
        ldiEtb?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesEtb::class.java)
            intent.putExtra("etb","ldiE")
            startActivity(intent)
        }

        return view
    }

}
