package co.recargas.sis.ui.paquetes.avantel

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
class FragmentTipoPaqueteAvantel : Fragment() {
    private var todoIAvantel:CardView?=null
    private var vozAvantel:CardView?=null
    private var internetAva:CardView?=null
    private var whatsappAvan:CardView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_tipo_paquete_avantel, container, false)
        todoIAvantel=view.findViewById(R.id.todoIAvantel)
        vozAvantel=view.findViewById(R.id.vozAvantel)
        internetAva=view.findViewById(R.id.internetAva)
        whatsappAvan=view.findViewById(R.id.whatsappAvan)

        todoIAvantel?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesAvantel::class.java)
            intent.putExtra("avantel","todoInc")
            startActivity(intent)
        }
        vozAvantel?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesAvantel::class.java)
            intent.putExtra("avantel","voz")
            startActivity(intent)
        }
        internetAva?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesAvantel::class.java)
            intent.putExtra("avantel","internet")
            startActivity(intent)
        }
        whatsappAvan?.setOnClickListener {
            var intent:Intent= Intent(context,RealizarPaquetesAvantel::class.java)
            intent.putExtra("avantel","whatsapp")
            startActivity(intent)
        }


        return view
    }

}
