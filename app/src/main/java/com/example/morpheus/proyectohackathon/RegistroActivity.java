package com.example.morpheus.proyectohackathon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNombre,edtapellidoPaterno,edtApellidoMaterno,edtCurp,edtTelefono,edtEmail,edtCalle,edtNumero,edtColonia;
    private TextView txtGenero,txtfechaNacimiento;
    private Button btnRegistro;


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
        btnRegistro = findViewById(R.id.btnRegistro);



        btnRegistro.setOnClickListener(this);
        txtGenero.setOnClickListener(this);
        txtfechaNacimiento.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnRegistro:
            break;

            case R.id.txtGenero:
                break;

            case R.id.txtfechaNacimiento:
                break;


        }
    }
    //PERMITE GENERAR EL JSON CON LOS DATOS DEL CLIENTE
    public String generarJSON(String nombre,String apellidoP,String apellidoM,String genero,String fecha,String curp,String calle,String numero,String telefono,
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
            jsonNacimiento.put("pais","México");
            jsonNacimiento.put("estado","Guanajuato");
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
            jsonDireccion.put("codigo","38931");

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

        return jsonGeneral.toString();
    }  //PERMJITE ARMAR EL JSON CON LOS DATOS QUE NECESITA LA API


}
