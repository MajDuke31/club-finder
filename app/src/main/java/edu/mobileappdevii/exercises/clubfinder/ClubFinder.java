package edu.mobileappdevii.exercises.clubfinder;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class ClubFinder extends AppCompatActivity
        implements ClubListFragment.OnFragmentInteractionListener {
    private MobileServiceClient mClient;
    private MobileServiceTable<Club> mClubTable;
    private Toolbar toolbar;
    private ClubListFragment clubListFragment;
    private ClubSearchFragment clubSearchFragment;
    private String clubId;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private String[] menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_finder);
        // Attaching the layout to the toolbar object
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        menuItems = getResources().getStringArray(R.array.club_menu_array);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(new ClubNavigationDrawerMenuAdapter(menuItems, this));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer,
                R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Code here will execute once drawer is opened
                // We're ignoring
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
                // We're ignoring
            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        final GestureDetector gestureDetector = new GestureDetector(
                ClubFinder.this, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                }
        );
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    drawer.closeDrawers();
                    Toast.makeText(ClubFinder.this, "The Item Adapter Position Clicked is: " +
                        mRecyclerView.getChildAdapterPosition(child) +
                        " layout position: " +
                        mRecyclerView.getChildLayoutPosition(child),
                            Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        }); // Touch listener

        clubListFragment = new ClubListFragment();
        clubSearchFragment = new ClubSearchFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.clubFinderFrameLayout, clubListFragment)
                .addToBackStack("home")
                .commit();
    }

    @Override
    public final void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() > 1)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search_club:
                FragmentManager fm = getFragmentManager();
                int lastFragment = fm.getBackStackEntryCount() -1;
                if(fm.getBackStackEntryAt(lastFragment).getName().equals("search"))
                    return true;

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.clubFinderFrameLayout, clubSearchFragment)
                        .addToBackStack("search")
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_club_finder, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(String clubId) {
        ClubViewFragment clubViewFragment = ClubViewFragment.newInstance(clubId);
        getFragmentManager().beginTransaction()
                .replace(R.id.clubFinderFrameLayout, clubViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
