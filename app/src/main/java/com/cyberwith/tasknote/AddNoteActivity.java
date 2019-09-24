package com.cyberwith.tasknote;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddNoteActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "Channel";

    private EditText titleEditText, detailsEditText;
    private Button saveButton;
    DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        this.setTitle("Add Note");

        titleEditText = findViewById(R.id.titleEditTextID);
        detailsEditText = findViewById(R.id.detailsEditTextID);
        saveButton = findViewById(R.id.saveButtonID);
        databaseReference = FirebaseDatabase.getInstance().getReference("Notes");
        auth = FirebaseAuth.getInstance();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String details = detailsEditText.getText().toString().trim();
                if(title.isEmpty()){
                    titleEditText.setError("Enter a title");
                    titleEditText.requestFocus();
                    return;
                }
                if(title.length()>50){
                    titleEditText.setError(" Type less then 50 character ");
                    titleEditText.requestFocus();
                    return;
                }

                DataUpload upload = new DataUpload(title,details);
                String key = auth.getUid();
                databaseReference.child(key).push().setValue(upload);
                Intent intent = new Intent(AddNoteActivity.this,HomeActivity.class);
                startActivity(intent);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(AddNoteActivity.this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("Task Note")
                        .setContentText("Your note is successfully save on our server")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AddNoteActivity.this);
                managerCompat.notify(1,builder.build());
            }
        });
    }
}
