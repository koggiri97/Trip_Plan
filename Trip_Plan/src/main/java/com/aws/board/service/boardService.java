package com.aws.board.service;

import java.util.List;

import com.aws.board.dto.Criteria;
import com.aws.board.dto.boardDTO;

public interface boardService {

	//------------------------------------------------------------------------------------------------------
	// REVIEW 목록
	//------------------------------------------------------------------------------------------------------
	public List<boardDTO> boardList(Criteria cri) throws Exception;
	
	//------------------------------------------------------------------------------------------------------
	// 리뷰 등록
	//------------------------------------------------------------------------------------------------------
	public int boardRegister(boardDTO boardDTO) throws Exception;
	
	//------------------------------------------------------------------------------------------------------
	// 리뷰 상세 조회
	//------------------------------------------------------------------------------------------------------
	public boardDTO boardDetail(int seq) throws Exception;
	
	//------------------------------------------------------------------------------------------------------
	// 리뷰 수정 화면
	//------------------------------------------------------------------------------------------------------
	public int boardUpdate(boardDTO boardDTO);
	
	//------------------------------------------------------------------------------------------------------
	// 리뷰 삭제
	//------------------------------------------------------------------------------------------------------
	public int boardDelete(int seq);
	
	public int getTotal();
	
} // END - public interface boardService
