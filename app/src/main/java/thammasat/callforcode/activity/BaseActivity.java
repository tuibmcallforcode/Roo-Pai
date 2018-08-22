package thammasat.callforcode.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import thammasat.callforcode.R;
import thammasat.callforcode.fragment.MicrophoneFragment;
import thammasat.callforcode.fragment.PermissionFragment;
import thammasat.callforcode.manager.InternalStorage;
import thammasat.callforcode.manager.Singleton;
import thammasat.callforcode.manager.WeatherApi;
import thammasat.callforcode.model.Disaster;
import thammasat.callforcode.model.DisasterMap;
import thammasat.callforcode.restful.ApiClient;
import thammasat.callforcode.restful.ApiInterface;

public class BaseActivity extends AppCompatActivity {

    protected Typeface bold, regular, light;
    private static final int REQUEST_RECORD_AUDIO = 1;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 2;
    protected Animation anim;
    protected BounceInterpolator interpolator;
    public static final String TAG = BaseActivity.class.getSimpleName();
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    protected ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    protected Singleton singleton = Singleton.getInstance();

    protected void getDisasterMap() {
        rx.Observable.fromCallable(new Callable<Call<List<DisasterMap>>>() {
            @Override
            public Call<List<DisasterMap>> call() throws Exception {
                return apiService.getDisasterMap();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Call<List<DisasterMap>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Call<List<DisasterMap>> listCall) {
                        listCall.enqueue(new Callback<List<DisasterMap>>() {
                            @Override
                            public void onResponse(Call<List<DisasterMap>> call, Response<List<DisasterMap>> response) {
                                try {
                                    InternalStorage.writeObject(BaseActivity.this, "disasterMap", response.body());
                                } catch (IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<DisasterMap>> call, Throwable t) {

                            }
                        });
                    }
                });
    }

    protected void getDisaster() {
        rx.Observable.fromCallable(new Callable<Call<List<Disaster>>>() {
            @Override
            public Call<List<Disaster>> call() throws Exception {
                return apiService.getDisaster();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Call<List<Disaster>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Call<List<Disaster>> listCall) {
                        listCall.enqueue(new Callback<List<Disaster>>() {
                            @Override
                            public void onResponse(Call<List<Disaster>> call, Response<List<Disaster>> response) {
                                try {
                                    InternalStorage.writeObject(BaseActivity.this, "disaster", response.body());
                                } catch (IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Disaster>> call, Throwable t) {

                            }
                        });
                    }
                });
    }


    protected void setAnimation() {
        anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        interpolator = new BounceInterpolator();
        anim.setInterpolator(interpolator);
    }

    protected void goToActivity(Class activity, int enterAnim, int exitAnim, boolean finish) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
        if(finish)
            finish();
    }

    protected void toasty(String type, String message) {
        switch (type) {
            case "success": {
                Toasty.success(this, message, Toast.LENGTH_SHORT).show();
                break;
            }
            case "warning": {
                Toasty.warning(this, message, Toast.LENGTH_SHORT).show();
                break;
            }
            case "error": {
                Toasty.error(this, message, Toast.LENGTH_SHORT).show();
                break;
            }
            default: {
                break;
            }
        }
    }

    protected void setTypeface() {
        bold = Typeface.createFromAsset(getAssets(), "fonts/Bold.ttf");
        regular = Typeface.createFromAsset(getAssets(), "fonts/Regular.ttf");
        light = Typeface.createFromAsset(getAssets(), "fonts/Light.ttf");
    }

    protected boolean checkRecordAudioPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    protected boolean checkRecordAccessFineLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    protected void requestAccessFineLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_ACCESS_FINE_LOCATION);
    }

    protected void requestRecordAudioPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                REQUEST_RECORD_AUDIO);
    }

    public void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(BaseActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    public boolean checkGPSStatus() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void weatherDialog() {
        boolean wrapInScrollView = false;
        MaterialDialog weatherDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.weather_dialog, wrapInScrollView)
                .build();

        final TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

        cityField = (TextView) weatherDialog.findViewById(R.id.city_field);
        updatedField = (TextView) weatherDialog.findViewById(R.id.updated_field);
        detailsField = (TextView) weatherDialog.findViewById(R.id.details_field);
        currentTemperatureField = (TextView) weatherDialog.findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) weatherDialog.findViewById(R.id.humidity_field);
        pressure_field = (TextView) weatherDialog.findViewById(R.id.pressure_field);
        weatherIcon = (TextView) weatherDialog.findViewById(R.id.weather_icon);

        WeatherApi.placeIdTask asyncTask = new WeatherApi.placeIdTask(new WeatherApi.AsyncResponse() {
            @Override
            public void processFinish(String weather_city, String weather_description, String
                    weather_temperature, String weather_humidity, String weather_pressure, String
                                              weather_updatedOn, String weather_iconText, String sun_rise) {
                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText("Humidity: " + weather_humidity);
                pressure_field.setText("Pressure: " + weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));
                cityField.setTypeface(bold);
                updatedField.setTypeface(regular);
                detailsField.setTypeface(regular);
                currentTemperatureField.setTypeface(bold);
                humidity_field.setTypeface(regular);
                pressure_field.setTypeface(regular);
                weatherIcon.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weathericons-regular-webfont.ttf"));
            }
        });
        asyncTask.execute("13.7251088", "100.3529049"); //  asyncTask.execute("Latitude", "Longitude")

        weatherDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MicrophoneFragment microphoneFragment = new MicrophoneFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainer, microphoneFragment);
                    transaction.commit();
                } else {
                    PermissionFragment permissionFragment = new PermissionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("permission", "Allow Record Audio");
                    bundle.putInt("type", 2);
                    permissionFragment.setArguments(bundle);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainer, permissionFragment);
                    transaction.commit();
                }
            }
            case REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToActivity(MainActivity.class, 0, 0, true);
                } else {
                    PermissionFragment permissionFragment = new PermissionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("permission", "Allow Location");
                    bundle.putInt("type", 1);
                    permissionFragment.setArguments(bundle);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainer, permissionFragment);
                    transaction.commit();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_OK) {
            goToActivity(MainActivity.class, 0, 0, true);
        } else if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_CANCELED) {
            finish();
        }
    }
}
