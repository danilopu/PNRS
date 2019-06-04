package com.example.vremenska;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static Context context;
    Button dugme1;
    EditText imegrada;
    //String grad;
    private ListView lista_gradova;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dugme1 = findViewById(R.id.dugme1);
        dugme1.setOnClickListener(this);

        imegrada = findViewById(R.id.imegrada);
        String grad = String.valueOf(imegrada.getText());

        adapter = new CustomAdapter(this);
        adapter.addGrad(new Grad("Novi Sad"));
        adapter.addGrad(new Grad("Loznica"));
        adapter.addGrad(new Grad("Sabac"));

        lista_gradova = findViewById(R.id.lista_gradova);
        lista_gradova.setAdapter(adapter);

        lista_gradova.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeGrad(position);
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dugme1:
                Grad grad = new Grad(imegrada.getText().toString());
                if(!imegrada.getText().toString().isEmpty()){
                    if(adapter.containsElement(grad)){
                        Toast.makeText(this,getString(R.string.warning1) , Toast.LENGTH_SHORT).show();
                    }else {
                        adapter.addGrad(new Grad(imegrada.getText().toString()));
                    }
                    imegrada.setText("");

                }else{
                    Toast.makeText(this, getString(R.string.warning2), Toast.LENGTH_SHORT).show();
                 }
                break;
            default:
                break;
        }
    }
}
