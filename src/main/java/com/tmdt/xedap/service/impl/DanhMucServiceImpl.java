package com.tmdt.xedap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tmdt.xedap.entity.DanhMuc;
import com.tmdt.xedap.repository.DanhMucRepository;
import com.tmdt.xedap.service.DanhMucService;

@Service
public class DanhMucServiceImpl implements DanhMucService {
	@Autowired
	private DanhMucRepository dmRepository;

	@Override
	public List<DanhMuc> getListService() {
		// TODO Auto-generated method stub
		return dmRepository.findAll();
	}

	@Override
	public ResponseEntity<DanhMuc> getDetailDanhMuc(String maDM) {
		// TODO Auto-generated method stub
		
		try {
			DanhMuc dm = dmRepository.findByMadm(maDM);
			
			if(dm == null) {
				return new ResponseEntity<DanhMuc>(new DanhMuc(), HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<DanhMuc>(dm, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<DanhMuc>(new DanhMuc(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> addDanhMuc(DanhMuc danhmuc) {
		try {
			DanhMuc findDanhMuc = dmRepository.findByMadm(danhmuc.getMadm());
			
			if(findDanhMuc!= null){
				return new ResponseEntity<String>("Mã danh mục đã tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			dmRepository.save(danhmuc);
			return new ResponseEntity<String>("Thêm danh mục thành công!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Đã xảy ra lỗi, thêm danh mục thất bại!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> deleteDanhMuc(String maDM) {
		try {
			DanhMuc findDanhMuc = dmRepository.findByMadm(maDM);
			
			if(findDanhMuc == null){
				return new ResponseEntity<String>("Mã danh mục không tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			dmRepository.deleteById(maDM);
			return new ResponseEntity<String>("Xoá danh mục thành công!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Đã xảy ra lỗi, xoá danh mục thất bại!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<DanhMuc> updateDanhMuc(String madm, DanhMuc danhmuc) {
		try {
			DanhMuc findDanhMuc = dmRepository.findByMadm(madm);
			
			if(findDanhMuc == null){
				return new ResponseEntity<DanhMuc>(new DanhMuc(), HttpStatus.BAD_REQUEST);
			}
			
			dmRepository.save(danhmuc);
			return new ResponseEntity<DanhMuc>(danhmuc, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<DanhMuc>(new DanhMuc(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
