package com.gmantovi.harmony.gsonClasses.album.get;

import com.google.gson.annotations.SerializedName;

public class AlbumGetMessage {


	@SerializedName("message")
	private AlbumGetContainer albumMessage;

	public void setAlbumMessage(AlbumGetContainer albumMessage) {
		this.albumMessage = albumMessage;
	}

	public AlbumGetContainer getAlbumMessage() {
		return albumMessage;
	}

}
