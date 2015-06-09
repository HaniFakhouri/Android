package com.keer.inbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

import java.util.List;

public class AllObjectsListAdapter extends ArrayAdapter {

    public interface OnTipDeletedListener {
        public void onTipDeletedListener(int index);
    }

    private Context context;

    private OnTipDeletedListener mTipDeletedListener;

    public AllObjectsListAdapter(Context context, List items, OnTipDeletedListener tipDeletedListener) {
        super(context, R.layout.xobject_item, items);
        this.context = context;
        mTipDeletedListener = tipDeletedListener;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        xObject item = (xObject)getItem(position);
        View viewToUse = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewToUse = mInflater.inflate(R.layout.xobject_item, null);

            holder = new ViewHolder();
            holder.tipTitle = (TextView)viewToUse.findViewById(R.id.txtXObjectTitle);
            holder.tipId = (TextView)viewToUse.findViewById(R.id.txtXObjectId);
            holder.btnDeleteTip = (Button)viewToUse.findViewById(R.id.btnDeleteXObject);

            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.tipTitle.setText(item.title);
        holder.tipId.setText(String.valueOf(item.id));
        holder.btnDeleteTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(getItem(position));
                mTipDeletedListener.onTipDeletedListener(position);
            }
        });

        return viewToUse;
    }

    private class ViewHolder{
        TextView tipTitle;
        TextView tipId;
        Button btnDeleteTip;
    }

}
