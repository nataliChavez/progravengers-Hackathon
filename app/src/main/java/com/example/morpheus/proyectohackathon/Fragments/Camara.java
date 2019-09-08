package com.example.morpheus.proyectohackathon.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.android.volley.RequestQueue;
import com.example.morpheus.proyectohackathon.BuildConfig;
import com.example.morpheus.proyectohackathon.DAO.ClienteDAO;
import com.example.morpheus.proyectohackathon.DAO.DAO;
import com.example.morpheus.proyectohackathon.PantallaPrincipalActivity;
import com.example.morpheus.proyectohackathon.R;
import com.example.morpheus.proyectohackathon.RegistroActivity;
import com.example.morpheus.proyectohackathon.Resources.CrearProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


import static android.app.Activity.RESULT_OK;

public class Camara extends Fragment {

    private  String path;//Guarda la cadena de la imagen

    static String RUTA_MEMORIA = "/sdcard/";
    private String resultadoImagen = "";
    private static  final String CARPETA_IMAGEN = "imagenesBVA";//Carpeta donde se guardan las fotos
    private static  final String DIRECTORIO_IMAGEN = RUTA_MEMORIA + CARPETA_IMAGEN;// CARPETA_PRINCIPAl+ CARPETA_IMAGEN;//RUTA CARPETA DE DIRECTORIO
    File fileImagen;//Guarda la foto
    Bitmap bitmap;//Guarda la imagen transformada
    private String marca, modelo, tipoArticulo;
    Uri imageUri;
    boolean tomarFoto=false;

    String imagen_frontal, imagen_latera, nombre_Fontal, nombre_lateral;

    private final static String MY_PROVIDER = BuildConfig.APPLICATION_ID + ".providers.FileProvider";
    private static final int COD_SELECCIONADA = 10;
    private static final int COD_FOTO = 20;


    Button btnFoto;

    //CREAR PROGRESSDIALOG
    private CrearProgressDialog progressDialog;
    private ProgressDialog dialogoProgreso;

    File imagenesBVA;
    String nombreImagen, nombreclintes, apellido1, apellido2, tipoAccion = "login";

    boolean foto = true;

    ClienteDAO clienteDAO = new  ClienteDAO();

    String nombre,paterno,materno;

    //INSTANCIA DE LA CLASE
    public static Camara getInstance(String opcion,String nombre,String paterno,String materno)
    {
        Camara fragment = new Camara();
        Bundle bundle = new Bundle();
        bundle.putString("TITULO", opcion);
        bundle.putString("NOMBRE",nombre);
        bundle.putString("PATERNO",paterno);
        bundle.putString("MATERNO",materno);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            tipoAccion = getArguments().getString("TITULO");
            nombre = getArguments().getString("NOMBRE");
            paterno = getArguments().getString("PATERNO");
            materno = getArguments().getString("MATERNO");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_camara, container, false);

        /* asignacion de controles*/
        btnFoto = vista.findViewById(R.id.btnFoto);

        setHasOptionsMenu(true);


        //INICIALIZAMOS EL PROGRESS DIALOG
        progressDialog = new CrearProgressDialog();

        Toast.makeText(getContext(), "Tome una foto de frente", Toast.LENGTH_SHORT).show();
        abrirCamara("_frontal");


        /*Inicializacion textView con el valor optenido en el fragment ListaArticulosFotos*/

        return vista;
    }

    /*
     *Metodo encargado de resivir la respues del inten lanzado
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean condicionSelecion = requestCode==COD_SELECCIONADA && resultCode== RESULT_OK ,
                condicionFoto     = requestCode==COD_FOTO && resultCode== RESULT_OK ;

        if (condicionSelecion ||condicionFoto){
            switch (requestCode) {

                case COD_FOTO:

                    /*
                     + NOTA AL GUARDAR LA FOTO EN UN URI EL onActivityResult DEVURLVE UN NULL POR LO QUE NO VA ENTRAR AL IF ...
                     * HAY QUE HACER UNA CONDICION PARA ESTE O NO MOSTRARA LA IMAGEN AUNQUE LA AYA GUARDADO YA EN LA EMORIA DLE TELEFONO
                     * CREO QUE NO ES NECESARIO EL switch PARA ESTO PERO DEBO CONSIDERAR EL BALOR DE CANCELAR PARA QUE NO CE CIERRE LA APP
                     */

                    MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                }
                            });
                    try {

                        bitmap = BitmapFactory.decodeFile(path);
                        RedimensionarImagen(bitmap, 300, 400);


                    }catch (Exception e){
                        FragmentManager fragmentMang = getActivity().getSupportFragmentManager();
                        Fragment   fragmento = new Camara();
                        fragmentMang.beginTransaction().replace(R.id.contentPrincial, fragmento).commit();

                    }

                    break;


            }

        } else {

            Toast.makeText(getContext(), "¡No se puede iniciar sesion!", Toast.LENGTH_LONG).show();

        }
    }


    private void abrirCamara(String tipoFoto){

        //Variables para comprobar si la carpeta ya existe encaso de no crearla.
        boolean carpetaCreda;


        imagenesBVA=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        carpetaCreda = imagenesBVA.exists();

        //variable para verificar la esistencia de la carpeta que alamacenara las fotos en el dispositivo

        //Verificamos si la carpeta ya esta creada
        if(carpetaCreda==false){
            //Si aun no esta creada entra y crea la carpeta ademas de mandar el mensaje al usuario
            carpetaCreda = imagenesBVA.mkdirs();
        }

        //Antes de abrir la cara verifica una vez mas que la carpete aya sido creada  de lo contrario no realizara la toma de fotografias ni el guardado de esta
        if(carpetaCreda==true){
            //Estraemos el tiempo transcurrido para darle nombre a la imagen
            Long consecutivo= System.currentTimeMillis()/1000;
            //Lo guardaos en la variable nombre y le damos el formato deseado
             nombreImagen =consecutivo.toString()+tipoFoto+".png";


            //Guaradamos la direccion del archivo
            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombreImagen;//indicamos la ruta de almacenamiento

            Log.i("nombreImagen", nombreImagen);

            // Creamos un File nuevo y le asignamos la ruta y nombre de la imagen
            fileImagen=new File(path);
            //Invocamos un Inten para hacer el llamado de la Camara
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Utilisando el URI guardamos la imagen en la direccion con el nombre generado
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                String authorities =getContext().getPackageName()+".provider";
                imageUri= FileProvider.getUriForFile(getContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else{
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent,COD_FOTO);
        }else {

            Toast.makeText(getContext(),"No se pudo Crear la carpeta",Toast.LENGTH_SHORT).show();

        }

    }


    public void CargarImagenServidor(){
        dialogoProgreso = progressDialog.CargarProgressDialog(getContext());
        clienteDAO.iniciarSesion(getContext(), imagen_frontal, imagen_latera, nombre_Fontal, nombre_lateral, new DAO.OnResultadoConsulta<JSONObject>() {
            @Override
            public void consultaSuccess(JSONObject jsonObject) {
                dialogoProgreso.dismiss();
                int codigo ;
                JSONObject jsonData;
                String nombre,mensaje;
                try {

                    codigo = jsonObject.getInt("codigo");

                    if(codigo == 0){

                        jsonData = jsonObject.getJSONObject("data");
                        nombre = jsonData.getString("nombre");

                        Intent intent = new Intent(getActivity(), PantallaPrincipalActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("NOMBRE",nombre);
                        startActivity(intent,bundle);

                    }else{
                        mensaje = jsonObject.getString("mensaje");
                        dialogoDespedida("No es posible iniciar sesion. "+mensaje).show();
                      //  Toast.makeText(getContext(), "No es posible iniciar sesion. "+mensaje, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {

                    Toast.makeText(getContext(), "Eror Inesperado", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void consultaFailed(String error, int codigo) {
                dialogoProgreso.dismiss();
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

            }
        });




       /* request = Volley.newRequestQueue(getContext());

        String url = "http://18.223.136.251:15000/aws/isperson";

        // Solicitar una respuesta de cadena de la URL proporcionada.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.i("ResouestaServe", response);

                if(foto){
                    abrirCamara("_lateral");

                    foto = false;
                }else {

                    Toast.makeText(getContext(), "¡Imagen Cargada", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "Datos Guardados", Toast.LENGTH_LONG).show();

                    Fragment nuevofragmento = new Camara();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentPrincial, nuevofragmento);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    foto = true;
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

                /*Mensaje de error al usuario
                //variable encargada de guardar el mensaje de error
                Toast.makeText(getContext(), "Chafio esto " + volleyError.networkResponse, Toast.LENGTH_SHORT).show();

            }
        }){
            /*Este metodo nos devuelve todos los valores dentro de un map
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen = ConvertirImagenString(bitmapImagen);



                /*Alimentamos el Map con los datos deseados
                * nombre de la foto
                * la foto
                * y bajar la resoolucion
                *

                Map<String,String> paramemetros = new HashMap<>();
                paramemetros.put("nombre_imagen",nombreImagen);
                paramemetros.put("imagen",imagen);


               Log.i( "Map",nombreImagen);

                //Regresamos el Map con todos los parametros
                return paramemetros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(stringRequest);*/
    }
    /*El metodo resive un parametro de tipo Bitmap el cual contiene la imagen selecionada por el usuario
     * despues es comprimido usando el metodo compress y formateda con la extencion indicada en este caso JPG
     * el numero 50 es el porcentaje que se desea comprimir la imagen despues de 40 la iagen subre cambios notorios se pixelea)
     * despues se convierte a formato Base64 y guardado en una variable string la cual es devuelta por el metodo.
     * */

    private AlertDialog dialogoDespedida(String mensaje)
    {
        View vistaAlert = LayoutInflater.from(getContext()).inflate(R.layout.item_alertdialog_terminar_registro, null);
        final Button btnAceptar = vistaAlert.findViewById(R.id.btnAceptar);
        final TextView textMensajeIngresarCodigo = vistaAlert.findViewById(R.id.textMensajeIngresarCodigo);
        textMensajeIngresarCodigo.setText(mensaje);
        final ImageView imgError = vistaAlert.findViewById(R.id.imgError);
        imgError.setImageDrawable(getContext().getDrawable(R.drawable.ic_mensaje_error));
        TextView edtInfo = vistaAlert.findViewById(R.id.txtInfo);
        TextView edtCuenta = vistaAlert.findViewById(R.id.txtCuenta);
        edtCuenta.setVisibility(View.GONE);
        edtInfo.setVisibility(View.GONE);
        final AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(vistaAlert);

        final AlertDialog alertdialog;
        alertdialog = builder.create();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
            }

        });

        return alertdialog;
    }

    public String ConvertirImagenString(Bitmap bitmap){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,30,array);
        byte []imagenByte= array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);



        return imagenString;
    }


    /*
     * Este metodo re encarga de cambiar las dimensiones de la imagen contenida dentro de ImagenView
     * indicando las nuevas dimensiones que esta tendra.
     */

    private String RedimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        Matrix matrix=new Matrix();

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);



        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
               //  bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;

        }

        if(ancho>anchoNuevo || alto>altoNuevo){

            float escalaAncho=Float.parseFloat("0.18382353");
            float escalaAlto=Float.parseFloat("0.3267974");

            matrix.postScale(escalaAncho,escalaAlto);

            Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);


            if (foto){

                imagen_frontal = ConvertirImagenString(bitmap1);
                nombre_Fontal = nombreImagen;
                foto = false;
                abrirCamara("_lateral");
            }else {

                imagen_latera = ConvertirImagenString(bitmap1);
                nombre_lateral = nombreImagen;
                foto = true;

                if(tipoAccion == "login"){

                    CargarImagenServidor();

                }else{
                    registrarImagenes();

                }


            }


            return resultadoImagen;

        }else{

            return resultadoImagen;

        }
    }




    public void registrarImagenes(){
         dialogoProgreso = progressDialog.CargarProgressDialog(getContext());

        clienteDAO.RegistroImagenes(getContext(), imagen_frontal, imagen_latera, nombre_Fontal, nombre_lateral, nombre, paterno, materno, new DAO.OnResultadoConsulta<JSONObject>() {
            @Override
            public void consultaSuccess(JSONObject jsonObject) {
                if (jsonObject != null){
                    int codigo;

                    try {
                        codigo = jsonObject.getInt("codigo");
                        if (codigo == 0){

                            Toast.makeText(getContext(), "Registro completado con exito", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Ocurrio un problema al registgrar, Intente nuevamente", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Ocurrio un problema al registgrar, Intente nuevamente", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Ocurrio un problema al registgrar, Intente nuevamente", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void consultaFailed(String error, int codigo) {

            }
        });


    }





    public JSONObject JSonImaganes(String imagen1, String imagen2, String nombre1, String nombre2, String nombreCliente, String apellidoCliente1, String apellidoCliente2){


        JSONObject obj= null;

        try{

            obj = new JSONObject();
            obj.put("imagen1",imagen1);
            obj.put("imagen2",imagen2);
            obj.put("nombre1",nombre1);
            obj.put("nombre2",nombre2);
            obj.put("nombreCliente",nombreCliente);
            obj.put("apellidoCliente1",apellidoCliente1);
            obj.put("apellidoCliente2",apellidoCliente2);




        } catch (Exception e) {
            e.printStackTrace();
        }



        return obj;


    }



}