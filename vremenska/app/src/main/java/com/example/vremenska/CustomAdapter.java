package com.example.vremenska;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Grad> mGrad;
    private RadioButton selected = null;
    private String str;
    private EditText novi_grad;


    public CustomAdapter(Context context) {
        mContext = context;
        mGrad = new ArrayList<Grad>();

    }

    public void addGrad(Grad grad) {
        mGrad.add(grad);
        notifyDataSetChanged();
    }

    public void removeGrad(int position) {
        mGrad.remove(position);
        notifyDataSetChanged();
    }

    public boolean containsElement(Grad element){
        boolean ind = false;
        for (Grad el : mGrad) {
            if (el.mImeGrada.equals(element.mImeGrada))
                ind = true;
        }
        return ind;
    }

    @Override
    public int getCount() {
        return mGrad.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = mGrad.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.element_row, null);
            ViewHolder holder = new ViewHolder();
            holder.dugmic = view.findViewById(R.id.nepotrebnodugme);
            holder.radiobutton = view.findViewById(R.id.radiobutton_grad);
            holder.ime_grada =  view.findViewById(R.id.text_grad_u_listi);
            view.setTag(holder);

        }

        Grad grad = (Grad) getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.ime_grada.setText(grad.mImeGrada);

        holder.dugmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( mContext, DetailsActivity.class);
                intent.putExtra("grad", holder.ime_grada.getText());
                mContext.startActivity(intent);

            }
        });
        holder.radiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( mContext, DetailsActivity.class);
                intent.putExtra("grad", holder.ime_grada.getText());
                mContext.startActivity(intent);
                selected = (RadioButton)v;
                selected.setChecked(false);

            }

        });

        return view;
    }







    private class ViewHolder {
        public RadioButton radiobutton;
        public TextView ime_grada = null;
        public Button dugmic;
    }


}

