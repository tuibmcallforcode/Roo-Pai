package thammasat.callforcode.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import es.dmoral.toasty.Toasty;
import thammasat.callforcode.R;
import thammasat.callforcode.activity.MainActivity;
import thammasat.callforcode.activity.WelcomeActivity;
import thammasat.callforcode.databinding.FragmentLoginBinding;

public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;
    private Animation anim;
    private BounceInterpolator interpolator;
    private CallbackManager callbackManager = CallbackManager.Factory.create();
    private GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 1234;
    private static final String TAG = WelcomeActivity.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTypeface();
        initInstance();
        eventListenerBinding();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        binding.llTwitter.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void initInstance() {
        anim = AnimationUtils.loadAnimation(this.getContext(), R.anim.bounce);
        interpolator = new BounceInterpolator();
        anim.setInterpolator(interpolator);

        binding.tvProjectName.setTypeface(bold);
        binding.tvLogin.setTypeface(regular);
        binding.tvSignUp.setTypeface(regular);
        binding.tvSignUpRec.setTypeface(light);
        binding.tvOr.setTypeface(light);

        binding.llFacebook.setTypeface(regular);
        binding.llFacebook.setReadPermissions("email");
        binding.llFacebook.setFragment(this);
        binding.llTwitter.setTypeface(regular);
        binding.llTwitter.setText(getResources().getString(R.string.login_with_twitter));
        binding.llTwitter.setTextSize(16);
        binding.llGoogle.setTypeface(regular);
        binding.llGoogle.setTextSize(16);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void eventListenerBinding() {
        binding.tvLogin.setOnClickListener(tvLoginClick());
        binding.llTwitter.setOnClickListener(llTwitterClick());
        binding.llFacebook.setOnClickListener(llFacebookClick());
        binding.llGoogle.setOnClickListener(llGoogleClick());
        binding.tvSignUp.setOnClickListener(tvSignUpClick());
        binding.llFacebook.registerCallback(callbackManager, llFacebookCallBack());
        binding.llTwitter.setCallback(llTwitterCallback());
    }

    @NonNull
    private View.OnClickListener llGoogleClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llGoogle.startAnimation(anim);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        };
    }

    @NonNull
    private Callback<TwitterSession> llTwitterCallback() {
        return new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                toasty("success", "Logged in");
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                getActivity().finish();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e(TAG, "Twitter authenticated: " + exception.getMessage());
                toasty("error", "Failed to authenticate. Please try again.");
            }
        };
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            toasty("success", "Logged in");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            getActivity().finish();
        } catch (ApiException e) {
            Log.e(TAG, "Google authenticated: " + e.getMessage());
            toasty("error", "Failed to authenticate. Please try again.");
        }
    }

    @NonNull
    private FacebookCallback<LoginResult> llFacebookCallBack() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                toasty("success", "Logged in");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                getActivity().finish();
            }

            @Override
            public void onCancel() {
                toasty("warning", "Canceled to authenticate.");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG, "Facebook authenticated: " + exception.getMessage());
                toasty("error", "Failed to authenticate. Please try again.");
            }
        };
    }

    @NonNull
    private View.OnClickListener tvSignUpClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvSignUp.startAnimation(anim);
                SignUpFragment signUpFragment = new SignUpFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmentContainer, signUpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };
    }

    @NonNull
    private View.OnClickListener llFacebookClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llFacebook.startAnimation(anim);
            }
        };
    }

    @NonNull
    private View.OnClickListener llTwitterClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llTwitter.startAnimation(anim);
            }
        };
    }

    @NonNull
    private View.OnClickListener tvLoginClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvLogin.startAnimation(anim);
                SignInFragment signInFragment = new SignInFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmentContainer, signInFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };
    }
}
