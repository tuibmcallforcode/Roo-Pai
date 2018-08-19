package thammasat.callforcode.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import thammasat.callforcode.R;
import thammasat.callforcode.activity.MainActivity;
import thammasat.callforcode.manager.Singleton;

public class BaseFragment extends Fragment {
    protected Typeface bold, regular, light;
    protected Animation anim;
    protected BounceInterpolator interpolator;
    private static final int REQUEST_RECORD_AUDIO = 1;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 2;
    protected Singleton singleton = Singleton.getInstance();

    protected void setTypeface() {
        bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bold.ttf");
        regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Regular.ttf");
        light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Light.ttf");
    }

    protected void goToActivity(Class activity, int enterAnim, int exitAnim, boolean finish) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
        getActivity().overridePendingTransition(enterAnim, exitAnim);
        if(finish)
            getActivity().finish();
    }

    protected void setAnimation() {
        anim = AnimationUtils.loadAnimation(this.getContext(), R.anim.bounce);
        interpolator = new BounceInterpolator();
        anim.setInterpolator(interpolator);
    }

    protected void toasty(String type, String message) {
        switch (type) {
            case "success": {
                Toasty.success(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
            }
            case "warning": {
                Toasty.warning(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
            }
            case "error": {
                Toasty.error(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
            }
            default: {
                break;
            }
        }
    }

    protected boolean checkRecordAudioPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    protected boolean checkRecordAccessFineLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    protected void requestAccessFineLocationPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_ACCESS_FINE_LOCATION);
    }

    protected void requestRecordAudioPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.RECORD_AUDIO},
                REQUEST_RECORD_AUDIO);
    }

    public boolean checkGPSStatus() {
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MicrophoneFragment microphoneFragment = new MicrophoneFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainer, microphoneFragment);
                    transaction.commit();
                } else {
                    PermissionFragment permissionFragment = new PermissionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("permission", "Allow Record Audio");
                    bundle.putInt("type", 2);
                    permissionFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
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
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainer, permissionFragment);
                    transaction.commit();
                }
            }
        }
    }
}
