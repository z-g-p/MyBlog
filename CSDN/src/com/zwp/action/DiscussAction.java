package com.zwp.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zwp.domain.Discuss;
import com.zwp.service.DiscussService;

public class DiscussAction extends ActionSupport implements ModelDriven<Discuss>{

	Discuss discuss=new Discuss();
	public Discuss getModel() {
		return discuss;
	}
	
	private DiscussService discussService;
	public DiscussService getDiscussService() {
		return discussService;
	}
	public void setDiscussService(DiscussService discussService) {
		this.discussService = discussService;
	}
	
	
	//�����۹�����棺
	public String toDisManagerView()
	{
		//�����ݿ��в�ѯ�������۹��������ʾ�����ݣ�
		//Ĭ����ʾ�����µ����ۣ�
		return "toDisManagerView";
	}
	
	
	//�������
	public String addDiscuss()
	{		
		//���۵�ʱ�䣺
		Date date = new Date();
        java.sql.Date sqlDate =new java.sql.Date(date.getTime());
        discuss.setDate(sqlDate);
		discussService.addDiscuss(discuss);
		
		//ȡ�����µ�id
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setAttribute("aid",discuss.getArticle().getAid());
		
		//�ص�ԭ�������½���
		return "OK";
	}
	
	//ɾ�����ۣ�
	public String delDiscuss()
	{		
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("disId");
		int disId=Integer.parseInt(id);
		
		discussService.delDiscuss(disId);
		
		String aid=request.getParameter("aid");
		request.setAttribute("aid",aid);
		//�ص�ԭ�������½���
		return "delOk";
	}
	
	
}
