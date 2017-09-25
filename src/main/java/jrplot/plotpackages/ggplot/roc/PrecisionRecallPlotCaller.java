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

import java.util.ArrayList;

import jrplot.rbinders.components.dataframe.DefaultDataframeContainer;
import jrplot.rbinders.components.dataframe.datatype.DoubleDataColumn;
import jrplot.rbinders.components.interfaces.IDataLoader;
import pt.ornrocha.logutils.messagecomponents.LogMessageCenter;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public class PrecisionRecallPlotCaller extends AbstractPRROCCurves{

	private String recvarname;
	private String precvarname;
	private double auc=0;
	
	
	
	
	
	public PrecisionRecallPlotCaller(IDataLoader inputdata, String recallvarname, String precisionvarname) {
		super(inputdata);
		this.precvarname=precisionvarname;
		this.recvarname=recallvarname;
		title="Precision Vs Recall";
		xlabel="Recall";
		ylabel="Precision";

	}
	
	public void setAUC(double auc){
		this.auc=auc;
	}
	
	public PrecisionRecallPlotCaller addAUC(double auc){
		setAUC(auc);
		return this;
	}
	
	public PrecisionRecallPlotCaller showAUCArea(){
		this.showAUCarea=true;
		return this;
	}
	
	
	public PrecisionRecallPlotCaller addXAxisLabel(String label){
		this.xlabel=label;
		return this;
	}
	
	public PrecisionRecallPlotCaller addYAxisLabel(String label){
		this.ylabel=label;
		return this;
	}
	
	public PrecisionRecallPlotCaller addTitle(String label){
		this.title=label;
		return this;
	}
	
	public PrecisionRecallPlotCaller setLineWidth(int width){
		this.linewidth=width;
		return this;
	}
	
	/**
	 * double value between (0,1]
	 * @param value
	 * @return
	 */
	public PrecisionRecallPlotCaller setLineTransparency(double value){
		this.linetransparency=value;
		return this;
	}
	
	public PrecisionRecallPlotCaller setggplotTheme(String theme_string){
		this.theme=theme_string;
		return this;
	}
	

	@Override
	public void configure() throws Exception {
		if(showAUCarea || (auc>0 && auc<=1)){
			colorfillsignature="auc.color.fill"+getSignature();
			inputelements.add(colorfillsignature);
			getRsession().eval(colorfillsignature+"=rgb(216,216,255,128,maxColorValue=255)");
		}
		if((auc>0 && auc<=1)){
			colortextsignature="auc.color.text"+getSignature();
			inputelements.add(colortextsignature);
			getRsession().eval(colortextsignature+"=rgb(0,0,0.6)");
		}
		
	}

	@Override
	public boolean needsConfiguration() {
		return true;
	}

	@Override
	protected ArrayList<RPackageInfo> requiredExtraLibraries() {
		return null;
	}
	
	@Override
	protected String getPlotUniqueSignatureID() {
		return "pr_"+getSignature();
	}

	@Override
	protected String[] getFunctionsToExecutePlot() {

		LogMessageCenter.getLogger().toClass(getClass()).addInfoMessage("Creating precison-recall plot with ggplot please wait...");
		String[] plcmd=new String[2];
		plcmd[0]=buildCommand();
		plcmd[1]="print("+getPlotUniqueSignatureID()+")";
		
		return plcmd;
	}


	
	private String buildCommand(){

		StringBuilder cmd=new StringBuilder();
		
		String plotdata=dataloader.getDataRSignature();
		inputelements.add(getPlotUniqueSignatureID());
		
		cmd.append(getPlotUniqueSignatureID()+"<- ggplot(data ="+plotdata+", aes(x = "+recvarname+", y = "+precvarname+"))");
		cmd.append("+ geom_line(aes(x = "+recvarname+", y = "+precvarname+"), "+getLineParameters()+")");
		cmd.append("+"+theme);
		cmd.append("+ggtitle(\""+title+"\")");
		cmd.append("+theme(plot.title = element_text(hjust = 0.5))");
		cmd.append("+scale_x_continuous(\""+xlabel+"\")");
		cmd.append("+scale_y_continuous(\""+ylabel+"\")");
		
		if(showAUCarea || (auc>0 && auc<=1))
			cmd.append("+geom_ribbon(aes(x="+recvarname+",ymin=0,ymax="+precvarname+"),fill="+colorfillsignature+")");
		 
		if(auc>0 && auc<=1){
	        	//cmd.append("+ annotate(\"segment\",x=0,xend=1,y=0,yend=1,linetype=2,color=\"grey50\")");
	        	cmd.append("+ annotate(\"text\",x=0.96,y=0.05,size=4,hjust=1,label=paste(\"AUPR =\",format("+String.valueOf(auc)+",digits=2)),col="+colortextsignature+")");
	        }
		
		return cmd.toString();
	}
	
	
	
	public static PrecisionRecallPlotCaller newplotmaker(String precisionlabel, String recalllabel, double[] precisionvalues, double[] recallvalues){
		 DoubleDataColumn preccol=DoubleDataColumn.create(precisionlabel, precisionvalues);
		 DoubleDataColumn recallcol=DoubleDataColumn.create(recalllabel, recallvalues);
		 
		 DefaultDataframeContainer dataframe=new DefaultDataframeContainer();
		 dataframe.appendDataColumnToDataframe(recallcol);
		 dataframe.appendDataColumnToDataframe(preccol);
		
		 PrecisionRecallPlotCaller prplotter=new PrecisionRecallPlotCaller(dataframe,recalllabel, precisionlabel);
		 return prplotter;
	}

	/*@Override
	protected boolean useSwingWorkerToExecution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected SwingWorker<Boolean, Void> executeFunctionWithSwingWorker() {
		// TODO Auto-generated method stub
		return null;
	}*/



}
