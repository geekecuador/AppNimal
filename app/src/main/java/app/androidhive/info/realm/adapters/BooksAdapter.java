package app.androidhive.info.realm.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import app.androidhive.info.realm.R;
import app.androidhive.info.realm.app.Prefs;
import app.androidhive.info.realm.model.Book;
import app.androidhive.info.realm.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

public class BooksAdapter extends RealmRecyclerViewAdapter<Book> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public BooksAdapter(Context context) {

        this.context = context;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();

        // get the article
        final Book book = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        // set the title and the snippet

        holder.textTitle.setText(book.getNombre());
        holder.textAuthor.setText(book.getRaza());
        holder.textDescription.setText((Integer.toString((int) book.getId()) ));

        // load the background image
        if (book.getImageUrl() != null) {
            Glide.with(context)
                    .load(book.getImageUrl().replace("https", "http"))
                    .asBitmap()
                    .fitCenter()
                    .into(holder.imageBackground);
        }

        //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                RealmResults<Book> results = realm.where(Book.class).findAll();

                // Get the book title to show it in toast message
                Book b = results.get(position);
                String title = b.getNombre();

                // All changes to data must happen in a transaction
                realm.beginTransaction();

                // remove single match
                results.remove(position);
                realm.commitTransaction();

                if (results.size() == 0) {
                    Prefs.with(context).setPreLoad(false);
                }

                notifyDataSetChanged();

                Toast.makeText(context, title + " fue removido", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //update single match from realm
        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_item, null);
                final EditText editID = (EditText) content.findViewById(R.id.txtPesoAlnacer);
                final EditText editNombre = (EditText) content.findViewById(R.id.txtNombre_item);
                final EditText editGenero = (EditText) content.findViewById(R.id.txtNombrePadre);
                final EditText editRaza = (EditText) content.findViewById(R.id.txtRazaPadre);
                final EditText editProposito = (EditText) content.findViewById(R.id.txtNombreMadre);
                final EditText editFechaNacimiento = (EditText) content.findViewById(R.id.txt_fecha_nacimiento);



                final EditText editTitle = (EditText) content.findViewById(R.id.txtNombrePadre);
                final EditText editAuthor = (EditText) content.findViewById(R.id.txtRazaPadre);
                final EditText editThumbnail = (EditText) content.findViewById(R.id.txtNombreMadre);


                editID.setText(Integer.toString((int) book.getId()));
                editNombre.setText(book.getNombre());
                editGenero.setText(book.getGenero());
                editRaza.setText(book.getRaza());
                editProposito.setText(book.getProposito());
                editFechaNacimiento.setText(book.getFecha_nacimiento());


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Editar ganado")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmResults<Book> results = realm.where(Book.class).findAll();

                                realm.beginTransaction();
                                results.get(position).setNombre(editNombre.getText().toString());
                                results.get(position).setGenero(editGenero.getText().toString());
                                results.get(position).setRaza(editRaza.getText().toString());
                                results.get(position).setProposito(editProposito.getText().toString());
                                results.get(position).setFecha_nacimiento(editFechaNacimiento.getText().toString());
                                results.get(position).setRaza(editRaza.getText().toString());
                                results.get(position).setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/09/39/d6/0939d613b35685cc89d0150a6755dad3.jpg");

                                realm.commitTransaction();

                                notifyDataSetChanged();
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

    // return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textTitle;
        public TextView textAuthor;
        public TextView textDescription;
        public ImageView imageBackground;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_books);
            textTitle = (TextView) itemView.findViewById(R.id.text_books_title);
            textAuthor = (TextView) itemView.findViewById(R.id.text_books_author);
            textDescription = (TextView) itemView.findViewById(R.id.text_books_description);
            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
        }
    }
}
