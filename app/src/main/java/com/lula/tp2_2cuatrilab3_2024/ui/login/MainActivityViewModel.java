package com.lula.tp2_2cuatrilab3_2024.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.lula.tp2_2cuatrilab3_2024.modelo.Usuario;
import com.lula.tp2_2cuatrilab3_2024.ui.registro.RegistroActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MainActivityViewModel extends AndroidViewModel {
    private Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void login(String mail, String contra) {
        File archivo = new File(getApplication().getFilesDir(), "usuarios.dat");

        try {
            //creo el nodo
            FileInputStream fis = new FileInputStream(archivo);
            //lo envuelvo en el buffer
            BufferedInputStream bis = new BufferedInputStream(fis);
            //lo envuelvo en el object
            ObjectInputStream ois = new ObjectInputStream(bis);

                try {
                    Usuario usu = (Usuario) ois.readObject();
                    String email = usu.getMail();
                    String pass = usu.getPass();
                    if(mail.equals(email) && contra.equals(pass))
                    {
                        Intent intent = new Intent(getApplication(), RegistroActivity.class);
                        //pongo esta bandera porque lanzo la activity desde un ViewModel
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplication(), "Email o Usuario incorrecto", Toast.LENGTH_LONG).show();
                    }
                } catch (EOFException eof) {
                    fis.close();
                } catch (ClassNotFoundException e) {
                    Toast.makeText(context, "No se encontró un objeto de esa clase", Toast.LENGTH_LONG).show();
                }
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "No se encontró el archivo", Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            Toast.makeText(context, "Error de entrada/salida", Toast.LENGTH_LONG).show();
            return;
        }
    }
}
