package app.androidhive.info.realm;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import app.androidhive.info.realm.activity.MainActivity;
import app.androidhive.info.realm.adapters.BooksAdapter;
import app.androidhive.info.realm.adapters.MascotaAdapter;
import app.androidhive.info.realm.adapters.RealmBooksAdapter;
import app.androidhive.info.realm.adapters.RealmMascotaAdapter;
import app.androidhive.info.realm.app.Prefs;
import app.androidhive.info.realm.model.Book;
import app.androidhive.info.realm.model.Mascota;
import app.androidhive.info.realm.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

public class MascotasActivity extends AppCompatActivity {

    private MascotaAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private FloatingActionButton fab;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);
        fab = (FloatingActionButton) findViewById(R.id.fabmascotas);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmascotas);
        setSupportActionBar(toolbar);
        setupRecycler();
        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }
        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getMascotas());

//        Toast.makeText(this, "Para eliminar los animales tener presionado", Toast.LENGTH_LONG).show();

        //add new item
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inflater = MascotasActivity.this.getLayoutInflater();
                final View content = inflater.inflate(R.layout.edit_item_mascota, null);
                final View content1 = inflater.inflate(R.layout.edit_item_mascota1, null);
                final View content2 = inflater.inflate(R.layout.edit_item_mascota2, null);

                final EditText editNombre = (EditText) content.findViewById(R.id.txtNombreMascota);
                final EditText editGenero = (EditText) content.findViewById(R.id.txtPesoMascota);
                final EditText editRaza = (EditText) content.findViewById(R.id.txtRazaMascota);


                final EditText fechaVacuna, inmunizacion, medicoVeterinario, proximaVacuna;

                fechaVacuna = (EditText) content1.findViewById(R.id.txtFechaVacuna);
                inmunizacion = (EditText) content1.findViewById(R.id.txtInmunizacion);
                medicoVeterinario = (EditText) content1.findViewById(R.id.txtMedicoVeterinario);
                proximaVacuna = (EditText) content1.findViewById(R.id.txtProximaVacuna);





                final EditText fechaDesparacitacion, tratamiento, proximaDesparacitacion;
                fechaDesparacitacion = (EditText) content2.findViewById(R.id.txtFechaDesparasitacion);
                tratamiento = (EditText) content2.findViewById(R.id.txtTratamiento);
                proximaDesparacitacion = (EditText) content2.findViewById(R.id.txtProximaDesparacitacion);





                final Mascota book = new Mascota();


                AlertDialog.Builder builder = new AlertDialog.Builder(MascotasActivity.this);
                builder.setView(content)
                        .setTitle("Añadir mascota")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                //book.setId(RealmController.getInstance().getBooks().size() + 1);

                                book.setNombre(editNombre.getText().toString());
                                book.setPeso(editGenero.getText().toString());
                                book.setRaza(editRaza.getText().toString());

                                book.setImageUrl("http://arcdn02.mundotkm.com/2015/08/perro-jugando.jpg");

                                if (editNombre.getText() == null || editGenero.getText().toString().equals("") || editRaza.getText().toString().equals(" ")) {
                                    Toast.makeText(MascotasActivity.this, "No se puede guardar, por que faltán datos.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Persist your data easily

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MascotasActivity.this);
                                    builder1.setView(content1).setTitle("Mascota").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            //fechaVacuna, inmunizacion, medicoVeterinario, proximaVacuna
                                            book.setFechaVacuna(fechaVacuna.getText().toString());
                                            book.setImunizacion(inmunizacion.getText().toString());
                                            book.setMedico_veterinario(medicoVeterinario.getText().toString());
                                            book.setProximaVacuna(proximaVacuna.getText().toString());

                                            AlertDialog.Builder builder2 = new AlertDialog.Builder(MascotasActivity.this);
                                            builder2.setView(content2).setTitle("").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    //fechaDesparacitacion, tratamiento, proximaDesparacitacion

                                                    book.setFechaDesparacitacion(fechaDesparacitacion.getText().toString());
                                                    book.setTratamiento(tratamiento.getText().toString());
                                                    book.setProximaDesparacitacion(proximaDesparacitacion.getText().toString());


                                                    realm.beginTransaction();
                                                    realm.copyToRealm(book);
                                                    realm.commitTransaction();
                                                    adapter.notifyDataSetChanged();
                                                    recycler.scrollToPosition(RealmController.getInstance().getBooks().size() - 1);
                                                }
                                            });
                                            AlertDialog dialog2 = builder2.create();
                                            dialog2.show();
                                        }
                                    });
                                    AlertDialog dialog1 = builder1.create();
                                    dialog1.show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }

    public void setRealmAdapter(RealmResults<Mascota> Mascota) {

        RealmMascotaAdapter realmAdapter = new RealmMascotaAdapter(this.getApplicationContext(), Mascota, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new MascotaAdapter(this);
        recycler.setAdapter(adapter);
    }

    private void setRealmData() {

        ArrayList<Mascota> mascotas = new ArrayList<>();

        Mascota book = new Mascota();



        book.setNombre("Rocky");
        book.setRaza("Pitbull");
        book.setImageUrl("http://arcdn02.mundotkm.com/2015/08/perro-jugando.jpg");
        mascotas.add(book);


        for (Mascota b : mascotas) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(b);
            realm.commitTransaction();
        }

        Prefs.with(this).setPreLoad(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }
}
