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
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
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

public class MainActivity extends AppCompatActivity {

    private MapView map;
    private IMapController mapController;
    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;

    private ArrayAdapter<EstacionesBici> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = (MapView) findViewById(R.id.map);

        descargarEstaciones();


    }
    @Override
    public void onStart() {
        descargarEstaciones();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Esto descargara de la api de internet
    private void descargarEstaciones() {

        RefreshAsyncTask refreshAsyncTask = new RefreshAsyncTask();
        refreshAsyncTask.execute();
    }

    class RefreshAsyncTask extends AsyncTask<Void, Void, ArrayList<EstacionesBici>> {

        Context context;
        Resources resources;
        MyLocationNewOverlay myLocationOverlay;
        MinimapOverlay mMinimapOverlay;
        ScaleBarOverlay mScaleBarOverlay;
        CompassOverlay mCompassOverlay;
        IMapController mapController;;

        MapView map;

        @Override
        protected ArrayList<EstacionesBici> doInBackground(Void... voids) {

            ApiBici api = new ApiBici();
            ArrayList<EstacionesBici> estaciones = null;
            estaciones = api.getBicing();
            return estaciones;
        }


        @Override
        protected void onPostExecute(ArrayList<EstacionesBici> estaciones) {
            super.onPostExecute(estaciones);

            mapController = map.getController();
            initializeMap();


            for (int i = 0; i <estaciones.size() ; i++) {
                Double latitud=estaciones.get(i).getLat();
                Double longitud=estaciones.get(i).getLon();
                String calle=estaciones.get(i).getStreetName();
                int number = estaciones.get(i).getStreetNumber();
                int slots=estaciones.get(i).getSlots();
                int bikes=estaciones.get(i).getBikes();
                int porcentaje=disponible(slots,bikes);
                GeoPoint estationpoint = new GeoPoint(latitud, longitud);
                Marker startMaker = new Marker(map);
                startMaker.setPosition(estationpoint);
                startMaker.setTitle(calle+" nº "+number);

                if (estaciones.get(i).getType().equals("BIKE")) {
                    if (porcentaje == 0) startMaker.setIcon(resources.getDrawable(R.drawable.ic_action_name));
                    if (porcentaje > 0 && porcentaje <= 25)
                        startMaker.setIcon(resources.getDrawable(R.drawable.disponmedia));
                    if (porcentaje > 25 && porcentaje <= 50)
                        startMaker.setIcon(resources.getDrawable(R.drawable.disponmedia));
                    if (porcentaje > 50 && porcentaje <= 75)
                        startMaker.setIcon(resources.getDrawable(R.drawable.casino));
                    if (porcentaje > 75 && porcentaje <= 100)
                        startMaker.setIcon(resources.getDrawable(R.drawable.nohay));

                } else  {
                    if (porcentaje == 0)
                        startMaker.setIcon(resources.getDrawable(R.drawable.motlibre));
                    if (porcentaje > 0 && porcentaje <= 25)
                        startMaker.setIcon(resources.getDrawable(R.drawable.mediomoto));
                    if (porcentaje > 25 && porcentaje <= 50)
                        startMaker.setIcon(resources.getDrawable(R.drawable.mediomoto));
                    if (porcentaje > 50 && porcentaje <= 75)
                        startMaker.setIcon(resources.getDrawable(R.drawable.casinomoto));
                    if (porcentaje > 75 && porcentaje <= 100)
                        startMaker.setIcon(resources.getDrawable(R.drawable.nomoto));

                }

                map.getOverlays().add(startMaker);
            }
            GeoPoint startPoint = new GeoPoint(41.38, 2.16);
            setZoom(startPoint);
            setOverlays();
            map.invalidate();
        }
        private void initializeMap() {
            map.setTileSource(TileSourceFactory.MAPNIK);
            map.setTilesScaledToDpi(true);

            map.setBuiltInZoomControls(true);
            map.setMultiTouchControls(true);
        }
        private void setZoom(GeoPoint startPoint) {
            //  Setteamos el zoom al mismo nivel y ajustamos la posición a un geopunto
            mapController.setZoom(15);
            mapController.setCenter(startPoint);
        }
        private void setOverlays(){
            //final DisplayMetrics dm=getResources().getDisplayMetrics();
            myLocationOverlay=new MyLocationNewOverlay(map);
            myLocationOverlay.enableMyLocation();
            myLocationOverlay.runOnFirstFix(new Runnable() {
                @Override
                public void run() {
                    mapController.animateTo(myLocationOverlay.getMyLocation());
                }
            });

            mScaleBarOverlay=new ScaleBarOverlay(map);
            mScaleBarOverlay.setCentred(true);
            //mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
            mCompassOverlay= new CompassOverlay(this.context,map);
            mCompassOverlay.enableCompass();

            map.getOverlays().add(myLocationOverlay);
            map.getOverlays().add(this.mMinimapOverlay);
            map.getOverlays().add(this.mScaleBarOverlay);
            map.getOverlays().add(this.mCompassOverlay);
        }
        private int disponible(int slots,int bikes){
            int porcentaje=0;
            int total=bikes+slots;
            porcentaje=(100*slots)/total;
            return porcentaje;
        }
    }
}
