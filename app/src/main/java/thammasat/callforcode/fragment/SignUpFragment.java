package thammasat.callforcode.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;

import thammasat.callforcode.R;
import thammasat.callforcode.databinding.FragmentSignupBinding;

public class SignUpFragment extends Fragment {

    private FragmentSignupBinding binding;
    private Typeface bold, regular, light;
    private Animation anim;
    private BounceInterpolator interpolator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_signup, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initInstance();
        eventListenerBinding();
    }

    private void eventListenerBinding() {

    }

    private void initInstance() {
        bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bold.ttf");
        regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Regular.ttf");
        light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Light.ttf");

        anim = AnimationUtils.loadAnimation(this.getContext(), R.anim.bounce);
        interpolator = new BounceInterpolator();
        anim.setInterpolator(interpolator);
    }
}
