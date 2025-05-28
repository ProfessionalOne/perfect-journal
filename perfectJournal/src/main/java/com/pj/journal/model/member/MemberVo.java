package com.pj.journal.model.member;

import java.util.Date;
import java.util.Objects;

public class MemberVo {
	private int userId, question;
	private String email, user, password, nickname, answer;
	private Date createdAt;
	
	public MemberVo(){}

	public MemberVo(int userId, int question, String email, String user, String password, String nickname,
			String answer, Date createdAt) {
		super();
		this.userId = userId;
		this.question = question;
		this.email = email;
		this.user = user;
		this.password = password;
		this.nickname = nickname;
		this.answer = answer;
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "RegisterVo [userId=" + userId + ", question=" + question + ", email=" + email + ", user=" + user
				+ ", password=" + password + ", nickname=" + nickname + ", answer=" + answer + ", createdAt="
				+ createdAt + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(answer, createdAt, email, nickname, password, question, user, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberVo other = (MemberVo) obj;
		return Objects.equals(answer, other.answer) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(email, other.email) && Objects.equals(nickname, other.nickname)
				&& Objects.equals(password, other.password) && question == other.question
				&& Objects.equals(user, other.user) && userId == other.userId;
	}

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
