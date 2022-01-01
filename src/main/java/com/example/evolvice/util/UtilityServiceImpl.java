package com.example.evolvice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.evolvice.model.EmployeeProjectModel;

@Service
public class UtilityServiceImpl implements IUtilityService {

	@Override
	public void processFile(MultipartFile file) throws IOException {
		List<String> lines = readFromInputStream(file.getInputStream());
		List<EmployeeProjectModel> allEmployeeProjects = new ArrayList<>();
		Set<Long> projectIds = new HashSet<>();
		parseFile(lines, allEmployeeProjects, projectIds);
		setOverlapedDuration(allEmployeeProjects);
		sortEmployeesList(allEmployeeProjects);
		printingResults(allEmployeeProjects, projectIds);
	}

	private void parseFile(List<String> lines, List<EmployeeProjectModel> models, Set<Long> projectIds) {
		for (int i = 0; i < lines.size(); i++) {
			String row = lines.get(i);
			String[] cells = row.split(",");
			if (cells[0] != null && cells[1] != null && cells[2] != null) {

				EmployeeProjectModel model = new EmployeeProjectModel(Long.parseLong(cells[0]),
						Long.parseLong(cells[1]), cells[2], cells[3]);

				// Adding project IDs
				projectIds.add(model.getProjectId());
				models.add(model);
			}

		}
	}

	private List<String> readFromInputStream(InputStream inputStream) throws IOException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines;
	}

	private void setOverlapedDuration(List<EmployeeProjectModel> models) {
		for (EmployeeProjectModel m1 : models) {
			for (EmployeeProjectModel m2 : models) {
				if (m1.getProjectId() == m2.getProjectId()) {
					if(m1.getEmployeeId() == m2.getEmployeeId())
						continue;
					long overlapedValue = calculateOverlapedPeriodBetweenTwoObjects(m1, m2);

					if (overlapedValue > m1.getOverlapedDuration())
						m1.setOverlapedDuration(overlapedValue);
					if (overlapedValue > m2.getOverlapedDuration())
						m2.setOverlapedDuration(overlapedValue);
				}
			}
		}
	}

	private long calculateOverlapedPeriodBetweenTwoObjects(EmployeeProjectModel model1, EmployeeProjectModel model2) {
		long overlapedValue = 0;
		if ((!model2.getDateFrom().isBefore(model1.getDateFrom()) && !model2.getDateTo().isAfter(model1.getDateTo()))

				|| (model2.getDateFrom().isAfter(model1.getDateFrom())
						&& !model2.getDateTo().isAfter(model1.getDateTo()))) {
			
			long duration1 = Duration.between(model1.getDateFrom(), model1.getDateTo()).toDays();
			long duration2 = Duration.between(model2.getDateFrom(), model2.getDateTo()).toDays();
			if ((model1.getDateFrom().equals(model2.getDateFrom()) || model1.getDateTo().equals(model2.getDateTo()))
					|| (model1.getDateFrom().isBefore(model2.getDateFrom()) && model1.getDateTo().isAfter(model2.getDateTo()))
					|| (model2.getDateFrom().isBefore(model1.getDateFrom()) && model2.getDateTo().isAfter(model1.getDateTo())))
				overlapedValue = duration1 <= duration2 ? duration1 : duration2;
			else
				overlapedValue = Math.abs(Duration.between(model1.getDateFrom(), model1.getDateTo()).toDays()
						- Duration.between(model2.getDateFrom(), model2.getDateTo()).toDays());
		} else if (model2.getDateFrom().isAfter(model1.getDateFrom())
				&& model2.getDateFrom().isBefore(model1.getDateTo())
				&& model2.getDateTo().isAfter(model1.getDateTo())) { // case of
																		// intersection
			Duration overlapedDuration = Duration.between(model2.getDateFrom(), model1.getDateTo());
			overlapedValue = overlapedDuration.toDays();
		}

		return overlapedValue;
	}

	private void sortEmployeesList(List<EmployeeProjectModel> models) {
		Comparator<EmployeeProjectModel> compareByProjectThenEmployeeThenPeriod = Comparator
				.comparing(EmployeeProjectModel::getProjectId)
				// .thenComparing(EmployeeProjectModel::getEmployeeId)
				.thenComparing(EmployeeProjectModel::getOverlapedDuration).reversed();

		Collections.sort(models, compareByProjectThenEmployeeThenPeriod);

	}

	private void printingResults(List<EmployeeProjectModel> models, Set<Long> projectIds) {
		for (Long l : projectIds) {
			System.out.println("The most pair are working together in project (" + l + ") are:");
			models.stream().filter(e -> e.getProjectId() == l).findFirst().ifPresent(e -> System.out
					.println("1) Employee : " + e.getEmployeeId() + " Period: " + e.getOverlapedDuration() +" Days."));
			models.stream().filter(e -> e.getProjectId() == l).skip(1).findAny().ifPresent(e -> System.out
					.println("2) Employee : " + e.getEmployeeId() + " Period: " + e.getOverlapedDuration() +" Days."));
		}
	}

}
