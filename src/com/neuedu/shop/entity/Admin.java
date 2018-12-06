package com.neuedu.shop.entity;

public class Admin {
	private Integer id;
	private String aname;
	private String apwd;
	
	public Admin() {

	}

	public Admin(String sname, String spwd) {
		super();
		this.aname = sname;
		this.apwd = spwd;
	}
	public Admin(Integer id, String sname, String spwd) {
		this(sname, spwd);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String sname) {
		this.aname = sname;
	}

	public String getSpwd() {
		return apwd;
	}

	public void setSpwd(String spwd) {
		this.apwd = spwd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((aname == null) ? 0 : aname.hashCode());
		result = prime * result + ((apwd == null) ? 0 : apwd.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (aname == null) {
			if (other.aname != null)
				return false;
		} else if (!aname.equals(other.aname))
			return false;
		if (apwd == null) {
			if (other.apwd != null)
				return false;
		} else if (!apwd.equals(other.apwd))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", sname=" + aname + ", spwd=" + apwd + "]";
	}
	
}
