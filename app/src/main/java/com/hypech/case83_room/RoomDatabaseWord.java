package com.hypech.case83_room;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {EntityWord.class}, version = 1, exportSchema = false)
public abstract class RoomDatabaseWord extends RoomDatabase {

    public abstract DaoWord wordDao();

    private static volatile RoomDatabaseWord INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RoomDatabaseWord getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactsContract.Data.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabaseWord.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                DaoWord dao = INSTANCE.wordDao();
                dao.deleteAll();

                EntityWord word = new EntityWord("Hello");
                dao.insert(word);
                word = new EntityWord("World");
                dao.insert(word);
            });
        }
    };
}