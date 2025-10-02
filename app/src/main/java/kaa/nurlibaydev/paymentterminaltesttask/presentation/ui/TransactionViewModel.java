package kaa.nurlibaydev.paymentterminaltesttask.presentation.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.concurrent.Executors;
import kaa.nurlibaydev.paymentterminaltesttask.data.TransactionRepositoryImpl;
import kaa.nurlibaydev.paymentterminaltesttask.data.entity.TransactionEntity;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.utils.ResultData;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.utils.SingleLiveEvent;

public class TransactionViewModel extends AndroidViewModel {

    private final TransactionRepositoryImpl repository;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = TransactionRepositoryImpl.getInstance(application);
    }

    private final SingleLiveEvent<ResultData<TransactionEntity>> _transactionState = new SingleLiveEvent<>();
    public LiveData<ResultData<TransactionEntity>> transactionState = _transactionState;

    private final MutableLiveData<ResultData<List<TransactionEntity>>> _allTransactions = new MutableLiveData<>();
    public LiveData<ResultData<List<TransactionEntity>>> allTransactions = _allTransactions;

    private final MutableLiveData<ResultData<Integer>> _clearState = new MutableLiveData<>();
    public LiveData<ResultData<Integer>> clearState = _clearState;

    public void getAllTransactions() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<TransactionEntity> list = repository.getAllTransactions();
            try {
                Thread.sleep(2000);  // Искусственная задержка для показа лоадера
                _allTransactions.postValue(new ResultData.Success<>(list));
            } catch (Exception e) {
                _allTransactions.postValue(new ResultData.Error<>(e));
            }
        });
    }

    public void insertTransaction(String amount, String status) {
        TransactionEntity transaction = new TransactionEntity(
                amount,
                status
        );
        Executors.newSingleThreadExecutor().execute(() -> {
            ResultData<Void> result = repository.insert(transaction);

            if (result instanceof ResultData.Success) {
                _transactionState.postValue(new ResultData.Success<>(transaction));
            } else if (result instanceof ResultData.Error) {
                _transactionState.postValue(new ResultData.Error<>(((ResultData.Error<Void>) result).getException()));
            }
        });
    }

    public void clearTransactions() {
        Executors.newSingleThreadExecutor().execute(() -> {
            ResultData<Integer> result = repository.clearTransactions();
            _clearState.postValue(result);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.shutdown();
    }
}
