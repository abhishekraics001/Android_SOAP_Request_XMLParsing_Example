package com.example.mappsdeveloper.soaprequest_example;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.mappsdeveloper.soaprequest_example.soap_request.HttpRequestServiceAbhishek;
import com.example.mappsdeveloper.soaprequest_example.soap_request_xml_parsing.HttpAll;
import com.example.mappsdeveloper.soaprequest_example.soap_request_xml_parsing.XMLParser;

import org.ksoap2.serialization.SoapObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements HttpAll.AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnsoaprequest = (Button) findViewById(R.id.btnsoaprequest);
        Button btnsoaprequestxmlparser = (Button) findViewById(R.id.btnsoaprequestxmlparser);


        btnsoaprequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CallWebServiceAbhishek().execute("xxxx");
            }
        });
        btnsoaprequestxmlparser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_operator_data();
            }
        });


    }
















    /*
            SOAP Request API / Webservice Consume
            1. Create a AsynkTask and override necessary method
            2. Create a class  where we are performing all execution  task - HttpRequestServiceAbhishek
     */
    class CallWebServiceAbhishek extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {

        }
        @Override
        protected String doInBackground(String... params)
        {
            String result = "";
            try
            {
                 SoapObject responceData =    HttpRequestServiceAbhishek.getRequestData("CreateOTP");
                 String aa= responceData.getProperty("Code").toString();
                 String dd= responceData.getProperty("des").toString();
                 Log.d("RESPONSE >>> Success --", responceData.toString());
                 Log.d("RESPONSE >>> Success  ||||||--", aa  +"---"+dd);
            }
            catch (Exception e)
            {
                Log.d("RESPONSE >>>  Error -- ", e.toString());
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s)
        {
            Toast.makeText(getApplicationContext() , "Responce -- "+s , Toast.LENGTH_SHORT).show();
        }
    }













    /*

 */
    public void get_operator_data()
    {
        MethedoName = "OpDetails";
        final SoapObject Request1 = new SoapObject(HttpAll.NAMESPACE1, MethedoName);
        HttpAll httpAll = new HttpAll(this,MainActivity.this, MethedoName, Request1);
        httpAll.execute();
    }



    String MethedoName = "";
    NodeList nl;
    @Override
    public void processFinish(String output) {
        if (output != null)
        {
            if (MethedoName.equals("OpDetails"))
            {
                ParseXMLData(output);
                Log.d("RESPONSE >>>  Sucess -- ", output);
            }
            else
            {
                Log.d("RESPONSE >>>  Error -- ", output);
            }
        }
    }

 XMLParser xmlParser;
 void  ParseXMLData(String output)
  {
        try
        {
            ArrayList battery_list = new ArrayList<>();
            xmlParser = new XMLParser();
            Document doc = xmlParser.parseXML(output);



            // if you want access any  single item from  document element
            NodeList nl2 = doc.getElementsByTagName("OpList");
            for (int i = 0; i < nl2.getLength(); i++)
            {
                Element e = (Element) nl2.item(i);
              //  battery_list.add(xmlParser.getValue(e, "OpType"));
            }



            // OR if multiple tag then use following step ----
            NodeList OpDetailsResult = doc.getElementsByTagName("OpDetailsResult");
            for (int i = 0; i < OpDetailsResult.getLength(); i++)
            {
                Node OpListNode = OpDetailsResult.item(i);
                try {
                    if (OpListNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element token = (Element) OpListNode;
                        NodeList OpList = token.getElementsByTagName("OpList");
                        for (int op = 0; i < OpList.getLength(); op++)
                        {
                            Element e = (Element) OpList.item(op);
                            battery_list.add(xmlParser.getValue(e, "OpType"));
                        }
                    }
                }
                catch (Exception e)
                {
                }
            }
            Log.d("RESPONSE >>>  Data -- ", battery_list.toString());
        }
        catch (Exception e)
        {
            Log.d("RESPONSE >>>  Error -- ", e.toString());
        }
    }
}
