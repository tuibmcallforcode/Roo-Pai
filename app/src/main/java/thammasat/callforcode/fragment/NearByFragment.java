package thammasat.callforcode.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import thammasat.callforcode.R;
import thammasat.callforcode.activity.InfoActivity;
import thammasat.callforcode.adapter.ListAdapter;
import thammasat.callforcode.databinding.FragmentNearByBinding;
import thammasat.callforcode.manager.OnItemClick;
import thammasat.callforcode.manager.Singleton;
import thammasat.callforcode.model.Disaster;

public class NearByFragment extends BaseFragment {

    private FragmentNearByBinding binding;
    private ListAdapter listAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Singleton singleton = Singleton.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDisasterList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_near_by, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initInstance() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        listAdapter = new ListAdapter(getContext(), disasterList, true);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemClick(new OnItemClick() {
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
    }

    @Override
    public void onStart() {
        super.onStart();
        initInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.recyclerView.setAdapter(null);
    }
}
