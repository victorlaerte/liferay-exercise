package com.example.luisafarias.myapplication.model;

import android.arch.lifecycle.ViewModel;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Victor Oliveira
 */
public class User extends ViewModel{

	public User(){}

	public String getName() {
		return this._name;
	}

	public String getEmail() { return this._email;}

	public String getUserId() {
		return this._userId;
	}

	public String getPassWord(){ return this._passWord;}

	public void setName(String name) {
		this._name = name;
	}

	public void setEmail(String email){ this._email = email; }

	public void setUserId(String userId) {
		this._userId = userId;
	}

	public void setPassWord(String passWord) { this._passWord = passWord;}

	private String _email;
	private String _name;
	private String _userId;
	private String _passWord;
}
