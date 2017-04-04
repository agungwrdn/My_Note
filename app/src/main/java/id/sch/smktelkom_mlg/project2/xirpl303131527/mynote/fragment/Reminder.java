package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Reminder extends Fragment {

    private FloatingActionButton addReminder;
    public Reminder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reminder,
                container, false);

        addReminder = (FloatingActionButton) rootView.findViewById(R.id.btnReminder);
        return rootView;
    }

}
