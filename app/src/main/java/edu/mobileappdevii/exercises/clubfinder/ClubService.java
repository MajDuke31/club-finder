package edu.mobileappdevii.exercises.clubfinder;


/**
 * Created by Antar on 12/8/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.Query;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClubService {
    private final static String url = "https://ritclubmanager.azurewebsites.net";
    private Activity activity;

    private MobileServiceTable<Club> mClubTable;
    private MobileServiceClient mClient;

    public ClubService(Activity activity) {
        try {
            this.activity = activity;
            mClient = new MobileServiceClient(url, activity);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mClubTable = mClient.getTable(Club.class);
    }

    public AsyncTask getList(final Activity activity, final ClubAdapter adapter, final List<String> clubIds) {
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<Club> result = mClubTable.execute().get();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.clear();
                            for (Club club : result) {
                                clubIds.add(club.getId());
                                adapter.add(club);
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (MobileServiceException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        asyncTask.execute();
        return asyncTask;
    }

    public AsyncTask search(final Activity activity, final ClubAdapter adapter, final String search) {
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Query query = mClubTable.where().subStringOf(search, "name");
                    final MobileServiceList<Club> result = mClubTable.execute(query).get();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.clear();
                            for (Club club : result) {
                                adapter.add(club);
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        asyncTask.execute();
        return asyncTask;
    }

    public AsyncTask getClub(final String clubId) {
        AsyncTask<Void, Void, Club> asyncTask = new AsyncTask<Void, Void, Club>() {
            @Override
            protected Club doInBackground(Void... params) {
                try {
                    Query query = mClubTable.where().field("id").eq(clubId);
                    MobileServiceList<Club> result = mClubTable.execute(query).get();
                    return result.get(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Club club) {
                super.onPostExecute(club);

                TextView clubNameTextView = (TextView) activity.findViewById(R.id.clubNameTextView);
                TextView clubDescTextView = (TextView) activity.findViewById(R.id.clubDescTextView);
                TextView clubDetailsTextView = (TextView) activity.findViewById(R.id.clubDetailsTextView);

                clubNameTextView.setText(club.getName());
                clubDescTextView.setText(club.getDescription());
                clubDetailsTextView.setText(club.getDetails());
            }
        };
        asyncTask.execute();
        return asyncTask;
    }
}