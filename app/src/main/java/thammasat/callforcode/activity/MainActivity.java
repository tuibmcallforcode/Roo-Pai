package thammasat.callforcode.activity;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import es.dmoral.toasty.Toasty;
import thammasat.callforcode.R;
import thammasat.callforcode.adapter.PagerAdapter;
import thammasat.callforcode.databinding.ActivityMainBinding;
import thammasat.callforcode.fragment.NearByFragment;
import thammasat.callforcode.manager.InternalStorage;
import thammasat.callforcode.manager.Singleton;
import thammasat.callforcode.manager.WeatherApi;
import thammasat.callforcode.model.Disaster;

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
    private Integer[] selectedSeverity;
    private String[] selectedSeverityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);

        getCurrentLocation();
        weatherDialog();
        getDisaster();
        getDisasterMap();
        setAnimation();
        setTypeface();
        initInstance();
        eventListenerBinding();
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

        TextView tvProjectName = (TextView) findViewById(R.id.tvProjectName);
        tvProjectName.setTypeface(bold);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("NEARBY"));
        tabLayout.addTab(tabLayout.newTab().setText("NEWS"));
        tabLayout.addTab(tabLayout.newTab().setText("MAP"));
        tabLayout.addTab(tabLayout.newTab().setText("STATS"));
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder( MainActivity.this)
                        .typeface("Bold.ttf", "Regular.ttf")
                        .backgroundColor(Color.parseColor("#454F63"))
                        .titleColor(Color.parseColor("#FFFFFF"))
                        .negativeColor(Color.parseColor("#FFFFFF"))
                        .positiveColor(Color.parseColor("#FFFFFF"))
                        .contentColor(Color.parseColor("#FFFFFF"))
                        .widgetColor(Color.parseColor("#FFFFFF"))
                        .title(R.string.severity)
                        .items(R.array.severity)
                        .itemsCallbackMultiChoice(selectedSeverity, new MaterialDialog.ListCallbackMultiChoice() {
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                String txt = "";
                                selectedSeverity = new Integer[text.length];
                                selectedSeverityId = new String[text.length];
                                for (int i = 0; i < text.length; i++) {
                                    txt = txt + text[i] + " ";
                                    selectedSeverity[i] = which[i];
                                    selectedSeverityId[i] = text[i].toString();
                                }
                                return false;
                            }
                        })
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.confirm)
                        .show();

            }
        });

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
                    tvStatus.setText("NEARBY");
                } else if (tab.getPosition() == 1) {
                    tvStatus.setText("NEWS");
                } else if (tab.getPosition() == 2) {
                    tvStatus.setText("MAP");
                    appBarLayout.setExpanded(false);
                } else if (tab.getPosition() == 3) {
                    tvStatus.setText("STATS");
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
            super.onBackPressed();
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
    public void onLocationChanged(Location location) {
        try {
            InternalStorage.writeObject(this, "latitude", location.getLatitude());
            InternalStorage.writeObject(this, "longitude", location.getLongitude());
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
}
