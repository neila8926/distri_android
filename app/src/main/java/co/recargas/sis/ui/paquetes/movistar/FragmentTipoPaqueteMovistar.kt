package co.recargas.sis.ui.paquetes.movistar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

import co.recargas.sis.R
import kotlinx.android.synthetic.main.fragment_tipo_paquete_avantel.*


class FragmentTipoPaqueteMovistar : Fragment() {
    var todoMov:CardView?=null
    var movisVoz:CardView?=null
    var interMovis:CardView?=null
    var larDMov:CardView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view=inflater.inflate(R.layout.fragment_tipo_paquete_movistar, container, false)
        todoMov=view.findViewById(R.id.todoMov)
        movisVoz=view.findViewById(R.id.movisVoz)
        interMovis=view.findViewById(R.id.interMovis)
        larDMov=view.findViewById(R.id.larDMov)

        todoMov?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesMovistar::class.java)
            intent.putExtra("movistar","todo")
            startActivity(intent)
        }
        movisVoz?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesMovistar::class.java)
            intent.putExtra("movistar","voz")
            startActivity(intent)
        }
        interMovis?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesMovistar::class.java)
            intent.putExtra("movistar","internet")
            startActivity(intent)
        }
        larDMov?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesMovistar::class.java)
            intent.putExtra("movistar","ldi")
            startActivity(intent)
        }
        return view
    }

}
