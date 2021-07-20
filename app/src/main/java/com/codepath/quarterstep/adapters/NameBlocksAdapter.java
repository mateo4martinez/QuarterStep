package com.codepath.quarterstep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.NameBlock;

import java.util.List;

public class NameBlocksAdapter extends BaseAdapter {
    public static final String TAG = "NameBlocksAdapter";

    private Context context;
    private List<NameBlock> blocks;

    public NameBlocksAdapter(Context context, List<NameBlock> blocks) {
        this.context = context;
        this.blocks = blocks;
    }

    @Override
    public int getCount() {
        return blocks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NameBlock block = blocks.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.block_name, parent, false);
        }

        RelativeLayout rlCell = convertView.findViewById(R.id.rlCell);
        TextView tvName = convertView.findViewById(R.id.tvName);

        block.setLayout(rlCell);
        tvName.setText(block.getName());

        return convertView;
    }
}
