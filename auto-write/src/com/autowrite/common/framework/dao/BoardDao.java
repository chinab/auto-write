package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;

public interface BoardDao {

	public abstract void writeBoard(Map param);
	
	public abstract void blackUserWrite(Map param);
	
	public abstract List<BoardEntity> listBoard(Map param);
	
	public abstract List<BoardEntity> listBoardByHitCount(Map param);
	
	public abstract List<BoardEntity> listBoardByRecommendCount(Map param);
	
	public abstract BoardEntity readBoard(Map param);

	public abstract List<Map> getCategoryList(Map param);

	public abstract Long writeBoardWithFile(Map param) throws Exception;

	public abstract void writeAttachmentFile(Map param);

	public abstract void writeReply(Map param) throws Exception;

	public abstract void modifyReply(Map param) throws Exception;

	public abstract void deleteReply(Map param) throws Exception;

	public abstract List<Map> readReply(Map param) throws Exception;

	public abstract void updateHitCount(Map param);

	public abstract void updateRecommendCount(Map param);
	
	public abstract void updateRejectCount(Map param);

	public abstract void modifyBoard(Map param);

	public abstract void boardUpdateDelYn(Map param);

	public abstract List<BoardEntity> listBoardByNew(Map param);

	public abstract List<BoardEntity> listBoardByUser(Map param);

	public abstract List<AttachmentEntity> readAttachment(Map param);

	public abstract Long countListBoard(Map param);

	public abstract BoardEntity blackUserView(Map param);

	public abstract BoardEntity readBoardWithDeletedUser(Map param);

	public abstract List<BoardEntity> listAllBusinessInfo(Map param);

	public abstract List<PaymentMasterEntity> listRecommendedBusinessInfo(Map param);

	public abstract List<BoardEntity> getRecentLineUpList(Map param);

	public abstract List<BoardEntity> getRecentPostscriptList(Map param);

	public abstract void modifyBoardAdmin(Map param);

	public abstract List<String> getFacilityNamesAsRegion(Map param);

	public abstract void insertFeedbackLog(Map param);

	public abstract int countFeedbackLog(Map param);

	public abstract void deleteRecentLineUp(Map param);

	public abstract List<BoardEntity> listNotice(Map param);

	public abstract void deleteReplyByUserSeqId(Map param) throws Exception;

	public abstract void boardUpdateDelYnByUserSeqId(Map param) throws Exception;

	public abstract int boardJump(Map param) throws Exception;
}
