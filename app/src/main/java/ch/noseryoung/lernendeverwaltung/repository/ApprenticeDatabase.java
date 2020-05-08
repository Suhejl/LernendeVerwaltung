package ch.noseryoung.lernendeverwaltung.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Class that generates and hands out the database.
 * If no database exists a new one is created.
 */
@Database(entities = {Apprentice.class}, version = 4, exportSchema = false)
public abstract class ApprenticeDatabase extends RoomDatabase {
    private static final String DB_NAME = "apprentice_db";
    private static ApprenticeDatabase apprenticeDatabase;

    public static synchronized ApprenticeDatabase getApprenticeDb(Context context){
        if(apprenticeDatabase == null){
            apprenticeDatabase = Room.databaseBuilder(context, ApprenticeDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return apprenticeDatabase;
    }

    public abstract ApprenticeDao getApprenticeDao();
}
