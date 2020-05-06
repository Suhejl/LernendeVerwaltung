package ch.noseryoung.lernendeverwaltung.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 3)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME = "apprentice_db";
    private static UserDatabase userDatabase;

    public static synchronized UserDatabase getUserDb(Context context){
        if(userDatabase == null){
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return userDatabase;
    }

    public abstract UserDao getUserDao();
}
