package edu.mobileappdevii.exercises.clubfinder;

import android.app.ListFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Antar on 12/8/2015.
 */
public class ClubSearchFragment extends ListFragment {
    private ClubAdapter mAdapter;
    private ClubService mClubService;

    public ClubSearchFragment() {
    }

    public static ClubListFragment newInstance() {
        ClubListFragment fragment = new ClubListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_search, container, false);

        mAdapter = new ClubAdapter(getActivity().getBaseContext(), android.R.layout.simple_list_item_1);
        setListAdapter(mAdapter);

        mClubService = new ClubService(getActivity());


        final EditText edit = (EditText) view.findViewById(R.id.editText);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = edit.getText().toString();
                mClubService.search(getActivity(), mAdapter, query);
            }
        });

        return view;
    }
}