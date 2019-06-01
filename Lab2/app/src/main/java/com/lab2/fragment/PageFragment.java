package com.lab2.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.lab2.R;
import com.lab2.TechData;
import com.lab2.VolleyController;

public class PageFragment extends Fragment {

    static final String ARGUMENT_TECH_IMAGE_URL = "ARG_TECH_IMAGE_URL";
    static final String ARGUMENT_TECH_DESCRIPTION = "ARG_TECH_DESCRIPTION";


    public static PageFragment newInstance(TechData tech) {
        PageFragment fragment = new PageFragment();

        Bundle arguments = new Bundle();

        arguments.putString(ARGUMENT_TECH_IMAGE_URL, tech.image_url);
        arguments.putString(ARGUMENT_TECH_DESCRIPTION, tech.description);

        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pager_item, container, false);

        Bundle arguments = getArguments();

        TextView descView = view.findViewById(R.id.pager_tech_description);
        descView.setText(arguments.getString(ARGUMENT_TECH_DESCRIPTION));

        String graphic =  arguments.getString(ARGUMENT_TECH_IMAGE_URL);
        final ImageView imageView = view.findViewById(R.id.pager_tech_image);
        view.findViewById(R.id.pager_tech_image).setTransitionName(graphic);

        ImageRequest request = new ImageRequest(graphic,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bm) {
                        imageView.setImageBitmap(bm);
                        getParentFragment().startPostponedEnterTransition();
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Pager image loader", "failed to load image:" + volleyError.getLocalizedMessage());
                        getParentFragment().startPostponedEnterTransition();
                    }
                });

        VolleyController.getInstance(view.getContext().getApplicationContext()).addToRequestQueue(request);

        return view;
    }
}
