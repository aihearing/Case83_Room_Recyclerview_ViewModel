package com.hypech.case83_room.L_RecyclerView;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.hypech.case83_room.EntityWord;

public class AdapterWordList extends ListAdapter<EntityWord, ViewHolderWord> {

    public AdapterWordList(@NonNull DiffUtil.ItemCallback<EntityWord> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ViewHolderWord onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderWord.create(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolderWord holder, int position) {
        EntityWord current = getItem(position);
        holder.bind(current.getWord());
    }

    public static class WordDiff extends DiffUtil.ItemCallback<EntityWord> {

        @Override
        public boolean areItemsTheSame(@NonNull EntityWord oldItem, @NonNull EntityWord newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull EntityWord oldItem, @NonNull EntityWord newItem) {
            return oldItem.getWord().equals(newItem.getWord());
        }
    }
}
