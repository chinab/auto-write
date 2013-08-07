package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;

@Component
public class BoardDaoImpl implements BoardDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public void writeBoard(Map param) {
		try {
			sqlHelper.insert("board.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void blackUserWrite(Map param) {
		try {
			sqlHelper.insert("board.blackuser.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Long writeBoardWithFile(Map param) throws Exception {
		long seqId = 0;
		
		seqId = (Long) sqlHelper.insert("board.write.with.file", param);
		
		return seqId;
	}

	@Override
	public List<BoardEntity> listBoard(Map param) {
		return sqlHelper.queryForList("board.list", param);
	}

	@Override
	public Long countListBoard(Map param) {
		return (Long) sqlHelper.queryForObject("board.list.count", param);
	}

	@Override
	public BoardEntity readBoard(Map param) {
		return (BoardEntity) sqlHelper.queryForObject("board.read", param);
	}

	
	@Override
	public BoardEntity readBoardWithDeletedUser(Map param) {
		return (BoardEntity) sqlHelper.queryForObject("board.read.with.deleted.user", param);
	}
	
	
	@Override
	public BoardEntity blackUserView(Map param) {
		return (BoardEntity) sqlHelper.queryForObject("board.blackuser.read", param);
	}

	@Override
	public List<BoardEntity> listBoardByHitCount(Map param) {
		return (List<BoardEntity>)sqlHelper.queryForList("board.list.by.hit.count", param);
	}
	
	@Override
	public List<BoardEntity> listBoardByRecommendCount(Map param) {
		return (List<BoardEntity>)sqlHelper.queryForList("board.list.by.recommend.count", param);
	}

	@Override
	public List<BoardEntity> listBoardByNew(Map param) {
		return (List<BoardEntity>)sqlHelper.queryForList("board.list.by.new", param);
	}
	
	@Override
	public List<BoardEntity> listBoardByUser(Map param) {
		return (List<BoardEntity>)sqlHelper.queryForList("board.list.by.user", param);
	}
	
	@Override
	public List<AttachmentEntity> readAttachment(Map param) {
		return (List<AttachmentEntity>)sqlHelper.queryForList("board.attachment.list", param);
	}
	
	@Override
	public List<Map> getCategoryList(Map param) {
		return (List<Map>)sqlHelper.queryForList("common.menu.list.for.board.category", param);
	}

	@Override
	public void writeAttachmentFile(Map param) {
		try {
			sqlHelper.insert("board.attachment.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void writeReply(Map param) throws Exception{
		sqlHelper.insert("board.reply.write", param);
	}
	
	
	@Override
	public void modifyReply(Map param) throws Exception{
		sqlHelper.update("board.reply.update", param);
	}
	
	
	@Override
	public void deleteReply(Map param) throws Exception{
		sqlHelper.delete("board.reply.delete", param);
	}
	
	
	@Override
	public void deleteReplyByUserSeqId(Map param) throws Exception{
		sqlHelper.delete("board.reply.delete.by.UserSeqId", param);
	}
	
	
	@Override
	public List<Map> readReply(Map param) throws Exception{
		List<Map> returnList = null;
		
		returnList = sqlHelper.queryForList("board.reply.read", param);
		
		return returnList;
	}

	@Override
	public void updateHitCount(Map param) {
		sqlHelper.update("board.update.hit.count", param);
	}
	
	@Override
	public void updateRecommendCount(Map param) {
		sqlHelper.update("board.update.recommend.count", param);
	}
	
	@Override
	public void updateRejectCount(Map param) {
		sqlHelper.update("board.update.reject.count", param);
	}

	@Override
	public void modifyBoard(Map param) {
		try {
			sqlHelper.insert("board.update", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void modifyBoardAdmin(Map param) {
		try {
			sqlHelper.insert("board.admin.update", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void boardUpdateDelYn(Map param) {
		try {
			sqlHelper.insert("board.delYn.update", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void boardUpdateDelYnByUserSeqId(Map param) throws Exception{
		try {
			sqlHelper.insert("board.delYn.update.by.userSeqId", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<BoardEntity> listAllBusinessInfo(Map param) {
		return (List<BoardEntity>)sqlHelper.queryForList("board.list.all.business.info", param);
	}
	
	@Override
	public List<PaymentMasterEntity> listRecommendedBusinessInfo(Map param) {
		return sqlHelper.queryForList("board.payment.master.list", param);
	}

	@Override
	public List<BoardEntity> getRecentLineUpList(Map param) {
		return sqlHelper.queryForList("board.payment.recent.lineup.list", param);
	}

	@Override
	public List<BoardEntity> getRecentPostscriptList(Map param) {
		return sqlHelper.queryForList("board.payment.recent.postscript.list", param);
	}

	@Override
	public List<String> getFacilityNamesAsRegion(Map param) {
		return sqlHelper.queryForList("board.facility.names.list", param);
	}

	@Override
	public void insertFeedbackLog(Map param) {
		sqlHelper.insert("board.feedback.log.insert", param);
	}

	@Override
	public int countFeedbackLog(Map param) {
		return (Integer) sqlHelper.queryForObject("board.feedback.log.count", param);
	}

	@Override
	public void deleteRecentLineUp(Map param) {
		sqlHelper.insert("board.recent.lineup.delete", param);
		//sqlHelper.delete("board.recent.lineup.delete", param);
	}

	@Override
	public List<BoardEntity> listNotice(Map param) {
		return sqlHelper.queryForList("board.notice.list", param);
	}

	@Override
	public int boardJump(Map param) throws Exception {
		return sqlHelper.update("board.jump", param);
	}
	
}
