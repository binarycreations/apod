package net.binarycreations.apod.astro;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.binarycreations.apod.astro.view.AstroRowView;
import net.binarycreations.apod.domain.AstroItem;

import java.util.List;

/**
 * @author graham.
 */
public class AstroAdapter extends RecyclerView.Adapter<AstroAdapter.ViewHolder> {

    private List<AstroItem> mApods;

    public AstroAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new AstroRowView(parent.getContext()));
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

        public AstroRowView mRowView;

        public ViewHolder(AstroRowView view) {
            super(view);

            mRowView = view;
        }
    }
}
