package net.binarycreations.apod.archive;


import net.binarycreations.apod.client.ApiError;
import net.binarycreations.apod.domain.AstroItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ArchivePresenterTest {

    private static final AstroItem ITEM = new AstroItem("title", "explantion", "url", AstroItem.MediaType.IMAGE, new
            Date());

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
        Date fromDate = createDate(2015, 11, 1);
        Date toDate = createDate(2015, 11, 7);
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
        List<AstroItem> toDisplay = new ArrayList<>();
        toDisplay.add(ITEM);
        sut.onConclusion(toDisplay);
        mockView.displayPictures(toDisplay);
    }

    private Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(year, month, day);
        return cal.getTime();
    }
}