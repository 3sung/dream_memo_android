package github.a3sung.dreammemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LocalDreamAdapter  extends BaseAdapter {

    private List<BoardDream> itemList;

    public LocalDreamAdapter(List<BoardDream> dataSet){
        itemList = dataSet;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        final Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.local_list_item, viewGroup, false);
        }

        TextView titleText = (TextView) view.findViewById(R.id.local_item_title);
        TextView keywordsText = (TextView) view.findViewById(R.id.local_item_keywords);
        TextView dateText = (TextView) view.findViewById(R.id.local_item_date);

        BoardDream selectItem = itemList.get(pos);

        titleText.setText(selectItem.getTitle());
        keywordsText.setText(selectItem.getKeywords());
        dateText.setText(selectItem.getTime());

        return view;
    }
}
