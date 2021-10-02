package com.greymatter.sesmo.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greymatter.sesmo.Adapter.EarthQuakeAdapter;
import com.greymatter.sesmo.Model.EarthQuake;
import com.greymatter.sesmo.R;

import java.util.ArrayList;

public class FragmentA extends Fragment {
    ArrayList<EarthQuake> itemslist = new ArrayList<>();
    EarthQuakeAdapter adapter;
    RecyclerView rv;


    public FragmentA() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        rv = view.findViewById(R.id.rv);
        adapter = new EarthQuakeAdapter(itemslist,getActivity());
        rv.setAdapter(adapter);

        getDatas();

        return view;
    }

    private void getDatas() {
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
        dref.child("All").orderByChild("type").equalTo("significant").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemslist.clear();
                for (DataSnapshot ds : snapshot.getChildren() ){
                    EarthQuake model = ds.getValue(EarthQuake.class);
                    itemslist.add(model);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}


