package kaa.nurlibaydev.paymentterminaltesttask.presentation.ui.result;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import kaa.nurlibaydev.paymentterminaltesttask.R;
import kaa.nurlibaydev.paymentterminaltesttask.data.entity.TransactionEntity;
import kaa.nurlibaydev.paymentterminaltesttask.databinding.FragmentResultBinding;

public class ResultFragment extends Fragment {

    private FragmentResultBinding binding;
    private TransactionEntity transaction;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            ResultFragmentArgs args = ResultFragmentArgs.fromBundle(getArguments());
            transaction = args.getTransaction();

            showTransactionDetails();
        }

        binding.btnToHistory.setOnClickListener(v ->
                NavHostFragment.findNavController(ResultFragment.this)
                        .navigate(ResultFragmentDirections.actionResultFragmentToHistoryFragment())
        );

        boolean isSuccess = Objects.equals(transaction.status, "SUCCESS");
        if(isSuccess) {
            binding.imgResult.setImageResource(R.drawable.check);
            binding.imgResult.setColorFilter(ContextCompat.getColor(requireContext(), R.color.color_primary));
            binding.tvResultStatus.setText("Операция успешна");
            binding.cardDetails.setVisibility(VISIBLE);
            binding.btnToHistory.setVisibility(VISIBLE);
        } else {
            binding.imgResult.setImageResource(R.drawable.ic_error);
            binding.tvResultStatus.setText("Операция отклонена");
            binding.btnToHistory.setVisibility(GONE);
            binding.cardDetails.setVisibility(GONE);
        }
    }

    private void showTransactionDetails() {
        long timestamp = transaction.timestamp;
        Date date = new Date(timestamp);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        String formattedDate = sdf.format(date);

        binding.tvTransactionId.setText(transaction.id);
        binding.tvTransactionDate.setText(formattedDate);
        binding.tvTransactionAmount.setText(transaction.amount);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}