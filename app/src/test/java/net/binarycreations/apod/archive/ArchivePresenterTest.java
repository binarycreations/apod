package net.binarycreations.apod.archive;


import net.binarycreations.apod.client.ApiError;
import net.binarycreations.apod.domain.AstroPick;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.threeten.bp.LocalDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ArchivePresenterTest {

    private static final AstroPick ITEM = new AstroPick("title", "explantion", "url", AstroPick.MediaType.IMAGE,
            LocalDate.now());

    @Mock
    ArchiveView mockView;

    @Mock
    ArchiveInteractor mockInteractor;

    ArchivePresenterImpl sut;

    @Before
    public void setUp() {
        sut = new ArchivePresenterImpl(mockInteractor);
        sut.setView(mockView);
    }

    @Test
    public void loadArchivePictures_shouldGetArchiveItems() {
        LocalDate fromDate = LocalDate.of(2015, 11, 1);
        LocalDate toDate = LocalDate.of(2015, 11, 7);

        sut.loadArchivePictures(fromDate, toDate);

        verify(mockInteractor).getArchiveItems(fromDate, toDate, sut);
    }

    @Test
    public void onAstroPickClick_shouldDisplayExplanation() {
        sut.onAstroPictureClick(ITEM);
        verify(mockView).displayAstroExplanation(ITEM);
    }

    @Test
    public void onError_shouldShowArchiveServiceUnavailableGivenApiError() {
        sut.onError(new ApiError("Testing..."));
        mockView.displayArchiveUnavailable();
    }

    @Test
    public void onError_shouldShowNoConnectivityGivenAnyOtherException() {
        sut.onError(new IOException());
        mockView.displayNoConnectivity();
    }

    @Test
    public void onConclusion_shouldDisplayPictures() {
        List<AstroPick> toDisplay = new ArrayList<>();
        toDisplay.add(ITEM);
        sut.onConclusion(toDisplay);
        mockView.displayPictures(toDisplay);
    }
}