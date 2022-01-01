package com.example.evolvice.model;

import java.time.Duration;
import java.time.Instant;

public class EmployeeProjectModel {
	long employeeId;
	long projectId;
	Instant dateFrom;
	Instant dateTo;
	Duration fullDuration;
	long overlapedDuration;

	public EmployeeProjectModel(long employeeId, long projectId, String dateFrom, String dateTo) {
		super();
		this.employeeId = employeeId;
		this.projectId = projectId;
		this.dateFrom = Instant.parse(dateFrom + "T00:00:00.00Z");

		if (!"null".equalsIgnoreCase(dateTo))
			this.dateTo = Instant.parse(dateTo + "T23:59:59.99Z");
		else
			this.dateTo = Instant.now();
		fullDuration = Duration.between(this.dateFrom, this.dateTo);
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public Instant getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Instant dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Instant getDateTo() {
		return dateTo;
	}

	public void setDateTo(Instant dateTo) {
		this.dateTo = dateTo;
	}

	public long getOverlapedDuration() {
		return overlapedDuration;
	}

	public void setOverlapedDuration(long overlapedDuration) {
		this.overlapedDuration = overlapedDuration;
	}

	public Duration getFullDuration() {
		return fullDuration;
	}

	public void setFullDuration(Duration fullDuration) {
		this.fullDuration = fullDuration;
	}

}
