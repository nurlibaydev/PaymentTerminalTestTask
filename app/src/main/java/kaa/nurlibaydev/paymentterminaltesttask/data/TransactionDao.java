package kaa.nurlibaydev.paymentterminaltesttask.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kaa.nurlibaydev.paymentterminaltesttask.data.entity.TransactionEntity;

@Dao
public interface TransactionDao {
    @Insert
    Long insert(TransactionEntity transaction);

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    List<TransactionEntity> getAllTransactionList();

    @Query("DELETE FROM transactions")
    int clearAll();
}
