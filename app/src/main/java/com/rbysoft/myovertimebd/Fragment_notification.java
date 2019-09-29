package com.rbysoft.myovertimebd;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rbysoft.myovertimebd.Model.NotificationModel;

import java.util.ArrayList;

public class Fragment_notification extends Fragment {


    View rootview;
    RecyclerView recyclerView;
    NotificationAdapter myadapter;
    ArrayList<NotificationModel>myList=new ArrayList<>();
    ProgressDialog progressDialog;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootview=LayoutInflater.from(container.getContext()).inflate(R.layout.activity_notification,container,false);
        recyclerView=rootview.findViewById(R.id.recyclerview);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Notification Data Loading...");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myadapter=new NotificationAdapter(myList);
        recyclerView.setAdapter(myadapter);
        LoadData();
        return rootview;

    }

    private void LoadData() {
        progressDialog.show();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference reference=db.collection("Notification");

        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                     progressDialog.cancel();
                    for (QueryDocumentSnapshot docref: task.getResult()){


                        NotificationModel temp=docref.toObject(NotificationModel.class);
                        myList.add(temp);

                    }
                    myadapter.notifyDataSetChanged();
                }else{
                    progressDialog.cancel();
                    Toast.makeText(getActivity(), "Data Not Loaded !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
