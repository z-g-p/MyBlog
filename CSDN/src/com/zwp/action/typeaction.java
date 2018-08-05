package com.zwp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zwp.domain.User;
import com.zwp.domain.mytype;
import com.zwp.service.TypeService;

public class typeaction extends ActionSupport implements ModelDriven<mytype>{
	
	//ģ��������װ
	private mytype mtype = new mytype();
	public mytype getModel() {
		return mtype;
	}
	
	private TypeService typeService;
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	
	//��������mytype�Զ����ǩ
	public String findType()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		List<mytype> typelist= typeService.findType(uid);
		request.getSession().setAttribute("typelist", typelist);
		return "totypePage";
	}
	
	//����Զ����ǩ
	public String addmytag()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
		mtype.setUser(user);
		
		typeService.addmytag(mtype);
		return "totypePage1";
	}
	
	//ɾ���Զ����ǩ
	public String delmytag()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String id=request.getParameter("mtid");
		int mtid=Integer.parseInt(id);
		mytype mtag=typeService.findOne(mtid);
		if(mtag!=null)
		{	
			typeService.delmytag(mtag);
		}
		return "totypePage1";
	}
	
	//�޸��Զ����ǩ��
	public String editmytag()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
		mtype.setUser(user);
		
		typeService.updatetag(mtype);
		
		return "totypePage1";
	}
	
	
	//�������֣�������ѯ�Զ����ǩ
	public String conditionfind() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		if(mtype.getMytag()!=null && !"".equals(mtype.getMytag())) {
			//����������ƣ��������Ʋ�ѯ
			List<mytype> typelist = typeService.conditionfind(mtype,uid);
			ServletActionContext.getRequest().setAttribute("typelist", typelist);
		} else {
			//�������κ����ݣ���ѯ����
			List<mytype> typelist = typeService.findType(uid);
			ServletActionContext.getRequest().setAttribute("typelist", typelist);
		}
		return "totypePage";
	}
}
