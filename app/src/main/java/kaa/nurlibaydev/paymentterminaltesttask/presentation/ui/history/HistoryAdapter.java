package kaa.nurlibaydev.paymentterminaltesttask.presentation.ui.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kaa.nurlibaydev.paymentterminaltesttask.data.entity.TransactionEntity;
import kaa.nurlibaydev.paymentterminaltesttask.databinding.ItemHistoryBinding;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final List<TransactionEntity> transactions = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TransactionEntity transaction);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setTransactions(List<TransactionEntity> transactionList) {
        transactions.clear();
        transactions.addAll(transactionList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        TransactionEntity transaction = transactions.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        private final ItemHistoryBinding binding;

        public HistoryViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TransactionEntity transaction) {
            binding.tvTransactionId.setText(transaction.id);
            binding.tvTransactionAmount.setText(transaction.amount);

            long timestamp = transaction.timestamp;
            Date date = new Date(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
            binding.tvTransactionDate.setText(sdf.format(date));
            binding.tvStatus.setText(transaction.status);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(transaction);
                }
            });
        }
    }
}
