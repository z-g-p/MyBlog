package com.zwp.action;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zwp.domain.Belong;
import com.zwp.domain.Discuss;
import com.zwp.domain.FirstType;
import com.zwp.domain.Reply;
import com.zwp.domain.State;
import com.zwp.domain.Type;
import com.zwp.domain.User;
import com.zwp.domain.article;
import com.zwp.domain.mytype;
import com.zwp.service.ArticleService;
import com.zwp.service.BelongService;
import com.zwp.service.DiscussService;
import com.zwp.service.ReplyService;
import com.zwp.service.TypeService;
import com.zwp.util.UploadUtil;

public class Articleaction extends ActionSupport implements ModelDriven<article>{
	
	article article1=new article();
	public article getModel() {
		return article1;
	}
	
	//�������µ�ͼƬ��
	private File upload;
	private String uploadFileName;
	
	//���ո��˷����ı����ֵ��
	private String newmtype;
	
	//������ѡ�������
	private String type1;
	private String type2;
	
	private ReplyService replyService;
	private ArticleService articleService;
	private TypeService typeService;
	private BelongService belongService;
	private DiscussService discussService;
	
	
	public void setDiscussService(DiscussService discussService) {
		this.discussService = discussService;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	public void setBelongService(BelongService belongService) {
		this.belongService = belongService;
	}
	public String getNewmtype() {
		return newmtype;
	}
	public void setNewmtype(String newmtype) {
		this.newmtype = newmtype;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	
	//������£�
	public String commit() throws IOException
	{	
		//�ϴ�ͼƬ������ͼƬ��url
		UploadUtil upLoadUtil=new UploadUtil();
		String url=upLoadUtil.uploadFile(upload,uploadFileName,"article",article1.getUser().getUid());
		//��ͼƬ��url�浽���ݿ��ֶ���
		article1.setPic(url);

        //���´�����ʱ�䣺
        Date date = new Date();
        java.sql.Date sqlDate =new java.sql.Date(date.getTime());
        article1.setDate(sqlDate);
        
        //�������µ�״̬��1Ϊ���
        //�˴�����ֱ����ҳ������һ�����صĵ�hidden�ύ��ģ�Ͷ����У�
        State state=new State();
        state.setStateId(1);
        
        article1.setState(state);
        
        articleService.commit(article1);
        
        //�������º����Ĺ�����
        String[] stringList=this.getNewmtype().split(",");
        //�����ظ���������ƣ�
        List list=Arrays.asList(stringList);
        Set set=new HashSet(list);
        String[] mtypeList=(String[]) set.toArray(new String[0]);
        
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
        
        for(int i=0;i<mtypeList.length;i++)
        {
        	//�������ֲ�ѯ�����
        	String name=mtypeList[i];
        	List<mytype> list1=typeService.findTypeByName(name,user.getUid());
        	if(!(list1.isEmpty()))
        	{	//���ڣ�ֱ�ӽ�������
        		mytype mytype1=list1.get(0);
        		
        		Belong belong=new Belong();
        		belong.setArticle1(article1);
        		belong.setMytype1(mytype1);
        		belongService.addBelong(belong);
        	}else{
        		//�����ڣ��ȴ�������ٽ�������
        		mytype mytype1=new mytype();
        		mytype1.setMytag(name);
        		mytype1.setUser(user);
        		typeService.addmytag(mytype1);
        		
        		Belong belong=new Belong();
        		belong.setArticle1(article1);
        		belong.setMytype1(mytype1);
        		
        		belongService.addBelong(belong);
        	}
        }
		//��ת�����¹�����棺
		return "Tomanage";
	}
	
	//drafts()��commit()�������һ�£��ϲ���
	//�����±��浽�ݸ��䣺
	public String drafts() throws IOException
	{	
		UploadUtil upLoadUtil=new UploadUtil();
		String url=upLoadUtil.uploadFile(upload,uploadFileName,"article",article1.getUser().getUid());
		article1.setPic(url);

        Date date = new Date();
        java.sql.Date sqlDate =new java.sql.Date(date.getTime());
        article1.setDate(sqlDate);
        
        //�������µ�״̬��2�ǲݸ���
        State state=new State();
        state.setStateId(2);
        
        article1.setState(state);
        
        articleService.commit(article1);
        
        
        //�����ݸ������º����Ĺ�����
        String[] stringList=this.getNewmtype().split(",");
        //�����ظ������
        List l=Arrays.asList(stringList);
        Set set=new HashSet(l);
        String[] mtypeList=(String[]) set.toArray(new String[0]);
        
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
        
        for(int i=0;i<mtypeList.length;i++)
        {
        	//�������ֲ�ѯ�����
        	String name=mtypeList[i];
        	List<mytype> list1=typeService.findTypeByName(name,user.getUid());
        	if(!(list1.isEmpty()))
        	{	//����
        		mytype mytype1=list1.get(0);
        		
        		Belong belong=new Belong();
        		belong.setArticle1(article1);
        		belong.setMytype1(mytype1);
        		belongService.addBelong(belong);
        	}else{
        		//������
        		mytype mytype1=new mytype();
        		mytype1.setMytag(name);
        		mytype1.setUser(user);
        		typeService.addmytag(mytype1);
        		
        		
        		Belong belong=new Belong();
        		belong.setArticle1(article1);
        		belong.setMytype1(mytype1);
        		
        		belongService.addBelong(belong);
        	}
        }
 
        //�ص��ݸ���ҳ��
		return "Todrafts";
	}
	
	
	//ȡ��ɾ���������·Żزݸ���
	public String cancelDel() throws IOException
	{	
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		int aid=Integer.parseInt(id);
		
		article article1 = articleService.findOne(aid);
		
        //���»ָ���ʱ�䣺
        Date date = new Date();
        java.sql.Date sqlDate =new java.sql.Date(date.getTime());
        article1.setDate(sqlDate);
        //����ݸ��䣺id:2
        State state=new State();
        state.setStateId(2);
        article1.setState(state);
        
        //�޸ģ�ʱ���״̬��
        articleService.updartilce(article1);
     
        //�ص�����վҳ��
		return "cancelsuccess";
	}
	
	
	//ɾ�����£��ŵ�����վ��
	public String takeTorecycle() throws IOException
	{	
		int aid=0;
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		aid=Integer.parseInt(id);
		
		article article1 = articleService.findOne(aid);
		
        //����ɾ����ʱ�䣺
        Date date = new Date();
        java.sql.Date sqlDate =new java.sql.Date(date.getTime());
        article1.setDate(sqlDate);
        
        //�������վ��id:4
        State state=new State();
        state.setStateId(4);
        article1.setState(state);
        	
        //�޸�
        articleService.updartilce(article1);
        
		return "Tomanage";
	}

	//takeTorecycle��takeTorecycleByDrafts()������ȫһ������ת��ҳ�治һ�����ϲ�
	//ɾ���ݸ壺�ŵ�����վ��
	public String takeTorecycleByDrafts() throws IOException
	{	
		int aid=0;
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		aid=Integer.parseInt(id);
		
		article article1 = articleService.findOne(aid);
		
        //����ɾ����ʱ�䣺
        Date date = new Date();
        java.sql.Date sqlDate =new java.sql.Date(date.getTime());
        article1.setDate(sqlDate);
        
        //�������վ��id:4
        State state=new State();
        state.setStateId(4);
        article1.setState(state);
        	
        //�޸�
        articleService.updartilce(article1);
        
        //��ת���ݸ���
		return "todraftsview";
	}
	
	
	//����ɾ�����£�
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
		
		//����ɾ�����£���Ȼ�ص�����վҳ��
		return "delsuccess";
	}
		
	
	//�����¹������
	public String manage(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//��ȡ�û���uid
		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		List<Type> typelist=typeService.findAllType();
		//���û�id����ȥ�������û�id��ѯ��
		List<mytype> mytypelist=typeService.findType(uid);
		List<article> articleList= articleService.findAllArticle(uid);
		
		request.getSession().setAttribute("articleList", articleList);
		request.getSession().setAttribute("typelist", typelist);
		request.getSession().setAttribute("mytypelist", mytypelist);
		
		return "manageView";
	}
	
	
	//���ݸ������
	public String todraftsView(){
		HttpServletRequest request = ServletActionContext.getRequest();

		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		List<article> articleList= articleService.findDrafts(uid);
		
		request.getSession().setAttribute("articleList", articleList);
		
		return "todraftsView";
	}
	
	
	//������վ����
	public String torecycleView(){
		HttpServletRequest request = ServletActionContext.getRequest();

		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		List<article> articleList= articleService.findRecycle(uid);
		
		request.getSession().setAttribute("articleList", articleList);
		
		return "torecycleView";
	}
	
	
	//Toupdarticle��ToAddArticleByDrafts��������һ������תҳ�治һ�����ϲ���
	//������ҳ�棺
	public String Toupdarticle()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		int aid=Integer.parseInt(id);
		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		//�������е��������ͣ�
		//�������е���ҳ��ǩ���ͣ�
		article article1 = articleService.findOne(aid);
		List<FirstType> ftaglist= typeService.findFtag();
		List<Type> tlist= typeService.findAllType();
		List<mytype> typelist= typeService.findType(uid);
		List<Belong> minetypeList=belongService.findBelong(aid);
		
		String minetext="";
		for(Belong bl:minetypeList)
		{
			String add=bl.getMytype1().getMytag();
			if("".equals(minetext))
			{
				minetext=minetext+add;
			}else{
				minetext=minetext+","+add;
			}
			
		}
	
		request.setAttribute("minetext", minetext);
		request.setAttribute("ftaglist", ftaglist);
		request.setAttribute("tlist", tlist);
		request.setAttribute("typelist", typelist);
		request.setAttribute("article", article1);
		request.setAttribute("minetypeList", minetypeList);
		
		//��ת������ҳ��
		return "toUpdateView";
	}
	
	
	//���ݸ������ҳ�棺
	public String ToAddArticleByDrafts()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		int aid=Integer.parseInt(id);
		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		//�������е��������ͣ�
		//�������е���ҳ��ǩ���ͣ�
		article article1 = articleService.findOne(aid);
		List<FirstType> ftaglist= typeService.findFtag();
		List<Type> tlist= typeService.findAllType();
		List<mytype> typelist= typeService.findType(uid);
		List<Belong> minetypeList=belongService.findBelong(aid);
		
		String minetext="";
		for(Belong bl:minetypeList)
		{
			String add=bl.getMytype1().getMytag();
			if("".equals(minetext))
			{
				minetext=minetext+add;
			}else{
				minetext=minetext+","+add;
			}
			
		}
	
		request.setAttribute("minetext", minetext);
		request.setAttribute("ftaglist", ftaglist);
		request.setAttribute("tlist", tlist);
		request.setAttribute("typelist", typelist);
		request.setAttribute("article", article1);
		request.setAttribute("minetypeList", minetypeList);
		
		return "AddArticleByDraftsView";
	}
	
	
	//updartilce��updDraft�������ݻ���һ�����ϲ���
	//�������£�
	public String updartilce()
	{	
		UploadUtil upLoadUtil=new UploadUtil();
		String url=upLoadUtil.uploadFile(upload,uploadFileName,"article",article1.getUser().getUid());
		article1.setPic(url);
		
        //��������Ϊ���״̬��
        State state=new State();
        state.setStateId(1);
        
        article1.setState(state);
        
        articleService.updartilce(article1);	
        
        //�������º����Ĺ�����
        String[] stringList=this.getNewmtype().split(",");
        //�����ظ������
        List l=Arrays.asList(stringList);
        Set set=new HashSet(l);
        String[] mtypeList=(String[]) set.toArray(new String[0]);
        
        //�������·���֮ǰ����ɾ��֮ǰ���·���ļ�¼
        belongService.deleteBelong(article1.getAid());
        
        HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
        
        for(int i=0;i<mtypeList.length;i++)
        {	
        	//�������ֲ�ѯ�����
        	String name=mtypeList[i];
        	List<mytype> list1=typeService.findTypeByName(name,user.getUid());
        	if(!(list1.isEmpty()))
        	{	//������ڣ�����ø���
        		mytype mytype1=list1.get(0);
        		
        		Belong belong=new Belong();
        		belong.setArticle1(article1);
        		belong.setMytype1(mytype1);
        		belongService.addBelong(belong);
        	}else{
        		//���˷��಻���ڣ��򴴽��µĸ��˷��࣬�ڽ������µķ���
        		mytype mytype1=new mytype();
        		mytype1.setMytag(name);
        		mytype1.setUser(user);
        		typeService.addmytag(mytype1);
        		
        		Belong belong=new Belong();
        		belong.setArticle1(article1);
        		belong.setMytype1(mytype1);
        		
        		belongService.addBelong(belong);
        	}
        }
        
		return "Tomanage";
	}
	
	//���²ݸ�����������£�
	public String updDraft()
	{	
		UploadUtil upLoadUtil=new UploadUtil();
		String url=upLoadUtil.uploadFile(upload,uploadFileName,"article",article1.getUser().getUid());
		article1.setPic(url);
   
        //��������Ϊ���״̬��
        State state=new State();
        state.setStateId(2);
        
        article1.setState(state);
        
        articleService.updartilce(article1);	
        
        //�������º����Ĺ�����
        String[] stringList=this.getNewmtype().split(",");
        //�����ظ������
        List l=Arrays.asList(stringList);
        Set set=new HashSet(l);
        String[] mtypeList=(String[]) set.toArray(new String[0]);
        
        //�������·���֮ǰ����ɾ��֮ǰ���·���ļ�¼
        belongService.deleteBelong(article1.getAid());
        
        HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
        
        for(int i=0;i<mtypeList.length;i++)
        {	
        	//�������ֲ�ѯ�����
        	String name=mtypeList[i];
        	List<mytype> list1=typeService.findTypeByName(name,user.getUid());
        	if(!(list1.isEmpty()))
        	{	//������ڣ�����ø���
        		mytype mytype1=list1.get(0);
        		
        		Belong belong=new Belong();
        		belong.setArticle1(article1);
        		belong.setMytype1(mytype1);
        		belongService.addBelong(belong);
        	}else{
        		//���˷��಻���ڣ��򴴽��µĸ��˷��࣬�ڽ������µķ���
        		mytype mytype1=new mytype();
        		mytype1.setMytag(name);
        		mytype1.setUser(user);
        		typeService.addmytag(mytype1);
        		
        		Belong belong=new Belong();
        		belong.setArticle1(article1);
        		belong.setMytype1(mytype1);
        		
        		belongService.addBelong(belong);
        	}
        }
        
        //��ת���ݸ������
		return "Todrafts";
	}
	
	
	//�������ͽ��в�ѯ��
	public String FindByTypes(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
		
		List<Type> typelist=typeService.findAllType();
		List<mytype> mytypelist=typeService.findType(user.getUid());
		
		
		if(type1==""&&type2=="")
		{//�����Ϊ�գ���ѯ��������
			List<article> articleList= articleService.findAllArticle(user.getUid());
			request.getSession().setAttribute("articleList", articleList);
		}else{
			//���򣬸�������ѯ
			List<article> articleList=articleService.FindByTypes(type1,type2,user.getUid());
			request.getSession().setAttribute("articleList", articleList);
		}
		
		request.getSession().setAttribute("typelist", typelist);
		request.getSession().setAttribute("mytypelist", mytypelist);
		request.setAttribute("type1", type1);
		request.setAttribute("type2", type2);
		
		return "manageView";
	}
	
	
	//�����¹ۿ�ҳ��:
	public String SelectOne()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		int aid=Integer.parseInt(id);
		
		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		
		//�������е��������ͣ�
		//�������е���ҳ��ǩ���ͣ�
		article article1 = articleService.findOne(aid);
		List<FirstType> ftaglist= typeService.findFtag();
		List<Type> tlist= typeService.findAllType();
		List<mytype> typelist= typeService.findType(uid);
		List<Belong> minetypeList=belongService.findBelong(aid);
		//��ѯ���е�����
		List<Discuss> discussList=discussService.findArticleDis(aid);
		//�鿴�������еĻظ�
		List<Reply> replyList=replyService.findReply(aid);
		
		request.setAttribute("ftaglist", ftaglist);
		request.setAttribute("tlist", tlist);
		request.setAttribute("typelist", typelist);
		request.setAttribute("article", article1);
		request.setAttribute("minetypeList", minetypeList);
		request.setAttribute("discussList", discussList);
		request.setAttribute("replyList", replyList);
		
		return "ToSeeView";
	}

	//������Ա���¹ۿ�ҳ��:
	public String mSeeOne()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("aid");
		int aid=Integer.parseInt(id);
		
		String id2=request.getParameter("uid");
		int uid=Integer.parseInt(id2);
		
		//�������е��������ͣ�
		//�������е���ҳ��ǩ���ͣ�
		article article1 = articleService.findOne(aid);
		List<Type> tlist= typeService.findAllType();
		List<mytype> typelist= typeService.findType(uid);
		List<Belong> minetypeList=belongService.findBelong(aid);
		//��ѯ���е�����
		List<Discuss> discussList=discussService.findArticleDis(aid);
		
		request.setAttribute("tlist", tlist);
		request.setAttribute("typelist", typelist);
		request.setAttribute("article", article1);
		request.setAttribute("minetypeList", minetypeList);
		request.setAttribute("discussList", discussList);
		
		return "ToManageSeeView";
	}
	
	//���ҵĲ��ͽ��棬�ɹۿ��Լ����е����£�
	public String toMyblok()
	{
		//�����û� 
		HttpServletRequest request=ServletActionContext.getRequest();
		User user=(User) request.getSession().getAttribute("user");
		int uid=user.getUid();
		//���Ҹ��û�������
		List<article> articleList=articleService.findAllArticle(uid);
		List<mytype> typelist= typeService.findType(uid);
		
		request.setAttribute("articleList", articleList);
		request.setAttribute("typelist", typelist);
		
		return "toMyblok";
	}
	
	//��������ҳ�����Թۿ������û���������£�
	public String toFirstView()
	{
		//�����û� 
		HttpServletRequest request=ServletActionContext.getRequest();
		
		//���������û��ķ��������
		List<article> articleList=articleService.AllUserArticle();
		
		request.setAttribute("articleList", articleList);
		
		return "toFirstView";
	}

}
