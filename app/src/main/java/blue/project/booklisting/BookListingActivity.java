package blue.project.booklisting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListingActivity extends AppCompatActivity {
    private Button searchButton;
    private TextView searchResultsTextView;
    private EditText searchEditText;
    private ListView volumesListView;
    private VolumeAdapter mVolumeAdapter;

    // Request URL
    private final String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);

        // Find the references of views in the layout
        searchEditText = findViewById(R.id.editText_search);
        searchButton = findViewById(R.id.button_search);
        searchResultsTextView = findViewById(R.id.textView_search_results);
        volumesListView = findViewById(R.id.listView_volums);

        // Make searchResultsTextView invisible
        searchResultsTextView.setVisibility(View.INVISIBLE);

        // Initialize adapter
        mVolumeAdapter = new VolumeAdapter(this, new ArrayList<>());

        // Set adapter to ListView
        volumesListView.setAdapter(mVolumeAdapter);

        // Set onClickListener on searchButton
        searchButton.setOnClickListener(view -> {
            // Make searchResultsTextView visible
            searchResultsTextView.setVisibility(View.VISIBLE);

            // Initialize and start AsyncTask
            VolumesAsyncTask volumesAsyncTask = new VolumesAsyncTask();
            volumesAsyncTask.execute(REQUEST_URL);
        });
    }

    private class VolumesAsyncTask extends AsyncTask<String, Void, List<Volume>> {
        @Override
        protected List<Volume> doInBackground(String... strings) {
            String url = strings[0];
            return QueryUtils.getData(url);
        }

        @Override
        protected void onPostExecute(List<Volume> volumes) {
            // Clear previous data
            mVolumeAdapter.clear();

            if (volumes != null) {
                mVolumeAdapter.addAll(volumes);
            }
        }
    }
}