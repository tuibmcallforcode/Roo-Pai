package thammasat.callforcode.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
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
import thammasat.callforcode.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private Typeface bold, regular, light;
    private Animation anim;
    private BounceInterpolator interpolator;

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

        initInstance();
        eventListenerBinding();
    }

    private void initInstance() {
        bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bold.ttf");
        regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Regular.ttf");
        light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Light.ttf");

        anim = AnimationUtils.loadAnimation(this.getContext(), R.anim.bounce);
        interpolator = new BounceInterpolator();
        anim.setInterpolator(interpolator);

        binding.tvProjectName.setTypeface(bold);
        binding.tvLogin.setTypeface(regular);
        binding.tvSignUp.setTypeface(regular);
        binding.tvFacebook.setTypeface(regular);
        binding.tvTwitter.setTypeface(regular);
        binding.tvSignUpRec.setTypeface(light);
        binding.tvOr.setTypeface(light);
    }

    private void eventListenerBinding() {
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
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
        });

        binding.llTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llTwitter.startAnimation(anim);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        binding.llFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llFacebook.startAnimation(anim);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
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
        });
    }
}
