package com.example.a53638138e.bicingbcn;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;

import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import java.util.ArrayList;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import static android.R.attr.fragment;
import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {

    private MapView map;
    MapController mapController ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = (MapView) findViewById(R.id.map);
        Resources resources = getResources();
        RefreshAsyncTask refreshAsyncTask = new RefreshAsyncTask(this,map,resources);
        refreshAsyncTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    ArrayList<EstacionesBici> estaciones;

    public class RefreshAsyncTask extends AsyncTask<Void, Void, Void >{

        Context context;
        MapView map;
        Resources resources;
        IMapController mapController;


        public RefreshAsyncTask (Context context, MapView map,Resources resources){
            this.context=context;
            this.map = map;
            this.resources = resources;

        }

        @Override
        protected Void doInBackground(Void... params) {
            estaciones = ApiBici.getBicing();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            initializeMap();

            mapController = map.getController();
                for (int i = 0; i < estaciones.size(); i++) {
                    Double latitud = estaciones.get(i).getLat();
                    Double longitud = estaciones.get(i).getLon();
                    String calle = estaciones.get(i).getStreetName();
                    int slots = estaciones.get(i).getSlots();
                    int bikes = estaciones.get(i).getBikes();
                    int total = bikes + slots;
                    int porcentaje = 0;
                    String tipo = estaciones.get(i).getType();
                    porcentaje = (100 * slots) / total;
                    GeoPoint estationpoint = new GeoPoint(latitud, longitud);
                    Marker startMaker = new Marker(map);
                    startMaker.setPosition(estationpoint);

                    Log.d("estacion",estaciones.get(i).getType());
                    if (tipo.equals("BIKE")) {
                        if (porcentaje == 0)
                            startMaker.setIcon(resources.getDrawable(R.drawable.biciok));
                        if (porcentaje > 0 && porcentaje <= 25)
                            startMaker.setIcon(resources.getDrawable(R.drawable.bicicasok));
                        if (porcentaje > 25 && porcentaje <= 50)
                            startMaker.setIcon(resources.getDrawable(R.drawable.bicimid));
                        if (porcentaje > 50 && porcentaje <= 75)
                            startMaker.setIcon(resources.getDrawable(R.drawable.bicicasno));
                        if (porcentaje > 75 && porcentaje <= 100)
                            startMaker.setIcon(resources.getDrawable(R.drawable.bicino));

                    }
                    if (tipo.equals("BIKE-ELECTRIC")) {
                        if (porcentaje == 0)
                            startMaker.setIcon(resources.getDrawable(R.drawable.motook));
                        if (porcentaje > 0 && porcentaje <= 25)
                            startMaker.setIcon(resources.getDrawable(R.drawable.motomidok));
                        if (porcentaje > 25 && porcentaje <= 50)
                            startMaker.setIcon(resources.getDrawable(R.drawable.midmoto));
                        if (porcentaje > 50 && porcentaje <= 75)
                            startMaker.setIcon(resources.getDrawable(R.drawable.casinomoto));
                        if (porcentaje > 75 && porcentaje <= 100)
                            startMaker.setIcon(resources.getDrawable(R.drawable.motono));

                    }
                    map.getOverlays().add(startMaker);
                }
               /* GeoPoint startPoint = new GeoPoint(41.38498,2.18417);
                setZoom(startPoint);*/
                map.invalidate();

            }
        }

        private void initializeMap() {
            map.setTileSource(TileSourceFactory.MAPNIK);
            map.setTilesScaledToDpi(true);
            map.setBuiltInZoomControls(true);
            map.setMultiTouchControls(true);
        }

        private void setZoom(GeoPoint startPoint) {
            //  Setteamos el zoom al mismo nivel y ajustamos la posición a un geopunto
            mapController.setZoom(10);
            mapController.setCenter(startPoint);
        }
    }
