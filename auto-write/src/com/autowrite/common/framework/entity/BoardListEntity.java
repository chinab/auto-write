package com.autowrite.common.framework.entity;

import java.util.List;
import java.util.Map;

public class BoardListEntity extends PageListWrapper {
	private List<BoardEntity> boardList;
	
	public BoardListEntity(){
		totalListCount = 0;
		totalPageNum = 1;
		pageNum = 1;
		pageSize = 20;
	}

	public List<BoardEntity> getBoardList() {
		return boardList;
	}
	public void setBoardList(List<BoardEntity> boardList) {
		this.boardList = boardList;
	}
	
}
