package com.pj.journal.model.board;

import java.util.Date;
import java.util.Objects;

public class BoardVo {
	private int postId, userId, views;
	private String nickname, title, content, image;
	private Date createdAt, updatedAt;
	private boolean isLocked;

	public BoardVo() {
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public BoardVo(int postId, int userId, int views, String nickname, String title, String content, String image,
			Date createdAt, Date updatedAt, boolean isLocked) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.views = views;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.image = image;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isLocked = isLocked;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, createdAt, image, isLocked, nickname, postId, title, updatedAt, userId, views);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardVo other = (BoardVo) obj;
		return Objects.equals(content, other.content) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(image, other.image) && isLocked == other.isLocked
				&& Objects.equals(nickname, other.nickname) && postId == other.postId
				&& Objects.equals(title, other.title) && Objects.equals(updatedAt, other.updatedAt)
				&& userId == other.userId && views == other.views;
	}

	@Override
	public String toString() {
		return "BoardVo [postId=" + postId + ", userId=" + userId + ", views=" + views + ", nickname=" + nickname
				+ ", title=" + title + ", content=" + content + ", image=" + image + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", isLocked=" + isLocked + "]";
	}

}
