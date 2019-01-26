package com.shaunwang.livekue.service;

import java.util.LinkedList;
import java.util.Queue;

import org.joda.time.LocalTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value="singleton")
public class EstimatorService {
	static private int estServiceTime;
	static private Queue<Integer> serviceTimes = new LinkedList<Integer>();
	static private int waitTime;
	
	public EstimatorService() {
		serviceTimes.add(20);
		serviceTimes.add(20);
		serviceTimes.add(20);
		calculateServiceTime();
	}
	
	public static int getWaitTime() {
		return waitTime;
	}

	public static void setWaitTime(int waitTime) {
		EstimatorService.waitTime = waitTime;
	}
	
	

	public static int getEstServiceTime() {
		return estServiceTime;
	}

	public static void setEstServiceTime(int estServiceTime) {
		EstimatorService.estServiceTime = estServiceTime;
	}
	
	private void calculateServiceTime() {
		int sum = 0;
		int count = 0;
	    for (int i: serviceTimes) {
	        sum += i;
	        count++;
	    }
		estServiceTime = sum / count;
	}
	
	public void addServiceTime(int t) {
		serviceTimes.add(t);
		if(serviceTimes.size() > 100) {
			serviceTimes.remove();
		}
		calculateServiceTime();
	}
	
	public int calculateWaitTime(int length) {
		waitTime = length * estServiceTime;
		if(new LocalTime(8, 0).plusSeconds(waitTime).isAfter(new LocalTime(17, 0))) {
			return waitTime = -1;
		}
		return waitTime;
	}
	
}
