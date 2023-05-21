/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.artist.search;

import com.gmantovi.harmony.gsonClasses.Header;
import com.google.gson.annotations.SerializedName;
/**
 * A class for json data retrieval.
 *
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class ArtistSearchContainer {

	@SerializedName("body")
	private ArtistSearchBody body;

	@SerializedName("header")
	private Header header;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public ArtistSearchBody getBody() {
		return body;
	}

	public void setBody(ArtistSearchBody body) {
		this.body = body;
	}
}
