package thammasat.callforcode.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;

import thammasat.callforcode.R;
import thammasat.callforcode.activity.BaseActivity;
import thammasat.callforcode.activity.InfoActivity;
import thammasat.callforcode.activity.WebViewActivity;
import thammasat.callforcode.adapter.ListAdapter;
import thammasat.callforcode.adapter.RelatedListAdapter;
import thammasat.callforcode.databinding.FragmentInfoBinding;
import thammasat.callforcode.manager.InternalStorage;
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
        binding.tvDuration.setText(time + " " + getResources().getString(R.string.days_ago));
        if (distance != 0)
            binding.tvDistance.setText(" | " + distance + " " + getResources().getString(R.string.km_away));
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

        binding.llTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (disaster.getSeverity().toLowerCase()) {
                    case "flood":
                        showTipsDialog("flood");
                        break;
                    case "earthquake":
                        showTipsDialog("earthquake");
                        break;
                    case "tropical cyclone":
                        showTipsDialog("tornado");
                        break;
                }
            }
        });
        binding.tvSource.setTypeface(regular);
        binding.llSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), WebViewActivity.class);
                i.putExtra("path", disaster.getSource());
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

            }
        });

        binding.llPrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (disaster.getSeverity().toLowerCase()) {
                    case "flood":
                        showPrepareDialog("flood");
                        break;
                    case "earthquake":
                        showPrepareDialog("earthquake");
                        break;
                    case "tropical cyclone":
                        showPrepareDialog("tornado");
                        break;
                }
            }
        });

        switch (disaster.getSeverity().toLowerCase()) {
            case "cold wave":
                binding.civTag.setImageResource(R.drawable.white_cold_wave);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "drought":
                binding.civTag.setImageResource(R.drawable.white_drought);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "earthquake":
                binding.civTag.setImageResource(R.drawable.white_earth_quake);
                binding.llPrepareness.setVisibility(View.VISIBLE);
                break;
            case "epidemic":
                binding.civTag.setImageResource(R.drawable.white_epidemic);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "extratropical cyclone":
                binding.civTag.setImageResource(R.drawable.white_extratropical_cyclone);
                binding.llPrepareness.setVisibility(View.VISIBLE);
                break;
            case "fire":
                binding.civTag.setImageResource(R.drawable.white_fire);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "wild fire":
                binding.civTag.setImageResource(R.drawable.white_fire);
                break;
            case "flash flood":
                binding.civTag.setImageResource(R.drawable.white_flash_flood);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "flood":
                binding.civTag.setImageResource(R.drawable.white_flood);
                binding.llPrepareness.setVisibility(View.VISIBLE);
                break;
            case "heat wave":
                binding.civTag.setImageResource(R.drawable.white_heat_wave);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "insect infestation":
                binding.civTag.setImageResource(R.drawable.white_insect_infestation);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "land slide":
                binding.civTag.setImageResource(R.drawable.white_land_slide);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "mud slide":
                binding.civTag.setImageResource(R.drawable.white_mud_slide);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "severe local storm":
                binding.civTag.setImageResource(R.drawable.white_severe_local_storm);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "snow avalanche":
                binding.civTag.setImageResource(R.drawable.white_snow_avalanche);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "storm surge":
                binding.civTag.setImageResource(R.drawable.white_storm_surge);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "technological disaster":
                binding.civTag.setImageResource(R.drawable.white_technological_disaster);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "tropical cyclone":
                binding.civTag.setImageResource(R.drawable.white_tropical_cyclone);
                binding.llPrepareness.setVisibility(View.VISIBLE);
                break;
            case "tsunami":
                binding.civTag.setImageResource(R.drawable.white_tsunami);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            case "volcano":
                binding.civTag.setImageResource(R.drawable.white_volcano);
                binding.llPrepareness.setVisibility(View.GONE);
                break;
            default:
                binding.civTag.setImageResource(R.drawable.white_technological_disaster);
                binding.llPrepareness.setVisibility(View.GONE);
        }
    }

    private void showTipsDialog(String category) {
        String tips = "";
        try {
            tips = (String) InternalStorage.readObject(getContext(), category + "Tips");
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

        final TextView tvTitle, tvDescription;
        ImageView ivClose;

        final MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .typeface("Bold.ttf", "Regular.ttf")
                .backgroundColor(Color.parseColor("#00B0FF"))
                .widgetColor(Color.parseColor("#00B0FF"))
                .customView(R.layout.tips_dialog, true)
                .build();

        tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(bold);
        tvDescription = (TextView) dialog.findViewById(R.id.tvDescription);
        tvDescription.setTypeface(regular);
        tvDescription.setText(Html.fromHtml(tips));
        ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showPrepareDialog(String category) {
        String tips = "";
        try {
            tips = (String) InternalStorage.readObject(getContext(), category + "Prepare");
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

        final TextView tvTitle, tvDescription;
        ImageView ivClose;

        final MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .typeface("Bold.ttf", "Regular.ttf")
                .backgroundColor(Color.parseColor("#F9A825"))
                .widgetColor(Color.parseColor("#F9A825"))
                .customView(R.layout.prepare_dialog, true)
                .build();

        tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(bold);
        tvDescription = (TextView) dialog.findViewById(R.id.tvDescription);
        tvDescription.setTypeface(regular);
        tvDescription.setText(Html.fromHtml(tips));
        ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
