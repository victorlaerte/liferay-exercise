package com.example.luisafarias.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.Item;

import java.util.List;

/**
 * Created by luisafarias on 19/10/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

	private List<Item> _feedItems;

	//TODO Se não esttiver utilizando remover
	private Context _context;
	private LayoutInflater _layoutInflater;

	public ItemAdapter(Context context, List<Item> feed) {
		this._feedItems = feed;
		this._context = context;
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.item_body, parent, false);
		return new ItemHolder(view);
		//        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		//        final View v = layoutInflater.inflate(R.layout.item_body,parent,false);
		//        return new ItemHolder(v);
	}

	@Override
	public int getItemCount() {
		return _feedItems.size();
	}

	public void updateAnswers(List<Item> items) {
		this._feedItems = items;
		notifyDataSetChanged();
	}

	private Item getItem(int adapterPosition) {
		return _feedItems.get(adapterPosition);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public void onBindViewHolder(final ItemHolder holder, int position) {
		Item item = _feedItems.get(position);
		holder.titleTextField.setText(item.getTitle());

		//        Item item_body = feedItems.get(position);
		//
		//        holder.titleTextField.setText(item_body.get_title());
		//        Document doc = Jsoup.parse(item_body.get_description());
		//        Element imageElement = doc.select("img").first();
		//        if (imageElement != null){
		//            String absoluteUrl = imageElement.absUrl("src");
		//            if (absoluteUrl != null){
		//
		//                holder.descriptionTextField.setText(doc.body().text());
		//            }
		//        }
		//        holder.publicationDateTextField.setText(item_body.get_publicationDate());
	}

	public class ItemHolder extends RecyclerView.ViewHolder {
		protected TextView titleTextField;
		protected TextView descriptionTextField;

		private ImageView imageView;
		private TextView publicationDateTextField;

		public ItemHolder(View itemView) {
			super(itemView);
			titleTextField = itemView.findViewById(R.id.title);
			imageView = itemView.findViewById(R.id.image);
			descriptionTextField = itemView.findViewById(R.id.description);
			publicationDateTextField = itemView.findViewById(R.id.pubdate);
		}
	}
}
