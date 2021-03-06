package widhimp.manajemenproyekreparasi.RepairList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import widhimp.manajemenproyekreparasi.Activity.EditkapalActivity;
import widhimp.manajemenproyekreparasi.Activity.ListkapalActivity;
import widhimp.manajemenproyekreparasi.Activity.SigninActivity;
import widhimp.manajemenproyekreparasi.Adapter.KapalAdapter;
import widhimp.manajemenproyekreparasi.Adapter.RepairlistAdapter;
import widhimp.manajemenproyekreparasi.Database.Database;
import widhimp.manajemenproyekreparasi.Inbox;
import widhimp.manajemenproyekreparasi.KirimPesan;
import widhimp.manajemenproyekreparasi.Komunikasi.KomunikasiTambah;
import widhimp.manajemenproyekreparasi.Object.kapal;
import widhimp.manajemenproyekreparasi.Object.repairlist;
import widhimp.manajemenproyekreparasi.R;
import widhimp.manajemenproyekreparasi.Search;

public class RepairlistActivity extends AppCompatActivity {
    private Button pengedokanpelayanan, konstruksi, lambung, deck,
            permesinan, navigation, kelistrikan, keselamatan, tangki,
            perpipaan, pengedokan, pelayanan, pembersihan, replating,
            cathodic, sumbatlunas, valves;
    private ImageView search;
    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repairlist);
        pengedokanpelayanan=(Button) findViewById(R.id.pengedokanpelayanan_repairlist);
        konstruksi=(Button) findViewById(R.id.konstruksi_repairlist);
        lambung=(Button) findViewById(R.id.lambung_repairlist);
        deck=(Button) findViewById(R.id.deck_repairlist);
        permesinan=(Button) findViewById(R.id.permesinan_repairlist);
        navigation=(Button) findViewById(R.id.navigation_repairlist);
        kelistrikan=(Button) findViewById(R.id.listrik_repairlist);
        keselamatan=(Button) findViewById(R.id.keselamatan_repairlist);
        tangki=(Button) findViewById(R.id.tangki_repairlist);
        perpipaan=(Button) findViewById(R.id.perpipaan_repairlist);
        search=(ImageView) findViewById(R.id.search_repairlist);
        pengedokanpelayanan.setOnClickListener(operasi);
        konstruksi.setOnClickListener(operasi);
        lambung.setOnClickListener(operasi);
        deck.setOnClickListener(operasi);
        permesinan.setOnClickListener(operasi);
        navigation.setOnClickListener(operasi);
        kelistrikan.setOnClickListener(operasi);
        keselamatan.setOnClickListener(operasi);
        tangki.setOnClickListener(operasi);
        perpipaan.setOnClickListener(operasi);
        search.setOnClickListener(operasi);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutop,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.inbox:
                Intent intentinbox=new Intent(getBaseContext(),Inbox.class);
                startActivity(intentinbox);
                break;
            case R.id.logout:
                Intent intentlogout=new Intent(getBaseContext(),SigninActivity.class);
                startActivity(intentlogout);
                break;
            case R.id.kirimpesan:
                Intent intentpesan=new Intent(getBaseContext(),KirimPesan.class);
                startActivity(intentpesan);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getBaseContext(),RepairListMenu.class);
        startActivity(intent);
    }

    View.OnClickListener operasi=new View.OnClickListener(){
        @Override
        public void onClick(View view)
        {
            switch (view.getId()){
                case R.id.pengedokanpelayanan_repairlist: openPengedokanpelayanan();break;
                case R.id.konstruksi_repairlist: openKonstruksi();break;
                case R.id.lambung_repairlist: openLambung();break;
                case R.id.deck_repairlist: openDeck();break;
                case R.id.permesinan_repairlist: openPermesinan();break;
                case R.id.navigation_repairlist: openNavigation();break;
                case R.id.listrik_repairlist: openListrik();break;
                case R.id.keselamatan_repairlist: openKeselamatan();break;
                case R.id.tangki_repairlist: openTangki();break;
                case R.id.perpipaan_repairlist: openPerpipaan();break;
                case R.id.search_repairlist: openSearch();break;
            }

        }
    };
    public void openPengedokanpelayanan(){
        LayoutInflater li = LayoutInflater.from(this);
        View input = li.inflate(R.layout.repairlist_pengedokanpelayanan,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        pengedokan=(Button) input.findViewById(R.id.pengedokan_pengedokanpelayanan);
        pelayanan=(Button) input.findViewById(R.id.pelayanan_pengedokanpelayanan);
        pengedokan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openIntent("Pengedokan");
            }
        });
        pelayanan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openIntent("Pelayanan Umum");
            }
        });
        dialog.setView(input);
        dialog.setCancelable(true);
        dialog.show();
    }
    public void openKonstruksi(){
        LayoutInflater li = LayoutInflater.from(this);
        View input = li.inflate(R.layout.repairlist_konstruksi,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        pembersihan=(Button) input.findViewById(R.id.pembersihan_konstruksi);
        replating=(Button) input.findViewById(R.id.replating_konstruksi);
        cathodic=(Button) input.findViewById(R.id.cathodic_konstruksi);
        sumbatlunas=(Button) input.findViewById(R.id.sumbatlunas_konstruksi);
        valves=(Button) input.findViewById(R.id.valves_konstruksi);
        pembersihan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openIntent("Pembersihan dan Pengecatan");
            }
        });
        replating.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openIntent("Replating");
            }
        });
        cathodic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openIntent("Cathodic Protection");
            }
        });
        sumbatlunas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openIntent("Sumbat Lunas, Almari Lambung, dan Katub");
            }
        });
        valves.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openIntent("Valves");
            }
        });
        dialog.setView(input);
        dialog.setCancelable(true);
        dialog.show();
    }
    public void openLambung(){
        openIntent("Peralatan Lambung dan Kelengkapannya");
    }
    public void openDeck(){
        openIntent("Deck Outfitting");
    }
    public void openPermesinan(){
        openIntent("Permesinan dan Kelengkapan");
    }
    public void openNavigation(){
        openIntent("Navigation Equipment");
    }
    public void openListrik(){
        openIntent("Sistem Kelistrikan");
    }
    public void openKeselamatan(){
        openIntent("Peralatan Keselamatan");
    }
    public void openTangki(){
        openIntent("Tangki - Tangki");
    }
    public void openPerpipaan(){
        openIntent("Perpipaan");
    }
    public void openIntent(String nama){
        Bundle bundle = new Bundle();
        bundle.putString("namarepairlist", nama);
        Intent intent=new Intent(getBaseContext(),RepairListDetail.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,0);
    }

    void openSearch(){
        LayoutInflater li = LayoutInflater.from(this);
        View input = li.inflate(R.layout.search,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final EditText nama_cari=(EditText) input.findViewById(R.id.namacari_search);
        Button cari=(Button)input.findViewById(R.id.cari_search);
        cari.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bundle bundle = new Bundle();
                bundle.putString("cari", nama_cari.getText().toString());
                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("menu", "repairlist");
                editor.commit();
                Intent intent=new Intent(getBaseContext(),Search.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        dialog.setView(input);
        dialog.setCancelable(true);
        dialog.show();
    }
}
