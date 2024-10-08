package com.tmdt.xedap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tmdt.xedap.entity.DanhMuc;
import com.tmdt.xedap.entity.NhaCungCap;
import com.tmdt.xedap.repository.NhaCungCapRepository;
import com.tmdt.xedap.service.NhaCungCapService;


@Service
public class NhaCungCapServiceImpl implements NhaCungCapService{
	
	@Autowired
	private NhaCungCapRepository nccRepository;

	@Override
	public List<NhaCungCap> getListService() {
		// TODO Auto-generated method stub
		return nccRepository.findAll();
	}

	@Override
	public ResponseEntity<NhaCungCap> getDetailNCC(String mancc) {
		try {
			NhaCungCap ncc = nccRepository.findByMancc(mancc);
			
			if(ncc == null) {
				return new ResponseEntity<NhaCungCap>(new NhaCungCap(), HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<NhaCungCap>(ncc, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<NhaCungCap>(new NhaCungCap(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> addNCC(NhaCungCap nhacungcap) {
		try {
			NhaCungCap findNCC = nccRepository.findByMancc(nhacungcap.getMancc());
			
			if(findNCC!= null){
				return new ResponseEntity<String>("Mã nhà cung cấp đã tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			nccRepository.save(nhacungcap);
			return new ResponseEntity<String>("Thêm nhà cung cấp thành công!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Đã xảy ra lỗi, thêm nhà cung cấp thất bại!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> deleteNCC(String mancc) {
		try {
			NhaCungCap findNCC = nccRepository.findByMancc(mancc);
			
			if(findNCC == null){
				return new ResponseEntity<String>("Mã nhà cung cấp không tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			nccRepository.deleteById(mancc);
			return new ResponseEntity<String>("Xoá nhà cung cấp thành công!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Đã xảy ra lỗi, xoá danh mục thất bại!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<NhaCungCap> updateNCC(String mancc, NhaCungCap nhacungcap) {
		try {
			NhaCungCap findNCC = nccRepository.findByMancc(mancc);
			
			if(findNCC  == null){
				return new ResponseEntity<NhaCungCap>(new NhaCungCap(), HttpStatus.BAD_REQUEST);
			}
			
			nccRepository.save(nhacungcap);
			return new ResponseEntity<NhaCungCap>(nhacungcap, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<NhaCungCap>(new NhaCungCap(), HttpStatus.BAD_REQUEST);
		}
	}

}
