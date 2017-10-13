package com.example.luisafarias.myapplication.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.Feed;


public class NewFeedFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            _userId = getArguments().getString("userId");
            _feed = getArguments().getParcelable("feed");
            _newOredit = getArguments().getBoolean("newOredit");
        }
//        if(getArguments().getParcelable("feed")!=null) {
//            _edit = true;
//            _feed = getArguments().getParcelable("feed");
//        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(NewFeedFragment.class.getName(),"ele esta aqui");
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_new_feed, container, false);
        if (_newOredit){
            EditText nome = _view.findViewById(R.id.newNameFeed);
            EditText url = _view.findViewById(R.id.newUrlFeed);
            nome.setText(_feed.get_nome());
            url.setText(_feed.get_url());
        }

        return _view;
    }

//    public Feed ReceiveFeed(){
//
//        EditText newUrlName = _view.findViewById(R.id.newNameFeed);
//        EditText newUrl = _view.findViewById(R.id.newUrlFeed);
//
//        String name = newUrlName.getText().toString();
//        String url = newUrl.getText().toString();
//
//        Feed feed = new Feed(name,url,_userId);
//
//        return feed;

//        FeedListFragment feedListFragment = new FeedListFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("feed",feed);
//        feedListFragment.setArguments(bundle);

//        FragmentManager fm = getFragmentManager();
//        Fragment fragmentTest = fm.findFragmentByTag("test");
//
//        if (fragmentTest != null) {
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.show(fragmentTest);
//            ft.replace(R.id.frame_layout_fragment,fragmentTest);
//            ft.commit();
//            getActivity().getFragmentManager().popBackStack();

//            if (getActivity() instanceof MainActivity) { //tentando pegar fragmentnewfeed?
//                MainActivity activity = ((MainActivity) getActivity());
//                activity.setFragmentResult();
//            }
//        }

//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof EditFragment.OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
    private Feed _feed;
    private boolean _newOredit = false;
    private String _userId;
    private View _view;
    //private OnFragmentInteractionListener mListener;

//    public interface OnFragmentInteractionListener {
//        public void onButtonPressed(Feed feed);
//
//    }

}
