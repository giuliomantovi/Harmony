/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.artist.get;

import com.google.gson.annotations.SerializedName;
/**
 * A class for json data retrieval.
 *
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
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
