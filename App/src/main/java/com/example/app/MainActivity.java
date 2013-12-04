package com.example.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity { //ActionBarActivity {

    // database access
    private NotesDataSource notesDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        // create helpers
        notesDataSource = new NotesDataSource(this);
        // open the database
        try {
            notesDataSource.open();
        } catch (SQLException e) {
            // log
        }

        // read all records into a list object
        List<Note> values = notesDataSource.getAllNotes();

        // use SimpleCursorAdapter to show the elements in the list
        ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                values);

        setListAdapter(adapter);
    }

    // onClick handler for the buttons
    public void onClick(View view) {
        // what is this for?
        //@SuppressWarnings("unchecked")

        // get adapter
        ArrayAdapter<Note> adapter = (ArrayAdapter<Note>) getListAdapter();

        // init
        Note note = null;

        // check the buttons pressed
        switch (view.getId()) {
            case R.id.btn_add:
                // add a random number to the string
                int nextId = new Random().nextInt(1000);

                // create a note object
                note = notesDataSource.createNote("Title " + nextId, "This is a text " + nextId);

                // add to the list
                adapter.add(note);

                break;

            case R.id.btn_del:
                // check if elements exist
                if (getListAdapter().getCount() > 0) {
                    // get the first element from the list
                    note = (Note) getListAdapter().getItem(0);

                    // delete from the database
                    notesDataSource.deleteNote(note);

                    // delete from the list
                    adapter.remove(note);
                }
                break;
        }
        // notify the database about the changes
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        notesDataSource.close();
        super.onPause();
    }

    @Override
    public void onResume() {
        try {
            notesDataSource.open();
        } catch (SQLException e) {
            // log
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // refresh
        switch (id) {

            // new note
            case R.id.action_new_note:
                //getSupportFragmentManager().beginTransaction()
                //        .add(R.id.container, new sNoteFragment())
                //        .commit();

                // create an intent
                Intent intentNote = new Intent(this, NoteActivity.class);
                // start the Activity
                startActivity(intentNote);

                return true;

            // refresh
            case R.id.action_refresh:
                Toast.makeText(getApplicationContext(), "Refresh clicked :)", Toast.LENGTH_SHORT).show();
                return true;

            // settings
            case R.id.action_settings:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}

