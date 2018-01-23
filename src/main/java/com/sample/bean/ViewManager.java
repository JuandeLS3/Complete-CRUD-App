package com.sample.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.sample.model.Property;
import redis.clients.jedis.Jedis;

@ManagedBean 
@ViewScoped

public class ViewManager implements Serializable {
          // adding a namespace is almost necessary
        private final static String namespace = "tp2Ev2:";
	private ArrayList<Property> cacheList = new ArrayList ();
	private Property item = new Property();
	private boolean edit = false;
	private Jedis jedis;

	public ViewManager() {
		jedis = new Jedis("localhost"); // Obj redis instanciado
                /* Loading from Redis server */
                loadList();
	}

	public void add() {
		cacheList.add(item);
		System.out.println(item.getKey() + ", " + item.getValue());
		jedis.set(namespace + item.getKey(),item.getValue());
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
		jedis.set(namespace + item.getKey(),item.getValue());
		item = new Property();
		edit = false;
	}

	public void delete(Property item) {
		jedis.del(namespace + item.getKey());
		cacheList.remove(item);
	}

	private void loadList() {
		Set<String> lkeys = jedis.keys(namespace + "*");
                for(String st : lkeys){
                  st = st.substring(namespace.length());
                  item.setKey(st);
                  item.setValue(jedis.get(namespace + st));
		  cacheList.add(item);
		  item = new Property();
                }
                item.setKey("");
                item.setValue("");
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
