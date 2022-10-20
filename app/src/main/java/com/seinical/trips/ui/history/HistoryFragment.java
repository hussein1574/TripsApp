package com.seinical.trips.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seinical.trips.R;
import com.seinical.trips.data.TempTripsDataManager;
import com.seinical.trips.data.TripsRecyclerAdapter;
import com.seinical.trips.databinding.FragmentHistoryBinding;

public class HistoryFragment extends Fragment {
    public TripsRecyclerAdapter adapter;
    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //omitted other object init for clarity

        RecyclerView recyclerView = root.findViewById(R.id.upcoming_recyclerview);
        adapter = new TripsRecyclerAdapter(getContext(), TempTripsDataManager.getInstance().getHistory());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}