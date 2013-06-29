package com.foohyfooh.longweekend;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class GetJSON extends AsyncTask<String, Void, String[]> {

    protected String[] doInBackground(String... params) {
        try{
            List list = new ArrayList<String>();
            RequestThread[] threads = new RequestThread[params.length];
            ExecutorService threadExecutor = Executors.newCachedThreadPool();
            for(int i = 0; i < params.length;i++){
                threads[i] = new RequestThread(params[i]);
                threadExecutor.execute(threads[i]);
            }
            threadExecutor.shutdown();
            boolean ended = threadExecutor.awaitTermination(40, TimeUnit.SECONDS);
            if(ended) for (RequestThread thread : threads)
                //Produces a list of all the holidays obtained from the requests
                //This also deals with duplicate lists
                list =  ListUtils.sum(list, thread.getList());
            return  (String[])list.toArray(new String[list.size()]);
        }catch (InterruptedException e){
            return null;
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    private class RequestThread implements Runnable{

        private final String param;
        private List<String> list = new ArrayList<String>();

        public RequestThread(String param){
            this.param = param;
        }

        public List<String> getList(){
            return  list;
        }

        @Override
        public void run() {
            try{
                URL url = new URL(param);
                String json = IOUtils.toString(url.openStream());
                JSONArray jsonArray = new JSONArray(json);
                for(int count = 0; count < jsonArray.length(); count++){
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    String value = String.format("Holiday: %s Date: %s Desc: %s\n", jsonObject.getString("holidayName"),
                            jsonObject.getString("holidayDate"), jsonObject.getString("holidayDesc"));
                    list.add(value);
                }
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}