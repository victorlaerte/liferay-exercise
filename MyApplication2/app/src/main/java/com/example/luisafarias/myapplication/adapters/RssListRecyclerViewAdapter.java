package com.example.luisafarias.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.activities.ItemListActivity;
import com.example.luisafarias.myapplication.fragments.PopUpFragment;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisafarias on 16/10/17.
 */

public class RssListRecyclerViewAdapter
        extends RecyclerView.Adapter<RssListRecyclerViewAdapter.CustomViewHolder>
        implements Filterable {


    public RssListRecyclerViewAdapter(Context context, List<Rss> rssList,
                                      String token) {
        this._context = context;
        setRssList(rssList);
        setToken(token);
    }

    public void setRssList(List<Rss> rssList) {
        this._rssList = rssList;
    }

    public void setRssListAux(List<Rss> rssListAux) {
        this._rssListAux = rssListAux;
    }

    public void setToken(String token) {
        this._token = token;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context)
                .inflate(R.layout.rss_body, null);//dar uma olhada aqui depois

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final Rss rss = _rssList.get(position);
        holder.name.setText(rss.getChannel().getTitle());
        holder.id.setText(rss.getId());

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.RSS, rss);
                bundle.putString(Constants.TOKEN, _token);
                PopUpFragment popUpFragment = new PopUpFragment();
                popUpFragment.setArguments(bundle);
                popUpFragment.show(
                        ((FragmentActivity) _context).getSupportFragmentManager(),
                        "idPopupFragment");

                Log.d("click longo", _rssList.get(position).getChannel().getTitle());

                return false;
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click curto", _rssList.get(position).getChannel().getTitle());
                Intent intent =
                        new Intent(_context, ItemListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.CHANNEL, rss.getChannel());
                bundle.putParcelable(Constants.RSS, rss);
                intent.putExtra(Constants.RSS, bundle);
                _context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return _rssList.size();
    }

    public void updateAnswers(List<Rss> rss) {
        this._rssList = rss;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (_filter == null) {
            _filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();

                    if (constraint == null || constraint.length() == 0) {
                        filterResults.values = _rssListAux;
                        filterResults.count = _rssListAux.size();
                    } else {

                        List<Rss> rssListAux = new ArrayList();

                        for (Rss r : _rssListAux) {
                            if (r.getChannel().getTitle().toUpperCase().
                                    contains(constraint.toString().toUpperCase())) {

                                Log.d("RssListAdapter", "update");
                                rssListAux.add(r);
                            }
                        }

                        filterResults.values = rssListAux;
                        filterResults.count = rssListAux.size();
                    }
                    return filterResults;
                }


                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results.count == 0) {
                        Log.d("RssListRecycleV", "null");
                    } else {
                        Log.d("RssListRecycleV", "not null");

                    }

                    updateAnswers((List<Rss>) results.values);
                }
            };
        }

        return _filter;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView id;
        protected View view;

        public CustomViewHolder(View view) {
            super(view);
            this.view = view;
            this.name = view.findViewById(R.id.nome_url_recebida);
            this.id = view.findViewById(R.id.idUrlTest);
        }
    }

    private List<Rss> _rssList;
    private List<Rss> _rssListAux;
    private Context _context;
    private Filter _filter;
    private String _token;
}
