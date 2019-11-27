package com.androidtask.musicwidget.model;

import android.content.ContentUris;
import android.net.Uri;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The type Song.
 */
public class Song implements Serializable{
	private final long id;
	private final String title;
	private final String artist;
	private final long duration;

	/**
	 * Instantiates a new Song.
	 *
	 * @param id       the id
	 * @param title    the title
	 * @param artist   the artist
	 * @param duration the duration
	 */
	public Song(long id, String title, String artist, long duration) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.duration = duration;
	}

	/**
	 * Gets title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets artist.
	 *
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Gets duration str.
	 *
	 * @return the duration str
	 */
	public String getDurationStr() {
		SimpleDateFormat df = new SimpleDateFormat("mm:ss", Locale.US);
		return df.format(new Date(duration));
	}

	/**
	 * Gets uri.
	 *
	 * @return the uri
	 */
	public Uri getURI() {
		return ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
	}
	
	@Override
	public String toString() {
		return title + " - " + artist + " - " + getDurationStr();
	}
}
