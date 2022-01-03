package com.veritasliberat.esp_trainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected String[] sessionDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CustomAdapter(sessionDetails);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void initDataset() {
        Session[] sessions = MainActivity.sessionDao.getAllSessions();
        List<String> sessionStrings = new ArrayList<>();
        for (Session session : sessions) {
            System.out.println(session.endTimestamp + " " + session.score);
            sessionStrings.add(session.endTimestamp + " " + session.score);
        }
        sessionDetails = new String[sessionStrings.size()];
        sessionDetails = sessionStrings.toArray(sessionDetails);
        System.out.println(Arrays.toString(sessionDetails));
    }
}
