package widhimp.manajemenproyekreparasi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;
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
import widhimp.manajemenproyekreparasi.Adapter.InboxAdapter;
import widhimp.manajemenproyekreparasi.Adapter.RepairlistAdapter;
import widhimp.manajemenproyekreparasi.Database.AmbilInbox;
import widhimp.manajemenproyekreparasi.Database.AmbilRepairList;
import widhimp.manajemenproyekreparasi.Object.inbox;
import widhimp.manajemenproyekreparasi.Object.repairlist;

public class Inbox extends AppCompatActivity {
    private ProgressDialog loading;
    public final String URL_AMBILDATA="http://188.166.240.88:8000/kapal/listinbox/";
    private String username_user, password_user, kategori_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        username_user = preferences.getString("username", null);
        password_user=preferences.getString("password",null);
        kategori_user=preferences.getString("kategori",null);

        ambildata();
        loading = ProgressDialog.show(this,"Mengambil data...","Harap tunggu...",false,false);

    }

    public void ambildata(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AMBILDATA,
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
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showdata(String json){
        loading.dismiss();
        AmbilInbox ambilInbox= new AmbilInbox(json);
        ArrayList<inbox> listinbox = new ArrayList<inbox>();
        ListView listView=(ListView) findViewById(R.id.list_inbox);
        InboxAdapter inboxAdapter = new InboxAdapter(this, 0, listinbox);
        ambilInbox.ambilinbox(inboxAdapter);
        listView.setAdapter(inboxAdapter);
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
}
