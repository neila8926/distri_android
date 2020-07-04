package co.recargas.sis

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import co.recargas.sis.interfaces.FragmentCom
import java.lang.ClassCastException


class PaquetesFragment : Fragment() {
   lateinit  var btnClaroP: CardView
    lateinit var btnTigoP: CardView

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



        btnClaroP=view.findViewById(R.id.btnClaroP)
        btnTigoP=view.findViewById(R.id.btnTigoP)

        btnClaroP?.setOnClickListener {
            listener?.paquetesClaro()
        }
        btnTigoP?.setOnClickListener{
        listener?.paquetesTigo()
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
