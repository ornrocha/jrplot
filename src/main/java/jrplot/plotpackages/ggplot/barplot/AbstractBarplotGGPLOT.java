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
package jrplot.plotpackages.ggplot.barplot;

import jrplot.plotpackages.ggplot.RggplotFeature;
import jrplot.rbinders.components.interfaces.IDataLoader;

public abstract class AbstractBarplotGGPLOT extends RggplotFeature{

	
	protected String yaxisvar;
	protected String xaxisvar;
	protected String categoryvar;
	protected String colorpalette="scale_fill_brewer(palette=\"Dark2\")";
	protected String xlabel;
	protected String ylabel;
	protected String title;
	protected String theme="theme_classic()";
	protected String themeparameters="theme(legend.position=\"top\",legend.title = element_blank(),legend.text = element_text(size = 16, face =\"bold\") , plot.title = element_text(face =\"bold\",size = 16, hjust = 0.5), axis.text.x=element_text(face =\"bold\",size = 16),axis.text.y=element_text(size = 16,face =\"bold\"), axis.title.x=element_text(size = 16,face =\"bold\"), axis.title.y=element_text(size = 16,face =\"bold\"))";
	
	public AbstractBarplotGGPLOT(IDataLoader inputdata, String xaxisvar, String yaxisvar,String categoryvar) {
		super(inputdata);
		this.xaxisvar=xaxisvar;
		this.yaxisvar=yaxisvar;
		this.categoryvar=categoryvar;
	}
	
	
	
	public void setColorPalette(String colorpalettecommand){
		this.colorpalette=colorpalettecommand;
	}



	public void setXlabel(String xlabel) {
		this.xlabel = xlabel;
	}



	public void setYlabel(String ylabel) {
		this.ylabel = ylabel;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public void setTheme(String theme) {
		this.theme = theme;
	}



	public void setThemeparameters(String themeparameters) {
		this.themeparameters = themeparameters;
	}
	
	
	

}
