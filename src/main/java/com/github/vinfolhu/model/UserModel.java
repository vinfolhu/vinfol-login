package com.github.vinfolhu.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.github.vinfolhu.model.enums.AccountTypeEnums;
import com.github.vinfolhu.utils.JavaWebTokenUtils;

public class UserModel extends BaseObject {

	@NotNull
	@Email
	public String email;

	@NotNull
	public String name;
	
	public String sex;
	
	public String age;
	
	public String birth;
	
	public String addr;

	public AccountTypeEnums accountType;

	public AccountTypeEnums getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountTypeEnums accountType) {
		this.accountType = accountType;
	}

	@NotNull
	public String password;
	public String[] roles;

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password!=null?JavaWebTokenUtils.getMD5Value(password):password;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setEmail(String email) {
		setId(JavaWebTokenUtils.getMD5Value(email));
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
}
