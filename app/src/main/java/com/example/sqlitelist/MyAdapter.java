package com.example.sqlitelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<DataBean> mDatas;
    public boolean flage = false;

    public MyAdapter(Context context,List<DataBean> mDatas){
        this.mContext=context;
        this.mDatas=mDatas;
        mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            // 下拉项布局
            convertView = mInflater.inflate(R.layout.relationlist, null);

            holder = new ViewHolder();
            holder.id=convertView.findViewById(R.id._id);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.check);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.tel = (TextView) convertView.findViewById(R.id.tel);
            holder.group = (TextView) convertView.findViewById(R.id.group);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final DataBean dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.id.setText(dataBean.id);
            holder.name.setText(dataBean.name);
            holder.tel.setText(dataBean.tel);
            holder.group.setText(dataBean.group);

            // 根据isSelected来设置checkbox的显示状况
            if (flage) {
                holder.checkBox.setVisibility(View.VISIBLE);
            } else {
                holder.checkBox.setVisibility(View.GONE);
            }

            holder.checkBox.setChecked(dataBean.isCheck);

            //注意这里设置的不是onCheckedChangListener，还是值得思考一下的
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataBean.isCheck) {
                        dataBean.isCheck = false;
                    } else {
                        dataBean.isCheck = true;
                    }
                }
            });
        }
        return convertView;

    }


    class ViewHolder {
        private TextView name;
        private TextView tel;
        private TextView group;
        private CheckBox checkBox;
        private TextView id;
    }

}
