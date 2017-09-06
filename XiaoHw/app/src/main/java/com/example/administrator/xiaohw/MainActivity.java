package com.example.administrator.xiaohw;

        import android.content.ContentValues;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.SimpleCursorAdapter;
        import android.widget.Toast;


        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

/**
 * 开发一个笑话连连看应用，要求如下：
 * a)离线时可以查看缓存的数据
 * b)在线时自动更新并缓存数据
 * c)列表翻页功能
 * d)列表下拉刷新功能
 * e)通信时数据格式为JSON
 */
public class MainActivity extends AppCompatActivity implements RefreshListView.IOnRefreshListener,
        RefreshListView.IOnLoadMoreListener {
    private ArticleDb articleDb;
    private SQLiteDatabase articleDbRead, articleDbWrite;
    private SimpleCursorAdapter articleAdapter;
    private RefreshListView mListView;
    private int pageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleDb = new ArticleDb(this);
        articleDbRead = articleDb.getReadableDatabase();
        articleDbWrite = articleDb.getWritableDatabase();

        initView();
        articleAdapter = new SimpleCursorAdapter(this, R.layout.list_article_cell, null, new String[]{"post_title", "post_date",},
                new int[]{R.id.tvPostTitle, R.id.tvPostDate});
        mListView.setAdapter(articleAdapter);
        initData();
        initMonitor();
    }

    /**
     * 点击Item进入相应的文章详情页面
     */
    private AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Cursor c = articleAdapter.getCursor();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ArticleDetailActivity.class);
            Bundle bundle = new Bundle(); // 创建Bundle对象
            bundle.putString("post_content", c.getString(c.getColumnIndex("post_content")));
            bundle.putString("post_title", c.getString(c.getColumnIndex("post_title")));
            bundle.putString("post_date", c.getString(c.getColumnIndex("post_date")));

            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private void initMonitor() {
        mListView.setOnItemClickListener(listViewItemClickListener);
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadMoreListener(this);
    }

    private void initData() {
        if (!Utils.checkNetworkState(this)) {
            Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
            Cursor c = articleDbRead.query("article_table", null, null, null, null, null, null);
            articleAdapter.changeCursor(c);
            mListView.onRefreshComplete();
            return;
        }
        articleDbWrite.delete("article_table", null, null);
        pageNum = 0;
        String url = "http://144.168.60.251/notes.php?page=10&offset=" + pageNum;
        //读取最新的数据
        readURL(url);
    }

    private void initView() {
        mListView = (RefreshListView) findViewById(R.id.listView);
    }

    /**
     * 加载数据
     *
     * @param url
     */
    public void readURL(String url) {

        if (!Utils.checkNetworkState(this)) {
            Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
            mListView.onRefreshComplete();
            mListView.onLoadMoreComplete(false);
            return;
        }

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.addRequestProperty("encoding", "UTF-8");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestMethod("GET");

                    InputStream iStream = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(iStream);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        builder.append(line);
                    }
                    br.close();
                    iStream.close();
                    return builder.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                pageNum++;
                JSONArray array;
                try {
                    array = new JSONArray(s);
                    mListView.onRefreshComplete();
                    if (array.length() > 0 && array.length() <= 10) {
                        mListView.onLoadMoreComplete(false);
                    } else {
                        mListView.onLoadMoreComplete(true);
                    }
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject lan = array.getJSONObject(i);
                        //保存文章数据到本地数据库进行缓存
                        ContentValues cv = new ContentValues();
                        cv.put("post_content", lan.getString("post_content"));
                        cv.put("post_title", lan.getString("post_title"));
                        cv.put("post_date", lan.getString("post_date").substring(0, 10));
                        articleDbWrite.insert("article_table", null, cv);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pageNum--;
                }
                refreshArticleListView();
                super.onPostExecute(s);
            }

        }.execute(url);
    }

    /*
     * 刷新文章列表
     */
    private void refreshArticleListView() {
        Cursor c = articleDbRead.query("article_table", null, null, null, null, null, null);
        articleAdapter.changeCursor(c);
    }


    @Override
    public void OnRefresh() {
        initData();
    }

    @Override
    public void OnLoadMore() {
        String url = "http://144.168.60.251/notes.php?page=10&offset=" + pageNum;
        readURL(url);
    }

}
