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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.activities.ItemListActivity;
import com.example.luisafarias.myapplication.dao.RssDAO;
import com.example.luisafarias.myapplication.fragments.PopUpFragment;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.util.AndroidUtil;
import com.example.luisafarias.myapplication.util.Constants;

import java.text.Normalizer;
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
		if (AndroidUtil.isOnline(_context)){
			String url = rss.getChannel().getImage().getUrl();
			ImageView iv = holder.image;

			Glide.with(holder.view.getContext()).load(url).into(iv);
		}
		Rss rssAux = RssDAO.getInstance().getRssRealm(rss);
		rss.setFavorite(rssAux.getFavorite());

		if (rss.getFavorite()){
			holder.favorite.setChecked(true);
		}

		holder.favorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
                rss.setFavorite(isChecked);
                RssDAO.getInstance().deleteRealmRss(rss);
			RssDAO.getInstance().addRssRealm(rss,rss);

        });


		holder.view.setOnLongClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.RSS, rss);
            bundle.putString(Constants.TOKEN, _token);
            PopUpFragment popUpFragment = new PopUpFragment();
            popUpFragment.setArguments(bundle);
            popUpFragment.show(
                ((FragmentActivity) _context).getSupportFragmentManager(),
                Constants.ID_POPUP);
            return false;
        });

		holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(_context, ItemListActivity.class);
            Bundle bundle = new Bundle();
			bundle.putParcelable(Constants.RSS, rss);
            intent.putExtra(Constants.RSS, bundle);
            _context.startActivity(intent);
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
				protected FilterResults performFiltering(
					CharSequence constraint) {

					FilterResults filterResults = new FilterResults();

					if (constraint == null || constraint.length() == 0) {
						filterResults.values = _rssListAux;
						filterResults.count = _rssListAux.size();
					} else {

						List<Rss> rssListAux = new ArrayList<>();

						for (Rss r : _rssListAux) {
							if (removeAccent(r.getChannel().getTitle().toUpperCase()).
								contains(removeAccent(constraint.toString().toUpperCase()))) {
								rssListAux.add(r);
							}
						}

						filterResults.values = rssListAux;
						filterResults.count = rssListAux.size();
					}
					return filterResults;
				}

				@Override
				protected void publishResults(CharSequence constraint,
					FilterResults results) {

					updateAnswers((List<Rss>) results.values);
				}
			};
		}

		return _filter;
	}

	class CustomViewHolder extends RecyclerView.ViewHolder {
		protected TextView name;
		protected TextView id;
		public ImageView image;
		protected View view;
		public CheckBox favorite;

		public CustomViewHolder(View view) {
			super(view);
			this.view = view;
			this.name = view.findViewById(R.id.nome_url_recebida);
			this.id = view.findViewById(R.id.idUrlTest);
			this.image = view.findViewById(R.id.imageView2);
			this.favorite = view.findViewById(R.id.fav);
		}
	}

	private String removeAccent(String str){
		if (str != null){
			str = Normalizer.normalize(str, Normalizer.Form.NFD);
			str = str.replaceAll("[^\\p{ASCII}]", "");
		}
		return str;
	}

	private List<Rss> _rssList;
	private List<Rss> _rssListAux;
	private Context _context;
	private Filter _filter;
	private String _token;
}
