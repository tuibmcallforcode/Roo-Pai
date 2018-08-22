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

        binding.tvCountry.setText(disaster.getTime());
        binding.tvTag.setText(disaster.getSeverity());
        binding.tvTitle.setText(disaster.getTitle());
        binding.tvDescription.setText(disaster.getDescription());

        switch (disaster.getSeverity().toLowerCase()) {
            case "cold wave":
                binding.civTag.setImageResource(R.drawable.white_cold_wave);
                break;
            case "drought":
                binding.civTag.setImageResource(R.drawable.white_drought);
                break;
            case "earthquake":
                binding.civTag.setImageResource(R.drawable.white_earth_quake);
                break;
            case "epidemic":
                binding.civTag.setImageResource(R.drawable.white_epidemic);
                break;
            case "extratropical cyclone":
                binding.civTag.setImageResource(R.drawable.white_extratropical_cyclone);
                break;
            case "fire":
                binding.civTag.setImageResource(R.drawable.white_fire);
                break;
            case "wild fire":
                binding.civTag.setImageResource(R.drawable.white_fire);
                break;
            case "flash flood":
                binding.civTag.setImageResource(R.drawable.white_flash_flood);
                break;
            case "flood":
                binding.civTag.setImageResource(R.drawable.white_flood);
                break;
            case "heat wave":
                binding.civTag.setImageResource(R.drawable.white_heat_wave);
                break;
            case "insect infestation":
                binding.civTag.setImageResource(R.drawable.white_insect_infestation);
                break;
            case "land slide":
                binding.civTag.setImageResource(R.drawable.white_land_slide);
                break;
            case "mud slide":
                binding.civTag.setImageResource(R.drawable.white_mud_slide);
                break;
            case "severe local storm":
                binding.civTag.setImageResource(R.drawable.white_severe_local_storm);
                break;
            case "snow avalanche":
                binding.civTag.setImageResource(R.drawable.white_snow_avalanche);
                break;
            case "storm surge":
                binding.civTag.setImageResource(R.drawable.white_storm_surge);
                break;
            case "technological disaster":
                binding.civTag.setImageResource(R.drawable.white_technological_disaster);
                break;
            case "tropical cyclone":
                binding.civTag.setImageResource(R.drawable.white_tropical_cyclone);
                break;
            case "tsunami":
                binding.civTag.setImageResource(R.drawable.white_tsunami);
                break;
            case "volcano":
                binding.civTag.setImageResource(R.drawable.white_volcano);
                break;
            default:
                binding.civTag.setImageResource(R.drawable.microphone);
        }
    }
}
