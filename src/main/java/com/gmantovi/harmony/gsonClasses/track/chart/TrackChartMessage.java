package com.gmantovi.harmony.gsonClasses.track.chart;

import com.google.gson.annotations.SerializedName;

public class TrackChartMessage {

	@SerializedName("message")
	private TrackChartContainer trackMessage;

	public void setTrackChartMessage(TrackChartContainer trackMessage) {
		this.trackMessage = trackMessage;
	}

	public TrackChartContainer getTrackChartMessage() {
		return trackMessage;
	}

}
