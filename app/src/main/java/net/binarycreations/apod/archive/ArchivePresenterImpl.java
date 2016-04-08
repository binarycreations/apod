package net.binarycreations.apod.archive;

import net.binarycreations.apod.app.background.Conclusion;
import net.binarycreations.apod.client.ApiError;
import net.binarycreations.apod.domain.AstroPick;

import org.threeten.bp.LocalDate;

import java.util.List;

class ArchivePresenterImpl implements ArchivePresenter, Conclusion<List<AstroPick>> {

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
    public void loadArchivePictures(LocalDate from, LocalDate to) {
        mInteractor.getArchiveItems(from, to, this);
    }

    @Override
    public void onAstroPictureClick(AstroPick item) {
        mView.displayAstroExplanation(item);
    }

    @Override
    public void onError(Exception exception) {
        if (exception instanceof ApiError) {
            mView.displayArchiveUnavailable();
        } else {
            mView.displayNoConnectivity();
        }
    }

    @Override
    public void onConclusion(List<AstroPick> result) {
        mView.displayPictures(result);
    }
}
