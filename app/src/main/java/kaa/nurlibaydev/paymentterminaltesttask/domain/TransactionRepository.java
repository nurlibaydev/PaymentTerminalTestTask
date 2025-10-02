package kaa.nurlibaydev.paymentterminaltesttask.domain;

import java.util.List;

import kaa.nurlibaydev.paymentterminaltesttask.data.entity.TransactionEntity;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.utils.ResultData;

public interface TransactionRepository {

    ResultData<Void> insert(TransactionEntity transaction);

    List<TransactionEntity> getAllTransactions();

    ResultData<Integer> clearTransactions();
}
