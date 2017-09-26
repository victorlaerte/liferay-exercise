package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wedeploy.android.auth.Authorization;

import java.util.ArrayList;

/**
 * Created by luisafarias on 26/09/17.
 */

public class FeedListAdapter {

    private ArrayList<Feed> mFeed;
    Context mContext;
    LayoutInflater mInflater;

    public FeedListAdapter(Context context, Authorization authorization){
        mFeed = Repositorio.getInstance(context).getAllFeeds(authorization);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount(){ return mFeed.size();}

    public Feed getItem(int position){ return mFeed.get(position);}

    public String getItemId(int position){ return mFeed.get(position).getId();}

    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
        }

        TextView text = (TextView) convertView.findViewById(android.R.id.text1);
        text.setText(mFeed.get(position).getNome());

        return convertView;
    }
}
