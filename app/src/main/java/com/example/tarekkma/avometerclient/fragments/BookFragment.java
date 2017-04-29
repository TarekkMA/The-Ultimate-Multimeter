package com.example.tarekkma.avometerclient.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarekkma.avometerclient.PdfActivity;
import com.example.tarekkma.avometerclient.R;
import com.example.tarekkma.avometerclient.data.Expiramint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class BookFragment extends Fragment {

    public BookFragment() {
        // Required empty public constructor
    }


    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        StringBuilder buf=new StringBuilder();

        try {
            String str;
            InputStream json=getContext().getAssets().open("pdfs/_index.json");
        BufferedReader in=
                new BufferedReader(new InputStreamReader(json, "UTF-8"));

            while ((str=in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("TEST", "onViewCreated: "+buf.toString());

        final List<Expiramint> expiramints = new Gson().fromJson(buf.toString(), new TypeToken<List<Expiramint>>(){}.getType());


        RecyclerView list = (RecyclerView)view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exiprment,parent,false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                VH vh = ((VH) holder);
                vh.indexTV.setText(expiramints.get(position).index);
                vh.nameTV.setText(expiramints.get(position).name);
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), PdfActivity.class);
                        String s = expiramints.get(position).file_path;
                        i.putExtra(PdfActivity.KEY_PATH,s);
                        Log.d("TEST",s);
                        getContext().startActivity(i);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return expiramints.size();
            }

            class VH extends RecyclerView.ViewHolder{
                public TextView nameTV,indexTV;
                public VH(View v) {
                    super(v);
                    nameTV = (TextView) v.findViewById(R.id.exp_name);
                    indexTV = (TextView) v.findViewById(R.id.exp_index);
                }
            }
        });


    }
}
