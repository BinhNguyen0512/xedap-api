package com.tmdt.xedap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tmdt.xedap.entity.DanhMuc;
import com.tmdt.xedap.entity.NhaCungCap;
import com.tmdt.xedap.entity.SanPham;
import com.tmdt.xedap.entity.ThuongHieu;
import com.tmdt.xedap.model.SanPhamModel;
import com.tmdt.xedap.repository.DanhMucRepository;
import com.tmdt.xedap.repository.NhaCungCapRepository;
import com.tmdt.xedap.repository.SanPhamRepository;
import com.tmdt.xedap.repository.ThuongHieuRepository;
import com.tmdt.xedap.service.SanPhamService;

@Service
public class SanPhamServiceImpl implements SanPhamService{
	
	@Autowired
	private SanPhamRepository spRepository;
	
	@Autowired
	private DanhMucRepository dmRepository;
	
	@Autowired 
	private ThuongHieuRepository thRepository;
	
	@Autowired
	private NhaCungCapRepository nccRepository;
	
	

	@Override
	public List<SanPham> getListService() {
		// TODO Auto-generated method stub
		return spRepository.findAll();
	}

	@Override
	public ResponseEntity<SanPham> getDetailSPBySlug(String slug) {
		// TODO Auto-generated method stub
		try {
			SanPham sp = spRepository.findBySlug(slug);
			
			if(sp == null) {
				return new ResponseEntity<SanPham>(new SanPham(), HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<SanPham>(sp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<SanPham>(new SanPham(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ResponseEntity<SanPham> getDetailSPByMaSp(String masp) {
		// TODO Auto-generated method stub
		try {
			SanPham sp = spRepository.findByMasp(masp);
			
			if(sp == null) {
				return new ResponseEntity<SanPham>(new SanPham(), HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<SanPham>(sp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<SanPham>(new SanPham(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> addSanPham(SanPhamModel sanpham) {
		// TODO Auto-generated method stub
		try {
			SanPham findSlugSP = spRepository.findBySlug(sanpham.getSlug());
			
			if(findSlugSP != null){
				return new ResponseEntity<String>("Tên sản phẩm đã tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			SanPham findMaSP = spRepository.findByMasp(sanpham.getMasp());
			
			if(findMaSP != null){
				return new ResponseEntity<String>("Mã sản phẩm đã tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			DanhMuc findDM = dmRepository.findByMadm(sanpham.getMadm());
			
			if(findDM == null) {
				return new ResponseEntity<String>("Mã danh mục không tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			ThuongHieu findTH = thRepository.findByMath(sanpham.getMath());

			if(findTH == null) {
				return new ResponseEntity<String>("Mã thương hiệu tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			NhaCungCap findNCC = nccRepository.findByMancc(sanpham.getMancc());
			
			if(findNCC == null) {
				return new ResponseEntity<String>("Mã nhà cung cấp tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			SanPham dataAddSP = new SanPham();
			dataAddSP.setMasp(sanpham.getMasp());
			dataAddSP.setTensp(sanpham.getTensp());
			dataAddSP.setSlug(sanpham.getSlug());
			dataAddSP.setSoluong(sanpham.getSoluong());
			dataAddSP.setDongia(sanpham.getDongia());
			dataAddSP.setChitietSP(sanpham.getChitietSP());
			dataAddSP.setImage(sanpham.getImage());
			dataAddSP.setImage2(sanpham.getImage2());
			dataAddSP.setImage3(sanpham.getImage3());
			dataAddSP.setTrangthai(sanpham.getTrangthai());
			dataAddSP.setThuonghieu(findTH);
			dataAddSP.setDanhmuc(findDM);
			dataAddSP.setNhacungcap(findNCC);
			
			
			spRepository.save(dataAddSP);
			return new ResponseEntity<String>("Thêm sản phẩm thành công!", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			
			return new ResponseEntity<String>("Đã xảy ra lỗi, thêm sản phẩm thất bại!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> deleteSanPham(String masp) {
		// TODO Auto-generated method stub
		try {
			SanPham findSP = spRepository.findByMasp(masp);
			
			if(findSP == null){
				return new ResponseEntity<String>("Mã sản phẩm không tồn tại!", HttpStatus.BAD_REQUEST);
			}
			
			spRepository.deleteById(masp);
			return new ResponseEntity<String>("Xoá sản phẩm thành công!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Đã xảy ra lỗi, xoá sản phẩm thất bại!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<SanPham> updateSP(String masp, SanPham sanpham) {
		// TODO Auto-generated method stub
		try {
			SanPham findSP = spRepository.findByMasp(masp);
			
			if(findSP  == null){
				return new ResponseEntity<SanPham>(new SanPham(), HttpStatus.BAD_REQUEST);
			}
			
			spRepository.save(sanpham);
			return new ResponseEntity<SanPham>(sanpham, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<SanPham>(new SanPham(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<SanPham> getListSanPhamByDM(String madm) {
		// TODO Auto-generated method stub
		return spRepository.getSPByMaDM(madm);
	}

	@Override
	public List<SanPham> getListSanPhamByTH(String math) {
		// TODO Auto-generated method stub
		return spRepository.getSPByMaTH(math);
	}

	@Override
	public List<SanPham> getListSanPhamByNCC(String mancc) {
		// TODO Auto-generated method stub
		return spRepository.getSPByMaNcc(mancc);
	}

	@Override
	public List<SanPham> getListSanPhamByNew() {
		// TODO Auto-generated method stub
		return spRepository.getSPByNew();
	}

	@Override
	public List<SanPham> getListSanPhamBySearch(String search) {
		// TODO Auto-generated method stub
		return spRepository.getSPBySearch(search);
	}

	@Override
	public List<SanPham> getListSanPhamBestSeller() {
		// TODO Auto-generated method stub
		return spRepository.getListBestSeller();
	}



}
