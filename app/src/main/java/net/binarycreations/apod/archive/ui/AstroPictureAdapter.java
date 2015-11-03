package net.binarycreations.apod.archive.ui;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.binarycreations.apod.domain.AstroItem;

import java.util.List;

/**
 * @author graham.
 */
public class AstroPictureAdapter extends RecyclerView.Adapter<AstroPictureAdapter.ViewHolder> {

    private List<AstroItem> mApods;

    public AstroPictureAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ArchiveRowView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AstroItem item = mApods.get(position);

        holder.mRowView.setTitle(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mApods == null ? 0 : mApods.size();
    }

    public void setItems(List<AstroItem> astroItems) {
        if (mApods == null || !mApods.equals(astroItems)) {
            mApods = astroItems;
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ArchiveRowView mRowView;

        public ViewHolder(ArchiveRowView view) {
            super(view);

            mRowView = view;
        }
    }
}
