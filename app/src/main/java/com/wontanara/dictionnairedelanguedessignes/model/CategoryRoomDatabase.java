package com.wontanara.dictionnairedelanguedessignes.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class CategoryRoomDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    private static volatile CategoryRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CategoryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CategoryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CategoryRoomDatabase.class, "category_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                CategoryDao dao = INSTANCE.categoryDao();
                dao.deleteAll();

                Category category = new Category(1, "LocalCat1", new GregorianCalendar());
                dao.insert(category);
                category = new Category(2, "LocalCat2", new GregorianCalendar());
                dao.insert(category);
            });
        }
    };

}
