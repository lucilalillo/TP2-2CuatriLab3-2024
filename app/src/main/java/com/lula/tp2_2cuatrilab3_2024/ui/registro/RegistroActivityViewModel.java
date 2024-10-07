package com.lula.tp2_2cuatrilab3_2024.ui.registro;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lula.tp2_2cuatrilab3_2024.modelo.Usuario;

import java.io.*;

public class RegistroActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mUsuarios;
    private MutableLiveData<String> mNombre;
    private MutableLiveData<String> mApellido;
    private MutableLiveData<String> mMail;
    private MutableLiveData<String> mPass;
    private MutableLiveData<Long> mDni;
    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMUsuario(){
        if(mUsuarios == null)
        {
            mUsuarios = new MutableLiveData<>();
        }
        return mUsuarios;
    }

    public LiveData<String> getMNombre(){
        if(mNombre == null){
            mNombre = new MutableLiveData<>();
        }
        return mNombre;
    }

    public LiveData<String> getMApellido(){
        if(mApellido == null)
        {
            mApellido = new MutableLiveData<>();
        }
        return mApellido;
    }

    public LiveData<String> getMMail(){
        if(mMail == null)
        {
            mMail = new MutableLiveData<>();
        }
        return mMail;
    }

    public LiveData<Long> getMDni(){
        if( mDni == null)
        {
            mDni = new MutableLiveData<>();
        }
        return mDni;
    }

    public LiveData<String> getMPass(){
        if(mPass == null)
        {
            mPass = new MutableLiveData<>();
        }
        return mPass;
    }

    public void guardarUsuario(String nombre, String apellido, String mail, String pass, long dni){
        Usuario usu = new Usuario(nombre, apellido, mail, pass ,dni);
        File archivo = new File(getApplication().getFilesDir(), "usuarios.dat");
        if(archivo.length() == 0) {
            try {
                //creo el nodo
                FileOutputStream fos = new FileOutputStream(archivo, false);//si quiero q funcione en modo append, pongo true
                //lo envuelvo en un buffer
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                //lo envuelvo con el objectOutputstream
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                //metodo para escribir un objeto
                oos.writeObject(usu);
                //limpio el buffer
                bos.flush();
                //cierro el nodo
                fos.close();
                usu = null;

                Toast.makeText(getApplication(), "Usuario Guardado", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo FileNotFound", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
            }
        }
        else {
            try{
                //creo el nodo
                FileOutputStream fos = new FileOutputStream(archivo, false);//si quiero q funcione en modo append, pongo true
                //lo envuelvo en un buffer
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjetosPropios oos = new ObjetosPropios();
                //metodo para escribir un objeto
                oos.writeObject(usu);
                //limpio el buffer
                bos.flush();
                //cierro el nodo
                fos.close();
                usu = null;
                Toast.makeText(getApplication(), "Usuario Guardado", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo FileNotFound", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void recuperarUsuario()
    {
        StringBuilder sb = new StringBuilder();
        File archivo = new File(getApplication().getFilesDir(), "usuarios.dat");
        String nombre = "";
        String apellido = "";
        String mail = "";
        String pass = "";
        long dni = 0;
        try {
            //creo el nodo
            FileInputStream fis = new FileInputStream(archivo);
            //lo envulevo en el buffer
            BufferedInputStream bis = new BufferedInputStream(fis);
            //lo envuelvo en el object
            ObjectInputStream ois = new ObjectInputStream(bis);

            while(true)
            {
                try{
                    Usuario usu = (Usuario)ois.readObject();
                    nombre = usu.getNombre();
                    apellido = usu.getApellido();
                    mail = usu.getMail();
                    dni = usu.getDni();
                    pass = usu.getPass();
                    sb.append(nombre + " " + apellido + " " + mail + " " + dni + " "+ pass + "\n");
                } catch (EOFException eof) {
                    mUsuarios.setValue(sb.toString());
                    mNombre.setValue(nombre);
                    mApellido.setValue(apellido);
                    mMail.setValue(mail);
                    mPass.setValue(pass);
                    mDni.setValue(dni);
                    fis.close();
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplication(), "Error al acceder al archivo FileNotFound", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplication(), "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
        }
    }

    private class ObjetosPropios extends ObjectOutputStream {
        ObjetosPropios() throws IOException {
            super();
        }
        ObjetosPropios(OutputStream o) throws IOException{
            super(o);
        }
        public void writeStreamHeader() throws IOException{
            return;
        }
    }
}
