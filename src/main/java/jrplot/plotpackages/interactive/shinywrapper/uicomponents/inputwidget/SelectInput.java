/************************************************************************** 
 * Orlando Rocha (ornrocha@gmail.com)
 *
 * This is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This code is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Public License for more details. 
 * 
 * You should have received a copy of the GNU Public License 
 * along with this code. If not, see http://www.gnu.org/licenses/ 
 *  
 */
package jrplot.plotpackages.interactive.shinywrapper.uicomponents.inputwidget;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jrplot.rbinders.components.interfaces.IDataFrameDataLoader;

public class SelectInput implements UIInputWidget{

	
	private String linkid;
	private String label;
	private LinkedHashMap<String, String> choices;
	private int selectedchoice;
	private boolean selectize=true;
	private Integer width=null;
	
	
	public SelectInput(String linkid, String label, LinkedHashMap<String, String> choices, int selectedchoice) {
		this.linkid = linkid;
		this.label = label;
		this.choices = choices;
		this.selectedchoice = selectedchoice;
	}


	public SelectInput(String linkid, String label, List<IDataFrameDataLoader> choices, int selectedchoice) {
		this.linkid = linkid;
		this.label = label;
		loadChoicesFromIDataLoader(choices);
		this.selectedchoice = selectedchoice;
	}




   private void loadChoicesFromIDataLoader(List<IDataFrameDataLoader> inchoices) {
	   this.choices=new LinkedHashMap<>();
	   
	   for (IDataFrameDataLoader loader : inchoices) {
		  choices.put(loader.getDataframeName(), loader.getDataframeName());
	   }
   }



	@Override
	public String getUICommand() {
		StringBuilder str=new StringBuilder();
		str.append("selectInput(");
		str.append("\""+linkid+"\",");
		str.append("\""+label+"\",");
		str.append(getListChoices()+",");
		str.append("selected ='"+getSelectedChoice()+"'");
		if(!selectize)
			str.append(", selectize = FALSE");
		if(width!=null)
			str.append(", width ="+String.valueOf(width));
		str.append(")");
		return str.toString();
	}
	
	
	public static SelectInput createNew(String linkid, String label, LinkedHashMap<String, String> choices, int selectedchoice) {
		return new SelectInput(linkid, label, choices, selectedchoice);
	}
	
	public static SelectInput createNew(String linkid, String label, List<IDataFrameDataLoader> choices, int selectedchoice) {
		return new SelectInput(linkid, label, choices, selectedchoice);
	}
	
	
	private String getListChoices() {
		StringBuilder str=new StringBuilder();
		
		str.append("list(");
		int n=0;
		for (String key : choices.keySet()) {
			str.append("'"+key+"'");
			if(n<(choices.size()-1))
				str.append(", ");
			n++;
		}
		str.append(")");
		
		return str.toString();
	}
	
	
	public String getMapChoicesList() {
		StringBuilder str=new StringBuilder();
		
		str.append("list(");
		int n=0;
		for (String key : choices.keySet()) {
			str.append("'"+key+"'="+choices.get(key));
			if(n<(choices.size()-1))
				str.append(", ");
			n++;
		}
		str.append(")");
		
		return str.toString();
	}
	
	public ArrayList<String> getChoices(){
		return new ArrayList<>(choices.keySet());
	}
	
   
	private String getSelectedChoice() {
		ArrayList<String> sel=new ArrayList<>(choices.keySet());
		return sel.get(selectedchoice);
	}

}
