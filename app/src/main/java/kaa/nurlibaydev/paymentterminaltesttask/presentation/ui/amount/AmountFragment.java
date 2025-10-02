package kaa.nurlibaydev.paymentterminaltesttask.presentation.ui.amount;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import kaa.nurlibaydev.paymentterminaltesttask.databinding.FragmentAmountBinding;

public class AmountFragment extends Fragment {

    private FragmentAmountBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAmountBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    binding.btnPay.setEnabled(binding.etAmount.getAmount() > 0);
                } catch (NumberFormatException e) {
                    binding.btnPay.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        binding.btnPay.setOnClickListener(v -> {
            String amount = binding.etAmount.getValue();
            NavHostFragment.findNavController(AmountFragment.this)
                    .navigate(AmountFragmentDirections.actionAmountFragmentToCardSimulationFragment(amount));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}