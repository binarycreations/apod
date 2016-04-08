package net.binarycreations.apod.domain.dao;

import android.support.annotation.NonNull;

import net.binarycreations.apod.domain.AstroPick;

import org.threeten.bp.LocalDate;

/**
 * Data access object for astronomy picks.
 *
 * @author graham.
 */
public interface AstroPickDao {

    /**
     * Insert an astronomy pick into storage.
     *
     * @param pick to save.
     * @return identifier to stored pick.
     */
    long insert(@NonNull AstroPick pick);

    /**
     * Find pick for a give day.
     *
     * @param date to search for.
     * @return the AstroPick for the date or null if it is not present.
     */
    AstroPick findPick(@NonNull LocalDate date);
}
