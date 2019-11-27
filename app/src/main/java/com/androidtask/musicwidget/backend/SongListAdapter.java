package com.androidtask.musicwidget.backend;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.CursorAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.androidtask.musicwidget.model.Song;
import com.androidtask.musicwidget.R;
import com.androidtask.musicwidget.model.Song;

/**
 * The type Song list adapter.
 */
public class SongListAdapter extends CursorAdapter implements SectionIndexer{
    private final AlphabetIndexer mAlphabetIndexer;

    /**
     * Instantiates a new Song list adapter.
     *
     * @param context the context
     * @param c       the c
     */
    public SongListAdapter(Activity context, Cursor c) {
        super(context, c, false);

        mAlphabetIndexer = new AlphabetIndexer(c,
                c.getColumnIndex(MediaStore.Audio.Media.ARTIST),
                " ABCDEFGHIJKLMNÑOPQRSTUVWXYZ");
        mAlphabetIndexer.setCursor(c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.song_list_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvArtist   = view.findViewById(R.id.textArtist);
        TextView tvTitle    = view.findViewById(R.id.textTitle);
        TextView tvDuration = view.findViewById(R.id.textDuration);

        String artist   = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        String title    = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

        Song song = new Song(0, title, artist, duration);

        tvArtist.setText(artist);
        tvTitle.setText(title);
        tvDuration.setText(song.getDurationStr());
    }

    /**
     * Gets song.
     *
     * @param position the position
     * @return the song
     */
    public Song getSong(int position) {
        Cursor cursor = (Cursor) getItem(position);
        String artist   = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        String title    = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

        return new Song(0, title, artist, duration);
    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
        mAlphabetIndexer.setCursor(cursor);
    }

    @Override
    public Object[] getSections() {
        return mAlphabetIndexer.getSections();
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mAlphabetIndexer.getPositionForSection(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return mAlphabetIndexer.getSectionForPosition(position);
    }
}
