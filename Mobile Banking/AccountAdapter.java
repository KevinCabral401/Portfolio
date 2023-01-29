package com.example.mobilebanking;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class AccountAdapter extends RecyclerView.Adapter {

    AccountAdapterListener accountAdapterListener;
    BankingSQLiteHelper bankingSQLiteHelper;

    public interface AccountAdapterListener {
        public void click(int position);
    }

    public AccountAdapter(BankingSQLiteHelper bankingSQLiteHelper){
        this.bankingSQLiteHelper = bankingSQLiteHelper;
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAccountNum;
        TextView textViewAmount;
        TextView textViewAccountName;
        TextView textViewType;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAccountName = itemView.findViewById(R.id.textViewName);
            textViewAccountNum = itemView.findViewById(R.id.textViewNumber);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewType = itemView.findViewById(R.id.textViewType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("position", position);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Account myAccount = bankingSQLiteHelper.getAccount(position);
        AccountViewHolder accountViewHolder = (AccountViewHolder) holder;
        accountViewHolder.textViewAccountNum.setText(myAccount.getNumber());
        accountViewHolder.textViewAccountName.setText(myAccount.getName());
        accountViewHolder.textViewAmount.setText(String.valueOf(myAccount.getAmount()));
        accountViewHolder.textViewType.setText(myAccount.getType());
    }

    @Override
    public int getItemCount() {
        return bankingSQLiteHelper.getCount();
    }
}
