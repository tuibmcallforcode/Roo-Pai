package thammasat.callforcode.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import thammasat.callforcode.R;
import thammasat.callforcode.databinding.FragmentStatsBinding;

public class StatsFragment extends BaseFragment {

    private FragmentStatsBinding binding;

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
                    disasterNumber.set(0, disasterNumber.get(0) + 1);
                    break;
                case "drought":
                    disasterNumber.set(1, disasterNumber.get(1) + 1);
                    break;
                case "earthquake":
                    disasterNumber.set(2, disasterNumber.get(2) + 1);
                    break;
                case "epidemic":
                    disasterNumber.set(3, disasterNumber.get(3) + 1);
                    break;
                case "extratropical cyclone":
                    disasterNumber.set(4, disasterNumber.get(4) + 1);
                    break;
                case "fire":
                    disasterNumber.set(5, disasterNumber.get(5) + 1);
                    break;
                case "wild fire":
                    disasterNumber.set(6, disasterNumber.get(6) + 1);
                    break;
                case "flash flood":
                    disasterNumber.set(7, disasterNumber.get(7) + 1);
                    break;
                case "flood":
                    disasterNumber.set(8, disasterNumber.get(8) + 1);
                    break;
                case "heat wave":
                    disasterNumber.set(9, disasterNumber.get(9) + 1);
                    break;
                case "insect infestation":
                    disasterNumber.set(10, disasterNumber.get(10) + 1);
                    break;
                case "land slide":
                    disasterNumber.set(11, disasterNumber.get(11) + 1);
                    break;
                case "mud slide":
                    disasterNumber.set(12, disasterNumber.get(12) + 1);
                    break;
                case "severe local storm":
                    disasterNumber.set(13, disasterNumber.get(13) + 1);
                    break;
                case "snow avalanche":
                    disasterNumber.set(14, disasterNumber.get(14) + 1);
                    break;
                case "storm surge":
                    disasterNumber.set(15, disasterNumber.get(15) + 1);
                    break;
                case "technological disaster":
                    disasterNumber.set(16, disasterNumber.get(16) + 1);
                    break;
                case "tropical cyclone":
                    disasterNumber.set(17, disasterNumber.get(17) + 1);
                    break;
                case "tsunami":
                    disasterNumber.set(18, disasterNumber.get(18) + 1);
                    break;
                case "volcano":
                    disasterNumber.set(19, disasterNumber.get(19) + 1);
                    break;
                default:
                    disasterNumber.set(20, disasterNumber.get(20) + 1);
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
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setValueLinePart1Length(0.7f);
        dataSet.setValueLinePart2Length(0.3f);

        PieData data = new PieData(dataSet);
        binding.barChart.setData(data);

        binding.barChart.setHoleRadius(40);
        binding.barChart.setTransparentCircleRadius(50);
        binding.barChart.animateY(3000, Easing.EasingOption.EaseOutBounce);
        binding.barChart.setUsePercentValues(true);

        binding.barChart.setEntryLabelTypeface(regular);
        binding.barChart.setEntryLabelColor(getResources().getColor(R.color.colorFontTitle));
    }

    private void setTab() {
        binding.tvWeek.setTypeface(regular);
        binding.tvMonth.setTypeface(bold);
        binding.tvYear.setTypeface(regular);

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
