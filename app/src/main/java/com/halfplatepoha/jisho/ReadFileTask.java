package com.halfplatepoha.jisho;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFileTask<A> extends AsyncTask<String, Void, A[]> {

        private Class<A[]> clazz;
        private Context context;
        private Listener<A> listener;

        public ReadFileTask(Context context, Class<A[]> clazz, Listener<A> listener) {
            this.clazz = clazz;
            this.listener = listener;
            this.context = context;
        }

        @Override
        protected A[] doInBackground(String... strings) {
            BufferedReader reader = null;
            try {
                InputStream is = context.getAssets().open(strings[0]);
                InputStreamReader isr = new InputStreamReader(is);
                reader = new BufferedReader(isr);
                Gson gson = new GsonBuilder().create();
                A[] results = gson.fromJson(reader, clazz);
                return results;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(A[] as) {
            if(listener != null)
                listener.onResult(as);
        }

        interface Listener<A> {
            void onResult(A[] result);
        }
    }