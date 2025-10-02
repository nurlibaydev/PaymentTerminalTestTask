package kaa.nurlibaydev.paymentterminaltesttask.presentation.ui.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import kaa.nurlibaydev.paymentterminaltesttask.data.entity.TransactionEntity;
import kaa.nurlibaydev.paymentterminaltesttask.databinding.FragmentTransactionBinding;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.ui.TransactionViewModel;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.utils.ResultData;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.utils.ToastUtils;

public class TransactionFragment extends Fragment {

    private FragmentTransactionBinding binding;
    private TransactionViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentTransactionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String amount = TransactionFragmentArgs.fromBundle(getArguments()).getAmount();
        binding.tvAmount.setText(amount);
        binding.btnPay.setOnClickListener( v ->
                viewModel.insertTransaction(amount, "SUCCESS")
        );

        TransactionEntity failedTransaction = new TransactionEntity(
                amount,
                "ERROR"
        );
        binding.btnError.setOnClickListener(v ->
                NavHostFragment.findNavController(TransactionFragment.this)
                        .navigate(TransactionFragmentDirections
                                .actionCardSimulationFragmentToResultFragment(failedTransaction))
        );

        viewModel.transactionState.observe(getViewLifecycleOwner(), resultData -> {
            if(resultData instanceof ResultData.Success) {
                TransactionEntity transaction = ((ResultData.Success<TransactionEntity>) resultData).getData();

                NavHostFragment.findNavController(TransactionFragment.this)
                        .navigate(TransactionFragmentDirections
                                .actionCardSimulationFragmentToResultFragment(transaction));
            } else if(resultData instanceof ResultData.Error) {
                String message = ((ResultData.Error<TransactionEntity>) resultData).getException().getMessage();
                ToastUtils.showToast(requireContext(), "Ошибка: " + message);
                TransactionEntity transaction = new TransactionEntity(
                        amount,
                        "ERROR"
                );
                NavHostFragment.findNavController(TransactionFragment.this)
                        .navigate(TransactionFragmentDirections
                                .actionCardSimulationFragmentToResultFragment(transaction));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
