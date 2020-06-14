package co.recargas.sis

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.recargas.sis.common.ConexionSocket
import kotlinx.android.synthetic.main.activity_main.*
import java.net.Socket

class MainActivity : AppCompatActivity() {
    var contador=0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        idIngresar.setOnClickListener{view->
            contador++
            Toast.makeText(this,"Probando el contador: $contador", Toast.LENGTH_LONG ).show()

            Ingresar().execute()





        }

    }
}
class Ingresar: AsyncTask<Void,Void,Void>(){

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: Void?): Void? {
       if(params.isNotEmpty()){

       }
        try {
            val hola = ConexionSocket().ClSocket("HOla")
            Log.i("sdf", "Respeuesta " + hola)
        }catch ( ex: Exception){ex.printStackTrace()}

        return null




    }

    override fun onPreExecute() {
        super.onPreExecute()
    }
}

