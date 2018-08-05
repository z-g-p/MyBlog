package com.zwp.dao;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.zwp.domain.FirstType;
import com.zwp.domain.Type;
import com.zwp.domain.mytype;

@Transactional(readOnly = false)
public class TypeDaoImpl implements TypeDao {
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}	
	
	
	//��ѯ���е���Ҫ����
	public List<FirstType> findFtag() {
		List<FirstType> list=(List<FirstType>) hibernateTemplate.find("from FirstType");
		return list;
	}
	//��ѯ���е����·���
	public List<Type> findAllType() {
		List<Type> list=(List<Type>) hibernateTemplate.find("from Type");
		return list;
	}
	//�����û�id���Ҹ��˷���
	public List<mytype> findType(int uid) {
		List<mytype> list=(List<mytype>) hibernateTemplate.find("from mytype where umid=?",uid);
		return list;
	}
	//����û��ĸ��˷��࣬��������Ҫ���û���id
	public void addmytag(mytype mtype) {
		hibernateTemplate.save(mtype);
	}
	//���ݸ��˷����Id���в�ѯ
	public mytype findOne(int mtid) 
	{
		mytype mtag=hibernateTemplate.get(mytype.class,mtid);
		return mtag;
	}
	//���ݸ������������ɾ��
	public void delmytag(mytype mtag) {
		hibernateTemplate.delete(mtag);
	}
	//�޸ĸ������
	public void updatetag(mytype mtype) {
		hibernateTemplate.update(mtype);
	}
	
	//��������ѯ����Ҫ���û���id
	public List<mytype> conditionfind(mytype mtype,int uid) {
		// 1 �������߶������ö��ĸ�ʵ������в���
		DetachedCriteria criteria = DetachedCriteria.forClass(mytype.class);
		// 2 ���ö�ʵ�����ĸ�����
		criteria.add(Restrictions.like("mytag", "%"+mtype.getMytag()+"%"));
		//������������߲�ѯ����ֱ��ʹ�����������
		criteria.add(Restrictions.eq("user.id", uid));
		// 3 ����hibernateTemplate����ķ����õ�list����
		List<mytype> list =(List<mytype>) hibernateTemplate.findByCriteria(criteria);	
	
		return list;
	}
	//���ݸ����������ƽ��в�ѯ����Ҫ�û�Id
	public List<mytype> findTypeByName(String name,int uid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(mytype.class);
		criteria.add(Restrictions.eq("mytag",name));
		criteria.add(Restrictions.eq("user.id", uid));
		List<mytype> list =(List<mytype>) hibernateTemplate.findByCriteria(criteria);	
		
		return list;
	}
}
