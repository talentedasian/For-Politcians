package com.example.demo.model.userRaterNumber;

import com.example.demo.model.UserRaterNumberInterface;

public abstract class AbstractUserRaterNumber implements UserRaterNumberInterface{
	
	protected final String name;
	protected final LoginMechanism loginMechanism;
	
	public String getName() {
		return name;
	}

	public LoginMechanism getLoginMechanism() {
		return loginMechanism;
	}

	public AbstractUserRaterNumber(String name, LoginMechanism loginMechanism) {
		super();
		this.name = name;
		this.loginMechanism = loginMechanism;
	}

	@Override
	public String toString() {
		return "AbstractUserRaterNumber [name=" + name + ", loginMechanism=" + loginMechanism + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loginMechanism == null) ? 0 : loginMechanism.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AbstractUserRaterNumber other = (AbstractUserRaterNumber) obj;
		if (loginMechanism != other.loginMechanism)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
