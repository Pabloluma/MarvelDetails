package com.pablo.trabajofingrado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BiometricCallback {
    EditText campoCorUsu;
    EditText campoPasswdUsu;
    Button boton,botonreghu,botonhu,inicio /*,recuperar*/;
    TextView tienes, noCuen, recuperar;
    ImageView imageGoogle;
    Spinner spinner;
    ArrayList<String> correo = new ArrayList<>();
    HashMap<String, String> diccionario = new HashMap<String, String>();
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    BiometricManager biometricManager;
    FirebaseAuth myauth;
    static int opcion = 0;
    MotionLayout motionLayout;
    DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        //getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        myauth = FirebaseAuth.getInstance();
        //instancia de los elementos de la vista
        boton = findViewById(R.id.botoncrear);
        campoCorUsu = findViewById(R.id.CampoCorr);
        campoPasswdUsu = findViewById(R.id.campoPasswd);
        botonreghu = findViewById(R.id.buttonregHuella);
        botonhu = findViewById(R.id.buttonhuella);
        tienes = findViewById(R.id.tienesCuen);
        imageGoogle = findViewById(R.id.imageButton4);
        inicio = findViewById(R.id.buttonini);
        recuperar = findViewById(R.id.buttonrecCon);
        noCuen = findViewById(R.id.notienesCuen);
        spinner = findViewById(R.id.spinner);
        motionLayout = findViewById(R.id.constraintLayout4);

        //Comprobamos si tiene conexión
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            falloConexion().show();
        }
        //Hacemos transición en al pulsar olvido contraseña
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if(currentId == R.id.end){
                        Intent intent = new Intent(getApplicationContext(), olvidoContrasenia.class);
                        startActivity(intent);
                        motionLayout.transitionToStart();
                    }
                } else {
                    motionLayout.transitionToStart();
                }
            }
            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Intent intent = new Intent(v.getContext(), login.class);
                    startActivity(intent);
                }else{
                    falloConexion().show();
                }

            }
        });
        seleccionarInicio();
    }

    public void seleccionarInicio(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Correo y Contraseña");
        lista.add("Biometricamente");
        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_dropdown_item_1line, lista);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String elemento = (String) spinner.getAdapter().getItem(position);
                if(spinner.getSelectedItem().toString().equalsIgnoreCase("Correo y Contraseña")){
                    botonreghu.setVisibility(View.INVISIBLE);
                    botonhu.setVisibility(View.INVISIBLE);
                    tienes.setVisibility(View.VISIBLE);
                    imageGoogle.setVisibility(View.VISIBLE);
                    inicio.setVisibility(View.VISIBLE);
                    recuperar.setVisibility(View.VISIBLE);
                    noCuen.setVisibility(View.VISIBLE);
                    campoCorUsu.setVisibility(View.VISIBLE);
                    campoPasswdUsu.setVisibility(View.VISIBLE);
                    boton.setVisibility(View.VISIBLE);
                }

                BiometricManager biometricManager  = BiometricManager.from(MainActivity.this);
                int auth = biometricManager.canAuthenticate();

                if(spinner.getSelectedItem().toString().equalsIgnoreCase("Biometricamente")){
                    if(auth == 0 || auth == 11){
                        tienes.setVisibility(View.INVISIBLE);
                        imageGoogle.setVisibility(View.INVISIBLE);
                        inicio.setVisibility(View.INVISIBLE);
                        recuperar.setVisibility(View.INVISIBLE);
                        noCuen.setVisibility(View.INVISIBLE);
                        campoCorUsu.setVisibility(View.INVISIBLE);
                        campoPasswdUsu.setVisibility(View.INVISIBLE);
                        boton.setVisibility(View.INVISIBLE);
                        botonreghu.setVisibility(View.VISIBLE);
                        botonhu.setVisibility(View.VISIBLE);
                    }else{
                        tienes.setVisibility(View.INVISIBLE);
                        imageGoogle.setVisibility(View.INVISIBLE);
                        inicio.setVisibility(View.INVISIBLE);
                        recuperar.setVisibility(View.INVISIBLE);
                        noCuen.setVisibility(View.INVISIBLE);
                        campoCorUsu.setVisibility(View.INVISIBLE);
                        campoPasswdUsu.setVisibility(View.INVISIBLE);
                        boton.setVisibility(View.INVISIBLE);
                        botonreghu.setVisibility(View.INVISIBLE);
                        botonhu.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botonhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcion = 0;
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Toast.makeText(MainActivity.this,"Has pulsado la huella de logueo",Toast.LENGTH_LONG).show();
                    new com.an.biometric.BiometricManager.BiometricBuilder(MainActivity.this)
                            .setTitle("Logueate")
                            .setSubtitle("Pon la Huella")
                            .setDescription("")
                            .setNegativeButtonText("Cancelar")
                            .build()
                            .authenticate(MainActivity.this);
                } else {
                    falloConexion().show();
                }
            }
        });
    }

    //Inicio de sesion con Google
    public void inicioGoogle(View v){
        opcion = 1;
        imageGoogle.setBackgroundDrawable(null);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this,gso);
            SigIn();
        } else {
            falloConexion().show();
        }
    }

    private void SigIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Tmgoogle();

            }catch (ApiException e){
                Toast.makeText(this,"Ha ocurrido un error",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void Tmgoogle() {
        Intent intent = new Intent(getApplicationContext(),temaspelisGoogle.class);
        intent.putExtra("opcion", opcion);
        startActivity(intent);
        finish();
    }

//Inicio de sesion correo/Contraseña
    public void inicioSesion(View v){
        opcion = 2;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String corUsu = campoCorUsu.getText().toString().trim();
            String passwdUsu = campoPasswdUsu.getText().toString().trim();
            if(corUsu.isEmpty() || passwdUsu.isEmpty()){
                Toast.makeText(MainActivity.this, "Primero tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
            }else{
                loginUser(corUsu,passwdUsu);
            }


            /*db = FirebaseDatabase.getInstance().getReference().child("Usuarios");
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for(DataSnapshot ds :dataSnapshot.getChildren()){
                            String mail = ds.child("email").getValue().toString();
                            String pass = ds.child("contrasenia").getValue().toString();
                            diccionario.put(mail,pass);
                            correo.add(mail);
                        }
                        System.out.println(diccionario);
                        String valor;
                        if(corUsu.isEmpty() || passwdUsu.isEmpty()){
                            Toast.makeText(MainActivity.this, "Primero tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
                        }else{
                        if(correo.contains(corUsu)){
                            valor = diccionario.get(corUsu);
                            if(passwdUsu.equals(valor)){
                                Intent intent = new Intent(getApplicationContext(), temaspelisGoogle.class);
                                intent.putExtra("correo",corUsu);
                                intent.putExtra("opcion", opcion);
                                startActivity(intent);
                                finish();
                                campoCorUsu.setText("");
                                campoPasswdUsu.setText("");
                            }else{
                                Toast.makeText(getApplicationContext(), "La contraseña no es la correcta", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "El correo o la contraseña no es correcto", Toast.LENGTH_SHORT).show();
                        }
                        if(diccionario.isEmpty()){
                            Toast.makeText(getApplicationContext(),"La base de datos está vacia\n Tienes que registrarte", Toast.LENGTH_LONG).show();
                        }
                        }

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/


        } else {
            falloConexion().show();
        }

    }

    private void loginUser(String corUsu, String passwdUsu) {
        myauth.signInWithEmailAndPassword(corUsu,passwdUsu).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), temaspelisGoogle.class);
                    intent.putExtra("correo",corUsu);
                    intent.putExtra("opcion", opcion);
                    startActivity(intent);
                    finish();
                    campoCorUsu.setText("");
                    campoPasswdUsu.setText("");
                }else{
                    Toast.makeText(getApplicationContext(), "El correo o la contraseña no es correcto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //Configurar Huella
    public void ajustes(View view){
        botonreghu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));


            }
        });
    }


    @Override
    public void onSdkVersionNotSupported() {

    }

    @Override
    public void onBiometricAuthenticationNotSupported() {

    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {

    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {

    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {

    }

    @Override
    public void onAuthenticationFailed() {

    }

    @Override
    public void onAuthenticationCancelled() {

    }
//Al autenticar correctamente la huella
    @Override
    public void onAuthenticationSuccessful() {
        Intent intent = new Intent(getApplicationContext(),temaspelisGoogle.class);
        intent.putExtra("opcion", opcion);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

    }



    //Comprobar si hay internet cada 5 segundos
   /* public void comprobarCadax(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                comprobarRed();//llamamos nuestro metodo
                handler.postDelayed(this,10000);//se ejecutara cada 10 segundos
            }
        },5000);

    }
    public void comprobarRed(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            Toast.makeText(this, "No hay internet", Toast.LENGTH_SHORT).show();
        }

    }*/
    public AlertDialog falloConexion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.layout_error_conexion_dialog,
                (ConstraintLayout)findViewById(R.id.successDialog));
        builder.setView(view);
        ((TextView)view.findViewById(R.id.textmensaje)).setText(R.string.textoAlerta);
        ((Button)view.findViewById(R.id.botonOK)).setText(R.string.OK);
        ((ImageView)view.findViewById(R.id.imagen)).setImageResource(R.drawable.baseline_error_24);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.botonOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        return alertDialog;

    }
}