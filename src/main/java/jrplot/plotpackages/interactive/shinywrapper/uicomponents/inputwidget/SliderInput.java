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

public class SliderInput implements UIInputWidget{

	
	private String linkid;
	private String label;
	private Number min;
	private Number max;
	private Number startvalue;
	private Number step;
	private boolean showticks=true;
	private boolean animate=false;
	private Integer width=null;
	
	
	
	
	
	public SliderInput(String linkid, 
			String label, 
			Number min, 
			Number max,
			Number startvalue,
			Number step) {
		this.linkid = linkid;
		this.label = label;
		this.min = min;
		this.max = max;
		this.startvalue=startvalue;
		this.step = step;
	}





	public SliderInput(String linkid, 
			String label, 
			Number min, 
			Number max,
			Number startvalue,
			Number step, 
			boolean showticks,
			boolean animate, Integer width) {
		this.linkid = linkid;
		this.label = label;
		this.min = min;
		this.max = max;
		this.startvalue=startvalue;
		this.step = step;
		this.showticks = showticks;
		this.animate = animate;
		this.width = width;
	}


	@Override
	public String getUICommand() {
		StringBuilder str=new StringBuilder();
		str.append("sliderInput(");
		str.append("\""+linkid+"\",");
		str.append("\""+label+"\",");
		str.append(String.valueOf(min)+",");
		str.append(String.valueOf(max)+",");
		str.append(String.valueOf(startvalue)+",");
		if(step!=null)
			str.append("step = "+String.valueOf(step)+",");
		str.append("ticks = "+(showticks?"TRUE":"FALSE")+",");
		str.append("animate = "+(animate?"TRUE":"FALSE"));
		if(width!=null)
			str.append(", width = "+String.valueOf(width));
		str.append(")");
			
		return str.toString();
	}
    
	
	
	/*sliderInput(inputId, label, min, max, value, step = NULL, round = FALSE,
			  format = NULL, locale = NULL, ticks = TRUE, animate = FALSE,
			  width = NULL, sep = ",", pre = NULL, post = NULL, timeFormat = NULL,
			  timezone = NULL, dragRange = TRUE)*/
	
	
	public String getLinkid() {
		return linkid;
	}





	public SliderInput setLinkElementid(String linkid) {
		this.linkid = linkid;
		return this;
	}





	public String getLabel() {
		return label;
	}





	public SliderInput setLabel(String label) {
		this.label = label;
		return this;
	}





	public Number getMin() {
		return min;
	}





	public SliderInput setMin(Number min) {
		this.min = min;
		return this;
	}





	public Number getMax() {
		return max;
	}





	public SliderInput setMax(Number max) {
		this.max = max;
		return this;
	}





	public Number getStep() {
		return step;
	}





	public SliderInput setStep(Number step) {
		this.step = step;
		return this;
	}





	public boolean isShowticks() {
		return showticks;
	}





	public SliderInput setShowticks(boolean showticks) {
		this.showticks = showticks;
		return this;
	}





	public boolean isAnimate() {
		return animate;
	}





	public SliderInput setAnimate(boolean animate) {
		this.animate = animate;
		return this;
	}





	public Integer getWidth() {
		return width;
	}





	public SliderInput setWidth(Integer width) {
		this.width = width;
		return this;
	}


    public static SliderInput createNew(String linkid, 
			String label, 
			Number min, 
			Number max,
			Number startvalue,
			Number step) {
    	
    	SliderInput slider=new SliderInput(linkid, label, min, max, startvalue, step);
    	return slider;
    	
    }


	
	
	public static void main(String[] args) {
		SliderInput input=new SliderInput("linktox","Slider", 0, 50, 4, 1);
		System.out.println(input.getUICommand());
		

	}

}
