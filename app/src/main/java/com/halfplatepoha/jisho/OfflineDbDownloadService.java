package com.halfplatepoha.jisho;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

/**
 * Created by surjo on 19/07/17.
 */

public class OfflineDbDownloadService extends Service {

    private StorageReference storageRef;

    @Override
    public void onCreate() {
        super.onCreate();
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://links-b04aa.appspot.com/dictionary.db");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Download service", "start!");

        try {
            File downloadLocation = File.createTempFile("dictionary", "db");
            storageRef.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.e("Download", uri.toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
//                    .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    Log.e("Download Progress: ", (taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount())*100f + "");
//                }
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
