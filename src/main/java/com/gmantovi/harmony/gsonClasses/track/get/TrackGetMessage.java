/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in README
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.track.get;

import com.google.gson.annotations.SerializedName;
/**
 * class for json data retrieval.
 * @author Sachin handiekar
 * @version 1.0
 */
public class TrackGetMessage {

	@SerializedName("message")
	private TrackGetContainer trackMessage;

	public void setTrackMessage(TrackGetContainer trackMessage) {
		this.trackMessage = trackMessage;
	}

	public TrackGetContainer getTrackMessage() {
		return trackMessage;
	}
}
