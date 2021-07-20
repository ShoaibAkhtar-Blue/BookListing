package blue.project.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BookListingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Volume>> {
    private final String LOG_TAG = BookListingActivity.class.getSimpleName();

    private Button searchButton;
    private TextView searchResultsTextView;
    private EditText searchEditText;
    private ListView volumesListView;
    private VolumeAdapter mVolumeAdapter;

    // Request URL
    private String requestUrl;

    // Loader id
    private final int LOADER_ID = 0;

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
            String userInput = searchEditText.getText().toString();
            String searchTerms = extractSearchTerms(userInput);
            if (searchTerms != null && !searchTerms.equals("")) {
                requestUrl = makeRequestUrl(searchTerms);

                // Log information about request URL
                Log.i(LOG_TAG, requestUrl);

                // Start Loader Manager
                getSupportLoaderManager().initLoader(LOADER_ID, null, this);
                // Restart Loader
                /**
                 * Restart Loader
                 * When the user tries to search another term or topic then it restarts the loader.
                 */
                getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
            } else {
                Toast.makeText(this, "Enter topic for search", Toast.LENGTH_SHORT).show();
            }
        });

        // Set onItemClickListener on volumesListview
        volumesListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Volume currentVolume = mVolumeAdapter.getItem(i);
            Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);
            intent.putExtra("currentVolume", currentVolume);
            startActivity(intent);
        });
    }

    @NonNull
    @NotNull
    @Override
    public Loader<List<Volume>> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        // Log information
        Log.i(LOG_TAG, "Loader is created");

        return new VolumeAsyncTaskLoader(this, requestUrl);
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull Loader<List<Volume>> loader, List<Volume> data) {
        // Log information
        Log.i(LOG_TAG, "Loader is finished");

        // Make searchResultsTextView visible
        searchResultsTextView.setVisibility(View.VISIBLE);
        // Clear previous data
        mVolumeAdapter.clear();

        if (data != null) {
            // Log information
            Log.i(LOG_TAG, "data found");

            mVolumeAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<List<Volume>> loader) {
        mVolumeAdapter.clear();
    }

    /**
     * Extract search terms
     * @param inputString received from editText_search
     * @return Search terms
     */
    private String extractSearchTerms(String inputString) {
        return inputString.trim().replace(" ", "+").toLowerCase();
    }

    /**
     * Create a request url by including search terms
     * @param searchTerms
     * @return Request url
     */
    private String makeRequestUrl(String searchTerms) {
        return "https://www.googleapis.com/books/v1/volumes?q=" + searchTerms + "&maxResults=10";
    }
}