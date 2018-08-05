package com.zwp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zwp.domain.Belong;
import com.zwp.domain.User;
import com.zwp.domain.article;
import com.zwp.domain.mytype;
import com.zwp.service.ArticleService;
import com.zwp.service.BelongService;
import com.zwp.service.TypeService;

public class BelongAction extends ActionSupport{
	
	private BelongService belongService;
	private TypeService typeService;
	private ArticleService articleService;
	private String aid;
	private String[] chkValue;
	
	
	public void setBelongService(BelongService belongService) {
		this.belongService = belongService;
	}
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String[] getChkValue() {
		return chkValue;
	}
	public void setChkValue(String[] chkValue) {
		this.chkValue = chkValue;
	}
	
	//ToupdbelongView()��ToupdbelongView2()����һ�£��ϲ���
	//�������·��ࣺ
	public String ToupdbelongView(){
		//����ҳ�洫����������id
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		int aid=Integer.parseInt(id);
		
		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		//����id��ѯ����
		article article1 = articleService.findOne(aid);
		//��ѯ���еĸ��˷���
		List<mytype> typelist= typeService.findType(uid);
		//��ѯ�����µķ��ࣺ
		List<Belong> minetypeList=belongService.findBelong(aid);
		
				
		request.setAttribute("article", article1);
		request.setAttribute("typelist", typelist);
		request.setAttribute("minetypeList", minetypeList);
		
		return "ToupdbelongView";
	}
	
	//ͨ���ݸ��䵽�������·�����棺
	public String ToupdbelongView2(){
		//����ҳ�洫����������id
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		int aid=Integer.parseInt(id);
		
		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		article article1 = articleService.findOne(aid);
		List<mytype> typelist= typeService.findType(uid);
		List<Belong> minetypeList=belongService.findBelong(aid);
		
				
		request.setAttribute("article", article1);
		request.setAttribute("typelist", typelist);
		request.setAttribute("minetypeList", minetypeList);
		
		return "ToupdbelongView2";
	}
	
	
	//updbelong()��updbelongByDrafts()����һ�£��ϲ���
	//�������·���
	public String updbelong(){
		
		//��ɾ����������belong�������еļ�¼
		int id=Integer.parseInt(aid);
		belongService.deleteBelong(id);
		
		//���´������µķ���
		for(int i=0;i<this.getChkValue().length;i++)
		{
			String value=this.getChkValue()[i];
			int mtid=Integer.parseInt(value);
			mytype mytype1=new mytype();
			mytype1.setMtid(mtid);
			
			article article1=new article();
			article1.setAid(id);
	
			Belong belong=new Belong();
			belong.setArticle1(article1);
			belong.setMytype1(mytype1);
			
			belongService.addBelong(belong);
		}
		
		return "Tomanage";
	}
	
	//ͨ���ݸ���������·���
	public String updbelongByDrafts(){
		
		//��ɾ����������belong�������еļ�¼
		int id=Integer.parseInt(aid);
		belongService.deleteBelong(id);
		
		//���´������µķ���
		for(int i=0;i<this.getChkValue().length;i++)
		{
			String value=this.getChkValue()[i];
			int mtid=Integer.parseInt(value);
			mytype mytype1=new mytype();
			mytype1.setMtid(mtid);
			
			article article1=new article();
			article1.setAid(id);
	
			Belong belong=new Belong();
			belong.setArticle1(article1);
			belong.setMytype1(mytype1);
			
			belongService.addBelong(belong);
		}
		
		//��ת���ݸ�����棺
		return "ToDraftsView";
	}
	
}
