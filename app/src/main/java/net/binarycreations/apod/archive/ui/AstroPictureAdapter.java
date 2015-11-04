package net.binarycreations.apod.archive.ui;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.binarycreations.apod.domain.AstroItem;

import java.util.List;

/**
 *
 *
 * @author graham.
 */
public class AstroPictureAdapter extends RecyclerView.Adapter<AstroPictureAdapter.ViewHolder> {

    private List<AstroItem> mApods;

    private ArchivePaginationListener mPaginationListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ArchiveRowView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AstroItem item = mApods.get(position);
        holder.mRowView.setTitle(item.getTitle());

        if (AstroItem.MediaType.IMAGE == item.getType()) {
            holder.mRowView.showPicture(item.getUrl());
        } else {
            holder.mRowView.showVideo();
        }

        checkForPagination(position);
    }

    private void checkForPagination(int position) {
        if (mPaginationListener != null && (mApods.size() - 1) <= position) {
            mPaginationListener.onNextPagination(mApods.get(mApods.size() - 1));
        }
    }

    @Override
    public int getItemCount() {
        return mApods == null ? 0 : mApods.size();
    }

    public void appendItems(List<AstroItem> astroItems) {
        if (mApods == null) {
            mApods = astroItems;
            notifyDataSetChanged();
        } else {
            int positionInsertedAt = mApods.size();
            mApods.addAll(positionInsertedAt, astroItems);
            notifyItemRangeInserted(positionInsertedAt, astroItems.size());
        }
    }

    public void setPaginationListener(ArchivePaginationListener listener) {
        mPaginationListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ArchiveRowView mRowView;

        public ViewHolder(ArchiveRowView view) {
            super(view);

            mRowView = view;
        }
    }
}
