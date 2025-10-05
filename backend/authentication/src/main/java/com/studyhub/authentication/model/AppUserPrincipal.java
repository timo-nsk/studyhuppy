package com.studyhub.authentication.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AppUserPrincipal implements UserDetails {

	private final AppUser appUser;
	private final Collection<? extends GrantedAuthority> authorities;

	public AppUserPrincipal(AppUser appUser, Collection<? extends GrantedAuthority> authorities1) {
		this.appUser = appUser;
		this.authorities = authorities1;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return appUser.getPassword();
	}

	@Override
	public String getUsername() {
		return appUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasAcceptedAgb() {
		return appUser.getAcceptedAgb();
	}

	public boolean hasNotificationSubscription() {
		return appUser.getNotificationSubscription();
	}
}
