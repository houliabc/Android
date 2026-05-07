package com.example.myqq;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.VH> {
    List<String> data;
    public FriendAdapter(List<String> data) { this.data = data; }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false));
    }
    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.tv.setText(data.get(position));
    }
    @Override
    public int getItemCount() { return data.size(); }

    class VH extends RecyclerView.ViewHolder {
        TextView tv;
        public VH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_name);
        }
    }
}