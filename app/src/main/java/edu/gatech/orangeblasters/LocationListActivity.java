package edu.gatech.orangeblasters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import edu.gatech.orangeblasters.location.Location;

public class LocationListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.locationList);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        LocationAdapter adapter = new LocationAdapter();
        ((OrangeBlastersApplication) getApplication()).getLocations().observe(this, adapter::submitList);
        mRecyclerView.setAdapter(mAdapter);


    }
}
