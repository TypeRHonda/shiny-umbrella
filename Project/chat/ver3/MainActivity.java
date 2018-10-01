import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private Student student;
    private String TAG="MainActivity";
    private DatabaseReference myRef;
    private Button UploadButton;
    private FirebaseListAdapter<Student> mAdapter;
    private RecyclerView recyclerView;
    private ImageView mImageView;
    private int i;
    private ListView listView;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;
    private Button DownloadButtonImage;
    private Uri downloadUrl;

    private FirebaseRecyclerAdapter<Student, myViewHolader> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        mName = (EditText) findViewById(R.id.mUploadName);
//        mEmail = (EditText) findViewById(R.id.mUploadEmailId);
//        mAge = (EditText) findViewById(R.id.mUploadAge);


        UploadButton = (Button) findViewById(R.id.UploadButton);
        mImageView = (ImageView) findViewById(R.id.mImageView);
//        DownloadButtonImage = (Button) findViewById(R.id.buttonDownload);


        recyclerView = (RecyclerView) findViewById(R.id.my_Recylerivew_);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //DataBase raf
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.keepSynced(true);

//        for(int i=0;i<5 ;i++)
//        {
//            Student s = new Student( "ameerhamz@",i,"Hamza");
//            myRef.push().setValue(s);
//
//        }

        // Write a message to the database


        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get the data from an ImageView as bytes
                mImageView.setDrawingCacheEnabled(true);
                mImageView.buildDrawingCache();
                Bitmap bitmap = mImageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                String path = "Firenamee/" + UUID.randomUUID() + ".png";
                storageRef = storage.getReference(path);

                UploadTask uploadTask = storageRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        downloadUrl = taskSnapshot.getDownloadUrl();

                        Student s = new Student("ameerhamza6733@gmail.com", 19, "hamza", downloadUrl.toString());

                        myRef.push().setValue(s);

                        Log.d("onSuccess", "" + downloadUrl);
//

                    }
                });


            }
        });

//        DownloadButtonImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ImageView imageView = (ImageView) findViewById(R.id.mDownloadImageView);
//
//                Picasso.with(MainActivity.this).load(downloadUrl.toString()).into(imageView);
//
//
//            }
//        });

        adapter = new FirebaseRecyclerAdapter<Student, myViewHolader>(
                Student.class, R.layout.item_row, myViewHolader.class, myRef
        ) {
            @Override
            protected void populateViewHolder(myViewHolader viewHolder, Student model, final int position) {


                viewHolder.NameTextView.setText(model.getName());
                viewHolder.EmailTextView.setText(model.getEmailId());
                viewHolder.AgeTextView.setText(String.valueOf(model.getAge()));
                Picasso.with(MainActivity.this).load(model.getImageurl()).into(viewHolder.imageView);

//                Picasso.with(MainActivity.this).load(model.getImageurl()).into(viewHolder.imageView);
                Log.e("myFirebase UID", adapter.getRef(position).getKey());
                //Item click listerer
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Item Cick position ", "" + position);
                        //remove node
                        adapter.getRef(+position).removeValue();
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
//
//        readFromDataBase = (Button) findViewById(R.id.m_read_button);
//
//        // Write to database


//// ...
//
//        readFromDataBase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        Log.e("Count", "" + dataSnapshot.getChildrenCount());
//                        for (DataSnapshot mydata : dataSnapshot.getChildren()) {
//                            Student s = mydata.getValue(Student.class);
//
//                            Log.e("MY Email", "" + s.getEmailId());
//                            Log.e("MY Name ", "" + s.getName());
//                            Log.e("MY age", "" + s.getAge());
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });
//
//
//    }
//
    }
        public static class myViewHolader extends RecyclerView.ViewHolder {

            public TextView NameTextView, AgeTextView, EmailTextView;
          public ImageView imageView;
            public View mView;


            public myViewHolader(View itemView) {
                super(itemView);

                NameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
                AgeTextView = (TextView) itemView.findViewById(R.id.AgetextView);
                EmailTextView = (TextView) itemView.findViewById(R.id.EmailtextView);
                imageView= (ImageView) itemView.findViewById(R.id.imageView);
//                imageView= (ImageView) itemView.findViewById(R.id.imageView);

                mView = itemView;


            }


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up UploadButton, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
