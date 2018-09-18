package thammasat.callforcode.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import thammasat.callforcode.R;
import thammasat.callforcode.activity.InfoActivity;
import thammasat.callforcode.adapter.ListAdapter;
import thammasat.callforcode.adapter.StatsAdapter;
import thammasat.callforcode.databinding.FragmentStatsBinding;
import thammasat.callforcode.manager.OnItemClick;
import thammasat.callforcode.model.Disaster;

public class StatsFragment extends BaseFragment {

    private FragmentStatsBinding binding;
    private List<Disaster> disasterListFlood = new ArrayList<>();
    private List<Disaster> disasterListFlashFlood = new ArrayList<>();
    private List<Disaster> disasterListEarthquake = new ArrayList<>();
    private List<Disaster> disasterListVolcano = new ArrayList<>();
    private List<Disaster> disasterListTropicalCyclone = new ArrayList<>();
    private List<Disaster> disasterListOthers = new ArrayList<>();
    private StatsAdapter statsAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_stats, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTypeface();
        setTab();
        getDisasterList();
        setBarChart();
    }

    private void setBarChart() {
        final ArrayList<String> disasterName = new ArrayList<>();
        disasterName.add(0, "Cold Wave");
        disasterName.add(1, "Drought");
        disasterName.add(2, "Earthquake");
        disasterName.add(3, "Epidemic");
        disasterName.add(4, "Extratropical Cyclone");
        disasterName.add(5, "Fire");
        disasterName.add(6, "Wild Fire");
        disasterName.add(7, "Flash Flood");
        disasterName.add(8, "Flood");
        disasterName.add(9, "Heat Wave");
        disasterName.add(10, "Insect Infestation");
        disasterName.add(11, "Land Slide");
        disasterName.add(12, "Mud Slide");
        disasterName.add(13, "Severe Local Storm");
        disasterName.add(14, "Snow Avalanche");
        disasterName.add(15, "Storm Surge");
        disasterName.add(16, "Technological Disaster");
        disasterName.add(17, "Tropical Cyclone");
        disasterName.add(18, "Tsunami");
        disasterName.add(19, "Volcano");
        disasterName.add(20, "Others");

        final ArrayList<Integer> disasterNumber = new ArrayList<>();
        disasterNumber.add(0, 0);
        disasterNumber.add(1, 0);
        disasterNumber.add(2, 0);
        disasterNumber.add(3, 0);
        disasterNumber.add(4, 0);
        disasterNumber.add(5, 0);
        disasterNumber.add(6, 0);
        disasterNumber.add(7, 0);
        disasterNumber.add(8, 0);
        disasterNumber.add(9, 0);
        disasterNumber.add(10, 0);
        disasterNumber.add(11, 0);
        disasterNumber.add(12, 0);
        disasterNumber.add(13, 0);
        disasterNumber.add(14, 0);
        disasterNumber.add(15, 0);
        disasterNumber.add(16, 0);
        disasterNumber.add(17, 0);
        disasterNumber.add(18, 0);
        disasterNumber.add(19, 0);
        disasterNumber.add(20, 0);

        for (int i = 0; i < disasterList.size(); i++) {
            switch (disasterList.get(i).getSeverity().toLowerCase()) {
                case "cold wave":
                    disasterNumber.set(20, disasterNumber.get(0) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "drought":
                    disasterNumber.set(20, disasterNumber.get(1) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "earthquake":
                    disasterNumber.set(2, disasterNumber.get(2) + 1);
                    disasterListEarthquake.add(disasterList.get(i));
                    break;
                case "epidemic":
                    disasterNumber.set(20, disasterNumber.get(3) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "extratropical cyclone":
                    disasterNumber.set(20, disasterNumber.get(4) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "fire":
                    disasterNumber.set(20, disasterNumber.get(5) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "wild fire":
                    disasterNumber.set(20, disasterNumber.get(6) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "flash flood":
                    disasterNumber.set(7, disasterNumber.get(7) + 1);
                    disasterListFlashFlood.add(disasterList.get(i));
                    break;
                case "flood":
                    disasterNumber.set(8, disasterNumber.get(8) + 1);
                    disasterListFlood.add(disasterList.get(i));
                    break;
                case "heat wave":
                    disasterNumber.set(20, disasterNumber.get(9) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "insect infestation":
                    disasterNumber.set(20, disasterNumber.get(10) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "land slide":
                    disasterNumber.set(20, disasterNumber.get(11) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "mud slide":
                    disasterNumber.set(20, disasterNumber.get(12) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "severe local storm":
                    disasterNumber.set(20, disasterNumber.get(13) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "snow avalanche":
                    disasterNumber.set(20, disasterNumber.get(14) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "storm surge":
                    disasterNumber.set(20, disasterNumber.get(15) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "technological disaster":
                    disasterNumber.set(20, disasterNumber.get(16) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "tropical cyclone":
                    disasterNumber.set(17, disasterNumber.get(17) + 1);
                    disasterListTropicalCyclone.add(disasterList.get(i));
                    break;
                case "tsunami":
                    disasterNumber.set(20, disasterNumber.get(18) + 1);
                    disasterListOthers.add(disasterList.get(i));
                    break;
                case "volcano":
                    disasterNumber.set(19, disasterNumber.get(19) + 1);
                    disasterListVolcano.add(disasterList.get(i));
                    break;
                default:
                    disasterNumber.set(20, disasterNumber.get(20) + 1);
                    disasterListOthers.add(disasterList.get(i));
            }
        }

        final ArrayList<PieEntry> entries = new ArrayList<>();
        int n = 0;
        for (int i = 0; i < disasterName.size(); i++) {
            if (disasterNumber.get(i) != 0) {
                entries.add(new PieEntry(disasterNumber.get(i), disasterName.get(i)));
                n++;
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setSelectionShift(10);
        dataSet.setValueTextSize(14);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueFormatter(new PercentFormatter());


        PieData data = new PieData(dataSet);
        binding.barChart.setData(data);

        binding.barChart.setHoleRadius(40);
        binding.barChart.setTransparentCircleRadius(50);
        binding.barChart.animateY(3000, Easing.EasingOption.EaseOutBounce);
        binding.barChart.setUsePercentValues(true);

        binding.barChart.setEntryLabelTypeface(bold);
        binding.barChart.setEntryLabelColor(getResources().getColor(R.color.colorWhite));
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.setCenterTextTypeface(light);
        binding.barChart.setCenterTextSize(16);

        binding.barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pe = (PieEntry) e;
                binding.tvTab.setVisibility(View.GONE);
                switch (pe.getLabel().toLowerCase()) {
                    case "flood":
                        binding.barChart.setCenterText(disasterListFlood.size() + " Items");
                        statsAdapter = new StatsAdapter(getContext(), disasterListFlood);
                        binding.recyclerView.setLayoutManager(linearLayoutManager);
                        binding.recyclerView.setAdapter(statsAdapter);
                        statsAdapter.setOnItemClick(new OnItemClick() {
                            @Override
                            public void onItemClick(Disaster disaster, long time, int distance) {
                                Intent intent = new Intent(getContext(), InfoActivity.class);
                                intent.putExtra("disaster", disaster);
                                intent.putExtra("time", time);
                                intent.putExtra("distance", distance);
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                            }
                        });
                        break;
                    case "flash flood":
                        binding.barChart.setCenterText(disasterListFlashFlood.size() + " Items");
                        statsAdapter = new StatsAdapter(getContext(), disasterListFlashFlood);
                        binding.recyclerView.setLayoutManager(linearLayoutManager);
                        binding.recyclerView.setAdapter(statsAdapter);
                        statsAdapter.setOnItemClick(new OnItemClick() {
                            @Override
                            public void onItemClick(Disaster disaster, long time, int distance) {
                                Intent intent = new Intent(getContext(), InfoActivity.class);
                                intent.putExtra("disaster", disaster);
                                intent.putExtra("time", time);
                                intent.putExtra("distance", distance);
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                            }
                        });
                        break;
                    case "earthquake":
                        binding.barChart.setCenterText(disasterListEarthquake.size() + " Items");
                        statsAdapter = new StatsAdapter(getContext(), disasterListEarthquake);
                        binding.recyclerView.setLayoutManager(linearLayoutManager);
                        binding.recyclerView.setAdapter(statsAdapter);
                        statsAdapter.setOnItemClick(new OnItemClick() {
                            @Override
                            public void onItemClick(Disaster disaster, long time, int distance) {
                                Intent intent = new Intent(getContext(), InfoActivity.class);
                                intent.putExtra("disaster", disaster);
                                intent.putExtra("time", time);
                                intent.putExtra("distance", distance);
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                            }
                        });
                        break;
                    case "volcano":
                        binding.barChart.setCenterText(disasterListVolcano.size() + " Items");
                        statsAdapter = new StatsAdapter(getContext(), disasterListVolcano);
                        binding.recyclerView.setLayoutManager(linearLayoutManager);
                        binding.recyclerView.setAdapter(statsAdapter);
                        statsAdapter.setOnItemClick(new OnItemClick() {
                            @Override
                            public void onItemClick(Disaster disaster, long time, int distance) {
                                Intent intent = new Intent(getContext(), InfoActivity.class);
                                intent.putExtra("disaster", disaster);
                                intent.putExtra("time", time);
                                intent.putExtra("distance", distance);
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                            }
                        });
                        break;
                    case "tropical cyclone":
                        binding.barChart.setCenterText(disasterListTropicalCyclone.size() + " Items");
                        statsAdapter = new StatsAdapter(getContext(), disasterListTropicalCyclone);
                        binding.recyclerView.setLayoutManager(linearLayoutManager);
                        binding.recyclerView.setAdapter(statsAdapter);
                        statsAdapter.setOnItemClick(new OnItemClick() {
                            @Override
                            public void onItemClick(Disaster disaster, long time, int distance) {
                                Intent intent = new Intent(getContext(), InfoActivity.class);
                                intent.putExtra("disaster", disaster);
                                intent.putExtra("time", time);
                                intent.putExtra("distance", distance);
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                            }
                        });
                        break;
                    default:
                        binding.barChart.setCenterText(disasterListOthers.size() + " Items");
                        statsAdapter = new StatsAdapter(getContext(), disasterListOthers);
                        binding.recyclerView.setLayoutManager(linearLayoutManager);
                        binding.recyclerView.setAdapter(statsAdapter);
                        statsAdapter.setOnItemClick(new OnItemClick() {
                            @Override
                            public void onItemClick(Disaster disaster, long time, int distance) {
                                Intent intent = new Intent(getContext(), InfoActivity.class);
                                intent.putExtra("disaster", disaster);
                                intent.putExtra("time", time);
                                intent.putExtra("distance", distance);
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    private void setTab() {
        binding.tvWeek.setTypeface(regular);
        binding.tvMonth.setTypeface(bold);
        binding.tvYear.setTypeface(regular);
        binding.tvTab.setTypeface(regular);

        binding.tvWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvWeek.setBackgroundResource(R.drawable.background_button_week_pressed);
                binding.tvWeek.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.tvMonth.setBackgroundResource(R.drawable.background_button_month);
                binding.tvMonth.setTextColor(getResources().getColor(R.color.colorFontTitle));
                binding.tvYear.setBackgroundResource(R.drawable.background_button_year);
                binding.tvYear.setTextColor(getResources().getColor(R.color.colorFontTitle));
                binding.tvWeek.setTypeface(bold);
                binding.tvMonth.setTypeface(regular);
                binding.tvYear.setTypeface(regular);
            }
        });

        binding.tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvWeek.setBackgroundResource(R.drawable.background_button_week);
                binding.tvWeek.setTextColor(getResources().getColor(R.color.colorFontTitle));
                binding.tvMonth.setBackgroundResource(R.drawable.background_button_month_pressed);
                binding.tvMonth.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.tvYear.setBackgroundResource(R.drawable.background_button_year);
                binding.tvYear.setTextColor(getResources().getColor(R.color.colorFontTitle));
                binding.tvWeek.setTypeface(regular);
                binding.tvMonth.setTypeface(bold);
                binding.tvYear.setTypeface(regular);
            }
        });

        binding.tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvWeek.setBackgroundResource(R.drawable.background_button_week);
                binding.tvWeek.setTextColor(getResources().getColor(R.color.colorFontTitle));
                binding.tvMonth.setBackgroundResource(R.drawable.background_button_month);
                binding.tvMonth.setTextColor(getResources().getColor(R.color.colorFontTitle));
                binding.tvYear.setBackgroundResource(R.drawable.background_button_year_pressed);
                binding.tvYear.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.tvWeek.setTypeface(regular);
                binding.tvMonth.setTypeface(regular);
                binding.tvYear.setTypeface(bold);
            }
        });
    }
}
