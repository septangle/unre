package com.unre.photo.biz.dto;

import java.math.BigDecimal;

public class MemberInformationDto {
	
    private Long id; //用户id
    
	private String memberName;//用户名
    
    private String company; //公司
    
    private String industry; //行业
    
    private String LevelName;//等级名称

    private String level; //等级
        
    private BigDecimal balance;//余额
    
    private BigDecimal freezeAmount; //冻结金额
    
    private BigDecimal consumeAmount;//消费总金额   
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	
	public String getLevelName() {
		return LevelName;
	}

	public void setLevelName(String levelName) {
		LevelName = levelName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}


		


}
