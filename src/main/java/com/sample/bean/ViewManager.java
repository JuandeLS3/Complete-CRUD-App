package com.sample.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.sample.model.Property;
import redis.clients.jedis.Jedis;

@ManagedBean 
@ViewScoped

public class ViewManager implements Serializable {

	ArrayList<Property> cacheList = new ArrayList ();
	private Property item = new Property();
	private boolean edit = false;
	private Jedis jedis;

	public ViewManager() {
		jedis = new Jedis("localhost"); // Obj redis instanciado
	}

	public void add() {
		cacheList.add(item);
		System.out.println(item.getKey() + ", " + item.getValue());
		jedis.set(item.getKey(),item.getValue());
		//jedis.get(item.getKey());
		item = new Property();
	}

	public void edit(Property item) {
		this.item = item;
		edit = true;
	}

	public boolean isEdit() {
		return edit;
	}

	public void save() {
		jedis.set(item.getKey(),item.getValue());
		item = new Property();
		edit = false;
	}

	public void delete(Property item) {
		jedis.del(item.getKey());
		cacheList.remove(item);
	}

	public List getCacheList() {
	return cacheList;
	}

	public Property getItem() {
		return item;
	}

	public void setItem(Property item){
		this.item=item;
	}
}
