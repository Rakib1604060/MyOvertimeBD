package com.rbysoft.myovertimebd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rbysoft.myovertimebd.Adapter.OvertimeAdapter;
import com.rbysoft.myovertimebd.DbHelper.DbHelper;
import com.rbysoft.myovertimebd.Model.OverTime;

import java.util.ArrayList;

public class Fragment_Detailslist extends Fragment {
    View rootview;
    ListView lv_Overtime;
    ArrayList<OverTime> overTimeList;
    OvertimeAdapter adapter;
    TextView overttext,dattext,regutext,daytext,nigtext,holidtext;
    String lang="";

    DbHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_details_list, container, false);
        InitializeCompo();
        lang=Saving.getAString(getActivity(),"Language");

        if (lang.equals("bn")){
          UpdateResource();
        }


        dbHelper = new DbHelper(getActivity());
        overTimeList = new ArrayList<>();
        overTimeList = dbHelper.getAllInfo();
        adapter = new OvertimeAdapter(getActivity(), overTimeList);
        lv_Overtime = rootview.findViewById(R.id.lvStudents);
        lv_Overtime.setAdapter(adapter);
        registerForContextMenu(lv_Overtime);



        return rootview;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(overTimeList.get(info.position).getDate());
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
    }

    private void UpdateResource() {
        overttext.setText(getResources().getString(R.string.overtimedetailsb));
        dattext.setText(getResources().getString(R.string.dateb));
        regutext.setText(getResources().getString(R.string.regularb));
        daytext.setText(getResources().getString(R.string.dayb));
        nigtext.setText(getResources().getString(R.string.nightb));
        holidtext.setText(getResources().getString(R.string.holidayb));


    }

    private void InitializeCompo() {

        overttext=rootview.findViewById(R.id.textView2);
       dattext=rootview.findViewById(R.id.datee);
       regutext=rootview.findViewById(R.id.regu);
       daytext=rootview.findViewById(R.id.dayy);
        nigtext=rootview.findViewById(R.id.nig);
        holidtext=rootview.findViewById(R.id.holid);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete_id:
                AlertDialog.Builder altdial = new AlertDialog.Builder(getActivity());
                altdial.setTitle("Warning").setIcon(R.drawable.ic_warning);
                altdial.setMessage("Do you want to delete this data?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (dbHelper.deleteData(overTimeList.get(info.position).getId()) > 0) {
                                    overTimeList.remove(info.position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "Successfully deleted", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), "Failed to delete.Try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alter = altdial.create();
                alter.setTitle("Warning"/*overTimeList.get(info.position).getDate()*/);
                alter.show();
                return false;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
