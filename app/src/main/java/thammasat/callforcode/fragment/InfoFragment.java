package thammasat.callforcode.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thammasat.callforcode.R;
import thammasat.callforcode.activity.BaseActivity;
import thammasat.callforcode.databinding.FragmentInfoBinding;
import thammasat.callforcode.model.Disaster;

public class InfoFragment extends BaseFragment {

    private FragmentInfoBinding binding;
    private Disaster disaster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            disaster = (Disaster) getArguments().getSerializable("disaster");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_info, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTypeface();
        initInstance();
    }

    private void initInstance() {
        binding.tvCountry.setTypeface(light);
        binding.tvDistance.setTypeface(light);
        binding.tvDuration.setTypeface(light);
        binding.tvTitle.setTypeface(bold);
        binding.tvTag.setTypeface(light);
        binding.tvDescription.setTypeface(regular);

        binding.tvCountry.setText(disaster.getCountry());
        binding.tvTag.setText(disaster.getDisaster());
        binding.tvTitle.setText(disaster.getTitle());
        binding.tvDescription.setText(disaster.getBody());
    }
}
