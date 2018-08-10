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
import thammasat.callforcode.databinding.FragmentRecoverBinding;

public class RecoverFragment extends BaseFragment {

    private FragmentRecoverBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_recover, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTypeface();
        setAnimation();
        initInstance();
        eventListenerBinding();
    }

    private void initInstance() {
        binding.tvProjectName.setTypeface(bold);
        binding.tvConfirm.setTypeface(regular);
        binding.etEmail.setTypeface(regular);
    }

    private void eventListenerBinding() {
        binding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvConfirm.startAnimation(anim);
                getFragmentManager().popBackStack();
            }
        });
    }
}
