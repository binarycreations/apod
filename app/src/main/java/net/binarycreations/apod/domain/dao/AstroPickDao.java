package net.binarycreations.apod.domain.dao;

import net.binarycreations.apod.domain.AstroPick;

import org.threeten.bp.LocalDate;

import java.util.List;

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
    long insert(AstroPick pick);

    /**
     * Find all picks between two dates.
     *
     * @param from to search from
     * @param to to search till
     * @return a list of found dates ordered by most recent first.
     */
    List<AstroPick> findAllBetweenDates(LocalDate from, LocalDate to);

}
