package com.codepath.quarterstep.models;

import android.widget.RelativeLayout;

public class NameBlock {
    private String name;
    private RelativeLayout rlCell;

    public NameBlock(String name) {
        this.name = name;
        this.rlCell = null;
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
}
