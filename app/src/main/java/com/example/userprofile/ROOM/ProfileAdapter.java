package com.example.userprofile.ROOM;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userprofile.R;
import com.example.userprofile.databinding.ItemListContentBinding;

public class ProfileAdapter extends ListAdapter<Model, ProfileAdapter.ViewHolder> {
    private OnItemClickListener listener;

    public ProfileAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Model> DIFF_CALLBACK = new DiffUtil.ItemCallback<Model>() {
        @Override
        public boolean areItemsTheSame(Model oldItem, Model newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Model oldItem, Model newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
//                    oldItem.getEmail().equals(newItem.getEmail()) &&
                    oldItem.getEmail().equals(newItem.getEmail());
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout using data binding
        ItemListContentBinding binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = getItemAt(position);
        holder.userName.setText(model.getName());
        holder.userEmail.setText(model.getEmail());
    }

    public Model getItemAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail;
        ItemListContentBinding binding;

        public ViewHolder(@NonNull ItemListContentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            userName = binding.tvName;
            userEmail = binding.tvEmailId;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Model model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
