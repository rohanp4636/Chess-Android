package com.example.daivikrohan.chessapp01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Home on 5/2/2016.
 */
public class ReplayList extends AppCompatActivity {

    ListView gameList;
    ArrayList<File> files;
    ArrayList<String> listItems;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chessgame);

        gameList = (ListView) findViewById(R.id.listOfGames);
        files = new ArrayList<File>();
        try {
            File file = getApplicationContext().getFilesDir();
            file = new File(file,"SavedGames");
            if(file.exists()){
                for(File game : file.listFiles()){
                    files.add(game);

                }
            }

        } catch (Exception e) {

        }

        listItems = new ArrayList<String>();
        sortName();

        gameList.setAdapter(new ArrayAdapter<String>(this,R.layout.filelist,listItems));

        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                int index = listItems.indexOf(item);
                File gameFile = files.get(index);
                try{
                    FileInputStream fileInputStream = new FileInputStream(gameFile);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
                    String moves = "";
                    String tmp = "";
                    while ((tmp = br.readLine()) != null) {
                        if(moves.isEmpty()){
                            moves = tmp;
                        }
                        else{
                            moves = moves +"_"+ tmp;
                        }
                    }
                    br.close();
                    fileInputStream.close();

                    Intent i = new Intent(ReplayList.this,ReplayGame.class);
                    i.putExtra("GameMoveList", moves);
                    startActivityForResult(i, 0);
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"File Is Corrupted.\nIt Will Be Deleted.",Toast.LENGTH_SHORT);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sortmenu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(listItems.isEmpty()){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.menu_name: sortName();
                                return true;
            case R.id.menu_date: sortDate();
                                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void sortName(){
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                String f1 = lhs.getName();
                String f2 = rhs.getName();
                return f1.compareTo(f2);
            }
        });
        listItems.clear();
        for(File file : files){
            listItems.add(file.getName());
        }
        gameList.setAdapter(new ArrayAdapter<String>(this,R.layout.filelist,listItems));

    }
    public void sortDate(){
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                Date f1 = new Date(lhs.lastModified());
                Date f2 = new Date(rhs.lastModified());
                cal1.setTime(f1);
                cal2.setTime(f2);
                Boolean test = cal1.after(cal2);
                Boolean test2 = cal1.before(cal2);
                int x = 0;
                if(cal1.after(cal2)){
                    x = -1;
                }
                else if( cal1.before(cal2)) {
                    x = 1;
                }
                else if(cal1.getTimeInMillis() == cal2.getTimeInMillis()){
                    x = 0;
                }
                return x;
            }
        });
        listItems.clear();
        for(File file : files){
            listItems.add(file.getName());
        }
        gameList.setAdapter(new ArrayAdapter<String>(this,R.layout.filelist,listItems));
    }
}
