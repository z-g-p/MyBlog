package com.zwp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zwp.domain.Manage;
import com.zwp.domain.State;
import com.zwp.domain.User;
import com.zwp.domain.article;
import com.zwp.service.ArticleService;
import com.zwp.service.ManageService;
import com.zwp.service.UserService;

public class ManageAction extends ActionSupport{
	
	private String musername;
	private String mpassword;
	
	private UserService userService;
	private ManageService manageService;
	private ArticleService articleService;
	
	
	public String getMusername() {
		return musername;
	}
	public void setMusername(String musername) {
		this.musername = musername;
	}
	public String getMpassword() {
		return mpassword;
	}
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}
	public void setManageService(ManageService manageService) {
		this.manageService = manageService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	
	public String login(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//�ж������Ƿ�Ϊ��
		if("".equals(musername) || "".equals(mpassword))
		{
			request.getServletContext().setAttribute("error", "�ʻ������¼���벻��Ϊ��");
			return "login";
		}else{
			//�����ݿ������֤�û��Ƿ��и��û���
			Manage manage=new Manage();
			manage.setMusername(musername);
			manage.setMpassword(mpassword);
			
			Manage m=manageService.find(manage);
			if(m!=null)
			{	//���ڣ���ת������Ա���棺
				request.getSession().setAttribute("manage",m);
				return "mloginsuccess";
			}else{
				//�����ڣ���ת����½���棺
				request.getServletContext().setAttribute("error", "�ʻ������¼�������");
				return "login";
			}
		}
	}

	
	//�����û�����
	public String manageUser()
	{
		//��ѯ���е��û���
		List<User> userList=userService.findAllUser();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("userList", userList);
		
		return "ManageUserView";
	}
	
	//ɾ���û�
	public String delUser()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String id= request.getParameter("uid");
		int uid=Integer.parseInt(id);
		userService.delUser(uid);
		
		//�����û��������
		return "toManageUserView";
	}
	
	//�������½��棺
	public String manageArticle()
	{
		//��ѯ���е����� 
		List<article> articleList=articleService.findAll();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("articleList", articleList);
		return "ManageArticleView";
		
	}

	//ɾ�����£�
	public String delartilce()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		int aid=Integer.parseInt(id);
		
		article article1 = articleService.findOne(aid);
		if(article1!=null)
		{	
			articleService.delartilce(article1);
		}
		
		//ɾ������,�ص����¹�����ҳ
		return "delsuccess";
	}
	
	//passArticle()��dispassArtilce()����һ�����ϲ���
	//ͨ�����
	public String passArticle(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String id= request.getParameter("aid");
		int aid=Integer.parseInt(id);
		article article=articleService.findOne(aid);
		
        //����Ϊ����״̬��
        State state=new State();
        state.setStateId(3);
        
        article.setState(state);
		
        articleService.updartilce(article);
        
		return "IfPass";
	}
	
	//��˲�ͨ��
	public String dispassArtilce(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String id= request.getParameter("aid");
		int aid=Integer.parseInt(id);
		article article=articleService.findOne(aid);
		
        //����Ϊ����״̬��
        State state=new State();
        state.setStateId(5);
        
        article.setState(state);
		
        articleService.updartilce(article);
        
		return "IfPass";
	}
	
}
