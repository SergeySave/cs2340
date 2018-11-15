package edu.gatech.orangeblasters;

import android.arch.lifecycle.LiveData;
import android.content.Context;
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

import java.util.List;

import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationFilteredList;
import edu.gatech.orangeblasters.location.LocationService;

public class LocationListActivity extends AppCompatActivity {

    private TextView notFound;

    private LocationFilteredList locationFilteredList;
    private String userId;
    private final OrangeBlastersApplication orangeBlastersApplication = OrangeBlastersApplication.getInstance();
    private final LocationService locationService = orangeBlastersApplication.getLocationService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        userId = intent.getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        setContentView(R.layout.activity_location_list);
        RecyclerView mRecyclerView = findViewById(R.id.location_recycler);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        notFound = findViewById(R.id.locationsNotFound);

        LocationAdapter adapter = new LocationAdapter();

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(),
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

            /**
             * method to test for empty location list
             */
            private void update() {
                SortedList<Location> sortedList = adapter.getSortedList();
                if (sortedList.size() == 0) {
                    notFound.setVisibility(View.VISIBLE);
                    notFound.setText(R.string.locationsNotFound);
                } else {
                    notFound.setVisibility(View.INVISIBLE);
                }
            }
        });

        initializeFilteredList();

        listenToFilteredList(locationService.getLiveIDList());

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

    private void initializeFilteredList() {
        locationFilteredList.setFilterText("");
        locationFilteredList.setDataSource(() -> locationService.getLocations());
    }

    private void listenToFilteredList(LiveData<List<String>> liveIdList) {
        liveIdList.observe(this, (list) -> {
            //When the ID list changes update the list
            locationFilteredList.setDataSource(() -> locationService.getLocations());
        });
    }

    /**
     * method to set all the displays for location
     */
    public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

        private SortedList<Location> getSortedList() {
            return locationFilteredList.getSortedList();
        }

        @NonNull
        @Override
        public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater
                    .inflate(R.layout.location_row, parent, false);
            return new LocationViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
            SortedList<Location> sortedList = getSortedList();
            holder.bind(sortedList.get(position));

        }

        @Override
        public int getItemCount() {
            SortedList<Location> sortedList = getSortedList();
            return sortedList.size();
        }

        /**
         * class to hold view of the location
         */
        public class LocationViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private Location location;

            /**
             * method to hold view of location
             * @param v represents view
             */
            LocationViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(v1 -> {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, LocationDetailsActivity.class);
                    intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, userId);
                    intent.putExtra(LocationDetailsActivity.EXTRA_LOCATION_ID, location.getId());
                    context.startActivity(intent);
                });
                textView = v.findViewById(R.id.textView);
            }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//            public TextView getTextView() {
//                return textView;
//            }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

            /**
             * method to set text on screen
             * @param item the item to add to the location list
             */
            void bind(Location item) {
                location = item;
                textView.setText(item.getName());
            }
        }
    }
}
