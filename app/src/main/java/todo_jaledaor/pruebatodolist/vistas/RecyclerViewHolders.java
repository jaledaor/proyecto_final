package todo_jaledaor.pruebatodolist.vistas;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    public TextView task_answer;
    public String uid="";
    String answer_dialog="";
    String question="";
    String category_dialog="";
    TextView txt_question;
    TextView txt_category;
    EditText answer_input;
    private FirebaseDatabase database_control;
    private DatabaseReference reference_control;
    private FirebaseAuth mAuth_control;
    private List<Task> taskObject;

    public RecyclerViewHolders(final View itemView, final List<Task> taskObject) {
        super(itemView);
        this.taskObject = taskObject;
        task_title = itemView.findViewById(R.id.task_title);
        task_answer = itemView.findViewById(R.id.task_answer);
        task_btn = itemView.findViewById(R.id.task_btn);
        task_date = itemView.findViewById(R.id.task_date);
        category = itemView.findViewById(R.id.task_category);
        database_control = FirebaseDatabase.getInstance();
        reference_control = database_control.getReference("Tareas");
        mAuth_control = FirebaseAuth.getInstance();

        uid = "";
        uid = mAuth_control.getCurrentUser().getUid().toString();
        task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), getAdapterPosition() + " Answer icon has been clicked", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view=v.inflate(v.getContext(),R.layout.dialog_answer, null);
                question = taskObject.get(getAdapterPosition()).getPregunta();
                category_dialog = taskObject.get(getAdapterPosition()).getCategoria();


                txt_question = view.findViewById(R.id.txt_question);
                txt_category = view.findViewById(R.id.txt_category);
                answer_input = view.findViewById(R.id.answer_input);

                txt_question.setText("Pregunta: "+question);
                txt_category.setText("Respuesta: "+category_dialog);

                builder.setView(view)
                        .setTitle("Responder Pregunta")
                        .setPositiveButton("OK", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                answer_dialog=answer_input.getText().toString();

                            }
                        })
                        .setNegativeButton("Cancel", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                final AlertDialog dialog = builder.create();
                dialog.show();

                    Toast.makeText(v.getContext(),"root -->"+answer_dialog,Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Task Title " + question);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tareas");

                    Query applesQuery = ref.orderByChild("pregunta").equalTo(question);
                    /*ref.child("respondida").setValue(true);
                    ref.child("uid_resp").setValue(uid);*/
                Toast.makeText(v.getContext(),"root -->"+ ref.getDatabase(),Toast.LENGTH_LONG).show();
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().child("uid_resp").setValue(uid);
                                appleSnapshot.getRef().child("respondida").setValue(true);
                                appleSnapshot.getRef().child("respuesta").setValue(answer_dialog);
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