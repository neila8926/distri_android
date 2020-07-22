package co.recargas.sis.ui.paquetes.virgin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import co.recargas.sis.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentTiposPaquetesVirgin : Fragment() {
    var antiplanes:CardView?=null
    var bolsasDato:CardView?=null
    var bolsasVoz:CardView?=null
    var whatsapp:CardView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_tipos_paquetes_virgin, container, false)

        antiplanes=view.findViewById(R.id.antiplanes)
        bolsasDato=view.findViewById(R.id.bolsasDato)
        bolsasVoz=view.findViewById(R.id.bolsasVoz)
        whatsapp=view.findViewById(R.id.whatsapp)

        antiplanes?.setOnClickListener {
            var intent=Intent(context, RealizarPaquetesVirgin::class.java)
            intent.putExtra("virgin","antiplan")
            startActivity(intent)
        }
        bolsasDato?.setOnClickListener {
            var intent=Intent(context,RealizarPaquetesVirgin::class.java)
            intent.putExtra("virgin","dato")
            startActivity(intent)
        }
        bolsasVoz?.setOnClickListener {
            var intent=Intent(context, RealizarPaquetesVirgin::class.java)
            intent.putExtra("virgin","voz")
            startActivity(intent)
        }
        whatsapp?.setOnClickListener {
            var intent=Intent(context, RealizarPaquetesVirgin::class.java)
            intent.putExtra("virgin","what")
            startActivity(intent)
        }

        return view
    }

}
