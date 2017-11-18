package se.berg.thomas.thingshub;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

/**
 * Created by thomas on 2017-11-12.
 */

@Dao
public interface SensorDataDao {
//    @Query("SELECT * FROM user")
//    List<SensorData> getAll();

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<SensorData> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
//            + "last_name LIKE :last LIMIT 1")
//    SensorData findByName(String first, String last);

    @Insert
    void insert(SensorData sensorData);

//    @Insert
//    void insertAll(SensorData... users);

//    @Delete
//    void delete(SensorData user);
}