package com.example.mappsdeveloper.soaprequest_example.soap_request;


import android.os.StrictMode;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.io.IOException;


public class HttpRequestServiceAbhishek {


	public static final String URL = "http://ser.luminousindia.com/mhrapp/service.asmx";
	public static final String NAMESPACE = "http://tempuri.org/";

	public static final String METHOD_CREATE_OTP = "MHrCreateOtp";

	
	static String SOAP_ACTION = NAMESPACE + METHOD_CREATE_OTP;



	// KEYS
	public static final String KEY_CODE = "Code";
	public static final String KEY_DESCRIPTION = "des";

	// STATUS
	public static final String STATUS_SUCCESS = "SUCCESS";
	// CREATE OTP

	
	
	
	/* private variables */
	private  static SoapObject request, response;

	public static SoapObject getRequestData(String methodName) throws IOException, Exception
	{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);

			request = new SoapObject(NAMESPACE, METHOD_CREATE_OTP);

			request.addProperty("empid","10995503");
			request.addProperty("imeinumber","99869679459");
			request.addProperty("osversion","6.1");
			request.addProperty("devicename","Android");
			request.addProperty("appversion","2.1");
			Log.i("REQUEST>>>>>>>>>>", request.toString()+"");


		return response = executeHTTPSE(request);
	}


	public  static SoapObject executeHTTPSE(SoapObject requestData)
	{
		// Create the envelop.Envelop will be used to send the request
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(requestData);

		// Says that the soap webservice is a .Net service
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		try
		{
			androidHttpTransport.call(SOAP_ACTION, envelope);
			response = (SoapObject) envelope.getResponse();
			String aa= response.getProperty("Code").toString();
			String dd= response.getProperty("des").toString();
			Log.d("RESPONSE>>>>>>>>>>>", response.toString());
			Log.d("RESPONSE >>> Success  ||||||--", aa  +"---"+dd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			 return    response;
		}
	}
}