package app.androidhive.info.realm.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import app.androidhive.info.realm.app.Prefs;
import app.androidhive.info.realm.R;
import app.androidhive.info.realm.adapters.BooksAdapter;
import app.androidhive.info.realm.adapters.RealmBooksAdapter;
import app.androidhive.info.realm.model.Book;
import app.androidhive.info.realm.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private BooksAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private FloatingActionButton fab;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        setRealmAdapter(RealmController.with(this).getBooks());
//
//        Toast.makeText(this, "Para eliminar los animales tener presionado", Toast.LENGTH_LONG).show();

        //add new item
        final Book book = new Book();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inflater = MainActivity.this.getLayoutInflater();
                final View content = inflater.inflate(R.layout.edit_item, null);
                final View content1 = inflater.inflate(R.layout.edit_item1, null);
                final View content2 = inflater.inflate(R.layout.edit_item2, null);

                final EditText editID = (EditText) content.findViewById(R.id.txtPesoAlnacer);
                final EditText editNombre = (EditText) content.findViewById(R.id.txtNombre_item);
                final EditText editGenero = (EditText) content.findViewById(R.id.txtNombrePadre);
                final EditText editRaza = (EditText) content.findViewById(R.id.txtRazaPadre);
                final EditText editProposito = (EditText) content.findViewById(R.id.txtNombreMadre);
                final EditText editFechaNacimiento = (EditText) content.findViewById(R.id.txt_fecha_nacimiento);

                //Segundo Layout
                final EditText editPesoalnacer = (EditText) content1.findViewById(R.id.txtPesoAlnacer);
                final EditText editPesoaldeteste = (EditText) content1.findViewById(R.id.txtPesoalDeteste1);
                final EditText editNombredelPadre = (EditText) content1.findViewById(R.id.txtNombrePadre);
                final EditText editRazadelPAdre = (EditText) content1.findViewById(R.id.txtNombreMadre);
                final EditText editNombreMadre = (EditText) content1.findViewById(R.id.txtNombreMadre);
                final EditText editRazaMadre = (EditText) content1.findViewById(R.id.txtRazaMadre);


                //Tercer Layout

                final Spinner spinnerAlimentacion = (Spinner) content2.findViewById(R.id.spinnerAlimentacion);
                final EditText cantidadLibras, numeroOrdenos, precioLitro, numerodeCrias;

                cantidadLibras = (EditText) content2.findViewById(R.id.txtCantidadLibras);
                numeroOrdenos = (EditText) content2.findViewById(R.id.txtNumeroOrdenos);
                precioLitro = (EditText) content2.findViewById(R.id.txtPrecioLitro);
                numerodeCrias = (EditText) content2.findViewById(R.id.txtNumeroCrias);


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setView(content)
                        .setTitle("Añadir Ganado")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                //book.setId(RealmController.getInstance().getBooks().size() + 1);
                                int ID = Integer.parseInt(editID.getText().toString());
                                book.setId(ID);
                                book.setNombre(editNombre.getText().toString());
                                book.setGenero(editGenero.getText().toString());
                                book.setRaza(editRaza.getText().toString());
                                book.setProposito(editProposito.getText().toString());
                                book.setFecha_nacimiento(editFechaNacimiento.getText().toString());
                                book.setRaza(editRaza.getText().toString());
                                book.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/09/39/d6/0939d613b35685cc89d0150a6755dad3.jpg");

                                if (editNombre.getText() == null || editID.getText().toString().equals("") || editRaza.getText().toString().equals(" ")) {
                                    Toast.makeText(MainActivity.this, "No se puede guardar, por que faltán datos.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Persist your data easily


                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                    builder1.setView(content1)
                                            .setTitle("Añadir Ganado").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {


                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            book.setPeso_al_nacer(Double.parseDouble(editPesoalnacer.getText().toString()));
                                            book.setPeso_al_desteste(Double.parseDouble(editPesoaldeteste.getText().toString()));
                                            book.setNombre_padre(editNombredelPadre.getText().toString());
                                            book.setRaza_padre(editRazadelPAdre.getText().toString());
                                            book.setNombre_madre(editNombreMadre.getText().toString());
                                            book.setRaza_madre(editRazaMadre.getText().toString());
                                            System.out.println(editPesoalnacer.getText().toString());



                                            AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
                                            builder3.setView(content2).setTitle("Añadir Ganado").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    book.setAlimentacion(spinnerAlimentacion.getSelectedItem().toString());
                                                    book.setCantidadLibras(cantidadLibras.getText().toString());
                                                    book.setNumeroOrdenos(numeroOrdenos.getText().toString());
                                                    book.setPrecioLitro(precioLitro.getText().toString());
                                                    book.setNumerodeCrias(numerodeCrias.getText().toString());
                                                    realm.beginTransaction();
                                                    realm.copyToRealm(book);
                                                    realm.commitTransaction();
                                                    adapter.notifyDataSetChanged();

                                                    // scroll the recycler view to bottom
                                                    recycler.scrollToPosition(RealmController.getInstance().getBooks().size() - 1);

                                                }
                                            });

                                            AlertDialog alertita = builder3.create();
                                            alertita.show();

                                            //DATA

                                        }
                                    });

                                    AlertDialog alertDialog = builder1.create();
                                    alertDialog.show();


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

    public void setRealmAdapter(RealmResults<Book> books) {

        RealmBooksAdapter realmAdapter = new RealmBooksAdapter(this.getApplicationContext(), books, true);
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
        adapter = new BooksAdapter(this);
        recycler.setAdapter(adapter);
    }

    private void setRealmData() {

        ArrayList<Book> books = new ArrayList<>();

        Book book = new Book();
        book.setId(1);
        book.setNombre("Ganador");
        book.setRaza("Holestein");
        book.setImageUrl("http://bmeditores.mx/wp-content/uploads/2014/01/ganado-bovino.jpg");
        books.add(book);

        book = new Book();
        book.setId(2);
        book.setNombre("Pepito");
        book.setRaza("Holestein");
        book.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/c/cd/Behi_frisiarrak.jpg");
        books.add(book);

        for (Book b : books) {
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