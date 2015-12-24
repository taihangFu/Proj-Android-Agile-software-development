package comp4920.pudgetplanner;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by michellephan on 29/09/2015.
 */
public class AAdapter extends RecyclerView.Adapter<AAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private IconHolder current;
    public int counter;

    //list of the data
    List<IconHolder> data = Collections.emptyList();


    public AAdapter(Context context, List<IconHolder> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    //called when recycler view
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        current = data.get(i);

            viewHolder.title.setText(current.title);
        viewHolder.icon.setImageResource(current.eIcon);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;


        public ViewHolder(View itemView) {
            super(itemView);
            //FOR WHEN THE USER CLICKS ON THE OPTIONS
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.iconImage);
        }

        @Override
        public void onClick(View v) {


                //context.startActivity(new Intent(context, SavingGoals.class));
        }
    }
}
