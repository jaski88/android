package pl.fancycode.tabatatimer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;


class CustomAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<CustomAdapterRow> rows;

    public CustomAdapter(Context context, ArrayList<CustomAdapterRow> rows) {
        super(context, android.R.layout.simple_list_item_2, rows);
        this.context = context;
        this.rows = rows;
    }

//    @Override
//    public int getCount() {
//        return rows.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return rows.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
//        View view = super.getView(position, convertView, parent);
//        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
//
//        text1.setText(persons.get(position).getName());
//        text2.setText(persons.get(position).getAge());
//        return view;
//
//        TwoLineListItem twoLineListItem;
//
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            twoLineListItem = (TwoLineListItem) inflater.inflate(
//                    android.R.layout.simple_list_item_2, null);
//        } else {
//            twoLineListItem = (TwoLineListItem) convertView;
//        }
//
//        TextView text1 = twoLineListItem.getText1();
//        TextView text2 = twoLineListItem.getText2();
//
//        text1.setText(rows.get(position).getTitle());
//        text2.setText(rows.get(position).getContent());
    	
        // Get the data item for this position
    	CustomAdapterRow row = (CustomAdapterRow) getItem(position);    
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        // Lookup view for data population
        TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
        TextView text2 = (TextView) convertView.findViewById(android.R.id.text2);
        // Populate the data into the template view using the data object
        text1.setText( row.getTitle() );
        text2.setText( row.getContent() );
        // Return the completed view to render on screen
        return convertView;

    }
}