package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.utils.RequestQueueSingleton;
import com.wontanara.dictionnairedelanguedessignes.utils.ZipManager;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ApiRepository {

    private final Application application;
    private final CategoryRepository categoryRepository;
    private final MutableLiveData<Resource<List<DownloadableCategory>>> downloadableCategories = new MutableLiveData<>();
    private final MutableLiveData<Resource<Boolean>> successSuggestion = new MutableLiveData<>();

    ApiRepository(Application application) {
        this.application = application;
        categoryRepository = new CategoryRepository(application);
    }

    LiveData<Resource<List<DownloadableCategory>>> getDownloadableCategories() {
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

        downloadableCategories.setValue(Resource.loading(null));
        queue.add(jsonArrayRequest);
        return downloadableCategories;
    }

    LiveData<Resource<CategoryWithWords>> downloadCategory(DownloadableCategory downloadableCategory) {
        MutableLiveData<Resource<CategoryWithWords>> categoryWithWords = new MutableLiveData<>();
        categoryWithWords.setValue(Resource.loading(null));
        Category category = new Category(downloadableCategory.getId(), downloadableCategory.getName(), downloadableCategory.getUpdated_at());
        ArrayList<Word> list = new ArrayList<>();

        RequestQueue queue = RequestQueueSingleton.getInstance(application).getRequestQueue();
        String url = application.getResources().getString(R.string.base_url) + "/api/category/" + downloadableCategory.getId() + "/word";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
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
            downloadZip(downloadableCategory, new CategoryWithWords(category, list), categoryWithWords);
        }, error -> {
            error.printStackTrace();
            categoryWithWords.setValue(Resource.error(error.getMessage(), null));
        });

        queue.add(jsonArrayRequest);
        return categoryWithWords;
    }

    private void downloadZip(DownloadableCategory downloadableCategory, CategoryWithWords data, MutableLiveData<Resource<CategoryWithWords>> categoryWithWords) {
        File file = new File(application.getExternalFilesDir("") + File.separator + downloadableCategory.getId() + ".zip");
        if (!file.exists()) {
            Uri uri = Uri.parse(application.getString(R.string.base_url) + "/api/category/" + downloadableCategory.getId() + "/file");
            DownloadManager downloadManager = (DownloadManager) application.getSystemService(Context.DOWNLOAD_SERVICE);

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(application, "", downloadableCategory.getId() + ".zip");

            downloadManager.enqueue(request);

            BroadcastReceiver onComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        ZipManager.unzip(application.getExternalFilesDir("") + File.separator + downloadableCategory.getId() + ".zip", application.getExternalFilesDir("") + File.separator + downloadableCategory.getId());
                        if (!file.delete()) {
                            throw new IOException("Unable to delete file " + file.getPath());
                        }

                        categoryWithWords.setValue(Resource.success(data));
                        categoryRepository.insert(data);

                        Toast.makeText(application, "Catégorie installée", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            application.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } else {
            try {
                ZipManager.unzip(application.getExternalFilesDir("") + File.separator + downloadableCategory.getId() + ".zip", application.getExternalFilesDir("") + File.separator + downloadableCategory.getId());
                if (!file.delete()) {
                    throw new IOException("Unable to delete file " + file.getPath());
                }

                categoryWithWords.setValue(Resource.success(data));
                categoryRepository.insert(data);

                Toast.makeText(application, "Catégorie installée", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    LiveData<Resource<Boolean>> postSuggestion(String word, String definition, String imagePath, String videoPath) {
        RequestQueue queue = RequestQueueSingleton.getInstance(application).getRequestQueue();
        String url = application.getResources().getString(R.string.base_url) + "/api/suggestion";

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                response -> successSuggestion.setValue(Resource.success(true)),
                error -> successSuggestion.setValue(Resource.error(error.getMessage(), false))
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", word);
                if (definition != null && !definition.isEmpty()) {
                    params.put("description", definition);
                }
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    if (imagePath != null) {
                        File imageFile = new File(imagePath);
                        byte[] imageData = FileUtils.readFileToByteArray(imageFile);
                        params.put("image", new DataPart(imageFile.getName(), imageData));
                    }
                    if (videoPath != null) {
                        File videoFile = new File(videoPath);
                        byte[] videoData = FileUtils.readFileToByteArray(videoFile);
                        params.put("video", new DataPart(videoFile.getName(), videoData));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };

        successSuggestion.setValue(Resource.loading(null));
        queue.add(multipartRequest);
        return successSuggestion;
    }

    public static class VolleyMultipartRequest extends Request<NetworkResponse> {
        private final Response.Listener<NetworkResponse> mListener;
        private final Map<String, String> mHeaders;

        public VolleyMultipartRequest(int method, String url,
                                      Response.Listener<NetworkResponse> listener,
                                      Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            this.mListener = listener;
            this.mHeaders = new HashMap<>();
        }

        @Override
        public Map<String, String> getHeaders() {
            return mHeaders;
        }

        @Override
        protected void deliverResponse(NetworkResponse response) {
            mListener.onResponse(response);
        }

        @Override
        protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
            try {
                return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
            } catch (Exception e) {
                return Response.error(new ParseError(e));
            }
        }

        protected Map<String, String> getParams() {
            return null;
        }

        protected Map<String, DataPart> getByteData() {
            return null;
        }

        public static class DataPart {
            private final String fileName;
            private final byte[] content;

            public DataPart(String name, byte[] data) {
                fileName = name;
                content = data;
            }

            public String getFileName() {
                return fileName;
            }

            public byte[] getContent() {
                return content;
            }
        }
    }
}