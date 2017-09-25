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
package jrplot.plotpackages.interactive.shinywrapper.uicomponents.outputwidget;

public class PlotOutput implements UIOutputWidget{

	
	protected String linkplotid;
	protected String width="100%";
	protected String height="400px";
	
	

	public PlotOutput(String linkserverplotid) {
		this.linkplotid = linkserverplotid;
	}
	
	
	protected String getFunctionName() {
		return "plotOutput";
	}
	

	public PlotOutput setWidthPercentage(int percentage) {
		if(percentage>0 && percentage<=100)
			width=String.valueOf(percentage)+"%";
		return this;
	}

	public PlotOutput setHeightPercentage(int percentage) {
		if(percentage>0 && percentage<=100)
			height=String.valueOf(percentage)+"%";
		return this;
	}

	public PlotOutput setWidthPixels(int numberpix) {
		width=String.valueOf(numberpix)+"px";
		return this;
	}

	public PlotOutput setHeightPixels(int numberpix) {
		height=String.valueOf(numberpix)+"px";
		return this;
	}

	public PlotOutput setAutoWidth() {
		width="auto";
		return this;
	}

	public PlotOutput setAutoHeight() {
		height="auto";
		return this;
	}
	
	


	public String getLinkplotid() {
		return linkplotid;
	}

	


	@Override
	public String getUICommand() {
		
		StringBuilder str=new StringBuilder();
		str.append(getFunctionName()+"(");
		str.append("\""+linkplotid+"\"");
		if(width!="100%")
			str.append(", width =\""+width+"\"");
		if(height!="400px")
			str.append(", height =\""+height+"\"");
		str.append(")");
		
		return str.toString();
	}
	
	
	public static PlotOutput createNew(String linkserverplotid) {
		PlotOutput plot=new PlotOutput(linkserverplotid);
		return plot;
	}

}
