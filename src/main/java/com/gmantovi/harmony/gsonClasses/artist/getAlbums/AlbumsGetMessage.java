package com.gmantovi.harmony.gsonClasses.artist.getAlbums;

import com.google.gson.annotations.SerializedName;

public class AlbumsGetMessage {

	@SerializedName("message")
	private AlbumsGetContainer albumsMessage;

	public void setAlbumsMessage(AlbumsGetContainer albumsMessage) {
		this.albumsMessage = albumsMessage;
	}

	public AlbumsGetContainer getAlbumsMessage() {
		return albumsMessage;
	}

}
