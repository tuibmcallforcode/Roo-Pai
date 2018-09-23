package thammasat.callforcode.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import thammasat.callforcode.R;
import thammasat.callforcode.activity.MainActivity;
import thammasat.callforcode.manager.InternalStorage;
import thammasat.callforcode.manager.Singleton;
import thammasat.callforcode.model.Disaster;
import thammasat.callforcode.model.DisasterMap;
import thammasat.callforcode.restful.ApiClient;
import thammasat.callforcode.restful.ApiInterface;

public class BaseFragment extends Fragment {
    protected Typeface bold, regular, light;
    protected Animation anim;
    protected BounceInterpolator interpolator;
    private static final int REQUEST_RECORD_AUDIO = 1;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 2;
    protected List<DisasterMap> disasterMapList = new ArrayList<>();
    protected List<Disaster> disasterList = new ArrayList<>();
    protected List<Disaster> relatedList = new ArrayList<>();
    protected ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    protected String languages;

    protected void getLocale() {
        try {
            languages = (String) InternalStorage.readObject(getContext(), "languages");
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }

    }

    protected void getRelatedList() {
        for(int i = 0 ; i < 5 ; i++){
            try{
                double focus = Math.random() * disasterList.size() + 1;
                relatedList.add(disasterList.get((int) focus));
            } catch (Exception e) {

            }

        }
    }
    protected void getDisasterMapList() {
        try{
            List<DisasterMap> disasterMapList = (List<DisasterMap>) InternalStorage.readObject(getContext(), "disasterMap");
            this.disasterMapList =  disasterMapList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void getDisasterList() {
        try{
            List<Disaster> disasterList = (List<Disaster>) InternalStorage.readObject(getContext(), "disaster");
            this.disasterList =  disasterList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}
