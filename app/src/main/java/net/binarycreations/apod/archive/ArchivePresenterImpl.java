package net.binarycreations.apod.archive;

import net.binarycreations.apod.app.background.Conclusion;
import net.binarycreations.apod.client.ApiError;
import net.binarycreations.apod.domain.AstroItem;

import java.util.Date;
import java.util.List;

class ArchivePresenterImpl implements ArchivePresenter {

    private final ArchiveInteractor mInteractor;

    private ArchiveView mView;

    ArchivePresenterImpl(ArchiveInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void setView(ArchiveView view) {
        mView = view;
    }

    @Override
    public void loadArchivePictures(Date from, Date to) {
        mInteractor.getArchiveItems(from, to, new Conclusion<List<AstroItem>>() {

            @Override
            public void onError(Exception exception) {
                if (exception instanceof ApiError) {
                    mView.displayArchiveUnavailable();
                } else {
                    mView.displayNoConnectivity();
                }
            }

            @Override
            public void onConclusion(List<AstroItem> result) {
                mView.displayPictures(result);
            }
        });
    }

    @Override
    public void onAstroPictureClick(AstroItem item) {
        mView.displayAstroExplanation(item);
    }
}
