package net.binarycreations.apod.archive;

import net.binarycreations.apod.domain.AstroItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ArchivePresenterImpl implements ArchivePresenter {

    private ArchiveView mView;

    @Override
    public void setView(ArchiveView view) {
        mView = view;
    }

    @Override
    public void loadAstroPictures() {
        List<AstroItem> apods = new ArrayList<>();
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));

        mView.displayPictures(apods);
    }
}
