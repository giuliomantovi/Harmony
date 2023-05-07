package com.gmantovi.harmony.gsonClasses.artist.get;

import com.google.gson.annotations.SerializedName;

public class ArtistGetMessage {



	@SerializedName("message")
	private ArtistGetContainer artistMessage;

	public void setArtistMessage(ArtistGetContainer artistMessage) {
		this.artistMessage = artistMessage;
	}

	public ArtistGetContainer getArtistMessage() {
		return artistMessage;
	}

}
