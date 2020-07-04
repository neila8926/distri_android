package co.recargas.sis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import co.recargas.sis.interfaces.FragmentCom
import co.recargas.sis.ui.TiposPaquetesClaro
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), FragmentCom {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        ).build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)

        Toast.makeText(this, " ffhgfhgfgh "+R.id.nav_host_fragment, Toast.LENGTH_LONG).show()



    }


   override fun paquetesClaro() {

       Toast.makeText(this,"Probando ando ",Toast.LENGTH_LONG).show()
       var intent: Intent= Intent(this,PaquetesActivity::class.java)
       intent.putExtra("paquete","claro")
       startActivity(intent)

   }

    override fun paquetesTigo() {
        Toast.makeText(this,"Probando ando ",Toast.LENGTH_LONG).show()
        var intent: Intent= Intent(this,PaquetesActivity::class.java)
        intent.putExtra("paquete","tigo")
        startActivity(intent)

    }




}