package thammasat.callforcode.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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

import java.util.Random;

import thammasat.callforcode.R;

import com.google.maps.android.ui.IconGenerator;

import thammasat.callforcode.activity.WebViewActivity;
import thammasat.callforcode.model.DisasterMap;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MapFragment extends BaseFragment {

    private GoogleMap googleMap;
    private MapView mMapView;
    private ClusterManager<DisasterMap> mClusterManager;
    private Random mRandom = new Random(1984);


    private class DisasterRenderer extends DefaultClusterRenderer<DisasterMap> {
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
        protected void onBeforeClusterItemRendered(DisasterMap disasterMap, MarkerOptions markerOptions) {
            BitmapDrawable bitmap = (BitmapDrawable) getApplicationContext().getResources().getDrawable(disasterMap.getProfilePhoto());
            Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap.getBitmap(), 110, 110, false);
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(smallMarker);
            markerOptions.icon(icon).title(disasterMap.getTitle()).snippet(disasterMap.getSource());
//            disasterMap.getSnippet()).title(disasterMap.getTitle()
//            mImageView.setImageResource(disasterMap.profilePhoto);
//            Bitmap icon = mIconGenerator.makeIcon();
//            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(disasterMap.name);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<DisasterMap> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
//            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
//            int width = mDimension;
//            int height = mDimension;
//
//            for (DisasterMap p : cluster.getItems()) {
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

                    //Request disasterMap updates:
                    googleMap.setMyLocationEnabled(true);
                }

                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);

                mClusterManager = new ClusterManager<DisasterMap>(getContext(), googleMap);
                mClusterManager.setRenderer(new DisasterRenderer());

                googleMap.setOnCameraIdleListener(mClusterManager);
                googleMap.setOnMarkerClickListener(mClusterManager);
                googleMap.setOnInfoWindowClickListener(mClusterManager);

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String[] items = marker.getSnippet().split(" -> ");
                        Intent i = new Intent(getContext(), WebViewActivity.class);
                        i.putExtra("path", items[1]);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                    }
                });

                mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<DisasterMap>() {
                    @Override
                    public boolean onClusterClick(Cluster<DisasterMap> cluster) {
                        // Show a toast with some info when the cluster is clicked.
                        Toast.makeText(getContext(), "Including " + cluster.getSize() + " items", Toast.LENGTH_SHORT).show();

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
                mClusterManager.setOnClusterInfoWindowClickListener(new ClusterManager.OnClusterInfoWindowClickListener<DisasterMap>() {
                    @Override
                    public void onClusterInfoWindowClick(Cluster<DisasterMap> cluster) {

                    }
                });
                mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<DisasterMap>() {
                    @Override
                    public boolean onClusterItemClick(DisasterMap disasterMap) {
                        return false;
                    }
                });
                mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<DisasterMap>() {
                    @Override
                    public void onClusterItemInfoWindowClick(DisasterMap disasterMap) {
                    }
                });
                addItems();
                mClusterManager.cluster();
//                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(Float.parseFloat("Lat"), Float.parseFloat("Long"));
//                googleMap.addMarker(new MarkerOptions().position(sydney).
//                        title("Title").snippet("TitleName"));
//
//                // For zooming automatically to the disasterMap of the marker
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
        getDisasterMapList();
        int profilePhoto = R.drawable.microphone;
        for (int i = 0; i < disasterMapList.size(); i++) {
            String[] items = disasterMapList.get(i).getTitle().split(" ");
            switch (items[0].toLowerCase()) {
                case "drought":
                    profilePhoto = R.drawable.drought;
                    break;
                case "earthquake":
                    profilePhoto = R.drawable.earth_quake;
                    break;
                case "hurricane":
                    profilePhoto = R.drawable.extratropical_cyclone;
                    break;
                case "wildfires":
                    profilePhoto = R.drawable.fire;
                    break;
                case "wildfire":
                    profilePhoto = R.drawable.fire;
                    break;
                case "floods":
                    profilePhoto = R.drawable.flood;
                    break;
                case "flood":
                    profilePhoto = R.drawable.flood;
                    break;
                case "storms":
                    profilePhoto = R.drawable.severe_local_storm;
                    break;
                case "typhoon":
                    profilePhoto = R.drawable.tropical_cyclone;
                    break;
                case "tsunami":
                    profilePhoto = R.drawable.tsunami;
                    break;
                case "highsurf":
                    profilePhoto = R.drawable.tsunami;
                    break;
                case "volcano":
                    profilePhoto = R.drawable.volcano;
                    break;
                default:
                    profilePhoto = R.drawable.microphone;
                    break;
            }
            if (items[1] == "heat")
                profilePhoto = R.drawable.heat_wave;
            else if (items[1] == "cold")
                profilePhoto = R.drawable.cold_wave;

            mClusterManager.addItem(new DisasterMap(
                    new LatLng(Double.parseDouble(disasterMapList.get(i).getLatitude()), Double.parseDouble(disasterMapList.get(i).getLongitude())),
                    disasterMapList.get(i).getId(),
                    disasterMapList.get(i).getPdcId(),
                    disasterMapList.get(i).getV(),
                    disasterMapList.get(i).getDescription(),
                    disasterMapList.get(i).getSeverity(),
                    disasterMapList.get(i).getSource(),
                    disasterMapList.get(i).getTime(),
                    disasterMapList.get(i).getTitle(),
                    profilePhoto));
        }

//        mClusterManager.addItem(new DisasterMap(position(), "Flash Flood", R.drawable.flash_flood));
//        mClusterManager.addItem(new DisasterMap(position(), "Insect Infestation", R.drawable.insect_infestation));
//        mClusterManager.addItem(new DisasterMap(position(), "Land Slide", R.drawable.land_slide));
//        mClusterManager.addItem(new DisasterMap(position(), "Mud Slide", R.drawable.mud_slide));
//        mClusterManager.addItem(new DisasterMap(position(), "Snow Avalanche", R.drawable.snow_avalanche));
//        mClusterManager.addItem(new DisasterMap(position(), "Storm Surge", R.drawable.storm_surge));
//        mClusterManager.addItem(new DisasterMap(position(), "Technological", R.drawable.technological));
    }
}
