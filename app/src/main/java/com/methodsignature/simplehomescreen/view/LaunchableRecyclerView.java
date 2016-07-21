package com.methodsignature.simplehomescreen.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.methodsignature.simplehomescreen.R;
import com.methodsignature.simplehomescreen.data.Launchable;
import com.methodsignature.simplehomescreen.view.lists.EvenPaddingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by randallmitchell on 7/20/16.
 */
public class LaunchableRecyclerView extends RecyclerView {

    public interface OnLaunchableClickListener {
        void onLaunchableClicked(Launchable launchable);
    }

    @Nullable
    private OnLaunchableClickListener onLaunchableClickListener;

    private final List<Launchable> launchableList = new ArrayList<>();

    public LaunchableRecyclerView(Context context) {
        this(context, null);
    }

    public LaunchableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LaunchableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setHasFixedSize(true);

        setAdapter(new LaunchableListAdapter(this));
        setLayoutManager(new LinearLayoutManager(getContext()));
        addItemDecoration(new EvenPaddingItemDecoration(getResources().getDimension(R.dimen.standard_list_item_margin)));
    }

    public void setOnLaunchableClickListener(OnLaunchableClickListener onLaunchableClickListener) {
        this.onLaunchableClickListener = onLaunchableClickListener;
    }

    /**
     * Call {@link Adapter#notifyDataSetChanged()} on {@link #getAdapter()}
     */
    public void addLaunchable(Launchable launchable) {
        launchableList.add(launchable);
    }

    private static class LaunchableListAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final LaunchableRecyclerView launchableRecyclerView;

        public LaunchableListAdapter(LaunchableRecyclerView launchableRecyclerView) {
            this.launchableRecyclerView = launchableRecyclerView;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Launchable launchable = launchableRecyclerView.launchableList.get(position);
            holder.title.setText(launchable.getAppName());
            holder.icon.setImageDrawable(launchable.getIcon());
            holder.position = position;
            holder.rootView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchableRecyclerView.onItemClicked(position);
                }
            });
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(launchableRecyclerView.getContext())
                    .inflate(R.layout.linear_launchable_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return launchableRecyclerView.launchableList.size();
        }
    }

    private void onItemClicked(int position) {
        if (onLaunchableClickListener != null) {
            onLaunchableClickListener.onLaunchableClicked(launchableList.get(position));
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public int position;

        public View rootView;

        @BindView(R.id.linear_launchable_list_item_icon)
        public ImageView icon;

        @BindView(R.id.linear_launchable_list_item_title)
        public TextView title;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            ButterKnife.bind(this, rootView);
        }
    }

    public void clear() {
        launchableList.clear();
        getAdapter().notifyDataSetChanged();
    }
}
