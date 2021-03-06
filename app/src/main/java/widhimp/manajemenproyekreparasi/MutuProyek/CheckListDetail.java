package widhimp.manajemenproyekreparasi.MutuProyek;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import widhimp.manajemenproyekreparasi.Activity.EditkapalActivity;
import widhimp.manajemenproyekreparasi.Activity.SigninActivity;
import widhimp.manajemenproyekreparasi.Adapter.CheckListAdapter;
import widhimp.manajemenproyekreparasi.Adapter.PenjadwalanAdapter;
import widhimp.manajemenproyekreparasi.Database.AmbilCheckList;
import widhimp.manajemenproyekreparasi.Database.AmbilPenjadwalan;
import widhimp.manajemenproyekreparasi.Database.Database;
import widhimp.manajemenproyekreparasi.Inbox;
import widhimp.manajemenproyekreparasi.KirimPesan;
import widhimp.manajemenproyekreparasi.Object.checklist;
import widhimp.manajemenproyekreparasi.Object.penjadwalan;
import widhimp.manajemenproyekreparasi.Object.repairlist;
import widhimp.manajemenproyekreparasi.R;

public class CheckListDetail extends AppCompatActivity {
    private String id_kapal, id_repairlist;
    private TextView nama;
    private Database database;
    private String username_user, password_user, kategori_user;
    private ProgressDialog loading;
    public final String URL_GET="http://188.166.240.88:8000/kapal/ambilrepairlist/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mutuproyek_checklist_detail);
        nama=(TextView)findViewById(R.id.nama_checklist);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("namarepairlist");
        nama.setText(name + " :");

        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        username_user = preferences.getString("username", null);
        password_user=preferences.getString("password",null);
        kategori_user=preferences.getString("kateg  ori",null);

        SharedPreferences preferences2 = getSharedPreferences("kapal", getApplicationContext().MODE_PRIVATE);
        id_kapal=preferences2.getString("idkapal",null);

        if(name.equals("Pengedokan"))
            id_repairlist="1";
        else if(name.equals("Pelayanan Umum"))
            id_repairlist="2";
        else if(name.equals("Pembersihan dan Pengecatan"))
            id_repairlist="3";
        else if(name.equals("Replating"))
            id_repairlist="4";
        else if(name.equals("Cathodic Protection"))
            id_repairlist="5";
        else if(name.equals("Sumbat Lunas, Almari Lambung, dan Katub"))
            id_repairlist="6";
        else if(name.equals("Valves"))
            id_repairlist="7";
        else if(name.equals("Peralatan Lambung dan Kelengkapannya"))
            id_repairlist="8";
        else if(name.equals("Deck Outfitting"))
            id_repairlist="9";
        else if(name.equals("Permesinan dan Kelengkapan"))
            id_repairlist="10";
        else if(name.equals("Navigation Equipment"))
            id_repairlist="11";
        else if(name.equals("Sistem Kelistrikan"))
            id_repairlist="12";
        else if(name.equals("Peralatan Keselamatan"))
            id_repairlist="13";
        else if(name.equals("Tangki - Tangki"))
            id_repairlist="14";
        else if(name.equals("Perpipaan"))
            id_repairlist="15";
        ambildata();
        loading = ProgressDialog.show(this,"Mengambil data...","Harap tunggu...",false,false);
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
        Intent intent=new Intent(getBaseContext(),CheckList.class);
        startActivity(intent);
    }

    public void ambildata(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        if(response.equals("Gagal")){

                        }
                        else
                            showdata(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username_user);
                params.put("password",password_user);
                params.put("id_repairlist", id_repairlist);
                params.put("id_kapal",id_kapal);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showdata(String json){
        loading.dismiss();
        AmbilCheckList ambilCheckList=new AmbilCheckList(json);
        ArrayList<checklist> listpenjadwalan=new ArrayList<checklist>();
        ListView listView=(ListView) findViewById(R.id.list_checklist);
        CheckListAdapter checkListAdapter=new CheckListAdapter(this,0,listpenjadwalan);
        ambilCheckList.ambilchecklist(checkListAdapter);
        listView.setAdapter(checkListAdapter);
    }

    private void showResponse(String respon){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(respon);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
