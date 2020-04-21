package com.example.sqlliteandrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button createDbbtn,addvluesbtn,getvaluesbtn;
    private SQLiteDbClass sqLiteDbClass;
    private TextView getvaluesTV;
    private EditText nameET,AddressET;
    private RecyclerView recyclerView;

    private ArrayList<DbModelClass> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        convertxmltojava();
    }

    private void convertxmltojava()
    {
        try
        {
            createDbbtn=findViewById(R.id.createdatabase);
            recyclerView=findViewById(R.id.RV);

            getvaluesTV=findViewById(R.id.getvaluesTV);
            getvaluesbtn=findViewById(R.id.getvaluesbtn);


            sqLiteDbClass=new SQLiteDbClass(this);

            createDbbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDB();
                }
            });

            nameET=findViewById(R.id.nameET);
            AddressET=findViewById(R.id.AddressET);
            addvluesbtn=findViewById(R.id.addbtn);

            addvluesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addvlauestodatabase();
                }
            });

            getvaluesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getvaluesfromDB();
                }
            });


        }
        catch (Exception e)
        {
            Toast.makeText(this, "Convertxmltojava"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void createDB()
    {
        try {

            sqLiteDbClass.getReadableDatabase();

        }
        catch (Exception e)
        {
            Toast.makeText(this, "createDb"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



    private void addvlauestodatabase()
    {
        try
        {
            if (!nameET.getText().toString().isEmpty()&&!AddressET.getText().toString().isEmpty()
            )
            {
                SQLiteDatabase sqLiteDatabase=sqLiteDbClass.getWritableDatabase();

                if (sqLiteDatabase!=null)
                {
                    ContentValues contentValues=new ContentValues();
                    nameET.requestFocus();

                    contentValues.put("Name",nameET.getText().toString());
                    contentValues.put("Address",AddressET.getText().toString());


                    long check=sqLiteDatabase.insert("classtable",null,contentValues);

                    if (check!=-1)
                    {
                        Toast.makeText(this, "Values Inserted", Toast.LENGTH_SHORT).show();

                        nameET.setText(null);
                        AddressET.setText(null);


                    }

                    else
                    {
                        Toast.makeText(this, "Fails to add vlaues", Toast.LENGTH_SHORT).show();
                    }

                }

                else
                {
                    Toast.makeText(this, "DB object is null", Toast.LENGTH_SHORT).show();
                }
            }

            else if (nameET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Enter the name please", Toast.LENGTH_SHORT).show();
                nameET.requestFocus();
            }



            else if (AddressET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Enter the address please", Toast.LENGTH_SHORT).show();
                AddressET.requestFocus();

            }
            else
            {

            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, "addvalues"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getvaluesfromDB()
    {
        try
        {

            SQLiteDatabase sqLiteDatabase=sqLiteDbClass.getReadableDatabase();
            if (sqLiteDatabase!=null)
            {
                Cursor cursor=sqLiteDatabase.rawQuery("Select * from classtable",null);

                StringBuffer stringBuffer=new StringBuffer();

                if (cursor.getCount()!=0)
                {
                    while (cursor.moveToNext())
                    {
                        DbModelClass Dbmodelclass=new DbModelClass();
                        Dbmodelclass.setName(cursor.getString(0));
                        Dbmodelclass.setAddress(cursor.getString(1));

                        arrayList.add(Dbmodelclass);
//                        stringBuffer.append("Name:"+cursor.getString(0)+"\n");
//                        stringBuffer.append("Address:"+cursor.getString(1)+"\n");
                    }

//                    getvaluesTV.setText(stringBuffer);

                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    DbAdaptorClass dbAdaptorClass=new DbAdaptorClass(arrayList);
                    recyclerView.setAdapter(dbAdaptorClass);
                }
                else
                {
                    Toast.makeText(this, "No values retrive", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "DB object is null", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, "getvaluesbtn"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
