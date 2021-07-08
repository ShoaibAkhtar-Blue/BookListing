package blue.project.booklisting;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VolumeAsyncTaskLoader extends AsyncTaskLoader<List<Volume>> {
    private String url;

    /**
     * Constructor
     * @param context of the App
     * @param url
     */
    public VolumeAsyncTaskLoader(@NonNull @NotNull Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public List<Volume> loadInBackground() {
        return QueryUtils.getData(url);
    }
}
