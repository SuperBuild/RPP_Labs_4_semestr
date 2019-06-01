package com.lab2.adapter;

import android.graphics.Bitmap;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.lab2.MainActivity;
import com.lab2.R;
import com.lab2.TechData;
import com.lab2.VolleyController;
import com.lab2.fragment.PagerFragment;

import org.json.JSONArray;
import org.json.JSONException;


import java.util.concurrent.atomic.AtomicBoolean;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ImageViewHolder> {

    private static JSONArray mData;
    private final ViewHolderListener viewHolderListener;

    public ListAdapter(Fragment fragment, JSONArray data) {
        mData = data;

        viewHolderListener = new ViewHolderListener(fragment);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ImageViewHolder(view, viewHolderListener);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.onBind();
    }

    @Override
    public int getItemCount() {
        return mData.length();
    }

    private static class ViewHolderListener {

        private Fragment fragment;
        private AtomicBoolean enterTransitionStarted;

        ViewHolderListener(Fragment fragment) {
            this.fragment = fragment;
            this.enterTransitionStarted = new AtomicBoolean();
        }

        void onLoadCompleted(ImageView view, int position) {
            if (MainActivity.currentPosition != position) {
                return;
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return;
            }
            fragment.startPostponedEnterTransition();
        }

        void onItemClicked(View view, int position) {
            MainActivity.currentPosition = position;

            ((TransitionSet) fragment.getExitTransition()).excludeTarget(view, true);

            ImageView transitioningView = view.findViewById(R.id.list_tech_image);

            PagerFragment pager = new PagerFragment();

            pager.setData(mData);

            fragment.getFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addSharedElement(transitioningView, transitioningView.getTransitionName())
                    .replace(R.id.fragment_container, pager, PagerFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
    }


    static class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private final TextView nameView;
        private final TextView descView;

        private final ImageView image;
        private final ViewHolderListener viewHolderListener;

        ImageViewHolder(View itemView,
                        ViewHolderListener viewHolderListener) {
            super(itemView);

            this.nameView = itemView.findViewById(R.id.list_tech_name);
            this.descView = itemView.findViewById(R.id.list_tech_description);
            this.image = itemView.findViewById(R.id.list_tech_image);
            this.viewHolderListener = viewHolderListener;

            itemView.findViewById(R.id.list_item).setOnClickListener(this);
        }

        void onBind() {
            final int adapterPosition = getAdapterPosition();

            TechData tech = null;

            try {
                tech = new TechData(mData.getJSONObject(adapterPosition));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            nameView.setText(tech.name);
            descView.setText(tech.description);

            ImageRequest request = new ImageRequest(tech.image_url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bm) {
                            image.setImageBitmap(bm);
                            viewHolderListener.onLoadCompleted(image, adapterPosition);
                        }
                    }, 0, 0, null, null,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("List image loader", "failed to load image:" + volleyError.getMessage());
                            viewHolderListener.onLoadCompleted(image, adapterPosition);
                        }
                    });

            VolleyController.getInstance(image.getContext()).addToRequestQueue(request);

            image.setTransitionName(tech.image_url);
        }

        @Override
        public void onClick(View view) {
            viewHolderListener.onItemClicked(view, getAdapterPosition());
        }
    }

}