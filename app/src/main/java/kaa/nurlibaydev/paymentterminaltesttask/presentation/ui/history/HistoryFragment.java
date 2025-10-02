package kaa.nurlibaydev.paymentterminaltesttask.presentation.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;
import kaa.nurlibaydev.paymentterminaltesttask.data.entity.TransactionEntity;
import kaa.nurlibaydev.paymentterminaltesttask.databinding.FragmentHistoryBinding;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.ui.TransactionViewModel;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.utils.ResultData;
import kaa.nurlibaydev.paymentterminaltesttask.presentation.utils.ToastUtils;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private final HistoryAdapter adapter = HistoryAdapter.getInstance();
    private TransactionViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        setupAdapter();
        setupObservers();
        viewModel.getAllTransactions();

        adapter.setOnItemClickListener(transaction -> { });
        binding.btnClear.setOnClickListener(v -> {
            binding.btnClear.setEnabled(false);
            binding.progressLoader.setVisibility(View.VISIBLE);
            binding.tvEmptyPlaceholder.setVisibility(View.GONE);
            binding.rvHistory.setVisibility(View.GONE);
            viewModel.clearTransactions();
        });
    }

    private void setupAdapter() {
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvHistory.setAdapter(adapter);
    }

    private void setupObservers() {
        binding.progressLoader.setVisibility(View.VISIBLE);
        viewModel.allTransactions.observe(getViewLifecycleOwner(), resultData -> {
            binding.progressLoader.setVisibility(View.GONE);
            binding.btnClear.setEnabled(true);
            if (resultData instanceof ResultData.Success) {
                List<TransactionEntity> list = ((ResultData.Success<List<TransactionEntity>>) resultData).getData();

                if (list != null && !list.isEmpty()) {
                    adapter.setTransactions(list);
                    binding.tvEmptyPlaceholder.setVisibility(View.GONE);
                    binding.rvHistory.setVisibility(View.VISIBLE);
                } else {
                    binding.rvHistory.setVisibility(View.GONE);
                    binding.tvEmptyPlaceholder.setVisibility(View.VISIBLE);
                }

            } else if (resultData instanceof ResultData.Error) {
                String message = ((ResultData.Error<List<TransactionEntity>>) resultData).getException().getMessage();
                ToastUtils.showToast(requireContext(), "Ошибка: " + message);
            }
        });
        viewModel.clearState.observe(getViewLifecycleOwner(), result -> {
            if (result instanceof ResultData.Success) {
                viewModel.getAllTransactions();
            } else if (result instanceof ResultData.Error) {
                ToastUtils.showToast(requireContext(),
                        ((ResultData.Error<Integer>) result).getException().getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.rvHistory.setAdapter(null);
        binding = null;
    }
}

