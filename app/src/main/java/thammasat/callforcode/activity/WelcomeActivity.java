package thammasat.callforcode.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.io.IOException;

import thammasat.callforcode.R;
import thammasat.callforcode.fragment.LoginFragment;
import thammasat.callforcode.manager.InternalStorage;

public class WelcomeActivity extends BaseActivity {

    private static final String TAG = WelcomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setLanguages();
    }

    private void initInstance() {
        TwitterConfig config = new TwitterConfig.Builder(WelcomeActivity.this)
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build();

        Twitter.initialize(config);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isFacebookLoggedIn = accessToken != null && !accessToken.isExpired();
        boolean isTwitterLoggedIn = TwitterCore.getInstance().getSessionManager().getActiveSession() != null;
        boolean isGoogleLoggedIn = GoogleSignIn.getLastSignedInAccount(WelcomeActivity.this) != null;

        if (isFacebookLoggedIn) {
            Log.d(TAG, "Authenticated with Facebook");
            goToActivity(MainActivity.class, 0, 0, true);
        } else if (isTwitterLoggedIn) {
            Log.d(TAG, "Authenticated with Twitter");
            goToActivity(MainActivity.class, 0, 0, true);
        } else if (isGoogleLoggedIn) {
            Log.d(TAG, "Authenticated with Google");
            goToActivity(MainActivity.class, 0, 0, true);
        } else {
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, loginFragment, "LoginFragment");
            transaction.commit();
        }
    }

    private void setLanguages() {
        try {
            String languages = (String) InternalStorage.readObject(WelcomeActivity.this, "languages");
            initInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            new MaterialDialog.Builder(WelcomeActivity.this)
                    .typeface("Bold.ttf", "Regular.ttf")
                    .backgroundColor(Color.parseColor("#454F63"))
                    .titleColor(Color.parseColor("#FFFFFF"))
                    .negativeColor(Color.parseColor("#FFFFFF"))
                    .positiveColor(Color.parseColor("#FFFFFF"))
                    .contentColor(Color.parseColor("#FFFFFF"))
                    .widgetColor(Color.parseColor("#FFFFFF"))
                    .title(R.string.languages)
                    .items(R.array.languages)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            try {
                                InternalStorage.writeObject(WelcomeActivity.this, "languages", text.toString());
                                initInstance();
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                            }
                            return false;
                        }
                    })
                    .negativeText(R.string.cancel)
                    .show();
        }
    }
}
