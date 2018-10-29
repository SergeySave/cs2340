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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Optional;

import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;
import edu.gatech.orangeblasters.location.Location;

/**
 * A login Location Employees see right when they log in
 */
public class LocEmployDashActivity extends AppCompatActivity {

    private static final int RESULT_ADD_DONATION = 1;
    public static final String PARAM_LOCATION_ID = "LOCATION_INDEX";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DonationAdapter adapter;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String locationId = getIntent().getStringExtra(PARAM_LOCATION_ID);
        if (locationId == null) {
            finish();
        }
        Optional<Location> optionalLocation = OrangeBlastersApplication.getInstance().getLocationService().getLocation(locationId);
        if (optionalLocation.isPresent()) {
            location = optionalLocation.get();
        } else {
            finish();
        }

        setContentView(R.layout.activity_locemploydash);

        Button mAddDonationButton = findViewById(R.id.addDonation);
        mAddDonationButton.setOnClickListener(view -> addingDonation());

        mRecyclerView = findViewById(R.id.donation_recycler);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new DonationAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        adapter.submitList(this.location.getDonations());
        mRecyclerView.setAdapter(adapter);
    }

    public static class DonationAdapter extends ListAdapter<Donation, LocEmployDashActivity.DonationAdapter.DonationViewHolder> {

        public DonationAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public LocEmployDashActivity.DonationAdapter.DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_row, parent, false);
            LocEmployDashActivity.DonationAdapter.DonationViewHolder vh = new DonationViewHolder(v);
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
                        return oldItem == newItem;
                    }

                    @Override
                    public boolean areContentsTheSame(Donation oldItem, Donation newItem) {
                        return oldItem.equals(newItem);
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
    private void addingDonation() {
        Intent intent = new Intent(this, AddDonationActivity.class);
        startActivityForResult(intent, RESULT_ADD_DONATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            int bitmap = data.getIntExtra(AddDonationActivity.RETURN_IMAGE, -1);
            String shortDesc = (String) data.getSerializableExtra(AddDonationActivity.RETURN_DESC_SHORT);
            String longDesc = (String) data.getSerializableExtra(AddDonationActivity.RETURN_DESC_LONG);
            String price = (String) data.getSerializableExtra(AddDonationActivity.RETURN_PRICE);
            DonationCategory category = (DonationCategory) data.getSerializableExtra(AddDonationActivity.RETURN_CATEGORY);
            String comments = (String) data.getSerializableExtra(AddDonationActivity.RETURN_COMMENTS);
            OffsetDateTime dateTime = (OffsetDateTime) data.getSerializableExtra(AddDonationActivity.RETURN_TIME);

            location.getDonations().add(new Donation(dateTime, location, shortDesc, longDesc, new BigDecimal(price), category, comments, bitmap));
            adapter.submitList(new ArrayList<>(location.getDonations()));
            mRecyclerView.setAdapter(adapter);
        }
    }
}