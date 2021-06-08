package com.coder.knight.jetpack.discover.data.source.local.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movie.class, TvShow.class}, version = 5, exportSchema = false)
public abstract class ContentDatabase extends RoomDatabase {
    public abstract ContentDao contentDao();

    private static ContentDatabase INSTANCE;

    public static synchronized ContentDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ContentDatabase.class, "content.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        ContentDao mContentDao;

        private PopulateDbAsyncTask(ContentDatabase database) {
            mContentDao = database.contentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
