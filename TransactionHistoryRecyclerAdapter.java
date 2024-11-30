package mobilepaymentapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionHistoryRecyclerAdapter extends RecyclerView.Adapter<TransactionHistoryRecyclerAdapter.ViewHolder> {
    private List<Transaction> transactions;

    public TransactionHistoryRecyclerAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return transactions.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.amountTextView.setText(transaction.getAmount());
        holder.paymentMethodTextView.setText(transaction.getPaymentMethod());
        holder.dateTextView.setText(transaction.getDate());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView amountTextView;
        private TextView paymentMethodTextView;
        private TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            paymentMethodTextView = itemView.findViewById(R.id.paymentMethodTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }


}

