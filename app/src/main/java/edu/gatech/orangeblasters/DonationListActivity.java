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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.function.Function;

import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;

/**
 * A login Location Employees see right when they log in
 */
public class DonationListActivity extends AppCompatActivity {

    private static final int RESULT_ADD_DONATION = 1;
    public static final String PARAM_LOCATION_ID = "LOCATION_INDEX";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DonationAdapter adapter;
    private TextView notFound;

    private Function<Donation, Integer> relevanceFilter = __ -> 1;
    private String locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = getIntent().getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        locationId = getIntent().getStringExtra(PARAM_LOCATION_ID);

        setContentView(R.layout.activity_donationlist);

        Button mAddDonationButton = findViewById(R.id.addDonation);
        mAddDonationButton.setVisibility(View.INVISIBLE);
        OrangeBlastersApplication.getInstance().getAccountService().getAccount(userId, account -> {
            account.ifPresent(acc -> {
                if (acc instanceof LocationEmployee && ((LocationEmployee)acc).getLocation().equals(locationId)) {
                    mAddDonationButton.setVisibility(View.VISIBLE);
                    mAddDonationButton.setOnClickListener(view -> addingDonation());
                }
            });
        });

        Button dashboard = findViewById(R.id.dashboardbutton);
        dashboard.setOnClickListener(v -> finish());

        mRecyclerView = findViewById(R.id.donation_recycler);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        notFound = findViewById(R.id.donationsNotFound);

        adapter = new DonationAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

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
                relevanceFilter = donation -> {
                    if (newText.isEmpty()) {
                        return 1;
                    }

                    String text = newText.toLowerCase();
                    return (donation.getDescShort().equalsIgnoreCase(text) ? 20 : 0) //Exact name = 20 points
                            + (donation.getDescShort().toLowerCase().contains(text) ? 5 : 0) //Contains name = 5 point
                            + (donation.getDescLong().toLowerCase().contains(text) ? 2 : 0) //Type = 2 point
                            + (donation.getComments().map(str -> str.toLowerCase().contains(text) ? 1 : 0).orElse(0)) //comments = 1 point
                            + (OrangeBlastersApplication.getInstance().getLocationService().getLocation(donation.getLocationId())
                                .map(loc -> loc.getName().toLowerCase().contains(text) ? 1 : 0).orElse(0)) // donation has the right name = 1 point
                            + (donation.getDonationCategory().getFullName().toLowerCase().contains(text) ? 3 : 0); //category = 3 points
                };
                doUpdateFilteredList(adapter);
                return true;
            }
        });
    }

    private void doUpdateFilteredList(DonationAdapter adapter) {
        adapter.getSortedList().beginBatchedUpdates();
        adapter.getSortedList().clear();
        OrangeBlastersApplication.getInstance().getDonationService().getDonations()
                .filter(don -> locationId == null || don.getLocationId().equals(locationId))
                //relevance has to be greater than 0
                .filter(don -> relevanceFilter.apply(don) > 0)
                .forEach(adapter.getSortedList()::add);
        adapter.getSortedList().endBatchedUpdates();

        if (adapter.getSortedList().size() == 0) {
            notFound.setVisibility(View.VISIBLE);
            notFound.setText(R.string.donationsNotFound);
        } else {
            notFound.setVisibility(View.INVISIBLE);
        }
    }

    public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

        @NonNull
        @Override
        public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_row, parent, false);
            DonationViewHolder vh = new DonationViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
            holder.bind(sortedList.get(position));
        }

        @Override
        public int getItemCount() {
            return sortedList.size();
        }

        private final SortedList<Donation> sortedList = new SortedList<>(Donation.class, new SortedList.Callback<Donation>() {
            @Override
            public int compare(Donation o1, Donation o2) {
                Integer relevence1 = relevanceFilter.apply(o1);
                Integer relevence2 = relevanceFilter.apply(o2);
                //Compare by relevance then by caseless name
                if (relevence1.compareTo(relevence2) != 0) {
                    return relevence1.compareTo(relevence2);
                }
                return o1.getDescShort().compareToIgnoreCase(o2.getDescShort());
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Donation oldItem, Donation newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Donation item1, Donation item2) {
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

        public SortedList<Donation> getSortedList() {
            return sortedList;
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

            public TextView getTextView() {
                return textView;
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
            String bitmapId = data.getStringExtra(AddDonationActivity.RETURN_IMAGE);
            String shortDesc = (String) data.getSerializableExtra(AddDonationActivity.RETURN_DESC_SHORT);
            String longDesc = (String) data.getSerializableExtra(AddDonationActivity.RETURN_DESC_LONG);
            String price = (String) data.getSerializableExtra(AddDonationActivity.RETURN_PRICE);
            DonationCategory category = (DonationCategory) data.getSerializableExtra(AddDonationActivity.RETURN_CATEGORY);
            String comments = (String) data.getSerializableExtra(AddDonationActivity.RETURN_COMMENTS);
            OffsetDateTime dateTime = (OffsetDateTime) data.getSerializableExtra(AddDonationActivity.RETURN_TIME);

            Donation donation = OrangeBlastersApplication.getInstance().getDonationService()
                    .createDonation(dateTime, locationId, shortDesc, longDesc, new BigDecimal(price), category, comments, bitmapId);

            OrangeBlastersApplication.getInstance().getLocationService().getLocation(locationId).ifPresent(location -> {
                location.getDonations().add(donation);
                OrangeBlastersApplication.getInstance().getLocationService().update(location);
            });

            doUpdateFilteredList(adapter);
        }
    }
}