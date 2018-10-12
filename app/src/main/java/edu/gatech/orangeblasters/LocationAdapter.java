package edu.gatech.orangeblasters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.gatech.orangeblasters.location.Location;

public class LocationAdapter extends ListAdapter<Location, LocationAdapter.LocationViewHolder> {

    public LocationAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_row, parent, false);
        LocationViewHolder vh = new LocationViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.getTextView().setText(getItem(position).getName());
    }

    public static final DiffUtil.ItemCallback<Location> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Location>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Location oldUser, @NonNull Location newUser) {
                    return oldUser.getName().equals(newUser.getName());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Location oldUser, @NonNull Location newUser) {
                    return oldUser.equals(newUser);
                }
            };

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public LocationViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            textView = (TextView) v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
