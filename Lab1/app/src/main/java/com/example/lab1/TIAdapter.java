package com.example.lab1;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TIAdapter extends RecyclerView.Adapter<TIAdapter.ItemHolder> {
    private final int itemNumber;
    private convertIndex converter;

    public TIAdapter(int itemNumber) {
        this.itemNumber = itemNumber;

        converter = new convertIndex();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        int number = position;

        holder.textView.setText(converter.convert(number));

        int backgroundColor = ContextCompat.getColor(holder.itemView.getContext(),
                number % 2 == 0 ? R.color.gray : R.color.white);

        holder.linearLayout.setBackgroundColor(backgroundColor);
    }

    @Override
    public int getItemCount() {
        return itemNumber;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final LinearLayout linearLayout;

        ItemHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView1);
            linearLayout = itemView.findViewById(R.id.line);
        }
    }
}
