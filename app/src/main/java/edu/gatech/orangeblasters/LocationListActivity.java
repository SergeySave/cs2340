package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import java.util.function.Function;

import edu.gatech.orangeblasters.location.Location;

public class LocationListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TextView notFound;

    private Function<Location, Integer> relevanceFilter = __ -> 1;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = getIntent().getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        setContentView(R.layout.activity_location_list);
        mRecyclerView = findViewById(R.id.location_recycler);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        notFound = findViewById(R.id.locationsNotFound);

        LocationAdapter adapter = new LocationAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Button dashboardButton = findViewById(R.id.dashboardbutton);
        dashboardButton.setOnClickListener(v -> finish());

        doUpdateFilteredList(adapter);

        OrangeBlastersApplication.getInstance().getLocationService().getLiveIDList().observe(this, (list) -> {
            //When the id list changes
            doUpdateFilteredList(adapter);
        });

        mRecyclerView.setAdapter(adapter);

        SearchView searchBar = findViewById(R.id.searchbar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return onQueryTextChange(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                relevanceFilter = location -> {
                    if (newText.isEmpty()) {
                        return 1;
                    }

                    String text = newText.toLowerCase();
                    return (location.getName().equalsIgnoreCase(text) ? 20 : 0) //Exact name = 20 points
                            + (location.getName().toLowerCase().contains(text) ? 5 : 0) //Contains name = 5 point
                            + (location.getType().getFullName().toLowerCase().contains(text) ? 2 : 0) //Type = 2 point
                            + (location.getAddress().toLowerCase().contains(text) ? 2 : 0) //Address = 2 point
                            + ((int)location.getDonations().stream().filter(donation ->  //1 point per relevant donation
                            donation.getDescShort().toLowerCase().contains(text) ||
                                    donation.getDescLong().toLowerCase().contains(text) ||
                                    donation.getComments().map(str -> str.toLowerCase().contains(text)).orElse(false)).count());
                };
                doUpdateFilteredList(adapter);
                return true;
            }
        });
    }

    private void doUpdateFilteredList(LocationAdapter adapter) {
        adapter.getSortedList().beginBatchedUpdates();
        adapter.getSortedList().clear();
        OrangeBlastersApplication.getInstance().getLocationService().getLocations()
                //relevance has to be greater than 0
                .filter(loc -> relevanceFilter.apply(loc) > 0)
                .forEach(adapter.getSortedList()::add);
        adapter.getSortedList().endBatchedUpdates();

        if (adapter.getSortedList().size() == 0) {
            notFound.setVisibility(View.VISIBLE);
            notFound.setText(R.string.locationsNotFound);
        } else {
            notFound.setVisibility(View.INVISIBLE);
        }
    }

    public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

        @NonNull
        @Override
        public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_row, parent, false);
            LocationViewHolder vh = new LocationViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
            holder.bind(sortedList.get(position));

        }

        @Override
        public int getItemCount() {
            return sortedList.size();
        }

        private final SortedList<Location> sortedList = new SortedList<>(Location.class, new SortedList.Callback<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                Integer relevence1 = relevanceFilter.apply(o1);
                Integer relevence2 = relevanceFilter.apply(o2);
                //Compare by relevance then by caseless name
                if (relevence1.compareTo(relevence2) != 0) {
                    return relevence1.compareTo(relevence2);
                }
                return o1.getName().compareToIgnoreCase(o2.getName());
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Location oldItem, Location newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Location item1, Location item2) {
                return item1.getId().equals(item2.getId());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });

        public SortedList<Location> getSortedList() {
            return sortedList;
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
