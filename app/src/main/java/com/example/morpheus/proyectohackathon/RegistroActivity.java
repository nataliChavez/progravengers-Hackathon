package com.example.morpheus.proyectohackathon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.morpheus.proyectohackathon.DAO.ClienteDAO;
import com.example.morpheus.proyectohackathon.DAO.DAO;
import com.example.morpheus.proyectohackathon.Fragments.Camara;
import com.example.morpheus.proyectohackathon.Resources.CrearProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNombre,edtapellidoPaterno,edtApellidoMaterno,edtCurp,edtTelefono,edtEmail,edtCalle,edtNumero,edtColonia;
    private TextView txtGenero,txtfechaNacimiento;
    private Button btnRegistro;

    private ClienteDAO clienteDAO = ClienteDAO.getInstance();

    //CREAR PROGRESSDIALOG
    private CrearProgressDialog progressDialog;
    private ProgressDialog dialogoProgreso;
    private String genero,nombreUsuario,Apaterno,Amaterno;
    private String fechaInicio;
    private int dia, mes, anio;
    //PARA OBTENER LA FECHA ACTUAL
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edtNombre = findViewById(R.id.edtNombre);
        edtapellidoPaterno = findViewById(R.id.edtapellidoPaterno);
        edtApellidoMaterno = findViewById(R.id.edtApellidoMaterno);
        edtCurp = findViewById(R.id.edtCurp);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtEmail = findViewById(R.id.edtEmail);
        edtCalle = findViewById(R.id.edtCalle);
        edtNumero = findViewById(R.id.edtNumero);
        edtColonia = findViewById(R.id.edtColonia);
        txtGenero = findViewById(R.id.txtGenero);
        txtfechaNacimiento = findViewById(R.id.txtfechaNacimiento);
        btnRegistro = findViewById(R.id.btnRegistrarCuenta);

        //INICIALIZAMOS EL PROGRESS DIALOG
        progressDialog = new CrearProgressDialog();

        //OBTENEMOS LOS VALORES DE LA FECHA DE HOY
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH) + 1;
        anio = calendar.get(Calendar.YEAR);

        btnRegistro.setOnClickListener(this);
        txtGenero.setOnClickListener(this);
        txtfechaNacimiento.setOnClickListener(this);

        genero = txtGenero.getText().toString();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegistrarCuenta:
                if (edtNombre.length() > 0 && edtApellidoMaterno.length()> 0 && edtapellidoPaterno.length() > 0 && edtEmail.length() > 0 &&
                        edtNombre.length() > 0 && edtColonia.length() > 0 ){
                    if (edtCurp.length() == 18 ){

                        if (edtTelefono.getText().length() == 10){
                            dialogoProgreso = progressDialog.CargarProgressDialog(RegistroActivity.this);
                            registrarCuenta(edtNombre.getText().toString(),edtapellidoPaterno.getText().toString(),edtApellidoMaterno.getText().toString(),"FEMALE","1995-08-23",
                                    edtCurp.getText().toString(),edtCalle.getText().toString(),edtNumero.getText().toString(),edtTelefono.getText().toString(),edtColonia.getText().toString(),edtEmail.getText().toString());

                        }else{
                            edtTelefono.setError("El teléfono no es valido");

                        }
                    }else{
                        edtCurp.setError("El curp no es valido");
                        
                    }
                }else{
                    Toast.makeText(this, "Hay campos vacios", Toast.LENGTH_SHORT).show();
                }

            break;

            case R.id.txtGenero:
                alert().show();
                break;

            case R.id.txtfechaNacimiento:
                DateInicial();
                break;


        }
    }

    //PERMITE HACER UN REGISTRO EN LA BD
    public void registrarCuenta(final String nombre, String paterno, String materno, String genero, String fecha, String curp, String calle, String numero, String telefono, String colonia, String correo){
        JSONObject jsonObject = generarJSON(nombre,paterno,materno,genero,fecha,curp,calle,numero,telefono,colonia,correo);
        clienteDAO.registrarCuenta(RegistroActivity.this, jsonObject, new DAO.OnResultadoConsulta<JSONObject>() {
            @Override
            public void consultaSuccess(JSONObject jsonObject) {
                dialogoProgreso.dismiss();
                JSONObject jsonResult;
                JSONObject jsonData;
                JSONObject jsonAccount;
                JSONArray jsonnumberFormats;
                String cuenta;
                String info;

                if (jsonObject != null){
                    try {
                        jsonResult = jsonObject.getJSONObject("result");
                        info = jsonResult.getString("info");
                        jsonData = jsonObject.getJSONObject("data");
                        jsonAccount = jsonData.getJSONObject("account");
                        jsonnumberFormats = jsonAccount.getJSONArray("numberFormats");
                        cuenta = jsonnumberFormats.getJSONObject(0).getString("number");

                        nombreUsuario = edtNombre.getText().toString();
                        Apaterno = edtapellidoPaterno.getText().toString();
                        Amaterno = edtApellidoMaterno.getText().toString();


                        edtNombre.setText("");
                        edtapellidoPaterno.setText("");
                        edtApellidoMaterno.setText("");
                        edtEmail.setText("");
                        edtCalle.setText("");
                        edtColonia.setText("");
                        edtNombre.setText("");
                        edtCurp.setText("");
                        edtTelefono.setText("");
                        edtNumero.setText("");

                        dialogoDespedida(info,cuenta).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegistroActivity.this, "Ocurrio un error inesperado. Intente de nuevo", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Ocurrio un error inesperado. Intente de nuevo", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void consultaFailed(String error, int codigo) {
                dialogoProgreso.dismiss();
                Toast.makeText(RegistroActivity.this, error, Toast.LENGTH_SHORT).show();


            }
        });

    }

    private AlertDialog dialogoDespedida(String info,String cuenta)
    {
        View vistaAlert = LayoutInflater.from(RegistroActivity.this).inflate(R.layout.item_alertdialog_terminar_registro, null);
        final Button btnAceptar = vistaAlert.findViewById(R.id.btnAceptar);
        TextView edtInfo = vistaAlert.findViewById(R.id.txtInfo);
        TextView edtCuenta = vistaAlert.findViewById(R.id.txtCuenta);
        edtInfo.setText(info);
        edtCuenta.setText("El numero de su cuenta bancaria es: "+cuenta);

        final AlertDialog.Builder  builder = new AlertDialog.Builder(RegistroActivity.this);
        builder.setCancelable(false);
        builder.setView(vistaAlert);

        final AlertDialog alertdialog;
        alertdialog = builder.create();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
                FragmentManager fragmentMang = getSupportFragmentManager();
                Fragment   fragmento =  Camara.getInstance("registro",nombreUsuario,Apaterno,Amaterno);
                fragmentMang.beginTransaction().replace(R.id.contentRegistro, fragmento).commit();
            }

        });

        return alertdialog;
    }

    //PERMITE GENERAR EL JSON CON LOS DATOS DEL CLIENTE
    public JSONObject generarJSON(String nombre,String apellidoP,String apellidoM,String genero,String fecha,String curp,String calle,String numero,String telefono,
                              String colonia,String correo){
        JSONObject jsonGeneral = new JSONObject();
        JSONObject jsonPersona = new JSONObject();
        JSONObject jsonNacimiento = new JSONObject();
        JSONObject jsonDocumento = new JSONObject();
        JSONObject jsonDireccion = new JSONObject();
        JSONObject jsonContacto = new JSONObject();

        try {
            jsonPersona.put("nombre",nombre);
            jsonPersona.put("paterno",apellidoP);
            jsonPersona.put("materno",apellidoM);
            jsonPersona.put("genero",genero);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonNacimiento.put("fecha",fecha);
            jsonNacimiento.put("pais","MEX");
            jsonNacimiento.put("estado","GU");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonDocumento.put("tipo","CURP");
            jsonDocumento.put("clave",curp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonDireccion.put("calle",calle);
            jsonDireccion.put("numero",numero);
            jsonDireccion.put("colonia",colonia);
            jsonDireccion.put("estado","GU");
            jsonDireccion.put("pais","MEX");
            jsonDireccion.put("CP","38931");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonContacto.put("numero",telefono);
            jsonContacto.put("correo",correo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonGeneral.put("persona",jsonPersona);
            jsonGeneral.put("nacimiento",jsonNacimiento);
            jsonGeneral.put("documento",jsonDocumento);
            jsonGeneral.put("direccion",jsonDireccion);
            jsonGeneral.put("contacto",jsonContacto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonGeneral;
    }

    private AlertDialog alert(){
        final String []opciones = getResources().getStringArray(R.array.opcionesGenero);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elija una opción:");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(opciones, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                genero = opciones[i];
            }
        });
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                txtGenero.setText(genero);

            }
        });


      return  builder.create();
    }

    //MUESTRA EL DATETIME PICKER DE LA FECHA INCIAL
    public void DateInicial() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int Day, Month, Year;
                Year = datePicker.getYear();
                Month = datePicker.getMonth() + 1;
                Day = datePicker.getDayOfMonth();
                fechaInicio = Year + "/" + Month + "/" + Day;
                txtfechaNacimiento.setText(String.format("%1$02d/%2$02d/%3$04d", Day, Month, Year));

                if (Day != 0 || Month != 0 || Year != 0) {
                    anio = Year;
                    mes = Month;
                    dia = Day;
                }
            }

        }, anio, mes - 1, dia);
        datePickerDialog.show();
        datePickerDialog.setCanceledOnTouchOutside(false);
    }




}
