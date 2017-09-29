package model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisafarias.myapplication.FeedListActivity;
import com.example.luisafarias.myapplication.R;
import com.wedeploy.android.auth.Authorization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisafarias on 26/09/17.
 */

public class FeedListAdapter extends BaseAdapter{

    private final List<Feed> mFeed;
    Context mContext;
    LayoutInflater mInflater;
    Authorization authorization;

    public FeedListAdapter(Context context,Authorization authorization, List<Feed> feeds){
        this.mContext = context;
        this.mFeed = feeds;
        this.mInflater = LayoutInflater.from(mContext);
        this.authorization = authorization;
    }

    public int getCount(){ return mFeed.size();}

    public Feed getItem(int position){ return mFeed.get(position);}

    public long getItemId(int position){ return Long.parseLong(mFeed.get(position).getId());}

    public View getView(int position, View convertView, ViewGroup parent){

        final View view = mInflater.inflate(R.layout.feed_body,parent,false);
        final Feed feed = mFeed.get(position);

        TextView nome = view.findViewById(R.id.nome_url_recebida);
        nome.setText(feed.getNome());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(FeedListAdapter.class.getName(),"click curto");

            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Log.d(FeedListAdapter.class.getName(),"click long");
                Toast.makeText(mContext,"long click",Toast.LENGTH_LONG).show();
                Repositorio.getInstance(mContext).removeFeed(feed,authorization);
                return true;
            }
        });

        return view;
    }
}
