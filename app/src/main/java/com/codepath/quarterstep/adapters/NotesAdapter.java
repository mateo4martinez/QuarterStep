package com.codepath.quarterstep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends BaseAdapter {
    public static final String TAG = "NotesAdapter";

    private Context context;
    private List<Note> notes;

    public NotesAdapter(Context context, List<List<Note>> emptyNotes) {
        this.context = context;
        this.notes = setAdapterPositions(emptyNotes);
    }

    @Override
    public int getCount() {
        return notes.size();
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
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = notes.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.block_note, parent, false);
        }

        RelativeLayout rlCell = convertView.findViewById(R.id.rlCell);
        TextView tvNoteName = convertView.findViewById(R.id.tvNoteName);

        note.setLayout(rlCell);
        if (note.getCol() == 0) {
            tvNoteName.setText(note.getNoteName());
            rlCell.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_inactive));
        }

        return convertView;
    }

    private List<Note> setAdapterPositions(List<List<Note>> emptyNotes) {
        List<Note> notes = new ArrayList<>();
        for (List<Note> row: emptyNotes) {
            for (Note note: row) {
                note.setAdapterPosition(notes.size());
                notes.add(note);
            }
        }
        return notes;
    }
}
