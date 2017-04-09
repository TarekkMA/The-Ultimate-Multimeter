package com.example.tarekkma.avometerclient;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarekkma.avometerclient.data.DisplayData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekkma on 4/6/17.
 */

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.VH> {

    List<DisplayData> dataList = new ArrayList<>();

    public DisplayAdapter(List<DisplayData> dataList) {
        this.dataList = dataList;
    }

    public void updateData(List<DisplayData> data){
        dataList = data;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display,parent,false));
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        DisplayData item = dataList.get(position);
        holder.valueTV.setText(String.valueOf(item.getValue()));
        holder.infoTV.setText(item.getUnit().symbol+"\n"+item.getUnit().multiplier.symbol+item.getUnit().unit);
        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(holder.itemView.getContext(), v);

                Menu menu = popup.getMenu();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                holder.itemView.getContext(),
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public static class VH extends RecyclerView.ViewHolder{
        public TextView valueTV,infoTV;
        public LinearLayout change;
        public VH(View v) {
            super(v);
            valueTV = (TextView)v.findViewById(R.id.display_value);
            infoTV = (TextView)v.findViewById(R.id.display_info);
            change = (LinearLayout) v.findViewById(R.id.display_change_units);
        }
    }
}
