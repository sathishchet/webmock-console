package com.chet.webmock.console.relay;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Relay {
	@Id
	@GeneratedValue
	private int id;
	@Column
	private int count;
	@Column
	private String name;
	@Column
	private int rand;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the rand
	 */
	public int getRand() {
		return rand;
	}

	/**
	 * @param rand
	 *            the rand to set
	 */
	public void setRand(int rand) {
		this.rand = rand;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "{name:" + name + ",id:" + id + ",rand" + rand + ",count:" + count + "}";
	}

}
