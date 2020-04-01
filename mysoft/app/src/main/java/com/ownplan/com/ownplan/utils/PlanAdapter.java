package com.ownplan.com.ownplan.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ownplan.logintest.R;

import java.util.List;

/*
作用：是一dairy中的recycleview的adapter里面有一个ViewHolder的内部类。
list中有存放plan。
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder>
{

    public PlanAdapter(List<Plan> mlist) {
        this.mlist = mlist;
    }


    private Context mcontext;

    private List<Plan> mlist;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载子项的布局
        mcontext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewitem_indiary, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Plan plan = mlist.get(position);
        holder.time.setText(plan.getTime());
        holder.doWhat.setText(plan.getDoWhat());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reMove(position);
                MysqliteHelper helper = DatabaseMananger.getIntance(mcontext);
                SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "delete from " + Sqlconstants.TABLE_PLAN + " where " + Sqlconstants.TIME + "='" + plan.getTime() + "'and " + Sqlconstants.DO_WHAT + "='" + plan.getDoWhat() + "'";
                DatabaseMananger.DBdelete(db, sql);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

   public void addPlan(Plan plan)
   {
       mlist.add(plan);
       notifyDataSetChanged();
   }
   public void reMove(int position)
   {
       mlist.remove(position);
       notifyItemChanged(position);
   }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView doWhat;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            doWhat = itemView.findViewById(R.id.doWhat);
            button = itemView.findViewById(R.id.button);


        }
    }

}
