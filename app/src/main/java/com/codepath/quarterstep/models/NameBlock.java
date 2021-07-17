package com.codepath.quarterstep.models;

import android.widget.RelativeLayout;

import com.codepath.quarterstep.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class NameBlock {
    private String name;
    private RelativeLayout rlCell;

    public NameBlock(String name) {
        this.name = name;
        this.rlCell = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLayout(RelativeLayout rlCell) {
        this.rlCell = rlCell;
    }

    public RelativeLayout getLayout() {
        return this.rlCell;
    }

    public static List<NameBlock> generateNameBlocks() {
        List<NameBlock> nameBlocks = new ArrayList<>();

        nameBlocks.addAll(Constants.NAME_BLOCKS);

        return nameBlocks;
    }
}
