package ch.noseryoung.lernendeverwaltung.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object.
 * Class that creates an interface for the program, which is able to execute SQL queries.
 * Only read and create logic is needed in our application.
 */
@Dao
public interface ApprenticeDao {
    @Query("SELECT * FROM apprentices")
    List<Apprentice> getAll();

    @Query("SELECT * FROM apprentices WHERE id = :apprenticeId")
    Apprentice getById(long apprenticeId);

    @Insert
    void insertApprentice(Apprentice apprentice);
}