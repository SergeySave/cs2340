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
import android.widget.TextView;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;
import edu.gatech.orangeblasters.location.Location;

/**
 * A login Location Employees see right when they log in
 */
public class DashboardActivity extends AppCompatActivity {

    public static final String PARAM_LOCATION_INDEX = "LOCATION_INDEX";

    private RecyclerView donRecyclerView;
    private RecyclerView locRecyclerView;
    private LinearLayoutManager donLayoutManager;
    private LinearLayoutManager locLayoutManager;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int locationIndex = getIntent().getIntExtra(PARAM_LOCATION_INDEX, -1);
        if (locationIndex < 0) {
            finish();
        }
        location = ((OrangeBlastersApplication) getApplication()).getLocations().get(locationIndex);

        setContentView(R.layout.activity_dashboard);

        donRecyclerView = findViewById(R.id.donation_recycler);
        locRecyclerView = findViewById(R.id.location_recycler);

        // use a linear layout manager
        locLayoutManager = new LinearLayoutManager(this);
        locRecyclerView.setLayoutManager(locLayoutManager);
        donLayoutManager = new LinearLayoutManager(this);
        donRecyclerView.setLayoutManager(donLayoutManager);

        DashboardActivity.DonationAdapter donadapter = new DonationAdapter();
        DashboardActivity.LocationAdapter locadapter = new LocationAdapter();

        DividerItemDecoration locdividerItemDecoration = new DividerItemDecoration(locRecyclerView.getContext(),
                locLayoutManager.getOrientation());
        locRecyclerView.addItemDecoration(locdividerItemDecoration);

        DividerItemDecoration dondividerItemDecoration = new DividerItemDecoration(donRecyclerView.getContext(),
                donLayoutManager.getOrientation());
        donRecyclerView.addItemDecoration(dondividerItemDecoration);

        donadapter.submitList(((OrangeBlastersApplication) getApplication()).getDonations());
        locadapter.submitList(((OrangeBlastersApplication) getApplication()).getLocations());
        location.getDonations().observe(this, donadapter::submitList);
        donRecyclerView.setAdapter(donadapter);
        locRecyclerView.setAdapter(locadapter);
    }

    public static class DonationAdapter extends ListAdapter<Donation, DashboardActivity.DonationAdapter.DonationViewHolder> {

        public DonationAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public DashboardActivity.DonationAdapter.DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_row, parent, false);
            DashboardActivity.DonationAdapter.DonationViewHolder vh = new DonationViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
            holder.bind(getItem(position));
        }


        public static final DiffUtil.ItemCallback<Donation> DIFF_CALLBACK =
                new DiffUtil.ItemCallback<Donation>() {
                    @Override
                    public boolean areItemsTheSame(Donation oldItem, Donation newItem) {
                        return false;
                    }

                    @Override
                    public boolean areContentsTheSame(Donation oldItem, Donation newItem) {
                        return false;
                    }
                };

        public static class DonationViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private Donation donation;

            public DonationViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(v1 -> {
                    Intent intent = new Intent(v.getContext(), DonationDetailsActivity.class);
                    intent.putExtra(DonationDetailsActivity.EXTRA_DONATION, this.getDonation());
                    v.getContext().startActivity(intent);
                });
                textView = v.findViewById(R.id.textView);
            }

            public TextView getTextView() {
                return textView;
            }

            private Donation getDonation() {
                return donation;
            }

            public void bind(Donation item) {
                donation = item;
                textView.setText(item.getDescShort());
            }
        }
    }

    public static class LocationAdapter extends ListAdapter<Location, LocationListActivity.LocationAdapter.LocationViewHolder> {

        public LocationAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public LocationListActivity.LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_row, parent, false);
            LocationListActivity.LocationAdapter.LocationViewHolder vh = new LocationListActivity.LocationAdapter.LocationViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull LocationListActivity.LocationAdapter.LocationViewHolder holder, int position) {
            holder.bind(getItem(position));

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
            private Location location;

            public LocationViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(v1 -> {
                    Intent intent = new Intent(v.getContext(), LocationDetailsActivity.class);
                    intent.putExtra(LocationDetailsActivity.EXTRA_LOCATION, location);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Uri uri = data.getData();
//        Bitmap bitmap = data.getParcelableExtra(AddDonationActivity.RETURN_IMAGE);
        int bitmap = data.getIntExtra(AddDonationActivity.RETURN_IMAGE, -1);
        String shortDesc = (String) data.getSerializableExtra(AddDonationActivity.RETURN_DESC_SHORT);
        String longDesc = (String) data.getSerializableExtra(AddDonationActivity.RETURN_DESC_LONG);
        String price = (String) data.getSerializableExtra(AddDonationActivity.RETURN_PRICE);
        DonationCategory category = (DonationCategory) data.getSerializableExtra(AddDonationActivity.RETURN_CATEGORY);
        String comments = (String) data.getSerializableExtra(AddDonationActivity.RETURN_COMMENTS);
        OffsetDateTime dateTime = (OffsetDateTime) data.getSerializableExtra(AddDonationActivity.RETURN_TIME);

        location.getDonations().add(new Donation(dateTime, location, shortDesc, longDesc, new BigDecimal(price), category, comments, bitmap));
    }
}