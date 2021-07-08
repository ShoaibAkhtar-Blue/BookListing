package blue.project.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BookListingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Volume>> {
    private Button searchButton;
    private TextView searchResultsTextView;
    private EditText searchEditText;
    private ListView volumesListView;
    private VolumeAdapter mVolumeAdapter;

    // Request URL
    private final String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";

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
            // Start Loader Manager
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        });
    }

    @NonNull
    @NotNull
    @Override
    public Loader<List<Volume>> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        return new VolumeAsyncTaskLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull Loader<List<Volume>> loader, List<Volume> data) {
        // Make searchResultsTextView visible
        searchResultsTextView.setVisibility(View.VISIBLE);
        // Clear previous data
        mVolumeAdapter.clear();

        if (data != null) {
            mVolumeAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<List<Volume>> loader) {
        mVolumeAdapter.clear();
    }
}