package app.androidhive.info.realm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import app.androidhive.info.realm.activity.MainActivity;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
//        GridView gridView = (GridView) findViewById(R.id.gridview);
//
//        gridView.setAdapter(new ImageAdapter(this));
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(GridActivity.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
//
//
//
//            }
//        });



        GridViewWithHeaderAndFooter gridView1 = (GridViewWithHeaderAndFooter) findViewById(R.id.gridview);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View headerView = layoutInflater.inflate(R.layout.test_header_view, null);
//        View footerView = layoutInflater.inflate(R.layout.test_footer_view, null);
        gridView1.addHeaderView(headerView);
//        gridView1.addFooterView(footerView);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(GridActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        Intent intent = new Intent(GridActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(GridActivity.this, MainActivity.class);
                        startActivity(intent1);

                    case 7:
                        Intent intent2 = new Intent(GridActivity.this, MascotasActivity.class);
                        startActivity(intent2);


                }




            }
        });
        gridView1.setAdapter(new ImageAdapter(this));
    }
}
