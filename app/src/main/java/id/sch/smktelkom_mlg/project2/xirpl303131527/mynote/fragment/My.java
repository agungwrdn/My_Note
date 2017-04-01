package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class My extends Fragment {
    private RecyclerView mMemoList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBmemo, mDBmemoUser;
    private ProgressBar pbMemo;

    public My() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mDBmemo = mDB.getReference().child("todo");
        mDBmemoUser = mDBmemo.child(mAuth.getCurrentUser().getUid().toString());

        mMemoList = (RecyclerView) getView().findViewById(R.id.recyclerViewNotes);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mMemoList.setLayoutManager(layoutManager);
        mMemoList.setHasFixedSize(true);

        mDBmemoUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long jumlahChild;
                jumlahChild = dataSnapshot.getChildrenCount();
                String strValue = jumlahChild.toString();
                Log.d("FirebaseCounter", "Jumlah child " + strValue);
                if (jumlahChild == 0) {
                    Log.d("FirebaseCounter", "No item yet !");
                    pbMemo.setVisibility(View.GONE);
                } else {
                    pbMemo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FirebaseCounter", databaseError.getMessage());
                pbMemo.setVisibility(View.GONE);
            }
        });
    }
}
