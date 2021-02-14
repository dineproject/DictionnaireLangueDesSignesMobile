package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.utils.RequestQueueSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ApiRepository {

    private final Application application;
    private final MutableLiveData<Resource<List<DownloadableCategory>>> downloadableCategories = new MutableLiveData<>();
    private final MutableLiveData<Resource<CategoryWithWords>> categoryWithWords = new MutableLiveData<>();

    ApiRepository(Application application) {
        this.application = application;
    }

    LiveData<Resource<List<DownloadableCategory>>> getDownloadableCategories () {
        RequestQueue queue = RequestQueueSingleton.getInstance(application).getRequestQueue();
        String url = application.getResources().getString(R.string.base_url) + "/api/category";

        ArrayList<DownloadableCategory> list = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj;
                try {
                    obj = response.getJSONObject(i);
                    int id = obj.getInt("id");
                    String name = obj.getString("name");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.FRANCE);
                    GregorianCalendar updated_at = new GregorianCalendar();
                    updated_at.setTime(Objects.requireNonNull(dateFormat.parse(obj.getString("updated_at"))));
                    int word_count = obj.getInt("word_count");
                    DownloadableCategory category = new DownloadableCategory(id, name, updated_at, word_count);
                    list.add(category);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
            downloadableCategories.setValue(Resource.success(list));
        }, error -> {
            error.printStackTrace();
            downloadableCategories.setValue(Resource.error(error.getMessage(), null));
        });

        queue.add(jsonArrayRequest);

        return downloadableCategories;
    }

    LiveData<Resource<CategoryWithWords>> downloadCategory(DownloadableCategory downloadableCategory) {
        Category category = new Category(downloadableCategory.getId(), downloadableCategory.getName(), downloadableCategory.getUpdated_at());
        ArrayList<Word> list = new ArrayList<>();

        RequestQueue queue = RequestQueueSingleton.getInstance(application).getRequestQueue();
        String url = application.getResources().getString(R.string.base_url) + "/api/category/" + downloadableCategory.getId() + "/word";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0 ; i < response.length() ; i++) {
                JSONObject obj;
                try {
                    obj = response.getJSONObject(i);
                    int id = obj.getInt("id");
                    String name = obj.getString("name");
                    String description = obj.getString("description");
                    int category_id = obj.getInt("category_id");
                    String video_path = obj.getString("video_path");
                    String image_path = obj.getString("image_path");
                    Word word = new Word(id, name, description, category_id, video_path, image_path);
                    list.add(word);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            downloadZip(downloadableCategory.getId());
            categoryWithWords.setValue(Resource.success(new CategoryWithWords(category, list)));
        }, error -> {
            error.printStackTrace();
            categoryWithWords.setValue(Resource.error(error.getMessage(), null));
        });

        queue.add(jsonArrayRequest);

        return categoryWithWords;
    }

    private void downloadZip(int id) {
        File file = new File(application.getExternalFilesDir("") + "/" + id + ".zip");
        if (!file.exists()) {
            Uri uri = Uri.parse(application.getString(R.string.base_url) + "/api/category/" + id + "/file");
            DownloadManager downloadManager = (DownloadManager) application.getSystemService(Context.DOWNLOAD_SERVICE);

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(application, "", id + ".zip");

            downloadManager.enqueue(request);
        }
    }
}
