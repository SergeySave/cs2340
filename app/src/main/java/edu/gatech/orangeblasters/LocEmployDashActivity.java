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

import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.location.Location;

/**
 * A login Location Employees see right when they log in
 */
public class LocEmployDashActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locemploydash);

        Button mAddDonationButton = findViewById(R.id.addDonation);
        mAddDonationButton.setOnClickListener(view -> addingDonation());

        //Set up the Register form
        Button register = findViewById(R.id.register);
        register.setOnClickListener(v -> startActivity(new Intent(LocEmployDashActivity.this, RegisterActivity.class)));

        setContentView(R.layout.activity_add_donation);
        mRecyclerView = findViewById(R.id.donation_recycler);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        LocEmployDashActivity.DonationAdapter adapter = new DonationAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        adapter.submitList(((OrangeBlastersApplication) getApplication()).getDonations());
        ((OrangeBlastersApplication) getApplication()).getDonations().observe(this, adapter::submitList);
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
                    Intent intent = new Intent(v.getContext(), LocationDetailsActivity.class);
                    intent.putExtra(DonationDetailsActivity.EXTRA_DONATION, donation);
                    v.getContext().startActivity(intent);
                });
                textView = v.findViewById(R.id.textView);
            }

            public TextView getTextView() {
                return textView;
            }

            public void bind(Donation item) {
                donation = item;
                //textView.setText(item.getTimestamp());
            }
        }
    }
    private void addingDonation() {
        startActivity(new Intent (this, AddDonationActivity.class));
    }
}