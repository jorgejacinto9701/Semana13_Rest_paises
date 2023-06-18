package com.example.semana13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.semana13.entity.Pais;
import com.example.semana13.service.ServicePais;
import com.example.semana13.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Spinner spnPais;
    ImageView idImageBandera;
    Button btnBuscar;
    TextView txtMoneda, txtCapital, txtIdioma, txtPoblacion;

    //adaptadores
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();

    //servicio
    ServicePais servicePais;

    //ArrayList de todos los paises
    List<Pais> lstPais = new ArrayList<Pais>();

    Context contex;

    MapView mMapView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contex = this;
        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,paises);
        spnPais =findViewById(R.id.spnPais);
        spnPais.setAdapter(adaptadorPais);

        idImageBandera = findViewById(R.id.idImageBandera);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtMoneda = findViewById(R.id.txtMoneda);
        txtCapital = findViewById(R.id.txtCapital);
        txtIdioma = findViewById(R.id.txtIdioma);
        txtPoblacion = findViewById(R.id.txtPoblacion);

        servicePais = ConnectionRest.getConnecion().create(ServicePais.class);
        mMapView = findViewById(R.id.idMapView);

        cargaPais();



        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posPais = spnPais.getSelectedItemPosition() - 1;
                Pais objPais = lstPais.get(posPais);

                txtCapital.setText("Capital : " + objPais.getCapital());
                txtPoblacion.setText("Población : " + String.valueOf(objPais.getPopulation()));
                if (objPais.getCurrencies() != null && objPais.getCurrencies().size()>0){
                    txtMoneda.setText("Moneda : " + objPais.getCurrencies().get(0).getCode() + " : " +
                                                    objPais.getCurrencies().get(0).getName());
                }
                if (objPais.getLanguages() != null && objPais.getLanguages().size()>0){
                    txtIdioma.setText("Idioma : " + objPais.getLanguages().get(0).getName() );
                }

                Glide.with(contex).load(objPais.getFlags().getPng()).into(idImageBandera);

            }
        });

        /*
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.idMapView);
        //mMapView.onCreate(mapViewBundle);

        //mMapView.getMapAsync(this);
        */
    }

    void cargaPais(){
        Call<List<Pais>> call = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()) {
                    lstPais = response.body();
                    paises.add("Seleccione un país");
                    for (Pais g : lstPais) {
                        paises.add(g.getName());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeAlert("Error  al servicio rest >>>>" + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {

            }
        });
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }*/
}