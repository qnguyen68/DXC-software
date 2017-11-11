package dxc.technology.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dxc.technology.dao.ModuleDAO;
import dxc.technology.entity.Module;
import dxc.technology.entity.Project;

@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	ModuleDAO moduleDAO;

	@Autowired
	APIService apiService;

	@Autowired
	ProjectService projectService;

	@Override
	public void getModule(String linkAPI) throws ParseException, IOException, JSONException {

		// initializer
		List<String> listKey = getApiService().getKeyAPI(linkAPI);

		// get name module
		ArrayList<Project> listProject = getProjectService().getAll();

		for (int i = 0; i < listKey.size(); i++) {

			if (getModuleDAO().findKey(listKey.get(i)) == 1) {
				Module module = new Module();
				module.setKey(listKey.get(i)); // set key

				for (int j = 0; j < listProject.size(); j++) {
					String temp = listProject.get(j).getName().replace(" ", "-");
					if (module.getKey().contains(temp)) {
						module.setProjectId(listProject.get(j).getId()); // set id project
					}
				}

				if (listKey.get(i).contains("%3A%2F")) {
					String[] strList = listKey.get(i).split("%3A%2F");
					String[] strList2 = strList[0].toString().split("%3A");
					strList2[1] = strList2[1].replace("-", " ");
					module.setName(strList2[1]); // set name
				} else {
					String[] strList = listKey.get(i).split("%3A");
					strList[1] = strList[1].replace("-", " ");
					module.setName(strList[1]); // set name
				}

				getModuleDAO().insert(module);
			} 
		}
	}

	@Override
	public int findId(String key) {
		return getModuleDAO().getIdModule(key).get(0);
	}

	@Override
	public ArrayList<Integer> getIdModule() {
		return getModuleDAO().getIdModule();
	}

	public ModuleDAO getModuleDAO() {
		return moduleDAO;
	}

	public void setModuleDAO(ModuleDAO moduleDAO) {
		this.moduleDAO = moduleDAO;
	}

	public APIService getApiService() {
		return apiService;
	}

	public void setApiService(APIService apiService) {
		this.apiService = apiService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
}
