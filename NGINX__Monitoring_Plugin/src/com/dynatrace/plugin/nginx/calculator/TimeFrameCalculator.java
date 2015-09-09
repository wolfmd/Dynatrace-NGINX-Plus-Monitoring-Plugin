package com.dynatrace.plugin.nginx.calculator;

import com.dynatrace.plugin.nginx.dto.NginxStatus;

public class TimeFrameCalculator {
	public double calculateTimeFrame(NginxStatus prev, NginxStatus cur) {
		return cur.getMeta().getTimestamp() - prev.getMeta().getTimestamp();
	}
}
