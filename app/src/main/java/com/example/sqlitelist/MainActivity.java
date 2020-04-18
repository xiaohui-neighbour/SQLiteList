package com.example.sqlitelist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private ImageView imageView;
    private Button sure;
    private Button write;
    private List<DataBean> mDatas;
    private MyAdapter mAdapter;
    private DatabaseHelper dbHelper;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatas=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listView);
        imageView=(ImageView)findViewById(R.id.myClick);
        imageView.setOnClickListener(this);
        sure=findViewById(R.id.sure);
        sure.setOnClickListener(this);
        write=findViewById(R.id.write);
        write.setOnClickListener(this);
        title=findViewById(R.id.title);
        getRelationFromDB();
    }

    private void getRelationFromDB()
    {
        dbHelper=new DatabaseHelper(this);
        Cursor cursor=dbHelper.query();
        initData(cursor);
        mAdapter = new MyAdapter(this, mDatas);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("temp", "onItemClick: ");
                AlertDialog.Builder adBuilder=new AlertDialog.Builder(MainActivity.this);
                adBuilder.setMessage("确认要删除记录吗？").setPositiveButton("确认",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.del(Integer.parseInt(mDatas.get(position).id));
                        Cursor cursor=dbHelper.query();
                        initData(cursor);
                        mAdapter = new MyAdapter(MainActivity.this, mDatas);
                        MainActivity.this.listView.setAdapter(mAdapter);
                    }
                }).setNegativeButton("取消",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog aleraDialog=adBuilder.create();
                aleraDialog.show();
            }
        });
        dbHelper.close();
    }

    public void myClick(View view)
    {
        Intent intent=new Intent(MainActivity.this,AddRelationActivity.class);
        startActivityForResult(intent,0x111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x111&&resultCode==0x111)
            getRelationFromDB();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myClick:
                myClick(v);
                break;
            case R.id.sure:
                btnOperateList(v);
                break;
            case R.id.write:
                btnEditList(v);
                break;
        }
    }

    private void initData(Cursor cursor){
        mDatas.clear();
        while(cursor.moveToNext()){
            DataBean dataBean = new DataBean(cursor.getString(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("tel")),cursor.getString(cursor.getColumnIndex("groupName")));
            mDatas.add(dataBean);
        }
    }


    /**
     * 编辑、取消编辑
     * @param view
     */
    public void btnEditList(View view) {

        mAdapter.flage = !mAdapter.flage;

        if (mAdapter.flage) {
            write.setText("取消");
            title.setText("批量删除");
            sure.setVisibility(View.VISIBLE);

        } else {
            write.setText("编辑");
            title.setText("通讯录");
            sure.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
    }



    /**
     * 删除选中数据
     * @param view
     */
    public void btnOperateList(View view) {
        if (mAdapter.flage) {

            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isCheck) {
                    dbHelper.del(Integer.parseInt(mDatas.get(i).id));
                }
            }
            Cursor cursor=dbHelper.query();
            initData(cursor);
            mAdapter = new MyAdapter(MainActivity.this, mDatas);
            MainActivity.this.listView.setAdapter(mAdapter);
            mAdapter.flage=true;
            btnEditList(view);
        }
    }
}
