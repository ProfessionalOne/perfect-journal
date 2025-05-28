package com.pj.journal.model.comment;

import java.util.Date;
import java.util.Objects;

public class CommentVo {
	
	public CommentVo() {}
	
	private int commentId,PostId,UserId,ord,level;
	private String content;
	private Date createdAt,updatedAt;
	private boolean isDeleted;
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getPostId() {
		return PostId;
	}
	public void setPostId(int postId) {
		PostId = postId;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Override
	public int hashCode() {
		return Objects.hash(PostId, UserId, commentId, content, createdAt, isDeleted, level, ord, updatedAt);
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
		return PostId == other.PostId && UserId == other.UserId && commentId == other.commentId
				&& Objects.equals(content, other.content) && Objects.equals(createdAt, other.createdAt)
				&& isDeleted == other.isDeleted && level == other.level && ord == other.ord
				&& Objects.equals(updatedAt, other.updatedAt);
	}
	@Override
	public String toString() {
		return "CommentVo [commentId=" + commentId + ", PostId=" + PostId + ", UserId=" + UserId + ", order=" + ord
				+ ", level=" + level + ", content=" + content + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", isDeleted=" + isDeleted + "]";
	}
	public CommentVo(int commentId, int postId, int userId, int ord, int level, String content, Date createdAt,
			Date updatedAt, boolean isDeleted) {
		super();
		this.commentId = commentId;
		PostId = postId;
		UserId = userId;
		this.ord = ord;
		this.level = level;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDeleted = isDeleted;
	}
	
	
}
