package github.a3sung.dreammemo;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BoardDreamAdapter extends BaseAdapter {
    private Context context;
    private List<BoardDream> boardDreams;
    private List<BoardDream> saveList;
    private int number;


    public BoardDreamAdapter(Context context, List<BoardDream> boardDreams,List<BoardDream> saveList) {
        this.context = context;
        this.boardDreams = boardDreams;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return boardDreams.size();
    }

    @Override
    public Object getItem(int position) {
        return boardDreams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.board_dream,null);
        TextView keyword =(TextView) v.findViewById(R.id.keyword);
        TextView title =(TextView) v.findViewById(R.id.title);
        TextView date =(TextView) v.findViewById(R.id.date);
        TextView BoardID = (TextView)v.findViewById(R.id.BoardID);
        BoardID.setText(boardDreams.get(position).getBoardID());
        keyword.setText(boardDreams.get(position).getDreamContent());
        title.setText(boardDreams.get(position).getTitle());
        date.setText(boardDreams.get(position).getTime());

        v.setTag(boardDreams.get(position).getBoardID());
        return v;


    }
}