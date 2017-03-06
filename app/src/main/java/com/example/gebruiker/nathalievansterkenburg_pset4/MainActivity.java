package com.example.gebruiker.nathalievansterkenburg_pset4;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import static com.example.gebruiker.nathalievansterkenburg_pset4.DatabaseHelper.SUBJECT;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;
    private EditText newtodo;
    private ListView todolist;
    private Drawable background;;
    ArrayList<Long> checked = new ArrayList<>();
    TodoCursorAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newtodo = (EditText) findViewById(R.id.newtodo);
        Log.i("wat is dit", String.valueOf(newtodo));

        // initialize DB
        dbManager = new DBManager(this);
        dbManager.open();

        // get data from DB
        Cursor cursor = dbManager.fetch(null);
        todoAdapter = new TodoCursorAdapter(this, cursor);

        // set cursor adapter to ListView
        todolist = (ListView) findViewById(R.id.todolist);
        background = todolist.getBackground();
        todolist.setAdapter(todoAdapter);
        Log.i("kom ik", "hier dan nog wel");
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
        }
    }

    public void AddToList(View view) {
        final String entry = newtodo.getText().toString();
        dbManager.insert(entry);
        newtodo.setText("");

        fetchCursor();
    }

    public void fetchCursor() {
        Cursor cursor = dbManager.fetch(null);

        // what is this for
        todoAdapter.changeCursor(cursor);
    }

    public void RemoveFromList(View view) {
    }

    public void ItemDone(View view) {
    }

    public class TodoCursorAdapter extends CursorAdapter {
        public TodoCursorAdapter(Context context, Cursor cursor) { super(context, cursor, 0); }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.simple_layout, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            // find fields to populate in inflated template
            TextView listitem = (TextView) view.findViewById(R.id.listitem);
            ImageView checked = (ImageView) view.findViewById(R.id.checked);

            Log.i("listitem", String.valueOf(listitem));
            String body = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SUBJECT));
            Log.i("body", body);
            String checkImage = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DONE));
            int theImage = getResources().getIdentifier(checkImage, "drawable", getPackageName());
            Drawable drawable = getResources().getDrawable(theImage);

            // as it turns out my listitem is Null at this point, I just don't know why
            // btw, it does work when my database is empty
            listitem.setText(body);
            checked.setBackground(drawable);

            Log.i("hier", "kom ik niet meer");
        }
    }

    private void setListener() {
        todolist.setOnItemLongClickListener((new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String toDelete = (todolist.getItemAtPosition(position)).toString();
                dbManager.delete(id);

                fetchCursor();
                return true;
            }
        }));

        todolist.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                todolist.clearChoices();
//                todolist.setItemChecked(position, true);
                Log.d("text", todolist.getItemAtPosition(position).toString());

                Cursor cursor = dbManager.fetch(DatabaseHelper._ID + " = " + id);
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DONE));
                Log.i("is dit het", image);

//                image = DatabaseHelper.CROSS;
                if (image.equals(DatabaseHelper.CHECK)) {
                    image = DatabaseHelper.CROSS;
                }
                else {
                    image = DatabaseHelper.CHECK;
                }

                dbManager.update(id, image);
                fetchCursor();
            }
        }));
    }

    public void OnItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
