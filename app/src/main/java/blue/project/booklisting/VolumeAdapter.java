package blue.project.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class VolumeAdapter extends ArrayAdapter<Volume> {
    /**
     * Constructor
     * @param context of the app
     * @param objects is the list of volumes
     */
    public VolumeAdapter(@NonNull @org.jetbrains.annotations.NotNull Context context, @NonNull @org.jetbrains.annotations.NotNull List<Volume> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Volume volume = getItem(position);

        // Find the reference of views in the layout
        TextView titleTextView = listItem.findViewById(R.id.textView_title);
        TextView authorNameTextView = listItem.findViewById(R.id.textView_author_name);

        // Set data to views
        titleTextView.setText(volume.getTitle());
        authorNameTextView.setText(volume.getAuthors());

        return listItem;
    }
}
