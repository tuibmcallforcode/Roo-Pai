package thammasat.callforcode.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;

import es.dmoral.toasty.Toasty;
import thammasat.callforcode.R;
import thammasat.callforcode.fragment.LoginFragment;

public class WelcomeActivity extends BaseActivity {

    private static final String TAG = WelcomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TwitterConfig config = new TwitterConfig.Builder(WelcomeActivity.this)
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build();

        Twitter.initialize(config);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isFacebookLoggedIn = accessToken != null && !accessToken.isExpired();
        boolean isTwitterLoggedIn = TwitterCore.getInstance().getSessionManager().getActiveSession() != null;
        boolean isGoogleLoggedIn = GoogleSignIn.getLastSignedInAccount(this) != null;

        if (isFacebookLoggedIn) {
            Log.d(TAG, "Authenticated with Facebook");
            goToMainActivity();
        } else if (isTwitterLoggedIn) {
            Log.d(TAG, "Authenticated with Twitter");
            goToMainActivity();
        } else if (isGoogleLoggedIn) {
            Log.d(TAG, "Authenticated with Google");
            goToMainActivity();
        } else {
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, loginFragment, "LoginFragment");
            transaction.commit();
        }
    }

    private void goToMainActivity() {
        toasty("success", "Logged in");
        if(checkRecordAccessFineLocationPermission()) {
            if(checkGPSStatus())
                goToActivity(MainActivity.class, 0, 0, true);
            else
                displayLocationSettingsRequest(this);
        } else {
            requestAccessFineLocationPermission();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        FragmentManager fragment = getSupportFragmentManager();
//        if (fragment != null) {
//            fragment.findFragmentByTag("LoginFragment").onActivityResult(requestCode, resultCode, data);
//        }
//    }
}
