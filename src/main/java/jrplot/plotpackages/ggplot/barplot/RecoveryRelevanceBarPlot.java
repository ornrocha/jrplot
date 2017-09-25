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

import java.util.ArrayList;

import jrplot.rbinders.components.interfaces.IDataFrameDataLoader;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public class RecoveryRelevanceBarPlot extends AbstractBarplotGGPLOT{

	
	private String stderrorvar;

	
	public RecoveryRelevanceBarPlot(IDataFrameDataLoader inputdata, String xaxisvar, String yaxisvar,String categoryvar) {
		super(inputdata,xaxisvar,yaxisvar,categoryvar);
	}

	public RecoveryRelevanceBarPlot(IDataFrameDataLoader inputdata, String xaxisvar, String yaxisvar,String categoryvar, String stderrorvar) {
		this(inputdata,xaxisvar,yaxisvar,categoryvar);
		this.stderrorvar=stderrorvar;
	}
	
	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean needsConfiguration() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ArrayList<RPackageInfo> requiredExtraLibraries() {
		ArrayList<RPackageInfo> xtralibs=new ArrayList<>();
		xtralibs.add(RPackageInfo.define("RColorBrewer"));
		return xtralibs;
	}
	
	
	@Override
	protected String getPlotUniqueSignatureID() {
		return "recrel_"+getSignature();
	}

	@Override
	protected String[] getFunctionsToExecutePlot() {
		
		String[] plcmd=new String[2];
		plcmd[0]=buildPlotCmds();
		plcmd[1]="print("+getPlotUniqueSignatureID()+")";
		return plcmd;
	}
	
	
	protected String buildPlotCmds(){
		
		StringBuilder cmd=new StringBuilder();
		
		String data=dataloader.getDataRSignature();
		
		inputelements.add(getPlotUniqueSignatureID());
		
		
		cmd.append(getPlotUniqueSignatureID()+"<- ggplot(data ="+data+", aes(x = factor("+data+"$"+xaxisvar+"), y = "+data+"$"+yaxisvar+",fill = factor("+data+"$"+categoryvar+")))+");
		cmd.append(colorpalette+"+");
		cmd.append("geom_bar(stat = \"identity\", aes(fill = "+data+"$"+categoryvar+"), position = \"dodge\")+");
		if(stderrorvar!=null){
			String flimitname="limit"+getSignature();
			inputelements.add(flimitname);
			rsession.silentlyVoidEval(flimitname+" <-function(x) {"
					+ " x[x>1] <-1; "
					+ "x[x<0] <-0; "
					+ "return(x) }");
			
			
			cmd.append("geom_errorbar(aes(ymin="+flimitname+"("+data+"$"+yaxisvar+"-"+data+"$"+stderrorvar+"), ymax="+flimitname+"("+data+"$"+yaxisvar+"+"+data+"$"+stderrorvar+")),width=.2,position=position_dodge(.9))+");
		}
		cmd.append("scale_y_continuous(limits = c(0, 1), breaks=seq(0, 1, by=0.1))+");
		if(xlabel!=null)
			cmd.append("xlab(\""+xlabel+"\")+");
		if(ylabel!=null)
			cmd.append("ylab(\""+ylabel+"\") +");
		if(title!=null)
			cmd.append("ggtitle(\""+title+"\")+");
		
		cmd.append(theme);
		if(themeparameters!=null){
			cmd.append("+");
			cmd.append(themeparameters);
			
		}
		
		return cmd.toString();
	}
	
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*@Override
	protected boolean useSwingWorkerToExecution() {
		return false;
	}

	@Override
	protected SwingWorker<Boolean, Void> executeFunctionWithSwingWorker() {
		// TODO Auto-generated method stub
		return null;
	}*/


}
