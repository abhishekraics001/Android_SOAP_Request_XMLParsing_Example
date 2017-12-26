package com.example.mappsdeveloper.soaprequest_example.soap_request_xml_parsing;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;


import com.example.mappsdeveloper.soaprequest_example.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


/**
 * Created by Nikhil Keshari on 1/14/2016.
 */
public class HttpAll extends AsyncTask<Void, Void, String> {

    SoapObject data;
    String method;
    SoapObject resultString;
    public static final String DEFAULT = "N/A";


   public static String NAMESPACE1 = "http://tempuri.org/";
    //------Gurgaon Webservices
    String URL1 = "http://172.16.8.15:6996/Webservices/eshfWebservice.asmx";


    public AsyncResponse delegate = null;


    public interface AsyncResponse {
        void processFinish(String output);
    }

    public HttpAll(AsyncResponse listener,Context ctx, String method, SoapObject object) {
        this.data = object;
        this.method = method;
        delegate = listener;

    }

    private String POST(String result) {
      //  TextFile.writeTextFileDataInExternalStorage(context ,  startDate  , "method",  NAMESPACE1 + method +"  :  " +data.toString() , "success-onPostExecute" ,result);
        return result;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }




    @Override
    protected String doInBackground(Void... params)
    {
        String SOAP_ACTION1 = NAMESPACE1 + method;
        SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope1.dotNet = true;
        envelope1.setOutputSoapObject(data);

        HttpTransportSE httpTransport1 = new HttpTransportSE(URL1);
        Log.d("transport", httpTransport1.toString());
        httpTransport1.debug = true;
        try
        {
            httpTransport1.call(SOAP_ACTION1, envelope1);
            resultString = (SoapObject) envelope1.bodyIn;
            String xml1 = httpTransport1.responseDump;
            return POST(xml1.toString());
        }
        catch (Exception e)
        {
            e.getMessage();
        }

        Log.d("dump Request: ", httpTransport1.requestDump);
        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        delegate.processFinish(s);
    }




}
