package thammasat.callforcode.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thammasat.callforcode.activity.MainActivity;
import thammasat.callforcode.databinding.FragmentPermissionBinding;

import thammasat.callforcode.R;

public class PermissionFragment extends BaseFragment {

    private FragmentPermissionBinding binding;
    private String permission;
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            permission = getArguments().getString("permission");
            type = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_permission, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAnimation();
        setTypeface();

        binding.tvPermission.setText(permission);
        binding.tvPermission.setTypeface(regular);
        binding.tvPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {
                    case 1:
                        binding.tvPermission.startAnimation(anim);
                        goToActivity(MainActivity.class, 0, 0, true);
                        break;
                }
            }
        });
    }
}
