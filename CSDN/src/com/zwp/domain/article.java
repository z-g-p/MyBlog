package com.zwp.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class article{
	
	private int aid;//���µ�id
	private String title;//���±���
	private String content;//��������
	private Date date;//���·����ʱ��
	private String pic;//�ϴ���ͼƬ
	private int allow;//�Ƿ��������ۣ�1Ϊ����2Ϊ��ֹ��Ĭ��Ϊ����
	
	private State state;//���µ�״̬������ˡ������ݸ��䡢����վ
	private Type type;//ԭ������ת��
	private User user;//�����������û�
	private FirstType ftype;//��ҳ��������
	
	private Set<Belong> setBelong=new HashSet<Belong>();
	private Set<Discuss> setDiscuss=new HashSet<Discuss>();
	private Set<Reply> setReply=new HashSet<Reply>();
	
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type=type;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public FirstType getFtype() {
		return ftype;
	}
	public void setFtype(FirstType ftype) {
		this.ftype = ftype;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Set<Belong> getSetBelong() {
		return setBelong;
	}
	public void setSetBelong(Set<Belong> setBelong) {
		this.setBelong = setBelong;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Set<Discuss> getSetDiscuss() {
		return setDiscuss;
	}
	public void setSetDiscuss(Set<Discuss> setDiscuss) {
		this.setDiscuss = setDiscuss;
	}
	public int getAllow() {
		return allow;
	}
	public void setAllow(int allow) {
		this.allow = allow;
	}
	public Set<Reply> getSetReply() {
		return setReply;
	}
	public void setSetReply(Set<Reply> setReply) {
		this.setReply = setReply;
	}
}
