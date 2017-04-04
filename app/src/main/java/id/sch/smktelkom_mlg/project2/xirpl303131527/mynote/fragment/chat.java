package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class chat extends Fragment {


    public chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chat,
                container, false);

        return rootView;
    }

}
