package kaa.nurlibaydev.paymentterminaltesttask.data;

import android.content.Context;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kaa.nurlibaydev.paymentterminaltesttask.data.entity.TransactionEntity;
import kaa.nurlibaydev.paymentterminaltesttask.domain.TransactionRepository;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.utils.ResultData;

public class TransactionRepositoryImpl implements TransactionRepository {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static TransactionRepositoryImpl instance;
    private final TransactionDao transactionDao;

    private TransactionRepositoryImpl(TransactionDao dao) {
        this.transactionDao = dao;
    }

    public static synchronized TransactionRepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new TransactionRepositoryImpl(AppDatabase.getInstance(context).transactionDao());
        }
        return instance;
    }

    @Override
    public ResultData<Void> insert(TransactionEntity transaction) {
        try {
            long rowId = transactionDao.insert(transaction);

            if (rowId > 0) {
                return new ResultData.Success<>(null);
            } else {
                return new ResultData.Error<>(new Throwable("Transaction not saved"));
            }
        } catch (Exception e) {
            return new ResultData.Error<>(e);
        }
    }

    @Override
    public List<TransactionEntity> getAllTransactions() {
        return transactionDao.getAllTransactionList();
    }

    @Override
    public ResultData<Integer> clearTransactions() {
        try {
            int deletedCount = transactionDao.clearAll();
            if (deletedCount >= 0) {
                return new ResultData.Success<>(deletedCount);
            } else {
                return new ResultData.Error<>(new Exception("Не удалось очистить таблицу"));
            }
        } catch (Exception e) {
            return new ResultData.Error<>(e);
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}

