package widhimp.manajemenproyekreparasi.BiayaProyek;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import widhimp.manajemenproyekreparasi.Activity.SigninActivity;
import widhimp.manajemenproyekreparasi.Adapter.BudgetProduksiAdapter;
import widhimp.manajemenproyekreparasi.Adapter.SdmgalangandetailAdapter;
import widhimp.manajemenproyekreparasi.Database.AmbilBudgetProduksi;
import widhimp.manajemenproyekreparasi.Database.AmbilSdmGalanganDetail;
import widhimp.manajemenproyekreparasi.Inbox;
import widhimp.manajemenproyekreparasi.KirimPesan;
import widhimp.manajemenproyekreparasi.Object.budgetproduksi;
import widhimp.manajemenproyekreparasi.Object.sdmgalangandetail;
import widhimp.manajemenproyekreparasi.R;
import widhimp.manajemenproyekreparasi.SdmProyek.SdmGalanganDetail;
import widhimp.manajemenproyekreparasi.SdmProyek.SdmSubcontractorDetail;

public class BudgetProduksi extends AppCompatActivity {
    private Button tambah;
    private String username_user, password_user, kategori_user,id_kapal;
    public final String URL_GET="http://188.166.240.88:8000/kapal/listbudget/";
    public final String URL_POST="http://188.166.240.88:8000/kapal/tambahsuntingbudget/";
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biayaproyek_budget);
        tambah=(Button) findViewById(R.id.tambah_biayaproyek_budget);
        tambah.setOnClickListener(operasi);
        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        username_user = preferences.getString("username", null);
        password_user=preferences.getString("password",null);
        kategori_user=preferences.getString("kategori",null);

        SharedPreferences preferences2 = getSharedPreferences("kapal", getApplicationContext().MODE_PRIVATE);
        id_kapal=preferences2.getString("idkapal",null);

        ambildata();
        loading = ProgressDialog.show(this,"Mengambil data...","Harap tunggu...",false,false);

//        if(kategori_user.equals("Galangan")){
//        }
//        else if(kategori_user.equals("PM")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
//        else if(kategori_user.equals("OS")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
//        else if(kategori_user.equals("Dept. Pengadaan")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
//        else if(kategori_user.equals("Dept. Produksi")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
//        else if(kategori_user.equals("Subcont")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
//        else if(kategori_user.equals("Class")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
//        else if(kategori_user.equals("QA/QC")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
//        else if(kategori_user.equals("Owner")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
//        else if(kategori_user.equals("Vendor")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
//        else if(kategori_user.equals("PPC")){
//            tambah.setVisibility(View.INVISIBLE);
//        }
    }

    View.OnClickListener operasi=new View.OnClickListener(){
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.tambah_biayaproyek_budget:
                    openTambah();break;
            }
        }
    };

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
        Intent intent=new Intent(getBaseContext(),BiayaProyek.class);
        startActivity(intent);
    }

    public void ambildata(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(BudgetProduksi.this, response, Toast.LENGTH_SHORT).show();
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
                params.put("id_kapal",id_kapal);
                params.put("username",username_user);
                params.put("password",password_user);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showdata(String json){
        loading.dismiss();
        AmbilBudgetProduksi ambilBudgetProduksi=new AmbilBudgetProduksi(json);
        ArrayList<budgetproduksi> list = new ArrayList<budgetproduksi>();
        ListView listView=(ListView) findViewById(R.id.list_biayaproyek_budget);
        BudgetProduksiAdapter budgetProduksiAdapter = new BudgetProduksiAdapter(this, 0, list);
        ambilBudgetProduksi.ambilbudgetproduksi(budgetProduksiAdapter);
        listView.setAdapter(budgetProduksiAdapter);
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

    public void openTambah(){
        LayoutInflater li = LayoutInflater.from(this);
        View input = li.inflate(R.layout.biayaproyek_budget_tambah,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        Button tambahbudget=(Button)input.findViewById(R.id.tambah_biayaproyek_budget_tambah);
        final EditText namabudget=(EditText)input.findViewById(R.id.nama_biayaproyek_budget_tambah);
        final EditText hargabudget=(EditText)input.findViewById(R.id.harga_biayaproyek_budget_tambah);
        tambahbudget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loading = ProgressDialog.show(view.getContext(),"Menyimpan data...","Harap tunggu...",false,false);
                String nama=namabudget.getText().toString();
                String harga=hargabudget.getText().toString();
                insert(nama,harga);
            }
        });
        dialog.setView(input);
        dialog.setCancelable(true);
        dialog.show();
    }

    public void insert(final String nama, final String harga){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        openUlang();
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
                params.put("id_kapal",id_kapal);
                params.put("nama_budget",nama);
                params.put("harga_budget",harga);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openUlang() {
        Intent intentTambah = new Intent(getBaseContext(), BudgetProduksi.class);
        startActivityForResult(intentTambah, 0);
    }
}
