package thammasat.callforcode.activity;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.twitter.sdk.android.core.TwitterCore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import thammasat.callforcode.R;
import thammasat.callforcode.adapter.PagerAdapter;
import thammasat.callforcode.databinding.ActivityMainBinding;
import thammasat.callforcode.fragment.PermissionFragment;
import thammasat.callforcode.manager.InternalStorage;
import thammasat.callforcode.model.Disaster;
import thammasat.callforcode.model.DisasterMap;
import thammasat.callforcode.model.Prepareness;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    protected LocationManager locationManager;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tvStatus;
    private AppBarLayout appBarLayout;
    private static final String TAG = MainActivity.class.getName();
    private boolean displayed = false, disaster = false, disasterMap = false, prepare = false;
    private MaterialProgressBar progressBar;
    private static final int ACCESS_LOCATION = 0;
    private String languages;
    private String[] prepareness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        prepareness = getResources().getStringArray(R.array.prepareness);
        setAnimation();
        setTypeface();
        TextView tvProjectName = (TextView) findViewById(R.id.tvProjectName);
        tvProjectName.setTypeface(bold);

        progressBar = (MaterialProgressBar) findViewById(R.id.progressBar);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        displayLocationSettingsRequest(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);

            try {
                boolean firstTime = (boolean) InternalStorage.readObject(MainActivity.this, "firstTime");
                List<Disaster> disasterList = (List<Disaster>) InternalStorage.readObject(MainActivity.this, "disaster");
                List<DisasterMap> disasterMapList = (List<DisasterMap>) InternalStorage.readObject(MainActivity.this, "disasterMap");
                languages = (String) InternalStorage.readObject(MainActivity.this, "languages");
                if (isNetworkConnected())
                    initial();
                else if ((disasterList != null && disasterMapList != null)) {
                    initInstance();
                    eventListenerBinding();
                    progressBar.setVisibility(View.GONE);
                } else {
                    permissionRequest("Please connect to the internet.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (isNetworkConnected()) {
                    try {
                        double latitude = 13.736717;
                        double longitude = 100.523186;
                        int radius = 10000;
                        Integer[] severity = new Integer[getResources().getStringArray(R.array.severity).length];
                        for (int i = 0; i < severity.length; i++) {
                            severity[i] = i;
                        }
                        String url = "172.20.10.13:8080";
                        InternalStorage.writeObject(this, "severity", severity);
                        InternalStorage.writeObject(this, "url", url);
                        InternalStorage.writeObject(MainActivity.this, "latitude", latitude);
                        InternalStorage.writeObject(MainActivity.this, "longitude", longitude);
                        InternalStorage.writeObject(MainActivity.this, "selectedRadiusValue", radius);
                        languages = (String) InternalStorage.readObject(MainActivity.this, "languages");
                    } catch (IOException ex) {
                        Log.e(TAG, e.getMessage());
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    initial();
                } else {
                    permissionRequest("Please connect to the internet.");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void permissionRequest(String message) {
        progressBar.setVisibility(View.GONE);
        PermissionFragment permissionFragment = new PermissionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("permission", message);
        bundle.putInt("type", 1);
        permissionFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, permissionFragment);
        transaction.commit();
    }

    private void initial() {
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
                        progressBar.setVisibility(View.GONE);
                        permissionRequest("Couldn't connect to server.");
                    }

                    @Override
                    public void onNext(Call<List<DisasterMap>> listCall) {
                        listCall.enqueue(new Callback<List<DisasterMap>>() {
                            @Override
                            public void onResponse(Call<List<DisasterMap>> call, Response<List<DisasterMap>> response) {
                                try {
                                    InternalStorage.writeObject(MainActivity.this, "disasterMap", response.body());
                                    InternalStorage.writeObject(MainActivity.this, "firstTime", false);
                                    disasterMap = true;
                                    if (disaster && disasterMap && prepare) {
                                        initInstance();
                                        eventListenerBinding();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                } catch (IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<DisasterMap>> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                permissionRequest("Connection timeout, please try again.");
                            }
                        });
                    }
                });

        rx.Observable.fromCallable(new Callable<Call<List<Disaster>>>() {
            @Override
            public Call<List<Disaster>> call() throws Exception {
                return languages.equals("en") ? apiService.getDisasterEn() : apiService.getDisasterOthers(10, 1, languages);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Call<List<Disaster>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        permissionRequest("Couldn't connect to server.");
                    }

                    @Override
                    public void onNext(Call<List<Disaster>> listCall) {
                        listCall.enqueue(new Callback<List<Disaster>>() {
                            @Override
                            public void onResponse(Call<List<Disaster>> call, Response<List<Disaster>> response) {
                                try {
                                    InternalStorage.writeObject(MainActivity.this, "disaster", response.body());
                                    InternalStorage.writeObject(MainActivity.this, "firstTime", false);
                                    disaster = true;
                                    if (disaster && disasterMap && prepare) {
                                        initInstance();
                                        eventListenerBinding();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                } catch (IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Disaster>> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                permissionRequest("Connection timeout, please try again.");
                            }
                        });
                    }
                });

        for (int i = 0; i < prepareness.length; i++) {
            final int n = i;
            if (languages.equals("en")) {
                rx.Observable.fromCallable(new Callable<Call<List<Prepareness>>>() {
                    @Override
                    public Call<List<Prepareness>> call() throws Exception {
                        return apiService.getPreparenessEn(prepareness[n]);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Call<List<Prepareness>>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                progressBar.setVisibility(View.GONE);
                                permissionRequest("Couldn't connect to server.");
                            }

                            @Override
                            public void onNext(Call<List<Prepareness>> listCall) {
                                listCall.enqueue(new Callback<List<Prepareness>>() {
                                    @Override
                                    public void onResponse(Call<List<Prepareness>> call, Response<List<Prepareness>> response) {
                                        try {
                                            InternalStorage.writeObject(MainActivity.this, prepareness[n] + "Tips", response.body().get(0).getTipsHTML());
                                            InternalStorage.writeObject(MainActivity.this, prepareness[n] + "Prepare", response.body().get(0).getPrepareHTML());
                                            InternalStorage.writeObject(MainActivity.this, prepareness[n] + "After", response.body().get(0).getAfterHTML());
                                            InternalStorage.writeObject(MainActivity.this, prepareness[n] + "Description", response.body().get(0).getDescriptionHTML());
                                            if (n == prepareness.length - 1)
                                                prepare = true;
                                            if (disaster && disasterMap && prepare) {
                                                initInstance();
                                                eventListenerBinding();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        } catch (IOException e) {
                                            Log.e(TAG, e.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Prepareness>> call, Throwable t) {
                                        progressBar.setVisibility(View.GONE);
                                        permissionRequest("Connection timeout, please try again.");
                                    }
                                });
                            }
                        });
            } else {
                rx.Observable.fromCallable(new Callable<Call<Prepareness>>() {
                    @Override
                    public Call<Prepareness> call() throws Exception {
                        return apiService.getPrepareness(prepareness[n], languages);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Call<Prepareness>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                progressBar.setVisibility(View.GONE);
                                permissionRequest("Couldn't connect to server.");
                            }

                            @Override
                            public void onNext(Call<Prepareness> preparenessCall) {
                                preparenessCall.enqueue(new Callback<Prepareness>() {
                                    @Override
                                    public void onResponse(Call<Prepareness> call, Response<Prepareness> response) {
                                        try {
                                            InternalStorage.writeObject(MainActivity.this, prepareness[n] + "Tips", response.body().getTipsHTML());
                                            InternalStorage.writeObject(MainActivity.this, prepareness[n] + "Prepare", response.body().getPrepareHTML());
                                            InternalStorage.writeObject(MainActivity.this, prepareness[n] + "After", response.body().getAfterHTML());
                                            InternalStorage.writeObject(MainActivity.this, prepareness[n] + "Description", response.body().getDescriptionHTML());
                                            if (n == prepareness.length - 1)
                                                prepare = true;
                                            if (disaster && disasterMap && prepare) {
                                                initInstance();
                                                eventListenerBinding();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        } catch (IOException e) {
                                            Log.e(TAG, e.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Prepareness> call, Throwable t) {
                                        progressBar.setVisibility(View.GONE);
                                        permissionRequest("Connection timeout, please try again.");
                                    }
                                });
                            }
                        });
            }
        }
    }

    private void initInstance() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.nearby)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.news)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.map)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.stats)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);
        setCustomFont();

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvStatus.setTypeface(bold);
    }

    private void eventListenerBinding() {
        final ImageView ivMenu = (ImageView) findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
                ivMenu.startAnimation(anim);
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    tvStatus.setText(getResources().getString(R.string.nearby));
                } else if (tab.getPosition() == 1) {
                    tvStatus.setText(getResources().getString(R.string.news));
                } else if (tab.getPosition() == 2) {
                    tvStatus.setText(getResources().getString(R.string.map));
                    appBarLayout.setExpanded(false);
                } else if (tab.getPosition() == 3) {
                    tvStatus.setText(getResources().getString(R.string.stats));
                    appBarLayout.setExpanded(false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final LinearLayout llStatus = (LinearLayout) findViewById(R.id.llStatus);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (-(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    llStatus.setVisibility(View.VISIBLE);
                    llStatus.animate().translationY(0)
                            .alpha(1.0f)
                            .setDuration(200)
                            .setListener(null);
                } else if (verticalOffset == 0) {
                    llStatus.animate().translationY(-200)
                            .setDuration(200)
                            .alpha(0.0f)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    llStatus.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                }
            }
        });
    }

    public void setCustomFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(regular);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new MaterialDialog.Builder(MainActivity.this)
                    .typeface("Bold.ttf", "Regular.ttf")
                    .backgroundColor(Color.parseColor("#454F63"))
                    .titleColor(Color.parseColor("#FFFFFF"))
                    .negativeColor(Color.parseColor("#FFFFFF"))
                    .positiveColor(Color.parseColor("#FFFFFF"))
                    .contentColor(Color.parseColor("#FFFFFF"))
                    .widgetColor(Color.parseColor("#FFFFFF"))
                    .title(R.string.confirmation)
                    .content(R.string.exit_app)
                    .negativeText(R.string.cancel)
                    .positiveText(R.string.confirm)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.weather:
                getCurrentLocation();
                weatherDialog();
                break;
            case R.id.settings:
                goToActivity(SettingsActivity.class, R.anim.enter_from_right, R.anim.exit_to_left, false);
                break;
            case R.id.logout:
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isFacebookLoggedIn = accessToken != null && !accessToken.isExpired();
                boolean isTwitterLoggedIn = TwitterCore.getInstance().getSessionManager().getActiveSession() != null;
                boolean isGoogleLoggedIn = GoogleSignIn.getLastSignedInAccount(this) != null;

                if (isFacebookLoggedIn) {
                    Log.d(TAG, "Facebook logged out");
                    toasty("success", "Logged out");
                    LoginManager.getInstance().logOut();
                    goToActivity(WelcomeActivity.class, R.anim.enter_from_left, R.anim.exit_to_right, true);
                } else if (isTwitterLoggedIn) {
                    Log.d(TAG, "Twitter logged out");
                    toasty("success", "Logged out");
                    TwitterCore.getInstance().getSessionManager().clearActiveSession();
                    goToActivity(WelcomeActivity.class, R.anim.enter_from_left, R.anim.exit_to_right, true);
                } else if (isGoogleLoggedIn) {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "Google logged out");
                                    toasty("success", "Logged out");
                                    goToActivity(WelcomeActivity.class, R.anim.enter_from_left, R.anim.exit_to_right, true);
                                }
                            });
                }
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            InternalStorage.writeObject(this, "latitude", location.getLatitude());
            InternalStorage.writeObject(this, "longitude", location.getLongitude());
            if (!displayed) {
                displayed = true;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);

                    try {
                        boolean firstTime = (boolean) InternalStorage.readObject(MainActivity.this, "firstTime");
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            double latitude = 13.736717;
                            double longitude = 100.523186;
                            int radius = 10000;
                            Integer[] severity = new Integer[getResources().getStringArray(R.array.severity).length];
                            for (int i = 0; i < severity.length; i++) {
                                severity[i] = i;
                            }
                            String url = "172.20.10.13:8080";
                            InternalStorage.writeObject(this, "severity", severity);
                            InternalStorage.writeObject(this, "url", url);
                            InternalStorage.writeObject(MainActivity.this, "latitude", latitude);
                            InternalStorage.writeObject(MainActivity.this, "longitude", longitude);
                            InternalStorage.writeObject(MainActivity.this, "selectedRadiusValue", radius);
                            languages = (String) InternalStorage.readObject(MainActivity.this, "languages");
                        } catch (IOException ex) {
                            Log.e(TAG, e.getMessage());
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        initial();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    permissionRequest("Please allow access location.");
                }
                break;
        }
    }
}
