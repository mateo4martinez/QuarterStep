package com.codepath.quarterstep.models;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import org.jetbrains.annotations.NotNull;

public class Note extends AppCompatButton {

    public Note(@NonNull @NotNull Context context) {
        super(context);
    }

    public Note(@NonNull @NotNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Note(@NonNull @NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
