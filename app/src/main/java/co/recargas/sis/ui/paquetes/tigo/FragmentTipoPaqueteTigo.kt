package co.recargas.sis.ui.paquetes.tigo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import co.recargas.sis.R
import co.recargas.sis.ui.paquetes.claro.RealizarPaquetesClaro

/**
 * A simple [Fragment] subclass.
 */
class FragmentTipoPaqueteTigo : Fragment() {
    lateinit var internet:CardView
    lateinit var minuto:CardView
    lateinit var combo:CardView
    lateinit var btnLdi: CardView
    lateinit var bolsaTigo: CardView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var vista = inflater.inflate(R.layout.fragment_tipo_paquete_tigo, container, false)
        internet=vista.findViewById(R.id.internet)
        minuto= vista.findViewById(R.id.minuto)
        combo=vista.findViewById(R.id.combo)
        btnLdi=vista.findViewById(R.id.btnLdi)
        bolsaTigo=vista.findViewById(R.id.bolsaTigo)

        combo.setOnClickListener {
            var intent=Intent(context,RealizarPaquetesTigo::class.java)
            intent.putExtra("tigo","combo")
            startActivity(intent)
        }
        internet.setOnClickListener {
            var intent=Intent(context,RealizarPaquetesTigo::class.java)
            intent.putExtra("tigo","internet")
            startActivity(intent)
        }
        minuto.setOnClickListener {
            var intent=Intent(context,RealizarPaquetesTigo::class.java)
            intent.putExtra("tigo","minutos")
            startActivity(intent)
        }
        bolsaTigo.setOnClickListener {
            var intent=Intent(context, RealizarPaquetesTigo::class.java)
            intent.putExtra("tigo","bolsa")
            startActivity(intent)
        }
        btnLdi.setOnClickListener {
            var intent=Intent(context,RealizarPaquetesTigo::class.java)
            intent.putExtra("tigo","ldi")
            startActivity(intent)
        }
        return vista
    }

}
