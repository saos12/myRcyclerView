package com.saos.admin.myrcyclerview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<Model> lst;
    ArrayList<ActivityModel> lstActivities;
    RecyclerView recycler;
    Button btn;


    //private final String URL_SERVICE ="https://www.daa.ugto.mx/daaservicesx/Service.asmx";
    //private final String NAMESPACE="https://www.daa.ugto.mx/";

    private final String URL_SERVICE ="http://10.74.16.225/testServer/serverSoap.php#";
    private final String NAMESPACE="http://10.74.16.225/testServer/";

    private String METHOD_NAME;
    private String SOAP_ACTION;

    private SoapObject request;
    private SoapSerializationEnvelope envelope=null;

    private Gson gsonRecept= null;

    private PropertyInfo paramNua=null,paramPassword=null,paramSmartPhone;

    private final int timeout=300000;

    private SoapPrimitive resultRequest= null;

    public ProgressDialog dialog;

    AdapterRecyclerView adapterActivities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler=findViewById(R.id.recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        btn=findViewById(R.id.btnDownload);
        lst= new ArrayList<Model>();
        lstActivities= new ArrayList<ActivityModel>();

        for (int g=0;g<50;g++)
            lst.add(new Model("Data # "+g+" ","Details kjhfkjsdhfkjhsdkjfhsjkdhfkjsdhfjkhsdkf # "+g+" "));
        new DownloadList().execute();


        //AdapterRecyclerView adapter= new AdapterRecyclerView(lst);
        /*
        adapterActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"select: "+lst.get(recycler.getChildAdapterPosition(v)).getData(),Toast.LENGTH_SHORT).show();
            }
        });
*/


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DownloadList().execute();
            }
        });

    }

    class DownloadList extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {

            return downloadServerList("146472","06657759","+524171105480");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog= new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading..");
            dialog.setIndeterminate(false);
            dialog.setMax(2000);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progreso= values[0].intValue();

            dialog.setProgress(progreso);

        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            JSONArray jsonArray=null;
            try {
                 jsonArray= new JSONArray(new String(strings));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i=0; i<jsonArray.length();i++)
            {
                try {
                    String aidi= jsonArray.getJSONObject(i).getString("ID_ACTIVIDAD_MC");
                    String name= jsonArray.getJSONObject(i).getString("NOMBRE_ACTIVIDAD_MC");
                    String horas= jsonArray.getJSONObject(i).getString("DURACION_HORAS_MC");
                    String ubicacion= jsonArray.getJSONObject(i).getString("UBICACION_MC");
                    String descrip= jsonArray.getJSONObject(i).getString("DESCRIPCION_MC");
                    String ini= jsonArray.getJSONObject(i).getString("FECHA_INICIO_MC");

                    Log.d("valores","id: "+aidi);
                    Log.d("valores","name: "+name);
                    Log.d("valores","horas: "+horas);
                    Log.d("valores","ubicacion: "+ubicacion);
                    Log.d("valores","description: "+descrip);
                    Log.d("valores","inicio: "+ini);

                    lstActivities.add(new ActivityModel(Integer.parseInt(aidi),name,Double.parseDouble(horas),ubicacion,descrip,ini));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            CargarLista(lstActivities);

            dialog.dismiss();
            Log.d("result doing", strings);




        }
    }


    public String downloadServerList(String nua,String pass,String smartPhone)
    {
        METHOD_NAME="GetActividadesID";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;

        String recep,result="";
        gsonRecept= new Gson();

        request= new SoapObject(NAMESPACE,METHOD_NAME);

        paramNua= new PropertyInfo();
        paramNua.setName("pNUA");
        paramNua.setValue(nua);
        paramNua.setType(String.class);

        paramPassword= new PropertyInfo();
        paramPassword.setName("pPassword");
        paramPassword.setValue(pass);
        paramPassword.setType(String.class);


        paramSmartPhone= new PropertyInfo();
        paramSmartPhone.setName("pSmartPhone");
        paramSmartPhone.setValue(smartPhone);
        paramSmartPhone.setType(String.class);


        request.addProperty(paramNua);
        request.addProperty(paramPassword);
        request.addProperty(paramSmartPhone);

        envelope= new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE url= new HttpTransportSE(URL_SERVICE);
        //HttpsTransportSE urls= new HttpsTransportSE(URL_SERVICE,443,"/Service",30000);

        try {
            url.call(SOAP_ACTION,envelope);
            //urls.call(SOAP_ACTION,envelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        try {
            result=(String)envelope.getResponse();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }

        //Log.d("fsdfsd",result);
        return result.toString();
    }

    public void CargarLista(ArrayList<ActivityModel> lstActivitiesM)
    {
        adapterActivities= new AdapterRecyclerView(lstActivitiesM);
        recycler.setAdapter(adapterActivities);
        adapterActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"select: "+lstActivities.get(recycler.getChildAdapterPosition(v)).getId(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
