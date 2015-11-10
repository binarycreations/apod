package net.binarycreations.apod.archive;

import net.binarycreations.apod.app.background.Conclusion;
import net.binarycreations.apod.domain.AstroPick;

import java.util.Date;
import java.util.List;

/**
 * Defines responsibility for browsing an archive of astronomy picks.
 *
 * @author graham.
 */
public interface ArchiveInteractor {

    void getArchiveItems(Date from, Date to, Conclusion<List<AstroPick>> archiveItems);

}