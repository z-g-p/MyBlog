package com.zwp.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.zwp.domain.Discuss;
import com.zwp.domain.article;
import com.zwp.domain.mytype;

@Transactional(readOnly = false)
public class ArticleDaoImpl implements ArticleDao {
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	//�ύ����
	public void commit(article article1) {
		hibernateTemplate.save(article1);
	}
	//����id��ѯ����
	public article findOne(int aid) {
		article article1=hibernateTemplate.get(article.class, aid);
		return article1;
	}
	//�ӻ���վɾ�����£�
	public void delartilce(article article1) {
		hibernateTemplate.delete(article1);
	}
	//�޸ĸ������£�
	public void updartilce(article article1) {
		List<article> list=(List<article>) hibernateTemplate.find("from article where aid=?",article1.getAid());
		article article2=list.get(0);
		
		//��ֵ
		if(article1.getContent()!=null)
		{
			article2.setContent(article1.getContent());
		}if(article1.getDate()!=null)
		{
			article2.setDate(article1.getDate());
		}if(article1.getFtype()!=null)
		{
			article2.setFtype(article1.getFtype());
		}if(article1.getPic()!=null && !"".equals(article1.getPic()))
		{
			article2.setPic(article1.getPic());
		}if(article1.getSetBelong()!=null)
		{
			article2.setSetBelong(article1.getSetBelong());
		}if(article1.getTitle()!=null)
		{
			article2.setTitle(article1.getTitle());
		}if(article1.getType()!=null)
		{
			article2.setType(article1.getType());
		}if(article1.getUser()!=null)
		{
			article2.setUser(article1.getUser());
		}if(article1.getState()!=null){
			article2.setState(article1.getState());
		}
		
		hibernateTemplate.update(article2);
	}

	//�������С��ȴ���ˡ������£�
	public List<article> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(article.class);
		// 2 ���ö�ʵ�����ĸ�����
		criteria.add(Restrictions.eq("state.id", 1));
		// 3 ����hibernateTemplate����ķ����õ�list����
		List<article> list =(List<article>) hibernateTemplate.findByCriteria(criteria);	
		
		return list;
	}

	//���������Ѿ���������£�
	public List<article> AllUserArticle() {
		DetachedCriteria criteria = DetachedCriteria.forClass(article.class);
		criteria.add(Restrictions.eq("state.id", 3));
		List<article> list =(List<article>) hibernateTemplate.findByCriteria(criteria);	
		
		return list;
	}
	
	
	public List<article> findAllArticle(int uid) {
		String sql="select * from article where auid="+uid+" and (asid=1 or asid=3 or asid=5)";

		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		/* Session session=hibernateTemplate.getSessionFactory().openSession();
		 * ʹ�����ַ�ʽ�õ����̲߳����Զ��رգ���������������ʱ�����׳��쳣�������������򱻹���*/
		SQLQuery query=session.createSQLQuery(sql).addEntity(article.class);
		
		List<article> articleList=query.list();	
		
		return articleList;
	}
	public List<article> findDrafts(int uid) {
		String sql="select * from article where auid="+uid+" and asid=2";

		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		//Session session=hibernateTemplate.getSessionFactory().openSession();
		SQLQuery query=session.createSQLQuery(sql).addEntity(article.class);
		
		List<article> articleList=query.list();	
		
		return articleList;
	}

	public List<article> findRecycle(int uid) {
		String sql="select * from article where auid="+uid+" and asid=4";

		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		//Session session=hibernateTemplate.getSessionFactory().openSession();
		SQLQuery query=session.createSQLQuery(sql).addEntity(article.class);
		
		List<article> articleList=query.list();	
		
		return articleList;
	}
	//ƴ��SQL���,���ж�����ѯ
	public List<article> FindByTypes(String type1, String type2,int uid) {
		//or���÷��������⣺
		String sql="select * from article where auid= "+uid+" and (asid=1 or asid=3)";
		
		if(!"".equals(type1) && type1!=null)
		{
			int t1=Integer.parseInt(type1);
			sql=sql+" and aid in (select abid from belong where mbid="+t1+")";
		}
		if(!"".equals(type2) && type2!=null)
		{
			int t2=Integer.parseInt(type2);
			sql=sql+" and atid="+t2;
		}

		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		//Session session=hibernateTemplate.getSessionFactory().openSession();
		SQLQuery query=session.createSQLQuery(sql).addEntity(article.class);
		
		List<article> articleList=query.list();	
		
		return articleList;
	}

}
