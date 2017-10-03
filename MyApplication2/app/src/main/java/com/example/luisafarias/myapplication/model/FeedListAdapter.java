package com.example.luisafarias.myapplication.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.luisafarias.myapplication.NewFeedFragment;
import com.example.luisafarias.myapplication.PopUpActivity;
import com.example.luisafarias.myapplication.R;
import com.wedeploy.android.auth.Authorization;

import java.util.List;

/**
 * Created by luisafarias on 26/09/17.
 */

public class FeedListAdapter extends BaseAdapter{

    public FeedListAdapter(Context context,Authorization authorization, List<Feed> feeds){
        this._context = context;
        this._feed = feeds;
        this._inflater = LayoutInflater.from(_context);
        this._authorization = authorization;
    }

    public int getCount(){ return _feed.size();}

    public Feed getItem(int position){ return _feed.get(position);}

    public long getItemId(int position){ return Long.parseLong(_feed.get(position).get_id());}

    public View getView(int position, View convertView, ViewGroup parent){

        final View view = _inflater.inflate(R.layout.feed_body,parent,false);
        final Feed feed = _feed.get(position);

        TextView nome = view.findViewById(R.id.nome_url_recebida);
        TextView urlTest = view.findViewById(R.id.idUrlTest);
        nome.setText(feed.get_nome());
        urlTest.setText(feed.get_id());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(FeedListAdapter.class.getName(),"click curto");
//                NewFeedFragment fragmentNewFeed = new NewFeedFragment();
//                FragmentManager fm = getFragmentManager(); //error
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.add(R.id.fragment_new_url,fragmentNewFeed);
//                ft.commit();

            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(_context, PopUpActivity.class);
                intent.putExtra("feed",feed);
                intent.putExtra("token", _authorization.getToken());

                Log.d(FeedListAdapter.class.getName(),"click long");
                Toast.makeText(_context,"long click",Toast.LENGTH_LONG).show();
                _context.startActivity(intent);
                //Repositorio.getInstance(_context).removeFeed(feed,_authorization);
                return true;
            }
        });

        return view;
    }

    private Authorization _authorization;
    private Context _context;
    private final List<Feed> _feed;
    private LayoutInflater _inflater;

}
