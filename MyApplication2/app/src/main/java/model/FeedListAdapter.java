package model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luisafarias.myapplication.R;
import com.wedeploy.android.auth.Authorization;

import java.util.ArrayList;

/**
 * Created by luisafarias on 26/09/17.
 */

public class FeedListAdapter extends BaseAdapter{

    private final ArrayList<Feed> mFeed;
    Context mContext;
    LayoutInflater mInflater;
    //private final Activity activity;

    public FeedListAdapter(Context context, Authorization authorization){
        this.mContext = context;
        this.mFeed = Repositorio.getInstance(mContext).getAllFeeds(authorization);
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        //this.activity = activity;
    }

    public int getCount(){ return mFeed.size();}

    public Feed getItem(int position){ return mFeed.get(position);}

    public long getItemId(int position){ return Long.parseLong(mFeed.get(position).getId());}

    public View getView(int position, View convertView, ViewGroup parent){

        View view = mInflater
                .inflate(R.layout.feed_body,parent,false);
        Feed feed = mFeed.get(position);

        TextView nome = (TextView)
                view.findViewById(R.id.nome_url_recebida);
        nome.setText(feed.getNome());
        return view;
    }
}
