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

public class CheckboxInput implements UIInputWidget{

	
	
	private String linkelemid;
	private String label;
	private boolean defaultvalue=false;
	private Integer width;
	
	
	
	
	
	
	public CheckboxInput(String linkelemid, String label) {
		super();
		this.linkelemid = linkelemid;
		this.label = label;
	}


	public CheckboxInput(String linkelemid, String label, boolean defaultvalue) {
		this.linkelemid = linkelemid;
		this.label = label;
		this.defaultvalue = defaultvalue;
	}

	public CheckboxInput(String linkelemid, String label, boolean defaultvalue, Integer width) {
		this.linkelemid = linkelemid;
		this.label = label;
		this.defaultvalue = defaultvalue;
		this.width = width;
	}


	public CheckboxInput setDefaultvalue(boolean defaultvalue) {
		this.defaultvalue = defaultvalue;
		return this;
	}


	public CheckboxInput setWidth(int width) {
		this.width = width;
		return this;
	}
	
	


	public CheckboxInput setLabel(String label) {
		this.label = label;
		return this;
	}


	@Override
	public String getUICommand() {
		StringBuilder str=new StringBuilder();
		str.append("checkboxInput(");
		str.append("\""+linkelemid+"\", ");
		str.append("\""+label+"\",");
		str.append((defaultvalue?"TRUE":"FALSE"));
		if(width!=null)
			str.append(", width = "+String.valueOf(width));
		str.append(")");
	
		return str.toString();
	}

	public static CheckboxInput createNew(String linkelemid, String label) {
		return new CheckboxInput(linkelemid, label);
	}
	
	public static CheckboxInput createNew(String linkelemid, String label, boolean defaultvalue) {
		return new CheckboxInput(linkelemid, label,defaultvalue);
	}
	
}
