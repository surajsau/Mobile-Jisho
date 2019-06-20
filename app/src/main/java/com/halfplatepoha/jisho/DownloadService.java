package com.halfplatepoha.jisho;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DownloadService extends Service {

    public static final String EXTRA_DOWNLOAD = "download";

    private DownloadDbClient downloadDbClient;

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;

    private int retryCount;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://firebasestorage.googleapis.com/")
                .build();

        downloadDbClient = retrofit.create(DownloadDbClient.class);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Jisho offline dictionary")
                .setContentText("Downloading File")
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initDownload();
        return START_STICKY;
    }

    private void initDownload(){
        Utils.deleteFile();

        Call<ResponseBody> request = downloadDbClient.downloadFile();
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                new DownloadFileAsyncTask().execute(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(retryCount < IConstants.MAX_DOWNLOAD_RETRY) {
                    Analytics.getInstance().recordDownloadFailure();

                    notificationBuilder.setContentText("Failed to download. Please check if internet connection is proper");
                    retryCount++;
                    initDownload();
                }
            }
        });
    }

    private class DownloadFileAsyncTask extends AsyncTask<ResponseBody, Download, Void> {

        @Override
        protected Void doInBackground(ResponseBody... params) {
            try {
                downloadFile(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Download... values) {
            sendNotification(values[0]);
        }

        private void downloadFile(ResponseBody body) throws IOException {

            if(body != null) {
                int count;
                byte data[] = new byte[1024 * 4];
                long fileSize = body.contentLength();
                InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
                File externalFolder = new File(IConstants.STORAGE_DIRECTORY);
                if (!externalFolder.exists())
                    externalFolder.mkdir();

                File outputFile = new File(externalFolder, IConstants.DICTIONARY_FILE_NAME);
                OutputStream output = new FileOutputStream(outputFile);
                long total = 0;
                long startTime = System.currentTimeMillis();
                int timeCount = 1;
                while ((count = bis.read(data)) != -1) {

                    total += count;
                    totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
                    double current = Math.round(total / (Math.pow(1024, 2)));

                    int progress = (int) ((total * 100) / fileSize);

                    long currentTime = System.currentTimeMillis() - startTime;

                    Download download = new Download();
                    download.setTotalFileSize(totalFileSize);

                    if (currentTime > 500 * timeCount) {

                        download.setCurrentFileSize((int) current);
                        download.setProgress(progress);
                        publishProgress(download);
                        timeCount++;
                    }

                    output.write(data, 0, count);
                }
                onDownloadComplete();
                output.flush();
                output.close();
                bis.close();
            } else {
                publishError();
            }

        }
    }

    private void publishError() {
        notificationBuilder.setContentText("Downlod failed. Servers seem to be overloaded so please try again later. Very, sorry for the problem.");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendNotification(Download download){

        sendIntent(download);
        notificationBuilder.setProgress(100,download.getProgress(),false);
        notificationBuilder.setContentText("Downloading file "+ download.getCurrentFileSize() +"/"+totalFileSize +" MB");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download){
        Intent intent = new Intent(IConstants.DOWNLOAD_BROADCAST_FILTER);
        intent.putExtra(EXTRA_DOWNLOAD,download);
        sendBroadcast(intent);
    }

    private void onDownloadComplete(){

        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0,0,false);
        notificationBuilder.setContentText("Successfully downloaded");
        notificationManager.notify(0, notificationBuilder.build());

        stopSelf();

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}