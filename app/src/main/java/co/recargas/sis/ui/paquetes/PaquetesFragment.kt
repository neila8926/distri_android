package co.recargas.sis.ui.paquetes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import co.recargas.sis.R
import co.recargas.sis.interfaces.FragmentCom
import kotlinx.android.synthetic.main.fragment_paquetes.*
import java.lang.ClassCastException


class PaquetesFragment : Fragment() {
    lateinit  var btnClaroP: CardView
    lateinit var btnTigoP: CardView
    lateinit var btnVirgin:CardView
    lateinit var btnEtb:CardView
    lateinit var btnMovistar:CardView
    lateinit var btnAvantel:CardView
    lateinit var btnExito:CardView
    lateinit var btnKalley:CardView
    lateinit var btnWings:CardView



    var listener : FragmentCom?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_paquetes, container, false)


        //asociando variables de la clase con el layout
        btnClaroP=view.findViewById(R.id.btnClaroP)
        btnTigoP=view.findViewById(R.id.btnTigoP)
        btnVirgin=view.findViewById(R.id.btnVirgin)
        btnEtb=view.findViewById(R.id.btnEtb)
        btnMovistar=view.findViewById(R.id.btnMovistarP)
        btnWings=view.findViewById(R.id.btnWingsP)
        btnKalley=view.findViewById(R.id.btnKalleyP)
        btnAvantel=view.findViewById(R.id.btnAvantelP)
        btnExito=view.findViewById(R.id.btnExitoP)

        //Eventos en cada boton

        btnClaroP?.setOnClickListener {
            listener?.paquetesClaro()
        }
        btnTigoP?.setOnClickListener{
        listener?.paquetesTigo()
        }
        btnVirgin.setOnClickListener {
            listener?.paqueteVirgin()
        }
        btnMovistar.setOnClickListener {
            listener?.paqueteMovistar()
        }
        btnEtb.setOnClickListener {
            listener?.paqueteEtb()
        }
        btnAvantel.setOnClickListener {
            listener?.paqueteAvantel()
        }
        btnKalley.setOnClickListener {
            listener?.paqueteKalley()
        }
        btnWings?.setOnClickListener {
            listener?.paqueteWings()
        }
        btnExito?.setOnClickListener {
            listener?.paqueteExito()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener=context as FragmentCom
        }catch (e: ClassCastException)
        {throw ClassCastException(context.toString()+"Debes implemetar la Interfaz")}
    }


}
