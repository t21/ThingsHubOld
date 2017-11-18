package se.berg.thomas.thingshub;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Database;

/**
 * Created by thomas on 2017-11-12.
 */

@Database(entities = {SensorData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SensorDataDao sensorDataDao();
}
