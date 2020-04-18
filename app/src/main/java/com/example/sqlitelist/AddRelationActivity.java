package com.example.sqlitelist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddRelationActivity extends AppCompatActivity {
    private EditText addName,addTel;
    private Spinner addGroup;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrelation);
        addName=(EditText) findViewById(R.id.addName);
        addTel=(EditText) findViewById(R.id.addTel);
        addGroup=(Spinner) findViewById(R.id.addGroup);
        button=(Button)findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });
    }
    public void save(View view)
    {
        final ContentValues values=new ContentValues();
        values.put("name",addName.getText().toString());
        values.put("tel",addTel.getText().toString());
        values.put("groupName",addGroup.getSelectedItem().toString());
        final DatabaseHelper dbHelper=new DatabaseHelper(getApplicationContext());
        final AlertDialog.Builder adBuilder=new AlertDialog.Builder(this);
        adBuilder.setMessage("确认保存记录吗？").setPositiveButton("确认",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.insert(values);
                Intent intent=getIntent();
                setResult(0x111,intent);
                AddRelationActivity.this.finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog aleraDialog=adBuilder.create();
        aleraDialog.show();
    }
}