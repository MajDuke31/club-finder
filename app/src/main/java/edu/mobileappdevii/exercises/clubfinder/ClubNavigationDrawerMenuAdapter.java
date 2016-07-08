package edu.mobileappdevii.exercises.clubfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by majdu on 12/9/2015.
 */
public class ClubNavigationDrawerMenuAdapter extends
        RecyclerView.Adapter<ClubNavigationDrawerMenuAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final String[] mMenuItems;
    private final Context context;

    public ClubNavigationDrawerMenuAdapter(String[] menuItems, Context passedContext) {
        mMenuItems = menuItems;
        context = passedContext;
    }

    @Override
    public ClubNavigationDrawerMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.drawer_menu_item, parent, false);
            ViewHolder vhItem = new ViewHolder(v, viewType, context);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.drawer_menu_item, parent, false);
            ViewHolder vhItem = new ViewHolder(v, viewType, context);
            return vhItem;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(ClubNavigationDrawerMenuAdapter.ViewHolder holder, int position) {
        if (holder.holderId == TYPE_ITEM) {
            holder.textView.setText(mMenuItems[position - 1]);
        } else {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_HEADER) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mMenuItems.length + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int holderId;
        Context context;
        TextView textView;

        public ViewHolder(View itemView, int viewType, Context c) {
            super(itemView);
            context = c;
            
            if (viewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.menuItemTextView);
                holderId = TYPE_ITEM;
            } else {
                holderId = TYPE_HEADER;
            }
        }
    }
}
