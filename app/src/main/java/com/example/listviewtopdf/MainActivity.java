package com.example.listviewtopdf;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static com.itextpdf.text.Annotation.MIMETYPE;
import static com.itextpdf.text.Element.ALIGN_CENTER;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ListView lstMylist;
    String[] idArray = {"1","2","3","4","5","6","7","8","9","10"};

    String[] nameArray = {"Ganesh Aghav","Ankita Chugule","Nitin Kamble","Yogesh Power",
            "Ram More","Pramod Shinde","Raju Raut","Sudhir Ugale","Nliesh Gosavi","Ajit Ghule"};

    String[] mobileArray = {"+91 9021418001","+91 9021418002","+91 9021418003","+91 9021418004",
            "+91 9021418005","+91 9021418006","+91 9021418007","+91 9021418008","+91 9021418009","+91 9021418010"};

    String[] devCatArray = {"Laptop","Printer","Desktop","Scanner",
            "All In One Desktop","All In One Printer","Tab","Mobile","Other","Desktop"};

    String[] proByUserArray = {"Testing","Testing","Other","Other",
            "Testing","Other","Testing","Testing","Other","Testing"};

    String[] addressArray = {"Kandivalli West","Kandivalli West","Kandivalli West","Kandivalli West",
            "Kandivalli West","Kandivalli West","Kandivalli West","Kandivalli West","Kandivalli West","Kandivalli West"};

    String[] createddateArray = {"15-10-2019","15-10-2019","15-10-2019","15-10-2019",
            "15-10-2019","15-10-2019","15-10-2019","15-10-2019","15-10-2019","15-10-2019"};

    String[] createdtimeArray = {"06:17 PM","06:17 PM","06:17 PM","06:17 PM",
            "06:17 PM","06:17 PM","06:17 PM","06:17 PM","06:17 PM","06:17 PM"};

    String[] callstatusArray = {"Open","Pending","Close","Open",
            "Close","Pending","Open","Close","Pending","Open"};

    String[] callassgndateArray = {"15-10-2019","15-10-2019","15-10-2019","15-10-2019",
            "15-10-2019","15-10-2019","15-10-2019","15-10-2019","15-10-2019","15-10-2019"};

    String[] callassgntimeArray = {"06:17 PM","06:17 PM","06:17 PM","06:17 PM",
            "06:17 PM","06:17 PM","06:17 PM","06:17 PM","06:17 PM","06:17 PM"};

    private ArrayList<HashMap<String, String>>  CallLogList;
    private ListAdapter adapter;
    private String FolderName="Download";
    private String FileName="PdfDemo.pdf";
    private String FilePath= Environment.getExternalStorageDirectory()+"/" +FolderName+"/" + FileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstMylist=(ListView)findViewById(R.id.mylist);
        GetDataForListView();

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Delete file if already exits
                File myFile = new File(FilePath);
                if(myFile.exists()){
                    myFile.delete();
                }

                CreateListViewPDF createListViewPDF=new CreateListViewPDF(CallLogList);
                createListViewPDF.execute();
            }
        });
    }

    public void GetDataForListView(){

        CallLogList = new ArrayList<HashMap<String, String>>();

        for(int i=0;i<idArray.length;i++){

            HashMap<String, String> hashMap = new HashMap<String, String>();

            hashMap.put("CALLLOG_CAL_LOG_ID", idArray[i]);
            hashMap.put("CALLLOG_CAL_LOG_CUST_NAME", nameArray[i]);
            hashMap.put("CALLLOG_CAL_LOG_MOBILE", mobileArray[i]);
            hashMap.put("CALLLOG_CAL_LOG_DEVICE_CAT", devCatArray[i]);
            hashMap.put("CALLLOG_CAL_LOG_PROB_BYUSER", proByUserArray[i]);
            hashMap.put("CALLLOG_CAL_LOG_ADDRESS", addressArray[i]);
            hashMap.put("CALLLOG_CAL_LOG_MODIFY_DATE", createddateArray[i]);
            hashMap.put("CALLLOG_CAL_LOG_CREATED_BY", nameArray[i]);
            hashMap.put("CALLLOG_CAL_LOG_CREATED_DATE", createddateArray[i]);
            hashMap.put("CALLLOG_CAL_LOG_CREATED_TIME", createdtimeArray[i]);
            hashMap.put("CALLLOG_CAL_CALL_ASSGIN_STATUS", callstatusArray[i]);
            hashMap.put("CALLLOG_CAL_CALL_ASSGIN_DATE", callassgndateArray[i]);
            hashMap.put("CALLLOG_CAL_CALL_ASSGIN_TIME", callassgntimeArray[i]);

            CallLogList.add(hashMap);

        }
        adapter = new SimpleAdapter(MainActivity.this, CallLogList,
                R.layout.listview_items,
                new String[]{"CALLLOG_CAL_LOG_ID",
                        "CALLLOG_CAL_LOG_CUST_NAME",
                        "CALLLOG_CAL_LOG_MOBILE",
                       "CALLLOG_CAL_LOG_DEVICE_CAT",
                        "CALLLOG_CAL_LOG_PROB_BYUSER",
                        "CALLLOG_CAL_LOG_ADDRESS",
                       "CALLLOG_CAL_LOG_CREATED_DATE",
                        "CALLLOG_CAL_LOG_CREATED_TIME",
                       "CALLLOG_CAL_CALL_ASSGIN_STATUS",
                        "CALLLOG_CAL_CALL_ASSGIN_DATE",
                       "CALLLOG_CAL_CALL_ASSGIN_TIME"},
                new int[]{
                        R.id.txtID,
                        R.id.txtCUSTNAME,
                        R.id.txtMOBILE,
                        R.id.txtDEVCAT,
                        R.id.txtPROBBYUSER,
                        R.id.txtADDRESS,
                        R.id.txtCREATEDDATE,
                        R.id.txtCREATEDTIME,
                        R.id.txtCALLSTAATUS,
                        R.id.txtCALLASSINDATE,
                        R.id.txtCALLASSINTIME});

        lstMylist.setAdapter(adapter);
    }

    public class CreateListViewPDF extends AsyncTask<String,String,String> {

        ArrayList<HashMap<String, String>> listdata;
        ProgressDialog progressDialog;

        public CreateListViewPDF(ArrayList<HashMap<String, String>> datalist){
            listdata=datalist;
            progressDialog=new ProgressDialog(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please wait pdf file creating...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String result="false";

            try {

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(FilePath));
                document.open();

                // Document Settings
                document.setPageSize(PageSize.A4);
                document.addCreationDate();
                document.addAuthor("MSDComputer");
                document.addCreator("Ganesh Aghav");


                // LINE SEPARATOR
                LineSeparator headerline = new LineSeparator();
                headerline.setLineColor(new BaseColor(0, 0, 0, 68));


               // Start dHeader Paragraph
                Paragraph ReportName = new Paragraph(CallLogDetails.ReportName);
                ReportName.setAlignment(Paragraph.ALIGN_CENTER);
                document.add(ReportName);

                Paragraph ReportTitle = new Paragraph(CallLogDetails.ReportTitle);
                ReportTitle.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(ReportTitle);

                Paragraph ReportDate = new Paragraph( "Created Date: "+ CallLogDetails.ReportDate);
                ReportDate.setAlignment(Paragraph.ALIGN_RIGHT);
                document.add(ReportDate);

                Paragraph ReportTime = new Paragraph("Created Time: "+ CallLogDetails.ReportTime);
                ReportTime.setAlignment(Paragraph.ALIGN_RIGHT);
                document.add(ReportTime);

                document.add(new Chunk(headerline));
                //End Header Paragraph


                //Start List View
                Font plainFont= new Font(Font.FontFamily.TIMES_ROMAN, 14,Font.BOLD);
                for(HashMap<String, String> map :listdata){

                    PdfPTable table = new PdfPTable(2);

                    Phrase phrase_CALLLOG_CAL_LOG_ID = new Phrase(CallLogDetails.CALLLOG_CAL_LOG_ID, plainFont);
                    PdfPCell CALLLOG_CAL_LOG_ID = new PdfPCell(phrase_CALLLOG_CAL_LOG_ID);
                    CALLLOG_CAL_LOG_ID.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_ID);
                    PdfPCell CALLLOG_CAL_LOG_ID1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_LOG_ID")));
                    CALLLOG_CAL_LOG_ID1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_ID1);


                    Phrase phrase_CALLLOG_CAL_LOG_CUST_NAME = new Phrase(CallLogDetails.CALLLOG_CAL_LOG_CUST_NAME, plainFont);
                    PdfPCell CALLLOG_CAL_LOG_CUST_NAME = new PdfPCell(phrase_CALLLOG_CAL_LOG_CUST_NAME);
                    CALLLOG_CAL_LOG_CUST_NAME.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_CUST_NAME);
                    PdfPCell CALLLOG_CAL_LOG_CUST_NAME1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_LOG_CUST_NAME")));
                    CALLLOG_CAL_LOG_CUST_NAME1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_CUST_NAME1);


                    Phrase phrase_CALLLOG_CAL_LOG_MOBILE = new Phrase(CallLogDetails.CALLLOG_CAL_LOG_MOBILE, plainFont);
                    PdfPCell CALLLOG_CAL_LOG_MOBILE = new PdfPCell(phrase_CALLLOG_CAL_LOG_MOBILE);
                    CALLLOG_CAL_LOG_MOBILE.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_MOBILE);
                    PdfPCell CALLLOG_CAL_LOG_MOBILE1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_LOG_MOBILE")));
                    CALLLOG_CAL_LOG_MOBILE1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_MOBILE1);


                    Phrase phrase_CALLLOG_CAL_LOG_DEVICE_CAT = new Phrase(CallLogDetails.CALLLOG_CAL_LOG_DEVICE_CAT, plainFont);
                    PdfPCell CALLLOG_CAL_LOG_DEVICE_CAT = new PdfPCell(phrase_CALLLOG_CAL_LOG_DEVICE_CAT);
                    CALLLOG_CAL_LOG_DEVICE_CAT.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_DEVICE_CAT);
                    PdfPCell CALLLOG_CAL_LOG_DEVICE_CAT1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_LOG_DEVICE_CAT")));
                    CALLLOG_CAL_LOG_DEVICE_CAT1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_DEVICE_CAT1);


                    Phrase phrase_CALLLOG_CAL_LOG_PROB_BYUSER = new Phrase(CallLogDetails.CALLLOG_CAL_LOG_PROB_BYUSER, plainFont);
                    PdfPCell CALLLOG_CAL_LOG_PROB_BYUSER = new PdfPCell(phrase_CALLLOG_CAL_LOG_PROB_BYUSER);
                    CALLLOG_CAL_LOG_PROB_BYUSER.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_PROB_BYUSER);
                    PdfPCell CALLLOG_CAL_LOG_PROB_BYUSER1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_LOG_PROB_BYUSER")));
                    CALLLOG_CAL_LOG_PROB_BYUSER1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_PROB_BYUSER1);


                    Phrase phrase_CALLLOG_CAL_LOG_ADDRESS = new Phrase(CallLogDetails.CALLLOG_CAL_LOG_ADDRESS, plainFont);
                    PdfPCell CALLLOG_CAL_LOG_ADDRESS = new PdfPCell(phrase_CALLLOG_CAL_LOG_ADDRESS);
                    CALLLOG_CAL_LOG_ADDRESS.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_ADDRESS);
                    PdfPCell CALLLOG_CAL_LOG_ADDRESS1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_LOG_ADDRESS")));
                    CALLLOG_CAL_LOG_ADDRESS1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_ADDRESS1);


                    Phrase phrase_CALLLOG_CAL_LOG_CREATED_DATE = new Phrase(CallLogDetails.CALLLOG_CAL_LOG_CREATED_DATE, plainFont);
                    PdfPCell CALLLOG_CAL_LOG_CREATED_DATE = new PdfPCell(phrase_CALLLOG_CAL_LOG_CREATED_DATE);
                    CALLLOG_CAL_LOG_CREATED_DATE.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_CREATED_DATE);
                    PdfPCell CALLLOG_CAL_LOG_CREATED_DATE1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_LOG_CREATED_DATE")));
                    CALLLOG_CAL_LOG_CREATED_DATE1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_CREATED_DATE1);


                    Phrase phrase_CALLLOG_CAL_LOG_CREATED_TIME = new Phrase(CallLogDetails.CALLLOG_CAL_LOG_CREATED_TIME, plainFont);
                    PdfPCell CALLLOG_CAL_LOG_CREATED_TIME = new PdfPCell(phrase_CALLLOG_CAL_LOG_CREATED_TIME);
                    CALLLOG_CAL_LOG_CREATED_TIME.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_CREATED_TIME);
                    PdfPCell CALLLOG_CAL_LOG_CREATED_TIME1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_LOG_CREATED_TIME")));
                    CALLLOG_CAL_LOG_CREATED_TIME1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_LOG_CREATED_TIME1);


                    Phrase phrase_CALLLOG_CAL_CALL_ASSGIN_STATUS = new Phrase(CallLogDetails.CALLLOG_CAL_CALL_ASSGIN_STATUS, plainFont);
                    PdfPCell CALLLOG_CAL_CALL_ASSGIN_STATUS = new PdfPCell(phrase_CALLLOG_CAL_CALL_ASSGIN_STATUS);
                    CALLLOG_CAL_CALL_ASSGIN_STATUS.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_CALL_ASSGIN_STATUS);
                    PdfPCell CALLLOG_CAL_CALL_ASSGIN_STATUS1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_CALL_ASSGIN_STATUS")));
                    CALLLOG_CAL_CALL_ASSGIN_STATUS1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_CALL_ASSGIN_STATUS1);


                    Phrase phrase_CALLLOG_CAL_CALL_ASSGIN_DATE = new Phrase(CallLogDetails.CALLLOG_CAL_CALL_ASSGIN_DATE, plainFont);
                    PdfPCell CALLLOG_CAL_CALL_ASSGIN_DATE = new PdfPCell(phrase_CALLLOG_CAL_CALL_ASSGIN_DATE);
                    CALLLOG_CAL_CALL_ASSGIN_DATE.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_CALL_ASSGIN_DATE);
                    PdfPCell CALLLOG_CAL_CALL_ASSGIN_DATE1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_CALL_ASSGIN_DATE")));
                    CALLLOG_CAL_CALL_ASSGIN_DATE1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_CALL_ASSGIN_DATE1);


                    Phrase phrase_CALLLOG_CAL_CALL_ASSGIN_TIME = new Phrase(CallLogDetails.CALLLOG_CAL_CALL_ASSGIN_TIME, plainFont);
                    PdfPCell CALLLOG_CAL_CALL_ASSGIN_TIME = new PdfPCell(phrase_CALLLOG_CAL_CALL_ASSGIN_TIME);
                    CALLLOG_CAL_CALL_ASSGIN_TIME.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_CALL_ASSGIN_TIME);
                    PdfPCell CALLLOG_CAL_CALL_ASSGIN_TIME1 = new PdfPCell(new Phrase( map.get("CALLLOG_CAL_CALL_ASSGIN_TIME")));
                    CALLLOG_CAL_CALL_ASSGIN_TIME1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    table.addCell(CALLLOG_CAL_CALL_ASSGIN_TIME1);

                    table.completeRow();

                    PdfPCell emptyRows = new PdfPCell(new Phrase("   "));
                    emptyRows.setColspan(2);
                    /* BaseColor mColorAccent = new BaseColor(224, 224, 224, 255);
                    emptyRows.setBackgroundColor(mColorAccent);*/
                    table.addCell(emptyRows);
                    table.completeRow();

                    document.add(table);


                }
                //End List View

                document.add(new Chunk(headerline));

                document.close();
                result="true";

            }
            catch (Exception ex){
                result="false";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result=="true"){
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"PDF file successfully Location: "+FilePath,Toast.LENGTH_LONG).show();
                OpenPdfFile(FilePath);
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"PDF file Not Created",Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();

        }

    }

    public void OpenPdfFile(String FilePath){

        File file = new File(FilePath);

        if (file.exists()) {
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            }
            catch (ActivityNotFoundException e) {
                Toast.makeText(MainActivity.this,
                        "No Application Available to View PDF",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
