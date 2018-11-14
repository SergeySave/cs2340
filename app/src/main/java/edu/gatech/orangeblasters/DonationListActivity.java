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

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import edu.gatech.orangeblasters.account.AccountType;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;
import edu.gatech.orangeblasters.donation.DonationFilteredList;
import edu.gatech.orangeblasters.donation.DonationService;
import edu.gatech.orangeblasters.location.LocationService;

/**
 * A login Location Employees see right when they log in
 */
public class DonationListActivity extends AppCompatActivity {

    private static final int RESULT_ADD_DONATION = 1;
    public static final String PARAM_LOCATION_ID = "LOCATION_INDEX";

    private DonationAdapter adapter;
    private TextView notFound;

    private DonationFilteredList donationFilteredList;
    private String locationId;
    private LocationService locationService = orangeBlastersApplication.getLocationService();
    private OrangeBlastersApplication orangeBlastersApplication =
            OrangeBlastersApplication.getInstance();
    private DonationService donationService = orangeBlastersApplication.getDonationService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = getIntent().getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        locationId = getIntent().getStringExtra(PARAM_LOCATION_ID);

        setContentView(R.layout.activity_donationlist);

        Button mAddDonationButton = findViewById(R.id.addDonation);
        mAddDonationButton.setVisibility(View.INVISIBLE);
        OrangeBlastersApplication.getInstance().getAccountService().getAccount(userId,
                account -> account.ifPresent(acc -> {
            if (acc.getType() == AccountType.EMPLOYEE && ((LocationEmployee)acc)
                    .getLocation().equals(locationId)) {
                mAddDonationButton.setVisibility(View.VISIBLE);
                mAddDonationButton.setOnClickListener(view -> addingDonation());
            }
        }));

        Button dashboard = findViewById(R.id.dashboard_button);
        dashboard.setOnClickListener(v -> finish());

        RecyclerView mRecyclerView = findViewById(R.id.donation_recycler);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        notFound = findViewById(R.id.donationsNotFound);

        adapter = new DonationAdapter();

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        donationFilteredList = new DonationFilteredList(new ListUpdateCallback() {
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


        initializeList();

        mRecyclerView.setAdapter(adapter);

        SearchView searchBar = findViewById(R.id.search_bar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return onQueryTextChange(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                donationFilteredList.setFilterText(newText);
                return true;
            }
        });
    }

    private void initializeList() {
        donationFilteredList.setFilterText("");
        donationFilteredList.setDataSource(() -> donationService.getDonations());
    }

    public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

        /**
         * Gets the sorted list
         *
         * @return the sorted list
         */
        private SortedList<Donation> getSortedList() {
            return donationFilteredList.getSortedList();
        }

        @NonNull
        @Override
        public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_row, parent, false);
            return new DonationViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
            holder.bind(getSortedList().get(position));
        }

        @Override
        public int getItemCount() {
            return getSortedList().size();
        }

        public class DonationViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private Donation donation;

            public DonationViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(v1 -> {
                    Intent intent = new Intent(v.getContext(), DonationDetailsActivity.class);
                    intent.putExtra(DonationDetailsActivity.EXTRA_DONATION, donation.getId());
                    v.getContext().startActivity(intent);
                });
                textView = v.findViewById(R.id.textView);
            }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//            public TextView getTextView() {
//                return textView;
//            }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

            /**
             * Binds the item to the donation
             *
             * @param item the item to bind
             */
            public void bind(Donation item) {
                donation = item;
                textView.setText(item.getDescShort());
            }
        }
    }

    /**
     * Adds the donation to the activity
     *
     */
    private void addingDonation() {
        Intent intent = new Intent(this, AddDonationActivity.class);
        startActivityForResult(intent, RESULT_ADD_DONATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            String bitmapId = data.getStringExtra(AddDonationActivity.RETURN_IMAGE);
            String shortDesc =
                    (String) data.getSerializableExtra(AddDonationActivity.RETURN_DESC_SHORT);
            String longDesc =
                    (String) data.getSerializableExtra(AddDonationActivity.RETURN_DESC_LONG);
            String price = (String) data.getSerializableExtra(AddDonationActivity.RETURN_PRICE);
            DonationCategory category = (DonationCategory) data.getSerializableExtra
                    (AddDonationActivity.RETURN_CATEGORY);
            String comments =
                    (String) data.getSerializableExtra(AddDonationActivity.RETURN_COMMENTS);
            OffsetDateTime dateTime =
                    (OffsetDateTime) data.getSerializableExtra(AddDonationActivity.RETURN_TIME);

            Donation donation = OrangeBlastersApplication.getInstance().getDonationService()
                    .createDonation(dateTime, locationId, shortDesc, longDesc,
                            new BigDecimal(price), category, comments, bitmapId);

            locationService.getLocation(locationId).ifPresent(location -> {
                location.getDonations().add(donation);
                locationService.update(location);
            });

            donationFilteredList.update();
        }
    }
}