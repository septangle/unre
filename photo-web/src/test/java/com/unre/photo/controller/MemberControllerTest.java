package com.unre.photo.controller;

import org.junit.Assert;
import org.junit.Test;

import com.unre.photo.biz.response.BaseResponse;
import com.unre.photo.biz.response.MemberResponse;
import com.unre.photo.util.JsonUtil;

@SuppressWarnings("static-access")
public class MemberControllerTest extends BaseTest {

	@Test
	public void testfindMemberById() throws Exception {
		String urlSuffix = "member/current.do";
		Long id = (long)16;
		String json = "{\"MemberDto\": {\"id\": \"" + id + "\"}}";
		String result = this.postRequest(urlSuffix, json);
		Assert.assertNotNull(result);
		System.out.println(result);
		BaseResponse res = (BaseResponse) JsonUtil.toObject(result, MemberResponse.class);
		Assert.assertNull(res.getError());
	}

	//@Test
	public void testqueryMember() throws Exception {
		String urlSuffix = "member/queryMember.do";
		String memberNo = "001";
		String json = "{\"MemberDto\": {\"memberNo\": \"" + memberNo + "\"}}";
		String result = this.postRequest(urlSuffix, json);
		Assert.assertNotNull(result);
		BaseResponse res = (BaseResponse) JsonUtil.toObject(result, MemberResponse.class);
		Assert.assertNull(res.getError());

	}
	
	//@Test
	public void testlogin() throws Exception {
		String urlSuffix = "member/login.do";
		String tel = "15026979827";
		String password = "1";
		String json = "{\"MemberDto\":{\"tel\":\""+tel+"\",\"password\":\""+password+"\"}}";
		String result = this.postRequest(urlSuffix, json);
		Assert.assertNotNull(result);
		BaseResponse res = (BaseResponse) JsonUtil.toObject(result, MemberResponse.class);
		Assert.assertNull(res.getError());

	}

}
