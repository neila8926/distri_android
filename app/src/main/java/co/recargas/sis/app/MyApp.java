package co.recargas.sis.app;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    //Definir un atributo estatico que va a ser referenecia a la propia clase para que podamos obtenerla en el lugar donde
    //la necesitemos

    private static MyApp instance;

    //para poder recibir, nos permite recibir un objeto de este tipo, devuelve un objeto de tipo instance
    public static MyApp getInstance(){return instance;}
    //Meodo que devuelve el contexto, referencia al especio de memoria
    public static Context getContext(){return  instance;}

    @Override
    public void onCreate() {
        instance=this;
        super.onCreate();
    }
}
