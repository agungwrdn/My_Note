package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.Chatgroup;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class chat extends Fragment {


    public chat() {
        // Required empty public constructor
    }
    private ListView listView;
    private Button addGroup;
    private EditText etTextgroup;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_room = new ArrayList<>();
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("group");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chat,
                container, false);

        listView = (ListView) rootView.findViewById(R.id.listview1);
        addGroup = (Button) rootView.findViewById(R.id.addGroup);
        etTextgroup = (EditText) rootView.findViewById(R.id.editText2);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,list_of_room);
        listView.setAdapter(arrayAdapter);

        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(etTextgroup.getText().toString(),"");
                root.updateChildren(map);
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator i  = dataSnapshot.getChildren().iterator();

                Set<String> set = new HashSet<String>();
                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_room.clear();
                list_of_room.addAll(set);

                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Chatgroup.class);
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
