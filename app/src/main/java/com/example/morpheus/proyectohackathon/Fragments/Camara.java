package com.example.morpheus.proyectohackathon.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.morpheus.proyectohackathon.R;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Camara extends Fragment {

    private  String path;//Guarda la cadena de la imagen

    static String RUTA_MEMORIA = "/sdcard/";
    private static  final String CARPETA_IMAGEN = "imagenesBVA";//Carpeta donde se guardan las fotos
    private static  final String DIRECTORIO_IMAGEN = RUTA_MEMORIA + CARPETA_IMAGEN;// CARPETA_PRINCIPAl+ CARPETA_IMAGEN;//RUTA CARPETA DE DIRECTORIO
    File fileImagen;//Guarda la foto
    Bitmap bitmap;//Guarda la imagen transformada
    private String marca, modelo, tipoArticulo;
    Uri imageUri;
    boolean tomarFoto=false;

    private static final int COD_SELECCIONADA = 10;
    private static final int COD_FOTO = 20;

    EditText campoNombre ;
    Button botonRegistro, btnFoto;
    ImageView imgFoto;
    TextView txtMarca, txtModelo, txtTipo;
    ProgressDialog progressDialog;
    RequestQueue request;
    //Errores errores = new Errores();

    private  String idArchivoFoto;
    private  String actualizarFoto;
    private  String numeroRegistro;
    private  String nombreImagen;



  /*  public static Camara getIntance (String elemento, String actualizarFoto, String numeroRegistro, String nombreImagen, String marca, String modelo, String tipoArticulo){
        FragmentCamara fragment= new FragmentCamara();
        Bundle bundle= new Bundle();
        bundle.putString("ARTICULOID",elemento);
        bundle.putString("ACTUALIZARFOTO",actualizarFoto);
        bundle.putString("NUMEROREGISTRO",numeroRegistro);
        bundle.putString("NOMBREIMAGEN",nombreImagen);
        bundle.putString("MARCA", marca);
        bundle.putString("MODELO", modelo);
        bundle.putString("TIPOARTICULO", tipoArticulo);
        fragment.setArguments(bundle);
        return fragment;
    }

    //


    public Camara() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            idArchivoFoto= getArguments().getString("ARTICULOID");
            actualizarFoto= getArguments().getString("ACTUALIZARFOTO");
            numeroRegistro= getArguments().getString("NUMEROREGISTRO");
            nombreImagen= getArguments().getString("NOMBREIMAGEN");
            marca = getArguments().getString("MARCA");
            modelo = getArguments().getString("MODELO");
            tipoArticulo = getArguments().getString("TIPOARTICULO");
        }

    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_camara, container, false);

        /* asignacion de controles*/
        campoNombre = vista.findViewById(R.id.edtNombre);
        botonRegistro = vista.findViewById(R.id.btnRegistrar);
        btnFoto = vista.findViewById(R.id.btnFoto);
        imgFoto = vista.findViewById(R.id.imgFoto);
        txtMarca = vista.findViewById(R.id.txtMarcaC);
        txtModelo = vista.findViewById(R.id.txtModeloC);
        txtTipo = vista.findViewById(R.id.txtTipoC);
        txtMarca.setText(marca);
        txtModelo.setText(modelo);
        txtTipo.setText(tipoArticulo);
        setHasOptionsMenu(true);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        /*Escuchar cuando el boton registro es precionado hace un llamdo al metodo
         * subir imagen*/
        botonRegistro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                progressDialog.show();
                if ((campoNombre.getText().toString()).length() > 0 && bitmap!=null){
                    SubirImagen();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.cancelar);
                    builder.setTitle("Hacen falta datos");
                    builder.setMessage("Debe completar los campos requeridos");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }

            }
        });


        /*Escuchar si el boon para tomar foto es precionado
         * realiza llamado a metodo mostrarDialogoOpciones*/
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap == null ){
                    mostrarDialogoOpciones();
                }else{
                    bitmap = null;
                    mostrarDialogoOpciones();
                }
                tomarFoto=true;
            }
        });

        /*Inicializacion textView con el valor optenido en el fragment ListaArticulosFotos*/

        return vista;
    }

    /*muestra en la pantalla el menu de opciones del cual puede seleccionar tomar una foto o escojer alguna que se encuentre en el
     * dispositivo*/
    private  void mostrarDialogoOpciones(){
        final CharSequence[]opciones = {"Tomar Foto","Elegir de la galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Elige una Opción ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    abrirCamara();
                }else{
                    if (opciones[i].equals("Elegir de la galeria")){
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccionar"), COD_SELECCIONADA);
                    }else {
                        if (opciones[i].equals("Cancelar")) {



                        } else {
                            dialogInterface.dismiss();
                        }
                    }
                }
            }
        });
        builder.show();
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
                case COD_SELECCIONADA:
                    Uri miPath = data.getData();
                    imgFoto.setImageURI(miPath);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), miPath);
                        imgFoto.setImageBitmap(bitmap);
                        bitmap = RedimensionarImagen(bitmap, 300, 600);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case COD_FOTO:

                    /* NOTA AL GUARDAR LA FOTO EN UN URI EL onActivityResult DEVURLVE UN NULL POR LO QUE NO VA ENTRAR AL IF ...
                     * HAY QUE HACER UNA CONDICION PARA ESTE O NO MOSTRARA LA IMAGEN AUNQUE LA AYA GUARDADO YA EN LA EMORIA DLE TELEFONO
                     * CREO QUE NO ES NECESARIO EL switch PARA ESTO PERO DEBO CONSIDERAR EL BALOR DE CANCELAR PARA QUE NO CE CIERRE LA APP
                     * */
                    MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                }
                            });
                    try {
                        bitmap = BitmapFactory.decodeFile(path);
                        Glide.with(getContext())
                                .load(path)
                                .crossFade()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .thumbnail(0.5f)
                                .into(imgFoto);

                        // try {
                        bitmap = RedimensionarImagen(bitmap, 600, 800);
                    }catch (Exception e){
                        Toast.makeText(getContext(), "Error inesperado", Toast.LENGTH_SHORT).show();

                        Fragment nuevofragmento = new ArticuloFotosFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_admin, nuevofragmento);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        e.printStackTrace();
                    }



                    break;

                case RESULT_CANCELED:
                    Toast.makeText(getContext(), "¡No selecciono ninguna imagen!", Toast.LENGTH_LONG).show();
                    Fragment nuevofragmento = FragmentCamara.getIntance(idArchivoFoto ,actualizarFoto,numeroRegistro,nombreImagen, marca, modelo, tipoArticulo);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_admin, nuevofragmento);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

            }

        } else {

            Toast.makeText(getContext(), "¡No selecciono ninguna imagen!", Toast.LENGTH_LONG).show();
            Fragment nuevofragmento = FragmentCamara.getIntance(idArchivoFoto,actualizarFoto,numeroRegistro, nombreImagen, marca, modelo, tipoArticulo);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_admin, nuevofragmento);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    private void abrirCamara(){

        //Variables para comprobar si la carpeta ya existe encaso de no crearla.
        boolean carpetaCreda;
        File carperaImagenesATC;

        carperaImagenesATC=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        carpetaCreda = carperaImagenesATC.exists();

        //variable para verificar la esistencia de la carpeta que alamacenara las fotos en el dispositivo

        //Verificamos si la carpeta ya esta creada
        if(carpetaCreda==false){
            //Si aun no esta creada entra y crea la carpeta ademas de mandar el mensaje al usuario
            carpetaCreda = carperaImagenesATC.mkdirs();
        }

        //Antes de abrir la cara verifica una vez mas que la carpete aya sido creada  de lo contrario no realizara la toma de fotografias ni el guardado de esta
        if(carpetaCreda==true){


            //Estraemos el tiempo transcurrido para darle nombre a la imagen
            Long consecutivo= System.currentTimeMillis()/1000;
            //Lo guardaos en la variable nombre y le damos el formato deseado
            String nombre=consecutivo.toString()+".png";

            //Guaradamos la direccion del archivo
            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;//indicamos la ruta de almacenamiento

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


    public void CargarWebService () {

        /*Inicializacion de variable resquest*/
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String nombre = campoNombre.getText().toString() + ".png";
        nombre = nombre.replace(" ", "_");
        nombre = nombre.toLowerCase();
        nombre = eliminarAcentos(nombre);
        String consulta="";
        if(actualizarFoto=="1"){
            consulta = "UPDATE articulo_imagen SET url ='"+nombre+"', articulo_id='"+ idArchivoFoto+"' WHERE id="+ numeroRegistro+";";
        }else {
            consulta = "INSERT INTO articulo_imagen(url,articulo_id)VALUES('" + nombre + "'," + idArchivoFoto + ");";
        }

        consulta = consulta.replace(" ", "%20");
        String cadenaClaveCliente = "?host=" + HOST + "&db=" + DB_LOCAL + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        String url = SERVER + RUTA_GENERAL + "consultaGeneral.php" + cadenaClaveCliente;

        JsonArrayRequest requestInsert = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error en imagen", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(requestInsert);
    }


    public void CargarImagenServidor(){

        request = Volley.newRequestQueue(getContext());

        String url = SERVER+RUTA_GENERAL+"wsJSONRegistroMovil.php?";

        // Solicitar una respuesta de cadena de la URL proporcionada.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"¡Imagen Cargada",Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Datos Guardados", Toast.LENGTH_LONG).show();
                Fragment nuevofragmento = new ArticuloFotosFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_admin, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

                /*Mensaje de error al usuario*/
                //variable encargada de guardar el mensaje de error
                errores.listarerrores(volleyError,getContext());

            }
        }){
            /*Este metodo nos devuelve todos los valores dentro de un map*/
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String actualizarFotoBD = actualizarFoto;
                String nombre = campoNombre.getText().toString();
                nombre = nombre.replace(" ", "_");
                nombre = nombre.toLowerCase();
                nombre = eliminarAcentos(nombre);
                String borrarImagenBD = nombreImagen;
                String imagen = ConvertirImagenString(bitmap);

                /*Alimentamos el Map con los datos deseados*/

                Map<String,String> paramemetros = new HashMap<>();
                paramemetros.put("actualizarFoto",actualizarFotoBD);
                paramemetros.put("borrarImagenBD",borrarImagenBD);
                paramemetros.put("nombre",nombre);
                paramemetros.put("imagen",imagen);

                //Regresamos el Map con todos los parametros
                return paramemetros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(stringRequest);
    }


    // La siguiente funcion elimina los acentos del nombre asignado a la imagen
    public static String eliminarAcentos(String cadenaAcentos) {

        final String ORIGINAL = "ÁáÉéÍíÓóÚúÑñÜü";
        final String REEMPLAZO = "AaEeIiOoUuNnUu";

        if (cadenaAcentos == null) {
            return null;
        }
        char[] arrayCadena = cadenaAcentos.toCharArray();
        for (int indice = 0; indice < arrayCadena.length; indice++) {
            int pos = ORIGINAL.indexOf(arrayCadena[indice]);
            if (pos > -1) {
                arrayCadena[indice] = REEMPLAZO.charAt(pos);
            }
        }
        return new String(arrayCadena);
    }




    /*El metodo resive un parametro de tipo Bitmap el cual contiene la imagen selecionada por el usuario
     * despues es comprimido usando el metodo compress y formateda con la extencion indicada en este caso JPG
     * el numero 50 es el porcentaje que se desea comprimir la imagen despues de 40 la iagen subre cambios notorios se pixelea)
     * despues se convierte a formato Base64 y guardado en una variable string la cual es devuelta por el metodo.
     * */


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
     *
     */

    private Bitmap RedimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {
        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){

            float escalaAncho=Float.parseFloat("0.18382353");
            float escalaAlto=Float.parseFloat("0.3267974");

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }
    }

    /* Metodo utilizado por el boton registro realiza un llamado al metodo BarraProgreso
     *  envia un mensaje al usuario
     *  y por ultimo realiza el llamado al metodo CargarWebService el caul se encarga de enviar la imagen al servidor
     */

    public void SubirImagen(){
        CargarImagenServidor();
        CargarWebService();
    }



}