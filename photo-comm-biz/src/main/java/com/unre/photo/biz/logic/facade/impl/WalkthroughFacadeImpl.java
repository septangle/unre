package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.PublicScanDto;
import com.unre.photo.biz.logic.core.IWalkthroughBiz;
import com.unre.photo.biz.logic.facade.IWalkthroughFacade;
import com.unre.photo.biz.response.WalkthroughResponse;
@Service
public class WalkthroughFacadeImpl implements IWalkthroughFacade{

	@Autowired
	private IWalkthroughBiz iwalkthroughBiz;
	@Override
	public WalkthroughResponse getPubilcScan() throws Exception {
		List<PublicScanDto> publicScanLIst=iwalkthroughBiz.findPublicScanList();
		WalkthroughResponse walkthroughResponse = new WalkthroughResponse();
		walkthroughResponse.setPublicScanList(publicScanLIst);
		return walkthroughResponse;
	}

}
