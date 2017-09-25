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
package jrplot.plotpackages.ggplot.roc;

import jrplot.plotpackages.ggplot.RggplotFeature;
import jrplot.rbinders.components.interfaces.IDataLoader;

public abstract class AbstractPRROCCurves extends RggplotFeature{

	
	protected String theme="theme_classic()";
	protected String title="";
	protected String xlabel="";
	protected String ylabel="";
	protected int linewidth=1;
	protected double linetransparency=1;
	
	
	protected String colorfillsignature=null;
	protected String colortextsignature=null;
	
	protected boolean showAUCarea=true;

	public AbstractPRROCCurves(IDataLoader inputdata) {
		super(inputdata);
	}

	protected String getLineParameters(){
		String line="";
		if(linewidth>=1)
			line+=", size = "+String.valueOf(linewidth);
		if(linetransparency>0.0 && linetransparency<1.0)
			line=line+", alpha = "+String.valueOf(linetransparency);
		return line;
	}
	

	

}
