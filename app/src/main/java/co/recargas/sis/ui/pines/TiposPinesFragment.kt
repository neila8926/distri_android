package co.recargas.sis.ui.pines

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import co.recargas.sis.R

class TiposPinesFragment:Fragment() {
    var netflix:CardView?=null
    var spotyfy:CardView?=null
    var imvu:CardView?=null
    var minecraft:CardView?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_pines,container,false)
        netflix=view.findViewById(R.id.btnNetflix)
        spotyfy=view.findViewById(R.id.btnSpotify)
        imvu=view.findViewById(R.id.btnImvu)
        minecraft=view.findViewById(R.id.btnMinecraft)
        var intent= Intent(context,
            RealizarPines::class.java)

        netflix?.setOnClickListener {
            intent.putExtra("pin","netflix")
            startActivity(intent)
        }
        spotyfy?.setOnClickListener {
            intent.putExtra("pin","spotify")
            startActivity(intent)
        }
        imvu?.setOnClickListener {
            intent.putExtra("pin","imvu")
            startActivity(intent)
        }
        minecraft?.setOnClickListener {
            intent.putExtra("pin","minecraft")
            startActivity(intent)
        }


        return view
    }
}