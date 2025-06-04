package com.pj.journal.model.comment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class CommentVo {
	private int commentId, postId, userId, groupId, level;
	private String nickname, content;
	private LocalDateTime createdAt, updatedAt;
	private boolean isDeleted;

	public CommentVo() {
	}

	public CommentVo(int commentId, int postId, int userId, int groupId, int level, String nickname, String content,
			LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted) {
		super();
		this.commentId = commentId;
		this.postId = postId;
		this.userId = userId;
		this.groupId = groupId;
		this.level = level;
		this.nickname = nickname;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDeleted = isDeleted;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "CommentVo [commentId=" + commentId + ", postId=" + postId + ", userId=" + userId + ", groupId=" + groupId
				+ ", level=" + level + ", nickname=" + nickname + ", content=" + content + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", isDeleted=" + isDeleted + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(commentId, content, createdAt, isDeleted, level, nickname, groupId, postId, updatedAt, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentVo other = (CommentVo) obj;
		return commentId == other.commentId && Objects.equals(content, other.content)
				&& Objects.equals(createdAt, other.createdAt) && isDeleted == other.isDeleted && level == other.level
				&& Objects.equals(nickname, other.nickname) && groupId == other.groupId && postId == other.postId
				&& Objects.equals(updatedAt, other.updatedAt) && userId == other.userId;
	}

}
