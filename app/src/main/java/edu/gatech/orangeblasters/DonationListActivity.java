package edu.gatech.orangeblasters;

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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import edu.gatech.orangeblasters.account.AccountService;
import edu.gatech.orangeblasters.account.AccountType;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;
import edu.gatech.orangeblasters.donation.DonationFilteredList;
import edu.gatech.orangeblasters.donation.DonationService;
import edu.gatech.orangeblasters.location.Location;
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
    private final OrangeBlastersApplication orangeBlastersApplication =
            OrangeBlastersApplication.getInstance();
    private final LocationService locationService = orangeBlastersApplication.getLocationService();
    private final DonationService donationService = orangeBlastersApplication.getDonationService();
    private final AccountService accountService = orangeBlastersApplication.getAccountService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        locationId = intent.getStringExtra(PARAM_LOCATION_ID);

        setContentView(R.layout.activity_donationlist);

        Button mAddDonationButton = findViewById(R.id.addDonation);
        mAddDonationButton.setVisibility(View.INVISIBLE);
        accountService.getAccount(userId,
                account -> account.ifPresent(acc -> {
            if (acc.getType() == AccountType.EMPLOYEE) {
                String locationId = ((LocationEmployee) acc).getLocation();
                if (locationId.equals(this.locationId)) {
                    mAddDonationButton.setVisibility(View.VISIBLE);
                    mAddDonationButton.setOnClickListener(view -> addingDonation());
                }
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
                SortedList<Donation> sortedList = adapter.getSortedList();
                if (sortedList.size() == 0) {
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
        donationFilteredList.setDataSource(() -> {
            Stream<Donation> donations = donationService.getDonations();
            if (locationId == null) {
                return donations;
            } else {
                return donations
                        .filter(don -> {
                            String locId = don.getLocationId();
                            return locId.equals(locationId);
                        });
            }
        });
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
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater
                    .inflate(R.layout.location_row, parent, false);
            return new DonationViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
            SortedList<Donation> sortedList = getSortedList();
            holder.bind(sortedList.get(position));
        }

        @Override
        public int getItemCount() {
            SortedList<Donation> sortedList = getSortedList();
            return sortedList.size();
        }

        public class DonationViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private Donation donation;

            public DonationViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(v1 -> {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DonationDetailsActivity.class);
                    intent.putExtra(DonationDetailsActivity.EXTRA_DONATION, donation.getId());
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

        if ((resultCode == RESULT_OK) && (null != data)) {
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

            Donation donation = donationService.createDonation(dateTime, locationId, shortDesc, longDesc,
                            new BigDecimal(price), category, comments, bitmapId);

            Optional<Location> optionalLocation = locationService.getLocation(locationId);
            optionalLocation.ifPresent(location -> {
                List<Donation> donations = location.getDonations();
                donations.add(donation);
                locationService.update(location);
            });

            donationFilteredList.update();
        }
    }
}