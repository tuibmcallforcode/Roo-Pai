package thammasat.callforcode.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;

import thammasat.callforcode.R;
import thammasat.callforcode.activity.MainActivity;
import thammasat.callforcode.databinding.FragmentSigninBinding;

public class SignInFragment extends BaseFragment {

    private FragmentSigninBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_signin, container, false);
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

    private void initInstance() {
        anim = AnimationUtils.loadAnimation(this.getContext(), R.anim.bounce);
        interpolator = new BounceInterpolator();
        anim.setInterpolator(interpolator);

        binding.tvProjectName.setTypeface(bold);
        binding.tvLogin.setTypeface(regular);
        binding.tvForgotPassword.setTypeface(light);
        binding.tvSignUpRec.setTypeface(light);
        binding.tvSignUp.setTypeface(bold);
        binding.etEmail.setTypeface(regular);
        binding.etPassword.setTypeface(regular);
    }

    private void eventListenerBinding() {
        binding.tvLogin.setOnClickListener(tvLoginClick());
        binding.tvForgotPassword.setOnClickListener(tvForgotPasswordClick());
        binding.llSignUp.setOnClickListener(llSignUpClick());
    }

    @NonNull
    private View.OnClickListener llSignUpClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llSignUp.startAnimation(anim);
                SignUpFragment signUpFragment = new SignUpFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmentContainer, signUpFragment);
                transaction.commit();
            }
        };
    }

    @NonNull
    private View.OnClickListener tvForgotPasswordClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvForgotPassword.startAnimation(anim);
                RecoverFragment recoverFragment = new RecoverFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmentContainer, recoverFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };
    }

    @NonNull
    private View.OnClickListener tvLoginClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvLogin.startAnimation(anim);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                getActivity().finish();
            }
        };
    }
}
