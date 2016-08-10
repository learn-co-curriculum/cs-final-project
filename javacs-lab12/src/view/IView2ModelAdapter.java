package view;

import java.util.List;
import java.util.Map.Entry;

public interface IView2ModelAdapter {

	public List<Entry<String, Integer>> search(String term);
}
