package com.example.luisafarias.myapplication.model;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.fragments.PopUpFragment;

import java.util.List;

/**
 * Created by luisafarias on 16/10/17.
 */

public class FeedListRecyclerViewAdapter extends RecyclerView.Adapter<FeedListRecyclerViewAdapter.CustomViewHolder> {

    private List<Feed> _feedList;
    private Context _context;
    private String _token;

    public FeedListRecyclerViewAdapter(Context context, List<Feed> feedList, String token){
        this._feedList = feedList;
        this._context = context;
        this._token = token;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_body,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Feed feed = _feedList.get(position);
        holder.name.setText(feed.get_nome());
        holder.id.setText(feed.get_id());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("feed",feed);
                    bundle.putString("token",_token);
                    PopUpFragment popUpFragment = new PopUpFragment();
                    popUpFragment.setArguments(bundle);
                    popUpFragment.show(
                            ((FragmentActivity)_context).getSupportFragmentManager(),"idPopupFragment");


                    Log.d("click longo",_feedList.get(position).get_nome());
                }else {
                    Log.d("click curto",_feedList.get(position).get_nome());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return _feedList.size();
    }






    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private ItemClickListener itemClickListener;
        protected TextView name;
        protected TextView id;

        public CustomViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            this.name = view.findViewById(R.id.nome_url_recebida);
            this.id = view.findViewById(R.id.idUrlTest);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }

        public void setItemClickListener(ItemClickListener itemClickListener){

            this.itemClickListener = itemClickListener;
        }
    }



}
