package co.recargas.sis.ui.paquetes.claro

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
class TiposPaquetesClaro : Fragment() {

    var internet: CardView?=null
    var voz:CardView?=null
    var todoInc:CardView?=null
    var ldi:CardView?=null
    var reventa:CardView?=null
    var prepago:CardView?=null
    var apps:CardView?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var vista= inflater.inflate(R.layout.fragment_tipos_paquetes_claro, container, false)
       //Tarjeta tipo Paquetes
        internet=vista.findViewById(R.id.internet)
        voz=vista.findViewById(R.id.voz)
        todoInc=vista.findViewById(R.id.todoInc)
        ldi=vista.findViewById(R.id.btnLdi)
        reventa=vista.findViewById(R.id.reventa)
        apps=vista.findViewById(R.id.apps)
        prepago=vista.findViewById(R.id.btnPrepago)

        internet?.setOnClickListener {
            var intent:Intent= Intent(context, RealizarPaquetesClaro::class.java)
            intent.putExtra("tipo","internet")
            startActivity(intent)
         }
        voz?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesClaro::class.java)
            intent.putExtra("tipo","voz")
            startActivity(intent)
        }
        todoInc?.setOnClickListener {
            var intent:Intent=Intent(context,RealizarPaquetesClaro::class.java)
            intent.putExtra("tipo","todoInc")
            startActivity(intent)
        }
        ldi?.setOnClickListener {
            var intent:Intent=Intent(context,RealizarPaquetesClaro::class.java)
            intent.putExtra("tipo","ldi")
            startActivity(intent)
        }
        reventa?.setOnClickListener {
            var intent:Intent=Intent(context,RealizarPaquetesClaro::class.java)
            intent.putExtra("tipo","reventa")
            startActivity(intent)
        }
        apps?.setOnClickListener {
            var intent:Intent=Intent(context,RealizarPaquetesClaro::class.java)
            intent.putExtra("tipo","apps")
            startActivity(intent)
        }
        prepago?.setOnClickListener {
            var intent:Intent=Intent(context,RealizarPaquetesClaro::class.java)
            intent.putExtra("tipo","prepago")
            startActivity(intent)
        }

        return vista
    }

}
