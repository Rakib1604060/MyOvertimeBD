package com.rbysoft.myovertimebd;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rbysoft.myovertimebd.Adapter.OvertimeAdapter;
import com.rbysoft.myovertimebd.DbHelper.DbHelper;
import com.rbysoft.myovertimebd.Model.OverTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Fragment_Detailslist extends Fragment {
    View rootview;
    ListView lv_Overtime;
    ArrayList<OverTime> overTimeList;
    OvertimeAdapter adapter;
    TextView overttext,dattext,regutext,daytext,nigtext,holidtext;
    String lang="";
    FloatingActionButton pdfdownloadbtn;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;



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


        overTimeList = dbHelper.GetTwoMonth(getcurrentMonthYear(1),getcurrentMonthYear(2));

        adapter = new OvertimeAdapter(getActivity(), overTimeList);
        lv_Overtime = rootview.findViewById(R.id.lvStudents);
        lv_Overtime.setAdapter(adapter);
        registerForContextMenu(lv_Overtime);

        pdfdownloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(overTimeList.isEmpty()) Toast.makeText(getActivity(), "Your List is Empty!!", Toast.LENGTH_SHORT).show();
                else{
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        return rootview;

    }
    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

   private  String getcurrentMonthYear(int a){
        Date today=new Date();

        Calendar cal=Calendar.getInstance();
        cal.setTime(today);

       int month = cal.get(Calendar.MONTH);
       month++;

       int year = cal.get(Calendar.YEAR);

       if (a==1){
           String dat=month+"-"+year;
           return  dat;}
       else{
           if (month==1){
               year=year-1;
               month=12;

           }else{
               month=month-1;

           }
           String datt=month+"-"+year;
           return datt;

       }


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
        pdfdownloadbtn=rootview.findViewById(R.id.pdfdownloadbtn);

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


    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        String pdfname = "overtime.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{3, 3, 3, 3, 3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("Date");
        table.addCell("Regular");
        table.addCell("Day");
        table.addCell("Night");
        table.addCell("Holiday");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (int i = 0; i < overTimeList.size(); i++) {
             OverTime overTime=overTimeList.get(i);
              table.addCell(overTime.getDate());
            if (overTime.getLeave()==0){

                table.addCell(String.valueOf(overTime.getRegular()));
                table.addCell(String.valueOf(overTime.getDay()));
                table.addCell(String.valueOf(overTime.getNight()));
                table.addCell(String.valueOf(overTime.getOff()));
            }
            else if (overTime.getLeave()==10){

                table.addCell("---");
                table.addCell("LEAVE");
                table.addCell("----");
                table.addCell("---");

            }
            else if (overTime.getLeave()==20){
                table.addCell("---");
                table.addCell("DAY");
                table.addCell("OFF");
                table.addCell("---");
            }

        }

//        System.out.println("Done");

        PdfWriter.getInstance(document, output);
        document.open();
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.BLUE);
        Font g = new Font(Font.FontFamily.COURIER, 14.0f, Font.NORMAL, BaseColor.BLUE);
        document.add(new Paragraph("MyOvertimeBD \n ", f));

        document.add(new Paragraph("Koster Takar Nirvul Hisab\n\n\n", g));
        document.add(table);

//        for (int i = 0; i < MyList1.size(); i++) {
//            document.add(new Paragraph(String.valueOf(MyList1.get(i))));
//        }
        document.close();

         //previewPdf();
          openFolder();
    }


    private void previewPdf() {

        PackageManager packageManager = getActivity().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }

    public void openFolder()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                 + "/Documents");
        intent.setDataAndType(uri, "pdf");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }
}
