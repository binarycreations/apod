package net.binarycreations.apod.archive;

import net.binarycreations.apod.app.background.Conclusion;
import net.binarycreations.apod.domain.AstroPick;

import org.threeten.bp.LocalDate;

import java.util.List;

/**
 * Defines responsibility for browsing an archive of astronomy picks.
 *
 * @author graham.
 */
public interface ArchiveInteractor {

    void getArchiveItems(LocalDate from, LocalDate to, Conclusion<List<AstroPick>> archiveItems);

}