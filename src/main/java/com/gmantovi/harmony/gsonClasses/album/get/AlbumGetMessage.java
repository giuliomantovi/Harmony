/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.album.get;

import com.google.gson.annotations.SerializedName;
/**
 * A class for json data retrieval.
 *
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
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
