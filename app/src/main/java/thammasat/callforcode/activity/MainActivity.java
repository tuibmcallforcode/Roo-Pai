package thammasat.callforcode.activity;

import android.animation.Animator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.twitter.sdk.android.core.TwitterCore;

import es.dmoral.toasty.Toasty;
import thammasat.callforcode.R;
import thammasat.callforcode.adapter.PagerAdapter;
import thammasat.callforcode.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Animation anim;
    private BounceInterpolator interpolator;
    private Typeface bold, regular, light;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tvStatus;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setTypeface();
        initInstance();
        eventListenerBinding();
    }

    private void setTypeface() {
        bold = Typeface.createFromAsset(getAssets(), "fonts/Bold.ttf");
        regular = Typeface.createFromAsset(getAssets(), "fonts/Regular.ttf");
        light = Typeface.createFromAsset(getAssets(), "fonts/Light.ttf");
    }

    private void initInstance() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
        interpolator = new BounceInterpolator();
        anim.setInterpolator(interpolator);

        TextView tvProjectName = (TextView) findViewById(R.id.tvProjectName);
        tvProjectName.setTypeface(bold);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("NEARBY"));
        tabLayout.addTab(tabLayout.newTab().setText("LATEST"));
        tabLayout.addTab(tabLayout.newTab().setText("MAP"));
        tabLayout.addTab(tabLayout.newTab().setText("STATS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        setCustomFont();

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvStatus.setTypeface(bold);
    }

    private void eventListenerBinding() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                    tvStatus.setText("LATEST");
                } else if (tab.getPosition() == 2) {
                    tvStatus.setText("MAP");
                } else if (tab.getPosition() == 3) {
                    tvStatus.setText("STATS");
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
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            boolean isFacebookLoggedIn = accessToken != null && !accessToken.isExpired();
            boolean isTwitterLoggedIn = TwitterCore.getInstance().getSessionManager().getActiveSession() != null;
            boolean isGoogleLoggedIn = GoogleSignIn.getLastSignedInAccount(this) != null;
            Log.i("hello", isFacebookLoggedIn + " - " + isTwitterLoggedIn + " - " + isGoogleLoggedIn);

            if (isFacebookLoggedIn) {
                Log.d(TAG, "Facebook logged out");
                toasty("success", "Logged out");
                LoginManager.getInstance().logOut();
                goToActivity(WelcomeActivity.class, R.anim.enter_from_left, R.anim.exit_to_right);
            } else if (isTwitterLoggedIn) {
                Log.d(TAG, "Twitter logged out");
                toasty("success", "Logged out");
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
                goToActivity(WelcomeActivity.class, R.anim.enter_from_left, R.anim.exit_to_right);
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
                                goToActivity(WelcomeActivity.class, R.anim.enter_from_left, R.anim.exit_to_right);
                            }
                        });
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToActivity(Class activity, int enterAnim, int exitAnim) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
        finish();
    }

    private void toasty(String type, String message) {
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
}
