package ch.noseryoung.lernendeverwaltung.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id = :userId")
    User getById(long userId);

    @Insert
    void insertUser(User user);
}