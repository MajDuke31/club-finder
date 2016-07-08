package edu.mobileappdevii.exercises.clubfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Antar on 12/8/2015.
 */
public class ClubAdapter extends ArrayAdapter<Club> {
    private final Context mContext;
    private final int mLayoutResourceId;
    private List<String> clubIds;

    public ClubAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final Club currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);
        final TextView clubNameTextView = (TextView) row.findViewById(android.R.id.text1);
        clubNameTextView.setText(currentItem.getName());
        return row;
    }
}