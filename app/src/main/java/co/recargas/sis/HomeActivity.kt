package co.recargas.sis

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import co.recargas.sis.interfaces.FragmentCom
import co.recargas.sis.ui.paquetes.PaquetesActivity
import co.recargas.sis.ui.paquetes.exito.RealizarPaquetesExito
import co.recargas.sis.ui.paquetes.kalley.RealizarPaquetesKalley
import co.recargas.sis.ui.paquetes.wings.RealizarPaquetesWings
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), FragmentCom {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        ).build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)


    }


    override fun paquetesClaro() {

       Toast.makeText(this,"Probando ando ",Toast.LENGTH_LONG).show()
       var intent: Intent= Intent(this,
           PaquetesActivity::class.java)
       intent.putExtra("paquete","claro")
       startActivity(intent)

   }

    override fun paquetesTigo() {
        Toast.makeText(this,"Probando ando ",Toast.LENGTH_LONG).show()
        var intent: Intent= Intent(this,
            PaquetesActivity::class.java)
        intent.putExtra("paquete","tigo")
        startActivity(intent)

    }

    override fun paqueteVirgin() {
        Toast.makeText(this, "boton virgin", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, PaquetesActivity::class.java)
        intent.putExtra("paquete","virgin")
        startActivity(intent)

    }
    override fun paqueteEtb() {
        Toast.makeText(this, "boton etb", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, PaquetesActivity::class.java)
        intent.putExtra("paquete","etb")
        startActivity(intent)

    }

    override fun paqueteAvantel() {
        Toast.makeText(this, "boton avantel", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, PaquetesActivity::class.java)
        intent.putExtra("paquete","avantel")
        startActivity(intent)

    }

    override fun paqueteMovistar() {
        Toast.makeText(this, "boton movistar", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, PaquetesActivity::class.java)
        intent.putExtra("paquete","movistar")
        startActivity(intent)

    }

    override fun paqueteExito() {
        Toast.makeText(this, "boton exito", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, RealizarPaquetesExito::class.java)
        intent.putExtra("paquete","exito")
        startActivity(intent)

    }

    override fun paqueteKalley() {
        Toast.makeText(this, "boton kalley", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, RealizarPaquetesKalley::class.java)
        intent.putExtra("paquete","kalley")
        startActivity(intent)

    }

    override fun paqueteWings() {
        Toast.makeText(this, "boton wigs", Toast.LENGTH_SHORT).show()
        var intent:Intent= Intent(this, RealizarPaquetesWings::class.java)
        intent.putExtra("paquete","wings")
        startActivity(intent)

    }






}