package github.a3sung.dreammemo;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> comments;
    private List<Comment> saveList;
    private int number;


    public CommentAdapter(Context context, List<Comment> comments, List<Comment> saveList) {
        this.context = context;
        this.comments = comments;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.comment_layout,null);
        TextView ID =(TextView) v.findViewById(R.id.ID);
        TextView BoardID =(TextView) v.findViewById(R.id.BoardID);
        TextView content =(TextView) v.findViewById(R.id.content);
        TextView userID = (TextView)v.findViewById(R.id.userID);
        BoardID.setText(comments.get(position).getBoardID());
        ID.setText(comments.get(position).getID());
        content.setText(comments.get(position).getContent());
        userID.setText(comments.get(position).getUserID());

        v.setTag(comments.get(position).getID());
        return v;


    }
}