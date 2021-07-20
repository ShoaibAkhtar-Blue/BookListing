package blue.project.booklisting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class BookDetailsActivity extends AppCompatActivity {
    private final String LOG_TAG = BookDetailsActivity.class.getSimpleName();

    private TextView bookTitleTextView;
    private TextView bookAuthorsTextView;
    private TextView publisherTextView;
    private TextView publishedDateTextView;
    private TextView pageCountTextView;
    private TextView descriptionTextView;

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
        //Intent intent = getIntent();
        //Volume currentVolume = (Volume) intent.getSerializableExtra("currentVolume");

        /*
        String bookTitle = intent.getStringExtra("Book Title");
        String bookAuthors = intent.getStringExtra("Book Authors");

         */

        // Set data to views
        /*
        bookTitleTextView.setText(currentVolume.getTitle());
        bookAuthorsTextView.setText(currentVolume.getAuthors());
        publisherTextView.setText(currentVolume.getPublisher());
        publishedDateTextView.setText(currentVolume.getPublishedDate());
        pageCountTextView.setText(String.valueOf(currentVolume.getPageCount()));
        descriptionTextView.setText(currentVolume.getDescription());

         */
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
    }
}