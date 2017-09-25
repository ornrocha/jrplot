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

public class ImageOutput extends PlotOutput{

	
	
	
	public ImageOutput(String linkserverimageid) {
		super(linkserverimageid);
	}
	
	
	protected String getFunctionName() {
		return "imageOutput";
	}
	
	public static ImageOutput createNew(String linkserverimageid) {
		ImageOutput plot=new ImageOutput(linkserverimageid);
		return plot;
	}
	
	
	
	public static void main(String[] args) {
		ImageOutput image=ImageOutput.createNew("io");
		image.setHeightPercentage(90).setWidthPercentage(90);
		
		System.out.println(image.getUICommand());
	}

}
