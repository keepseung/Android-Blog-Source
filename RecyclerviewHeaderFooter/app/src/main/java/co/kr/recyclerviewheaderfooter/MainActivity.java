package co.kr.recyclerviewheaderfooter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Person> arrayList = new ArrayList<>();
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        for (int a =0; a < 100; a++){
            arrayList.add(new Person("이름"+a));
        }
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new RecyclerAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);




    }
}