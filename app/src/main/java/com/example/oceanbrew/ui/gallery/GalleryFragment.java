package com.example.oceanbrew.ui.gallery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.oceanbrew.Admin;
import com.example.oceanbrew.HomePageFragment;
import com.example.oceanbrew.R;
import com.example.oceanbrew.databinding.FragmentGalleryBinding;
import com.example.oceanbrew.model.Drinks;
import com.example.oceanbrew.model.Posts;
import com.example.oceanbrew.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private  Button btnChoose;
    private TextView textView;
    private Uri uri;
    private final int PICK_IMAGE_REQUEST = 71;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        TextView mAddRow, m1D;
        Button mSend;
        Spinner mCategory;
        DatabaseReference mCategoryDbRef;

        mCategoryDbRef = FirebaseDatabase.getInstance().getReference("Category");
        mCategory = root.findViewById(R.id.spn_category_admin);

        ArrayList<String> Category = new ArrayList<>();

        mCategoryDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String item = ds.getKey().toString();
                    Category.add(item);
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,Category);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mCategory.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        m1D = root.findViewById(R.id.first_delete);
        m1D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableLayout table =(TableLayout) root.findViewById(R.id.tl_material);
                int num_rows = table.getChildCount();
                if (num_rows > 3) {
                    table.removeViewAt(1);
                }
            }
        });

        mAddRow = root.findViewById(R.id.tv_addrow_admin);
        mAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableLayout table =(TableLayout) root.findViewById(R.id.tl_material);
                int num_rows = table.getChildCount();

                TableRow row = new TableRow(getActivity().getApplication());
                EditText ed_name = new EditText(getActivity().getApplication());
                ed_name.setTextColor(Color.BLACK);
                EditText ed_num = new EditText(getActivity().getApplication());
                ed_num.setTextColor(Color.BLACK);
                TextView tv_delete = new TextView(getActivity().getApplication());
                tv_delete.setTextColor(Color.BLACK);
                tv_delete.setText("Delete");
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TableLayout table =(TableLayout) root.findViewById(R.id.tl_material);
                        int num_rows = table.getChildCount();
                        if (num_rows > 3) {
                            View row = (View) v.getParent();
                            ViewGroup container = ((ViewGroup)row.getParent());
                            container.removeView(row);
                            container.invalidate();
                        }
                    }
                });

                TableRow r = (TableRow) table.getChildAt(num_rows-2);
                TextView t1 = (TextView) r.getChildAt(0);
                TextView t2 = (TextView) r.getChildAt(1);
                String txt1 = t1.getText().toString();
                String txt2 = t2.getText().toString();

                if (TextUtils.isEmpty(txt1)) {
                    t1.setError("Please fill this field");
                } else if (TextUtils.isEmpty(txt2)) {
                    t2.setError("Please fill this field");
                } else {
                    row.addView(ed_name);
                    row.addView(ed_num);
                    row.addView(tv_delete);
                    table.addView(row, num_rows-1);
                }
            }
        });

        mSend = root.findViewById(R.id.btn_send_admin);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ingredients = "";
                TableLayout table = (TableLayout) root.findViewById(R.id.tl_material);
                int num_rows = table.getChildCount();
                int countError = 0;

                for (int row = 1; row < num_rows-1; row++) {
                    TableRow indexOfRow = (TableRow) table.getChildAt(row);
                    for (int cell = 0; cell <= 1; cell++) {
                        TextView tv = (TextView) indexOfRow.getChildAt(cell);
                        String txt = tv.getText().toString();
                        if (!TextUtils.isEmpty(txt)) {
                            Ingredients += txt+";";
                        } else {
                            countError += 1;
                            tv.setError("ádas");
                        }
                    }
                }
                if (countError > 0) {
                    Toast.makeText(getActivity().getApplication(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    EditText mNameOfDrink, mMethod, mGarnish;
                    DatabaseReference mPostDbRef;
                    Date currentTime;
                    SharedPreferences sharedPreferences;

                    mNameOfDrink = root.findViewById(R.id.ed_nameofdrinks_admin);
                    mMethod = root.findViewById(R.id.ed_method);
                    mGarnish = root.findViewById(R.id.ed_garnish);
                    currentTime = Calendar.getInstance().getTime();
                    mPostDbRef = FirebaseDatabase.getInstance().getReference("Drinks");
                    String NameOfDrink = mNameOfDrink.getText().toString();
                    String Method = mMethod.getText().toString();
                    String Garnish = mGarnish.getText().toString();
                    String CategorySelected = mCategory.getSelectedItem().toString();
                    String WhenPost = currentTime.toString();
                    sharedPreferences = getContext().getSharedPreferences("session_admin", Context.MODE_PRIVATE);
                    String Username = sharedPreferences.getString("session_admin_username", "");

                    String nameImage;
                    nameImage = WhenPost.replaceAll(" ", "");

                    if (uploadImage(nameImage) == true) {
                        Drinks drinks = new Drinks(CategorySelected, Garnish, Ingredients, Method, NameOfDrink, WhenPost, nameImage);
                        mPostDbRef.push().setValue(drinks);
                        Toast.makeText(getContext(), "Add Recipes Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), Admin.class));
                    } else {
                        Toast.makeText(getContext(), "thaibai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textView = root.findViewById(R.id.tv_nameImageAdmin);
        btnChoose = root.findViewById(R.id.btn_chooseImageAdmin);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null )
        {
            uri = data.getData();
            textView.setText("Choosen Image");
        }
    }

    public boolean uploadImage(String name) {
        if(uri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/" + name);
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity().getApplication(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity().getApplication(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
            return true;
        }
        return false;
    }
}