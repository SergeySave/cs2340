package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.orangeblasters.location.Location;

public class LocationListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_list);
        mRecyclerView = findViewById(R.id.location_recycler);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        LocationAdapter adapter = new LocationAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        adapter.submitList(OrangeBlastersApplication.getInstance().getLocationService().getLiveIDList().getValue());
        OrangeBlastersApplication.getInstance().getLocationService().getLiveIDList().observe(this, adapter::submitList);
        mRecyclerView.setAdapter(adapter);

        Button dashboardButton = (Button)findViewById(R.id.dashboardbutton);
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationListActivity.this, DashboardActivity.class));
            }
        });
    }

    public static class LocationAdapter extends ListAdapter<String, LocationAdapter.LocationViewHolder> {

        public LocationAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_row, parent, false);
            LocationViewHolder vh = new LocationViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
            holder.bind(OrangeBlastersApplication.getInstance().getLocationService().getLocation(getItem(position)).orElse(null));

        }

        public static final DiffUtil.ItemCallback<String> DIFF_CALLBACK =
                new DiffUtil.ItemCallback<String>() {
                    @Override
                    public boolean areItemsTheSame(
                            @NonNull String oldUser, @NonNull String newUser) {
                        return oldUser.equals(newUser);
                    }
                    @Override
                    public boolean areContentsTheSame(
                            @NonNull String oldUser, @NonNull String newUser) {
                        return oldUser.equals(newUser);
                    }
                };

        public static class LocationViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private Location location;

            public LocationViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(v1 -> {
                    Intent intent = new Intent(v.getContext(), LocationDetailsActivity.class);
                    intent.putExtra(LocationDetailsActivity.EXTRA_LOCATION_ID, location.getId());
                    v.getContext().startActivity(intent);
                });
                textView = v.findViewById(R.id.textView);
            }

            public TextView getTextView() {
                return textView;
            }

            public void bind(Location item) {
                location = item;
                textView.setText(item.getName());
            }
        }
    }
}
