package com.example.ivanovnv.recycletest;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 * Created by IvanovNV on 17.04.2018.
 */

public class RecycleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private final String TAG = "TAGFilter";
    private RecyclerView mRecycler;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final ContactsAdapter mContactsAdapter = new ContactsAdapter();
    private View mErrorView;
    private Random mRandom = new Random();
    private ContactsAdapter.OnItemClickListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContactsAdapter.OnItemClickListener) {
            mListener = (ContactsAdapter.OnItemClickListener) context;
        }
    }

    public static RecycleFragment newInstance() {
        return new RecycleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecycler = view.findViewById(R.id.recycler);
        mSwipeRefreshLayout = view.findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.error_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mContactsAdapter);
        mRecycler.addItemDecoration(new CardDecoration());
        mContactsAdapter.setListener(mListener);

    }

    @Override
    public void onRefresh() {
//        mSwipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                int count = mRandom.nextInt(4);
//
//                if(count == 0) {
//                    showError();
//                } else {
//                    showData(count);
//                }
//
//                if(mSwipeRefreshLayout.isRefreshing()){
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }
//        }, 2000);

        getLoaderManager().restartLoader(0, null, this);
    }

//    private void showData(int count) {
//        mMockAdapter.addData(MockGenerator.generate(count), true);
//        mErrorView.setVisibility(View.GONE);
//        mRecycler.setVisibility(View.VISIBLE);
//    }
//
//    private void showError() {
//        mErrorView.setVisibility(View.VISIBLE);
//        mRecycler.setVisibility(View.GONE);
//    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null, null, ContactsContract.Contacts._ID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mContactsAdapter.swapCursor(data);

        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
}
