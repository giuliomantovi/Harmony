package com.gmantovi.harmony.gsonClasses.artist.search;

import com.gmantovi.harmony.gsonClasses.Header;
import com.google.gson.annotations.SerializedName;

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
