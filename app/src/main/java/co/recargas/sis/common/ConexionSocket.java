package co.recargas.sis.common;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ConexionSocket {
    static String response="Error de conexion";
    static Socket socket = null;

    public ConexionSocket() {
    }

    public String ClSocket(String pip) {

        try {

            socket = new Socket(Constantes.HOST,Constantes.PUERTO);
            //DataOutputStream datos_enviados= new DataOutputStream(socket.getOutputStream());
            Log.i("INFO", "ClSocket: "+Constantes.HOST + " "+pip);

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        out.println(pip);
        out.flush();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4024);
        byte[] buffer = new byte[4024];
        int bytesRead;
        InputStream in = socket.getInputStream();
        while ((bytesRead = in.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
            response = byteArrayOutputStream.toString();
            Log.i("socket","ss"+response);
        }
        out.close();
        in.close();
        socket.close();
    } catch (IOException e) {
        e.printStackTrace();
            Log.i("socket","ss"+response);
            return "Error de Conexión";
    }

        return response;
}
}





