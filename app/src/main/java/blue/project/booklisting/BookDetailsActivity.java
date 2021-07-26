package blue.project.booklisting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BookDetailsActivity extends AppCompatActivity {
    private final String LOG_TAG = BookDetailsActivity.class.getSimpleName();

    private TextView bookTitleTextView;
    private TextView bookAuthorsTextView;
    private TextView publisherTextView;
    private TextView publishedDateTextView;
    private TextView pageCountTextView;
    private TextView descriptionTextView;
    private Button bookPreviewButton;
    private String canonicalVolumeLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // Set title of this activity
        setTitle("Book Details");

        // Enable Home/Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        initViews();

        // Get intent and data
        Intent intent = getIntent();
        Volume currentVolume = (Volume) intent.getSerializableExtra("currentVolume");

        // Set data to views
        bookTitleTextView.setText(currentVolume.getTitle());
        bookAuthorsTextView.setText(currentVolume.getAuthors());
        publisherTextView.setText(currentVolume.getPublisher());
        publishedDateTextView.setText(currentVolume.getPublishedDate());
        pageCountTextView.setText(String.valueOf(currentVolume.getPageCount()));
        descriptionTextView.setText(currentVolume.getDescription());

        // Set onClickListener on Book Preview button
        bookPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get book info link
                canonicalVolumeLink = currentVolume.getCanonicalVolumeLink();
                // Convert info link into uri
                Uri bookUri = Uri.parse(canonicalVolumeLink);

                // Create Intent and pass Uri
                Intent bookPreviewIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(bookPreviewIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Initialize views
     */
    private void initViews() {
        // Find references of different views in the layout
        bookTitleTextView = findViewById(R.id.textView_book_title);
        bookAuthorsTextView = findViewById(R.id.textView_book_authors);
        publisherTextView = findViewById(R.id.textView_publisher);
        publishedDateTextView = findViewById(R.id.textView_publishedDate);
        pageCountTextView = findViewById(R.id.textView_pageCount);
        descriptionTextView = findViewById(R.id.textView_description);
        bookPreviewButton = findViewById(R.id.button_book_preview);
    }
}