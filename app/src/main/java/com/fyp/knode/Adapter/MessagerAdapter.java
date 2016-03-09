package com.fyp.knode.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Johnny on 01/03/2016.
 */
public class MessagerAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mMessages;

    public MessagerAdapter (Context context, List<ParseObject> messages){
    super(context, R.layout.chat_item);
        mContext = context;
        mMessages= messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        //LayoutInflater from xml
        //convertView also get the Contexts
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iconImageView = (ImageView) convertView.findViewById(R.id.messageIcon);
            viewHolder.nameLabel = (TextView) convertView.findViewById(R.id.sendIdLabel);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        ParseObject message = mMessages.get(position);
        if(message.getString(Constants.KEY_FILE_TYPE).equals(Constants.TYPE_IMAGE)){
            viewHolder.iconImageView.setImageResource(R.drawable.ic_action_camera);
        }else {
            viewHolder.iconImageView.setImageResource(R.drawable.ic_action_play_over_video);
        }
        viewHolder.nameLabel.setText(message.getString(Constants.KEY_SENDER_NAME));
        return convertView;
    }

    private static class  ViewHolder {

        ImageView iconImageView;
        TextView nameLabel;
    }
}
