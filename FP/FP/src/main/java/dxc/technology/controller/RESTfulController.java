package dxc.technology.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dxc.technology.model.DailyData;
import dxc.technology.model.ShortData;
import dxc.technology.service.APIService;
import dxc.technology.service.DailyReportService;
import dxc.technology.service.ModuleService;
import dxc.technology.service.ProjectService;

@RestController
@RequestMapping("/api")
public class RESTfulController {

	@Autowired
	ProjectService projectService;

	@Autowired
	ModuleService moduleService;

	@Autowired
	DailyReportService dailyReportService;

	@Autowired
	APIService apiService;

	@Autowired
	private Environment env;

	@CrossOrigin
	@RequestMapping(value = "/allprojects", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public ArrayList<DailyData> showAllProjects() {
		Date date = new Date();
		return apiService.getDailyData(date);
	}

	@CrossOrigin
	@RequestMapping(value = "/updateSonarQube", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public void updateSonarQube() {
		try {
			String apiProject = env.getProperty("api.project");
			projectService.getProject(apiProject);
			moduleService.getModule(apiProject);
			dailyReportService.saveOrUpdate(apiProject, new Date());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/{columnName}/{projectId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public ArrayList<ShortData> showAllTectDebtinMonth(@PathVariable("projectId") int projectId,
			@PathVariable("columnName") String columnName) {
		return dailyReportService.getMonthlyData(projectId, columnName);
	}

}
