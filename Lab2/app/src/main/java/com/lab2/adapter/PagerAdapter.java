package com.lab2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lab2.TechData;
import com.lab2.fragment.PageFragment;

import org.json.JSONArray;
import org.json.JSONException;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private JSONArray mData;

    public PagerAdapter(FragmentManager fm) {
        super(fm);

        mData = new JSONArray();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.length();
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return PageFragment.newInstance(new TechData(mData.getJSONObject(position)));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setData(JSONArray data) {
        mData = data;
        notifyDataSetChanged();
    }
}