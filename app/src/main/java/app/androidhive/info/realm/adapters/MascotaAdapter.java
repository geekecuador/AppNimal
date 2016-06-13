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
import app.androidhive.info.realm.model.Mascota;
import app.androidhive.info.realm.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by davidpulloquinga on 8/6/16.
 */

public class MascotaAdapter extends RealmRecyclerViewAdapter<Mascota>  {


    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public MascotaAdapter(Context context) {
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascotas, parent, false);
        return new MascotaAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        realm = RealmController.getInstance().getRealm();

        // get the article
        final Mascota book = getItem(position);
        // cast the generic view holder to our specific one
        final MascotaAdapter.CardViewHolder holder = (MascotaAdapter.CardViewHolder) viewHolder;

        // set the title and the snippet

        beber(book, holder);

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

                RealmResults<Mascota> results = realm.where(Mascota.class).findAll();
                // Get the book title to show it in toast message
                Mascota b = results.get(position);
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

    }

    private void beber(Mascota book, CardViewHolder holder) {
        holder.textNombre.setText(book.getNombre());
        holder.textGenero.setText(book.getPeso());
        holder.textRaza.setText(book.getRaza());
    }

    @Override
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textNombre;
        public TextView textGenero;
        public TextView textRaza;
        public ImageView imageBackground;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_mascotas);
            textNombre = (TextView) itemView.findViewById(R.id.text_nombre_mascotas);
            textGenero = (TextView) itemView.findViewById(R.id.text_especie_mascotas);
            textRaza = (TextView) itemView.findViewById(R.id.text_raza_mascotas);
            imageBackground = (ImageView) itemView.findViewById(R.id.image_background_mascotas);
        }
    }
}
