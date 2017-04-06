package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment;


import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.AddTarget;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.R;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.model.Target;


/**
 * A simple {@link Fragment} subclass.
 */
public class Reminder extends Fragment {
    private RecyclerView mTarget;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBtarget, mDBtargetUser, mDatabase;
    private FloatingActionButton addTarget;

    public Reminder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder,container, false);
        addTarget = (FloatingActionButton) view.findViewById(R.id.btnReminder);
        addTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddTarget();
            }
        });
        mTarget = (RecyclerView) view.findViewById(R.id.recycleviewTarget);
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mDBtarget = mDB.getReference().child("target");
        mDBtargetUser = mDBtarget.child(mAuth.getCurrentUser().getUid().toString());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mTarget.setLayoutManager(layoutManager);
        mTarget.setHasFixedSize(true);


        mDatabase = mDB.getReference().child("target").child(mAuth.getCurrentUser().getUid().toString());

        mDBtargetUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long jumlahChild;
                jumlahChild = dataSnapshot.getChildrenCount();
                String strValue = jumlahChild.toString();
                Log.d("FirebaseCounter", "Jumlah child " + strValue);
                if (jumlahChild == 0) {
                    Log.d("FirebaseCounter", "No item yet !");
                   // tvNoTodo.setVisibility(View.VISIBLE);
                } else {
                    //tvNoTodo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FirebaseCounter", databaseError.getMessage());
                //tvNoTodo.setVisibility(View.VISIBLE);
                //tvNoTodo.setText("Error." + " " + databaseError.getMessage());
            }
        });
        return view;
    }

    private void gotoAddTarget() {
        Intent go = new Intent(getActivity(), AddTarget.class);
        startActivity(go);
    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<Target, TargetViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Target, TargetViewHolder>(
                Target.class,
                R.layout.layout_target,
                Reminder.TargetViewHolder.class,
                mDBtargetUser
        ) {
            @Override
            protected void populateViewHolder(TargetViewHolder viewHolder, Target model, int position) {
                final String note_key = getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDetail(model.getDue());

                //viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                  //  @Override
                    //public void onClick(View v) {
                      //  Intent r = new Intent(getActivity(), EditTodoActivity.class);
                        //r.putExtra("note_key", note_key);
                       // startActivity(r);
                   // }
                //});

                viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return false;
                    }
                });

                viewHolder.llDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child(note_key).removeValue();
                        Snackbar.make(getView(), getResources().getString(R.string.done), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        };

        mTarget.setAdapter(firebaseRecyclerAdapter);
    }


    public static class TargetViewHolder extends RecyclerView.ViewHolder {
        View mView;
        LinearLayout llDone;

        public TargetViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            llDone = (LinearLayout) mView.findViewById(R.id.linearLayoutDone);
        }

        public void setTitle(String title) {
            TextView targtTitle = (TextView) mView.findViewById(R.id.todo_title);
            targtTitle.setText(title);
        }

        public void setDetail(String detail) {
            TextView todoContent = (TextView) mView.findViewById(R.id.todo_detail);
            todoContent.setText(detail);
        }

    }

}
