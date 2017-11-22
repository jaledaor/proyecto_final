package todo_jaledaor.pruebatodolist.vistas;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

import todo_jaledaor.pruebatodolist.R;


public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    private static final String TAG = RecyclerViewHolders.class.getSimpleName();
    public Button task_btn;
    public TextView task_title;
    public TextView task_date;
    public TextView category;
    private List<Task> taskObject;
    public RecyclerViewHolders(final View itemView, final List<Task> taskObject) {
        super(itemView);
        this.taskObject = taskObject;
        task_title = (TextView)itemView.findViewById(R.id.task_title);
        task_btn = (Button)itemView.findViewById(R.id.task_btn);
        task_date = (TextView)itemView.findViewById(R.id.task_date);
        category = (TextView)itemView.findViewById(R.id.task_category);
        task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Answer icon has been clicked", Toast.LENGTH_LONG).show();
                String taskTitle = taskObject.get(getAdapterPosition()).getPregunta();
                Log.d(TAG, "Task Title " + taskTitle);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.orderByChild("task").equalTo(taskTitle);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });
    }
}