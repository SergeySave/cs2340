package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationFilteredList;

public class LocationListActivity extends AppCompatActivity {

    private TextView notFound;

    private LocationFilteredList locationFilteredList;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = getIntent().getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        setContentView(R.layout.activity_location_list);
        RecyclerView mRecyclerView = findViewById(R.id.location_recycler);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        notFound = findViewById(R.id.locationsNotFound);

        LocationAdapter adapter = new LocationAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Button dashboardButton = findViewById(R.id.dashboard_button);
        dashboardButton.setOnClickListener(v -> finish());

        locationFilteredList = new LocationFilteredList(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                adapter.notifyItemRangeInserted(position, count);
                update();
            }

            @Override
            public void onRemoved(int position, int count) {
                adapter.notifyItemRangeRemoved(position, count);
                update();
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                adapter.notifyItemMoved(fromPosition, toPosition);
                update();
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                adapter.notifyItemRangeChanged(position, count);
                update();
            }

            private void update() {
                if (adapter.getSortedList().size() == 0) {
                    notFound.setVisibility(View.VISIBLE);
                    notFound.setText(R.string.locationsNotFound);
                } else {
                    notFound.setVisibility(View.INVISIBLE);
                }
            }
        });

        locationFilteredList.setFilterText("");
        locationFilteredList.setDataSource(() -> OrangeBlastersApplication.getInstance().getLocationService().getLocations());

        OrangeBlastersApplication.getInstance().getLocationService().getLiveIDList().observe(this, (list) -> {
            //When the ID list changes update the list
            locationFilteredList.setDataSource(() -> OrangeBlastersApplication.getInstance().getLocationService().getLocations());
        });

        mRecyclerView.setAdapter(adapter);

        SearchView searchBar = findViewById(R.id.search_bar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return onQueryTextChange(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                locationFilteredList.setFilterText(newText);
                return true;
            }
        });
    }

    public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

        private SortedList<Location> getSortedList() {
            return locationFilteredList.getSortedList();
        }

        @NonNull
        @Override
        public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_row, parent, false);
            return new LocationViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
            holder.bind(getSortedList().get(position));

        }

        @Override
        public int getItemCount() {
            return getSortedList().size();
        }

        public class LocationViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private Location location;

            public LocationViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(v1 -> {
                    Intent intent = new Intent(v.getContext(), LocationDetailsActivity.class);
                    intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, userId);
                    intent.putExtra(LocationDetailsActivity.EXTRA_LOCATION_ID, location.getId());
                    v.getContext().startActivity(intent);
                });
                textView = v.findViewById(R.id.textView);
            }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//            public TextView getTextView() {
//                return textView;
//            }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

            public void bind(Location item) {
                location = item;
                textView.setText(item.getName());
            }
        }
    }
}
