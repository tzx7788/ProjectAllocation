package ZhixiongTang.ProjectAllocation.api.impl;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import ZhixiongTang.ProjectAllocation.api.AdminService;

@Service("adminService#default")
public class DefaultAdminService implements AdminService {

	public Response getInformationFromAid(String aid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response loginAdmin(String aid, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response logoutStudent(String aid, String studentSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response matching(String aid, String studentSession,
			Boolean isFinished) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response getAllStudents(String aid, String studentSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response addStudent(String aid, String studentSession,
			HttpHeaders headers) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response deleteStudent(String aid, String sid, String studentSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response getAllProfessors(String aid, String studentSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response addProfessor(String aid, String studentSession,
			HttpHeaders headers) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response deleteProfessor(String aid, String pid,
			String studentSession) {
		// TODO Auto-generated method stub
		return null;
	}

}
