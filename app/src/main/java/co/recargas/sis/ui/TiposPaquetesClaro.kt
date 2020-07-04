package co.recargas.sis.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView

import co.recargas.sis.R
import co.recargas.sis.RealizarPaquetes

/**
 * A simple [Fragment] subclass.
 */
class TiposPaquetesClaro : Fragment() {

    var internet: CardView?=null
    var voz:CardView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var vista= inflater.inflate(R.layout.fragment_tipos_paquetes_claro, container, false)
        internet=vista.findViewById(R.id.internet)
        voz=vista.findViewById(R.id.voz)

        internet?.setOnClickListener {
            var intent:Intent= Intent(context, RealizarPaquetes::class.java)
            intent.putExtra("tipo","internet")
            startActivity(intent)
         }
        voz?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetes::class.java)
            intent.putExtra("tipo","voz")
            startActivity(intent)
        }

        return vista
    }

}
