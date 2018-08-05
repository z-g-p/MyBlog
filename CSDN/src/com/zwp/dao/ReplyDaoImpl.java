package com.zwp.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.zwp.domain.Reply;
import com.zwp.domain.article;

@Transactional(readOnly = false)
public class ReplyDaoImpl implements ReplyDao {
	
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	//�ظ��û����ۣ�
	public void addreply(Reply reply) {
		hibernateTemplate.save(reply);
	}

	//��������id�������Ե�����
	public List<Reply> findReply(int aid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Reply.class);
		criteria.add(Restrictions.eq("article.id", aid));
		List<Reply> list =(List<Reply>) hibernateTemplate.findByCriteria(criteria);	
		return list;
	}

	
}
