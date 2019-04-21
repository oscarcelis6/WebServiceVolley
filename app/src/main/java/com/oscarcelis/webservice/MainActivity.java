package com.oscarcelis.webservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;


public class MainActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {

    private EditText edtId;
    private EditText edtNombre;
    private EditText edtEmail;

    private RadioButton rbM;
    private RadioButton rbF;

    private AutoCompleteTextView edtCiudad;

    private Spinner edtProfesion;

    private TextView txtError;
    private TextView txtGenero;

    private RequestQueue myRequest;
    private JsonArrayRequest jsonArrayRequest;


    private Button btnGuardarDatos;

    public static final String URLWebServices = "http://192.168.0.15/webservice/insert.php?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId = findViewById(R.id.edtId);

        edtNombre = findViewById(R.id.edtNombre);
        //Pone en mayúsculas el texto del campo nombre
        edtNombre.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        edtEmail = findViewById(R.id.edtEmail);

        edtCiudad = findViewById(R.id.edtCiudad);
        edtProfesion = findViewById(R.id.edtProfesion);

        rbM = findViewById(R.id.radioButM);
        rbF = findViewById(R.id.radioButF);

        btnGuardarDatos = findViewById(R.id.btnGuardarRegistro);

        myRequest = Volley.newRequestQueue(this);

        txtError = findViewById(R.id.txtErrror);
        txtGenero = findViewById(R.id.genero);

        //Inicia Autocomplete TextView
        ArrayAdapter<CharSequence> adaptadorCiudades = ArrayAdapter.
                createFromResource(this, R.array.ciudades,
                    android.R.layout.simple_list_item_1);

        edtCiudad.setAdapter(adaptadorCiudades);
        //Finaliza Autocomplete TextView

        //Inicia Spinner profesiones
        ArrayAdapter<CharSequence> adaptadorProfesiones = ArrayAdapter.
                createFromResource(this, R.array.profesiones,
                        android.R.layout.simple_list_item_1);

        edtProfesion.setAdapter(adaptadorProfesiones);
        //Finaliza Spinner profesiones


        rbM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtGenero.setText(rbM.getText().toString());
            }
        });

        rbF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtGenero.setText(rbF.getText().toString());
            }
        });

        btnGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarWebService();
            }
        });

    }

    private void iniciarWebService() {

        String rutaUrl = URLWebServices +
                "dni="+edtId.getText().toString()+
                "&nombre="+edtNombre.getText().toString()+
                "&sexo="+ txtGenero.getText().toString()+
                "&email="+edtEmail.getText().toString()+
                "&ciudad="+edtCiudad.getText().toString()+
                "&profesion="+edtProfesion.getSelectedItem().toString();

        rutaUrl = rutaUrl.replace(" ", "%20");

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,rutaUrl,null,this,this);
        myRequest.add(jsonArrayRequest);
    }


    @Override
    public void onResponse(JSONArray response) {
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        edtId.setText("");
        edtNombre.setText("");
        txtGenero.setText("");
        edtEmail.setText("");
        edtCiudad.setText("");

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        txtError.setText(error.toString());
        Log.d("ULTIMO_ERROR: ", error.toString());

    }


}