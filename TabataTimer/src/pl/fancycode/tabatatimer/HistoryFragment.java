package pl.fancycode.tabatatimer;

import java.util.ArrayList;

import pl.fancycode.tabatatimer.HistoryDB.DBRow;
import pl.fancycode.tabatatimer.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * A placeholder fragment containing a simple view.
 */
public class HistoryFragment extends Fragment {
	
	private static final String TAG = "TabataTracker";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HistoryFragment newInstance(int sectionNumber) {	  	
    	HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate( R.layout.fragment_history, container, false);
        
        ListView lv = (ListView) rootView.findViewById(R.id.list_view);
        
    	HistoryDB db = new HistoryDB(getActivity());
    	ArrayList<CustomAdapterRow> rows = db.getAllRows( );
    	
    	int count = db.countRows();
    	Log.d(TAG, "Count rows: " + count);

    	CustomAdapter adapter = new CustomAdapter(getActivity(), rows);
    	
        
//        ArrayAdapter<HistoryDB.DBRow> adapter = new ArrayAdapter<HistoryDB.DBRow>(getActivity(), android.R.layout.simple_list_item_1, rows );
        lv.setAdapter(adapter);
        
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
//    	HistoryDB db = new HistoryDB(activity.getBaseContext());
//    	int count = db.countRows();
//    	Log.d(TAG, "Count rows: " + count);
        
        
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }
    
}