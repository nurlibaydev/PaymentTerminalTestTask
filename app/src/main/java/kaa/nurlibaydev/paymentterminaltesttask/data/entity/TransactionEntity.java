package kaa.nurlibaydev.paymentterminaltesttask.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "transactions")
public class TransactionEntity implements Serializable {

    @PrimaryKey
    @NonNull
    public String id;

    public String amount;
    public String status; // "SUCCESS" / "ERROR"
    public long timestamp;

    public TransactionEntity(String amount, String status) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }
}
