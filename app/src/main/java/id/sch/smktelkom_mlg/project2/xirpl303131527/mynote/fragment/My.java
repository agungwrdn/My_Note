package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.AddMemo;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.EditMemo;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.R;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.model.memo;


/**
 * A simple {@link Fragment} subclass.
 */
public class My extends Fragment {
    private RecyclerView mMemoList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBmemo, mDBmemoUser;
    private ProgressBar pbMemo;
    private TextView tvNoNote;

    public My() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my,
                container, false);
        tvNoNote = (TextView) rootView.findViewById(R.id.textViewNoNote);
        FloatingActionButton Madd = (FloatingActionButton) rootView.findViewById(R.id.buttonadd);
        Madd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked();
            }
        });

        return rootView;
    }

    private void onAddClicked() {
        Intent intent = new Intent(getActivity().getApplication(),AddMemo.class);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mDBmemo = mDB.getReference().child("memo");
        mDBmemoUser = mDBmemo.child(mAuth.getCurrentUser().getUid().toString());

        mMemoList = (RecyclerView) getView().findViewById(R.id.recyclerViewMemo);
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
                    tvNoNote.setVisibility(View.VISIBLE);
                 //   pbMemo.setVisibility(View.GONE);
                } else {
                    tvNoNote.setVisibility(View.GONE);
                   // pbMemo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               Log.d("FirebaseCounter", databaseError.getMessage());
                tvNoNote.setVisibility(View.VISIBLE);
                //pbMemo.setVisibility(View.GONE);
                tvNoNote.setText("Error."+""+ databaseError.getMessage());
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<memo, MemoViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<memo, MemoViewHolder>(
                memo.class,
                R.layout.memo,
                MemoViewHolder.class,
                mDBmemoUser
        ) {
            @Override
            protected void populateViewHolder(MemoViewHolder viewHolder, memo model, final int position) {
                final String note_key = getRef(position).getKey();

                viewHolder.setContent(model.getContent());
                viewHolder.setTime(model.getTime());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent z = new Intent(getActivity(), EditMemo.class);
                        z.putExtra("note_key", note_key);
                        startActivity(z);
                    }
                });

                viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return false;
                    }
                });
            }
        };

        mMemoList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MemoViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public MemoViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setContent(String content) {
            TextView noteContent = (TextView) mView.findViewById(R.id.note_content);
            noteContent.setText(content);
        }

        public void setTime(String time) {
            TextView noteTime = (TextView) mView.findViewById(R.id.note_time);
            noteTime.setText(time);
        }
    }
}
