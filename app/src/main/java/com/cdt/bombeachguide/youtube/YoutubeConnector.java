package com.cdt.bombeachguide.youtube;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.cdt.bombeachguide.fragment.ListVideoFragment;
import com.cdt.bombeachguide.fragment.VideoFragment;
import com.cdt.bombeachguide.pojo.VideoItem;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.common.collect.Lists;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Trang on 6/12/2015.
 */
public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;

    private String TAG = "Youtube Connector";

    // Your developer key goes here
    public static final String KEY
            = "AIzaSyAZe5fNrlkPPIwJgZAFZ4jUek-2YKy0OV4";

    public YoutubeConnector(Context context) {
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.readonly");

        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName("minerguide-975").build();

        try{
            query = youtube.search().list("id,snippet");
            query.setKey(KEY);
            query.setType("video");

            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch(IOException e){
            Log.d(TAG, "Could not initialize: " + e);
        }
    }

    public List<VideoItem> search(List<String> listUrl){
        List<VideoItem> items = new ArrayList<VideoItem>();

        for (String keywords : listUrl){
            query.setQ(keywords);
            try{
                SearchListResponse response = query.execute();
                List<SearchResult> results = response.getItems();

                for(SearchResult result:results){
                    VideoItem item = new VideoItem();
                    item.setTitle(result.getSnippet().getTitle());
                    item.setDescription(result.getSnippet().getDescription());
                    item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                    item.setId(result.getId().getVideoId());
                    items.add(item);
                }
            }
            catch (GoogleJsonResponseException e) {
                System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                        + e.getDetails().getMessage());
            }
            catch(IOException e){
                Log.d(TAG, "Could not search: " + e);
            }
        }
        return items;
    }

    public void searchVideoFromUrl(List<String> listUrl, ListVideoFragment fragment){
        List<VideoItem> videos = new ArrayList<VideoItem>();
        for (String youtubeUrl : listUrl){
            try {
                String videoID = getYoutubeVideoId(youtubeUrl);
                HttpClient client = new DefaultHttpClient();
                // Perform a GET request to YouTube for a JSON list of all the videos by a specific user
                HttpUriRequest request = new HttpGet("https://www.googleapis.com/youtube/v3/videos?id="+videoID+"&key="+KEY+"&part=snippet&alt=json");
                // Get the response that YouTube sends back
                HttpResponse response = client.execute(request);
                // Convert this response into a readable string
                String jsonString = StreamUtils.convertToString(response.getEntity().getContent());
                // Create a JSON object that we can use from the String
                JSONObject json = new JSONObject(jsonString);

                // Get are search result items
                JSONArray jsonArray = json.getJSONArray("items");

                // Create a list to store are videos in

                // Loop round our JSON list of videos creating Video objects to use within our app
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("snippet");
                    // The title of the video
                    String title = jsonObject.getString("title");

                    // A url to the thumbnail image of the video
                    String thumbUrl = jsonObject.getJSONObject("thumbnails").getJSONObject("standard").getString("url");
                    
                    VideoItem entity = new VideoItem();
                    entity.setTitle(title);
                    entity.setThumbnailURL(thumbUrl);
                    entity.setId(videoID);
                    videos.add(entity);
                }
                if(videos.size() > 4){
                    fragment.addVideoItems(new ArrayList<VideoItem>(videos));
                    videos.clear();
                }
            }catch (Exception e){
                System.err.println("Exception : " + e.toString());
            }
        }
        if(!videos.isEmpty()){
            fragment.addVideoItems(new ArrayList<VideoItem>(videos));
            videos.clear();
        }
    }


    public static String getYoutubeVideoId(String youtubeUrl)
    {
        String video_id="";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("http"))
        {

            String expression = "^.*((youtu.be"+ "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches())
            {
                String groupIndex1 = matcher.group(7);
                if(groupIndex1!=null && groupIndex1.length()==11)
                    video_id = groupIndex1;
            }
        }
        System.out.println("Video id  : " + video_id);
        return video_id;
    }


}