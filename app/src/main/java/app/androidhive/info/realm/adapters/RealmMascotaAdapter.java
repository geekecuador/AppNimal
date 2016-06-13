package app.androidhive.info.realm.adapters;

import android.content.Context;

import app.androidhive.info.realm.model.Book;
import app.androidhive.info.realm.model.Mascota;
import io.realm.RealmResults;

/**
 * Created by davidpulloquinga on 8/6/16.
 */

public class RealmMascotaAdapter extends RealmModelAdapter<Mascota> {
    public RealmMascotaAdapter(Context context, RealmResults<Mascota> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
