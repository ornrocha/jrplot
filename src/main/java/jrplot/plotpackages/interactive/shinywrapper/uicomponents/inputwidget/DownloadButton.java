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

public class DownloadButton implements UIInputWidget{

	
	private String linkelemid;
	private String label;
	
	
	public DownloadButton() {
		
	}
	
	
	public DownloadButton(String linkelemid, String label) {
		this.linkelemid = linkelemid;
		this.label = label;
	}

	
	public String getLinkelemid() {
		return linkelemid;
	}


	public DownloadButton setLinkElementid(String linkelemid) {
		this.linkelemid = linkelemid;
		return this;
	}


	public String getLabel() {
		return label;
	}


	public DownloadButton setLabel(String label) {
		this.label = label;
		return this;
	}


	@Override
	public String getUICommand() {
		StringBuilder str=new StringBuilder();
		str.append("downloadButton(");
		str.append("\""+linkelemid+"\",");
		str.append("\""+label+"\"");
		str.append(")");
			
		return str.toString();
	}
	
	
	public static DownloadButton createNew(String linkelemid, String label) {
		return new DownloadButton(linkelemid, label);
	}

}
