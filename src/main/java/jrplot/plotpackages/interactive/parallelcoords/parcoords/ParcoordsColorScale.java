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
package jrplot.plotpackages.interactive.parallelcoords.parcoords;

public enum ParcoordsColorScale {
	
	
	RColorBrewerBuPu{
		
		@Override
		public String getColorScaleCmd() {
			return "RColorBrewer::brewer.pal(4,\"BuPu\")[4]";
		}
	},
	
	RColorBrewer{
		
		@Override
		public String getColorScaleCmd() {
			return "RColorBrewer::brewer.pal("+String.valueOf(this.numbercolors)+",\""+this.colorbrewername+"\")["+String.valueOf(this.numbercolors)+"]";
		}
		
		@Override
		public void setColorBrewerScheme(String name, int numbercolors) {
			this.colorbrewername=name;
			this.numbercolors=numbercolors;
		}
	},
	
	D3CATEGORY10{
		@Override
		public String getColorScaleCmd() {
			return "htmlwidgets::JS(\"d3.scale.category10()\")";
		}
	},
	D3CATEGORY20{
		@Override
		public String getColorScaleCmd() {
			return "htmlwidgets::JS(\"d3.scale.category20()\")";
		}
	},
	D3CATEGORY20B{
		@Override
		public String getColorScaleCmd() {
			return "htmlwidgets::JS(\"d3.scale.category20b()\")";
		}
	},
	D3CATEGORY20C{
		@Override
		public String getColorScaleCmd() {
			return "htmlwidgets::JS(\"d3.scale.category20c()\")";
		}
	};
	

	
	
	
	public String getColorScaleCmd() {
		return getColorScaleCmd();
	}
	
	public void setColorBrewerScheme(String name, int numbercolors) {
		this.colorbrewername=name;
		this.numbercolors=numbercolors;
	}
	
	
	protected String colorbrewername = "GnBu";
	protected int numbercolors=4;
	
	
	public static ParcoordsColorScale RColorBrewerwithColorBrewerScheme(String name, int numbercolors) {
		ParcoordsColorScale colorscale=ParcoordsColorScale.RColorBrewer;
		colorscale.setColorBrewerScheme(name, numbercolors);
		return colorscale;
	}
	
	
	public static void main(String[] args) {
		ParcoordsColorScale t=ParcoordsColorScale.RColorBrewerwithColorBrewerScheme("PuBu", 5);
		
		System.out.println(t.getColorScaleCmd());
	}
	
}
