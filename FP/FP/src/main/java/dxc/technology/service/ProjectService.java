package dxc.technology.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.ParseException;
import org.json.JSONException;

import dxc.technology.entity.Project;

public interface ProjectService {

	public void getProject(String linkAPI) throws ParseException, IOException, JSONException;

	public ArrayList<Project> getAll();
}
