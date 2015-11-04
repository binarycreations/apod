package net.binarycreations.apod.archive;

import com.squareup.okhttp.OkHttpClient;

import net.binarycreations.apod.app.background.Tasks;
import net.binarycreations.apod.client.NasaApodClient;

/**
 * Provides access to archive related functionality.
 *
 * @author graham.
 */
public class ArchiveFactory {

    public ArchivePresenter getArchivePresenter() {
        return new ArchivePresenterImpl(new ArchiveInteractorImpl(new Tasks(), new NasaApodClient(new OkHttpClient(), "DEMO_KEY")));
    }

}
