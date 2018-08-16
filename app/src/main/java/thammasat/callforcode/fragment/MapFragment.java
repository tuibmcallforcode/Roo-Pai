package thammasat.callforcode.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import thammasat.callforcode.R;

import com.google.maps.android.ui.IconGenerator;

import thammasat.callforcode.activity.InfoActivity;
import thammasat.callforcode.manager.MultiDrawable;
import thammasat.callforcode.model.Location;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MapFragment extends BaseFragment {

    private GoogleMap googleMap;
    private MapView mMapView;
    private ClusterManager<Location> mClusterManager;
    private Random mRandom = new Random(1984);


    private class DisasterRenderer extends DefaultClusterRenderer<Location> {
        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        private final ImageView mImageView;
//        private final ImageView mClusterImageView;
        private final int mDimension;

        public DisasterRenderer() {
            super(getApplicationContext(), googleMap, mClusterManager);

            View multiProfile = getLayoutInflater().inflate(R.layout.cluster_location, null);
            mClusterIconGenerator.setContentView(multiProfile);
//            mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

            mImageView = new ImageView(getApplicationContext());
            mDimension = (int) getResources().getDimension(R.dimen.cluster_location_size);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.cluster_location_size);
            mImageView.setPadding(padding, padding, padding, padding);
            mIconGenerator.setContentView(mImageView);
        }

        @Override
        protected void onBeforeClusterItemRendered(Location location, MarkerOptions markerOptions) {
            BitmapDrawable bitmap = (BitmapDrawable) getResources().getDrawable(location.profilePhoto);
            Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap.getBitmap(), 110, 110, false);
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(smallMarker);
            markerOptions.icon(icon).title("Warning").snippet(location.name);
//            location.getSnippet()).title(location.getTitle()
//            mImageView.setImageResource(location.profilePhoto);
//            Bitmap icon = mIconGenerator.makeIcon();
//            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(location.name);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<Location> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
//            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
//            int width = mDimension;
//            int height = mDimension;
//
//            for (Location p : cluster.getItems()) {
//                // Draw 4 at most.
//                if (profilePhotos.size() == 4) break;
//                Drawable drawable = getResources().getDrawable(p.profilePhoto);
//                drawable.setBounds(0, 0, width, height);
//                profilePhotos.add(drawable);
//            }
//            MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
//            multiDrawable.setBounds(0, 0, width, height);
//
//            mClusterImageView.setImageDrawable(multiDrawable);
//            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
//            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
            BitmapDrawable bitmap = (BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.warning);
            Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap.getBitmap(), 100, 100, false);
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(smallMarker);
            markerOptions.icon(icon);
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            return cluster.getSize() > 1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                if (googleMap != null) {
                    return;
                }
                googleMap = mMap;


                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    googleMap.setMyLocationEnabled(true);
                }

                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);

                mClusterManager = new ClusterManager<Location>(getContext(), googleMap);
                mClusterManager.setRenderer(new DisasterRenderer());

                googleMap.setOnCameraIdleListener(mClusterManager);
                googleMap.setOnMarkerClickListener(mClusterManager);
                googleMap.setOnInfoWindowClickListener(mClusterManager);

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                    }
                });

                mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Location>() {
                    @Override
                    public boolean onClusterClick(Cluster<Location> cluster) {
                        // Show a toast with some info when the cluster is clicked.
                        String firstName = cluster.getItems().iterator().next().name;
                        Toast.makeText(getContext(), cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();

                        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
                        // inside of bounds, then animate to center of the bounds.

                        // Create the builder to collect all essential cluster items for the bounds.
                        LatLngBounds.Builder builder = LatLngBounds.builder();
                        for (ClusterItem item : cluster.getItems()) {
                            builder.include(item.getPosition());
                        }
                        // Get the LatLngBounds
                        final LatLngBounds bounds = builder.build();

                        // Animate camera to the bounds
                        try {
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return true;
                    }
                });
                mClusterManager.setOnClusterInfoWindowClickListener(new ClusterManager.OnClusterInfoWindowClickListener<Location>() {
                    @Override
                    public void onClusterInfoWindowClick(Cluster<Location> cluster) {

                    }
                });
                mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Location>() {
                    @Override
                    public boolean onClusterItemClick(Location location) {
                        return false;
                    }
                });
                mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<Location>() {
                    @Override
                    public void onClusterItemInfoWindowClick(Location location) {

                    }
                });
                addItems();
                mClusterManager.cluster();
//                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(Float.parseFloat("Lat"), Float.parseFloat("Long"));
//                googleMap.addMarker(new MarkerOptions().position(sydney).
//                        title("Title").snippet("TitleName"));
//
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition
//                        (cameraPosition));

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void addItems() {
        mClusterManager.addItem(new Location(position(), "Cold Wave", R.drawable.cold_wave));
        mClusterManager.addItem(new Location(position(), "Drought", R.drawable.drought));
        mClusterManager.addItem(new Location(position(), "Earth Quake", R.drawable.earth_quake));
        mClusterManager.addItem(new Location(position(), "Extra Tropical Cyclone", R.drawable.extratropical_cyclone));
        mClusterManager.addItem(new Location(position(), "Fire", R.drawable.fire));
        mClusterManager.addItem(new Location(position(), "Flash Flood", R.drawable.flash_flood));
        mClusterManager.addItem(new Location(position(), "Flood", R.drawable.flood));
        mClusterManager.addItem(new Location(position(), "Heat Wave", R.drawable.heat_wave));
        mClusterManager.addItem(new Location(position(), "Insect Infestation", R.drawable.insect_infestation));
        mClusterManager.addItem(new Location(position(), "Land Slide", R.drawable.land_slide));
        mClusterManager.addItem(new Location(position(), "Mud Slide", R.drawable.mud_slide));
        mClusterManager.addItem(new Location(position(), "Severe Local Storm", R.drawable.severe_local_storm));
        mClusterManager.addItem(new Location(position(), "Snow Avalanche", R.drawable.snow_avalanche));
        mClusterManager.addItem(new Location(position(), "Storm Surge", R.drawable.storm_surge));
        mClusterManager.addItem(new Location(position(), "Technological", R.drawable.technological));
        mClusterManager.addItem(new Location(position(), "Tropical Cyclone", R.drawable.tropical_cyclone));
        mClusterManager.addItem(new Location(position(), "Tsunami", R.drawable.tsunami));
        mClusterManager.addItem(new Location(position(), "Volcano", R.drawable.volcano));
    }

    private LatLng position() {
        return new LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683));
    }

    private double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }
}
