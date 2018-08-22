package thammasat.callforcode.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        getDisasterList();
        initInstance();
        eventListenerBinding();
    }

    private void initInstance() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        listAdapter = new ListAdapter(getContext(), disasterList);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onItemClick(Disaster disaster) {
                Intent intent = new Intent(getContext(), InfoActivity.class);
                intent.putExtra("disaster", disaster);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });
    }

    private void eventListenerBinding() {

    }
}
