package com.example.roomdbdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdbdemo.R;
import com.example.roomdbdemo.activity.EditDataActivity;
import com.example.roomdbdemo.activity.ShowDataActivity;
import com.example.roomdbdemo.db.DataEntity;

import java.util.List;

import javax.security.auth.callback.Callback;

public class ShowDataListAdapter extends RecyclerView.Adapter<ShowDataListAdapter.ViewHolder> {

    private Context context;
    private View view;
    private ViewHolder viewHolder;
    private List<DataEntity> datas;
    private Callback mCallback;


    public ShowDataListAdapter(Context context, List<DataEntity> datas) {
        this.context = context;
        this.datas = datas;

    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.style_item_data, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final DataEntity mdata = datas.get(position);

        holder.tvName.setText("Name: " + mdata.getFirstName() + " " + mdata.getLastName());
        holder.tvMobile.setText("Mobile No: " + mdata.getMobileNo());
        holder.tvBirthDate.setText("BirthDate: " + mdata.getBirthDate());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onDeleteClick(datas.get(position));
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditDataActivity.class);
                intent.putExtra("fName",""+mdata.getFirstName());
                intent.putExtra("lName",""+mdata.getLastName());
                intent.putExtra("Mobile",""+mdata.getMobileNo());
                intent.putExtra("BirthDate",""+mdata.getBirthDate());
                intent.putExtra("id",""+mdata.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0)
            return datas.size();
        else
            return 0;
    }

    public interface Callback {
        void onDeleteClick(DataEntity data);
    }


    public void addItems(List<DataEntity> userList) {
        datas = userList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvMobile, tvBirthDate;
        private Button btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvBirthDate = itemView.findViewById(R.id.tvBirthDate);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
