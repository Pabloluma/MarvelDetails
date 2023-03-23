package com.pablo.trabajofingrado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pablo.trabajofingrado.CapAmerica.CA_item;
import com.pablo.trabajofingrado.Hulk.hulk_item;
import com.pablo.trabajofingrado.Spiderman.spiderman_item;
import com.pablo.trabajofingrado.Thor.Thor_item;
import com.pablo.trabajofingrado.ironman.IM_item;

import java.util.ArrayList;

public class temaspelisGoogle extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount account;

    MenuItem items;
    String name;
    String email;
    ListView lista;
    String nombre = "";
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference reference;
    DatabaseReference mybd;
    DatabaseReference db;

    String[] tituloGrup = new String[5];
    ArrayList<String> nombres = new ArrayList<>();

    //Al pulsar atras muestra un toast
    // Da una pantalla hacia atras el metodo super.onBackPressed();
   @Override
    public void onBackPressed() {
        Toast.makeText(this, "No se puede retroceder mas", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temaspelis_google);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            MainActivity mainActivity = new MainActivity();
            mainActivity.falloConexion().show();
        }
        lista = findViewById(R.id.lista);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            name = account.getDisplayName();
            email = account.getEmail();
        }
        db = FirebaseDatabase.getInstance().getReference("Usuarios");
        sacarNombrePersonajes();

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        Intent intent2 = getIntent();
        int opcion = intent2.getIntExtra("opcion", -1);
        if (opcion == 0){
            navigationView.getMenu().findItem(R.id.nombre).setTitle("Huella");
            navigationView.getMenu().findItem(R.id.nombre).setIcon(R.drawable.baseline_fingerprint_24);
        }else if (opcion == 1){
            navigationView.getMenu().findItem(R.id.nombre).setTitle(name);
            navigationView.getMenu().findItem(R.id.nombre).setIcon(R.drawable.iconogoogle);
        }else if(opcion == 2){
            Bundle bundle = getIntent().getExtras();
            String cor = bundle.getString("correo");
            Query query = db.orderByChild("email").equalTo(cor);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        nombre  = dataSnapshot.child("username").getValue().toString();
                        CharSequence charSequence = nombre;
                        navigationView.getMenu().findItem(R.id.nombre).setTitle(charSequence);
                        navigationView.getMenu().findItem(R.id.nombre).setIcon(R.drawable.baseline_account_circle_24);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        ActionBar a = getSupportActionBar();
        a.setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);
        a.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                0,0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nombre:
                        Intent intent2 = getIntent();
                        int opcion = intent2.getIntExtra("opcion", -1);
                        if(opcion == 2){
                            Intent in = new Intent(getApplicationContext(), account.class);
                            in.putExtra("usuario",nombre);
                            startActivity(in);
                        }
                        break;

                    case R.id.ajustes:
                        break;

                    case R.id.logout:
                        finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        gsc.signOut();
                        break;

                    default:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void sacarNombrePersonajes(){
        mybd = FirebaseDatabase.getInstance().getReference();
        mybd.child("Personajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot ds :dataSnapshot.getChildren()){
                        String nombre = ds.child("Nombre").getValue().toString();
                        temaspelisGoogle.this.nombres.add(nombre);
                    }
                    for (int i = 0; i < nombres.size(); i++) {
                        tituloGrup[i] = nombres.get(i);
                    }
                    int[] foto = {R.drawable.spiderman,R.drawable.hulk,R.drawable.capitan,R.drawable.thor,R.drawable.ironman};
                    CustomList adapter = new CustomList(temaspelisGoogle.this,tituloGrup,foto);
                    lista.setAdapter(adapter);
                    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            switch (position) {
                                case 0:
                                    startActivity(new Intent(getApplicationContext(), spiderman_item.class));
                                    break;
                                case 1:
                                    startActivity(new Intent(getApplicationContext(), hulk_item.class));
                                    break;
                                case 2:
                                    startActivity(new Intent(getApplicationContext(), CA_item.class));
                                    break;
                                case 3:
                                    startActivity(new Intent(getApplicationContext(), Thor_item.class));
                                    break;
                                case 4:
                                    startActivity(new Intent(getApplicationContext(), IM_item.class));
                                    break;
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout.isOpen()){
                    drawerLayout.closeDrawer(navigationView);
                }else{
                    drawerLayout.openDrawer(navigationView);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Crear Menu
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        item = findViewById(R.id.nombre);
        Intent intent2 = getIntent();
        int opcion = intent2.getIntExtra("opcion", -1);
        if (opcion == 0){
            menu.findItem(R.id.nombre).setTitle("Huella");
        }else if (opcion == 1){
            menu.findItem(R.id.nombre).setTitle(name);
        }else if(opcion == 2){
            Bundle bundle = getIntent().getExtras();
            String cor = bundle.getString("correo");
            Query query = db.orderByChild("email").equalTo(cor);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        nombre  = dataSnapshot.child("username").getValue().toString();
                        CharSequence charSequence = nombre;
                        menu.findItem(R.id.nombre).setTitle(charSequence);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/

////////////////////////////////////////////////
            /*DB admin = new DB(this, "Bases", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Bundle bundle = getIntent().getExtras();
            String str = bundle.getString("correo");
            Cursor cursor3 = bd.rawQuery("SELECT NombreUsuario FROM Usuarios WHERE Correo = '"+str +"'", null);
            while(cursor3.moveToNext()){
                nombre = cursor3.getString(0);
                CharSequence charSequence = nombre;
                menu.findItem(R.id.nombre).setTitle(charSequence);
            }
            cursor3.close();*/
//////////////////////////////////////////////////////////
       /* }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }*/
}