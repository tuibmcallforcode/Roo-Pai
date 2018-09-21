package thammasat.callforcode.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thammasat.callforcode.R;
import thammasat.callforcode.activity.BaseActivity;
import thammasat.callforcode.activity.InfoActivity;
import thammasat.callforcode.adapter.ListAdapter;
import thammasat.callforcode.adapter.RelatedListAdapter;
import thammasat.callforcode.databinding.FragmentInfoBinding;
import thammasat.callforcode.manager.OnItemClick;
import thammasat.callforcode.model.Disaster;

public class InfoFragment extends BaseFragment {

    private FragmentInfoBinding binding;
    private Disaster disaster;
    private long time;
    private int distance;
    private RelatedListAdapter listAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            disaster = (Disaster) getArguments().getSerializable("disaster");
            time = getArguments().getLong("time");
            distance = getArguments().getInt("distance");
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
        getDisasterList();
        getRelatedList();
        initInstance();
    }

    private void initInstance() {
        binding.tvDistance.setTypeface(light);
        binding.tvDuration.setTypeface(light);
        binding.tvTag.setTypeface(light);
        binding.tvDescription.setTypeface(regular);
        binding.tvInterest.setTypeface(light);

        binding.tvTag.setText(disaster.getSeverity());
        binding.tvDescription.setText(disaster.getDescription());
        binding.tvDuration.setText(time + " days ago");
        if (distance != 0)
            binding.tvDistance.setText(" | " + distance + " km away");
        linearLayoutManager = new LinearLayoutManager(getContext());
        listAdapter = new RelatedListAdapter(getContext(), relatedList);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(listAdapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
        listAdapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onItemClick(Disaster disaster, long time, int distance) {
                Intent intent = new Intent(getContext(), InfoActivity.class);
                intent.putExtra("disaster", disaster);
                intent.putExtra("time", time);
                intent.putExtra("distance", distance);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                getActivity().finish();
            }
        });

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
