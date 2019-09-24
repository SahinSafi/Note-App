package com.cyberwith.tasknote;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private Context context;
    private List<DataUpload> uploadList;
    private OnClickListener listener;

    public RecycleAdapter(Context context, List<DataUpload> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        DataUpload upload = uploadList.get(position);
        holder.titleText.setText(upload.getTitle());
        holder.detailsText.setText(upload.getDetails());

    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        TextView titleText, detailsText;

        public MyViewHolder( View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleTextID);
            detailsText = itemView.findViewById(R.id.detailsTextID);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Do you want to delete?");
            MenuItem delete =  menu.add(Menu.NONE,1 ,1 , "Delete");

            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(listener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            listener.onDelete(position);
                            break;
                    }
                }
            }
            return false;
        }
    }

    public interface OnClickListener{
        void onDelete(int position);
    }

    public void setOnItemClickListener(OnClickListener listener){
        this.listener = listener;
    }
}
