package com.jaf.examples.expert.lesson1.q2;

import java.util.Set;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
public class MyInfo {

	private Set<String> enabledFeature;
	private boolean enabled;

	public Set<String> getEnabledFeature() {
		return enabledFeature;
	}

	public void setEnabledFeature(Set<String> enabledFeature) {
		this.enabledFeature = enabledFeature;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
