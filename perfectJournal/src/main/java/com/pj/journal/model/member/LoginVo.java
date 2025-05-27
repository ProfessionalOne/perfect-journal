package com.pj.journal.model.member;

import java.util.Date;
import java.util.Objects;

public class LoginVo {
	
	public LoginVo() {}
	
	private int userId, question;
	private String email,user,password,nickname,answer;
	private Date created_date;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getQuestion() {
		return question;
	}
	public void setQuestion(int question) {
		this.question = question;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	@Override
	public int hashCode() {
		return Objects.hash(answer, created_date, email, nickname, password, question, user, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginVo other = (LoginVo) obj;
		return Objects.equals(answer, other.answer) && Objects.equals(created_date, other.created_date)
				&& Objects.equals(email, other.email) && Objects.equals(nickname, other.nickname)
				&& Objects.equals(password, other.password) && question == other.question
				&& Objects.equals(user, other.user) && userId == other.userId;
	}
	@Override
	public String toString() {
		return "LoginDao [userId=" + userId + ", question=" + question + ", email=" + email + ", user=" + user
				+ ", password=" + password + ", nickname=" + nickname + ", answer=" + answer + ", created_date="
				+ created_date + "]";
	}
	public LoginVo(int userId, int question, String email, String user, String password, String nickname,
			String answer, Date created_date) {
		super();
		this.userId = userId;
		this.question = question;
		this.email = email;
		this.user = user;
		this.password = password;
		this.nickname = nickname;
		this.answer = answer;
		this.created_date = created_date;
	}
	
	
}
