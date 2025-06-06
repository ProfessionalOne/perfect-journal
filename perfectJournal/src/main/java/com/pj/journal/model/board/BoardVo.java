package com.pj.journal.model.board;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class BoardVo {

	private int postId, userId, views;
	private String nickname, title, content, image;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private boolean isTimeCapsule;
	private Integer duration;
	private Integer isLocked = 0;
	private LocalDate releaseDate;

	public BoardVo() {
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

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public Integer getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isTimeCapsule() {
		return isTimeCapsule;
	}

	public void setTimeCapsule(boolean isTimeCapsule) {
		this.isTimeCapsule = isTimeCapsule;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public BoardVo(int postId, int userId, int views, String nickname, String title, String content, String image,
			LocalDateTime createdAt, LocalDateTime updatedAt, Integer isLocked, boolean isTimeCapsule, Integer duration,
			LocalDate releaseDate) {
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
		this.isTimeCapsule = isTimeCapsule;
		this.duration = duration;
		this.releaseDate = releaseDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, createdAt, duration, image, isLocked, isTimeCapsule, nickname, postId, releaseDate,
				title, updatedAt, userId, views);
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
				&& Objects.equals(duration, other.duration) && Objects.equals(image, other.image)
				&& isLocked == other.isLocked && isTimeCapsule == other.isTimeCapsule
				&& Objects.equals(nickname, other.nickname) && postId == other.postId
				&& Objects.equals(releaseDate, other.releaseDate) && Objects.equals(title, other.title)
				&& Objects.equals(updatedAt, other.updatedAt) && userId == other.userId && views == other.views;
	}

	@Override
	public String toString() {
		return "BoardVo [postId=" + postId + ", userId=" + userId + ", views=" + views + ", nickname=" + nickname
				+ ", title=" + title + ", content=" + content + ", image=" + image + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", isLocked=" + isLocked + ", isTimeCapsule=" + isTimeCapsule
				+ ", duration=" + duration + ", releaseDate=" + releaseDate + "]";
	}

}
