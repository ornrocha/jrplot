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

public class ROCurvePlotCaller extends AbstractPRROCCurves{
	
	private String tprvarname;
	private String fprvarname;
	
	private boolean referenceline=true;
	//private double auc=0;
	private double auroc=0;


	public ROCurvePlotCaller(IDataLoader inputdata, String tprvarname, String fprvarname) {
		super(inputdata);
		this.tprvarname=tprvarname;
		this.fprvarname=fprvarname;
		title="ROC Curve";
		xlabel="False Positive Rate (1-Specificity)";
		ylabel="True Positive Rate (Sensitivity)";
				
	}
	
	/*public void setAUC(double auc){
		this.auc=auc;
	}*/
	
	/*public ROCurvePlotCaller addAUC(double auc){
		setAUC(auc);
		return this;
	}*/
	
	public void setAUROC(double auroc){
		this.auroc=auroc;
	}
	
	public  ROCurvePlotCaller addAUROC(double auroc){
		setAUROC(auroc);
		return this;
	}
	
	public  ROCurvePlotCaller showReferenceLine(){
		referenceline=true;
		return this;
	}
	
	public  ROCurvePlotCaller notShowReferenceLine(){
		referenceline=false;
		return this;
	}
	
	
	public ROCurvePlotCaller addXAxisLabel(String label){
		this.xlabel=label;
		return this;
	}
	
	public ROCurvePlotCaller addYAxisLabel(String label){
		this.ylabel=label;
		return this;
	}
	
	
	public ROCurvePlotCaller addTitle(String label){
		this.title=label;
		return this;
	}
	
	public ROCurvePlotCaller setLineWidth(int width){
		this.linewidth=width;
		return this;
	}
	
	/**
	 * double value between (0,1]
	 * @param value
	 * @return
	 */
	public ROCurvePlotCaller setLineTransparency(double value){
		this.linetransparency=value;
		return this;
	}
	
	public ROCurvePlotCaller setggplotTheme(String theme_string){
		this.theme=theme_string;
		return this;
	}
	
	public ROCurvePlotCaller showAUCArea(){
		this.showAUCarea=true;
		return this;
	}
	

	@Override
	public void configure() throws Exception {
		
		if(showAUCarea || (auroc>0 && auroc<=1)){
			colorfillsignature="auc.color.fill"+getSignature();
			inputelements.add(colorfillsignature);
			getRsession().eval(colorfillsignature+"=rgb(216,216,255,128,maxColorValue=255)");
		}
		if((auroc>0 && auroc<=1)){
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
		return "roc_"+getSignature();
	}

	@Override
	protected String[] getFunctionsToExecutePlot() {
		
		LogMessageCenter.getLogger().toClass(getClass()).addInfoMessage("Creating ROC plot with ggplot please wait...");
		
		String[] plcmd=new String[2];
		plcmd[0]=buildCommand();
		plcmd[1]="print("+getPlotUniqueSignatureID()+")";
		
		return plcmd;
	}
	
	
	private String buildCommand(){
		StringBuilder cmd=new StringBuilder();
		
		String plotdata=dataloader.getDataRSignature();
		inputelements.add(getPlotUniqueSignatureID());
		
		cmd.append(getPlotUniqueSignatureID()+"<- ggplot(data ="+plotdata+", aes(x = "+fprvarname+", y = "+tprvarname+"))");
		cmd.append("+ geom_line(aes(x = "+fprvarname+", y = "+tprvarname+"), "+getLineParameters()+")");
		//cmd.append("+ geom_line(aes(x = "+fprvarname+", y = "+tprvarname+"))");
		if(referenceline)
			cmd.append("+geom_abline (intercept = 0, slope = 1)");
		cmd.append("+"+theme);
		cmd.append("+ggtitle(\""+title+"\")");
		cmd.append("+theme(plot.title = element_text(hjust = 0.5))");
		cmd.append("+scale_x_continuous(\""+xlabel+"\")");
		cmd.append("+scale_y_continuous(\""+ylabel+"\")");
		
	/*	if(auroc>0 && auroc<=1)
		cmd.append("+ annotate(\"text\",x=0.96,y=0.09,size=4,hjust=1,label=paste(\"AUROC =\",format("+String.valueOf(auroc)+",digits=2)),col="+colortextsignature+")");	*/
		
		if(showAUCarea || (auroc>0 && auroc<=1))
			cmd.append("+geom_ribbon(aes(x="+fprvarname+",ymin=0,ymax="+tprvarname+"),fill="+colorfillsignature+")");
        if(auroc>0 && auroc<=1){
        	//cmd.append("+ annotate(\"segment\",x=0,xend=1,y=0,yend=1,linetype=2,color=\"grey50\")");
        	cmd.append("+ annotate(\"text\",x=0.96,y=0.05,size=4,hjust=1,label=paste(\"AUROC =\",format("+String.valueOf(auroc)+",digits=2)),col="+colortextsignature+")");
        }
		
		return cmd.toString();
	}
	
	

	
	public static ROCurvePlotCaller newplotmaker(String tprlabel, String fprlabel, double[] tprvalues, double[] fprvalues){
		 DoubleDataColumn tprcol=DoubleDataColumn.create(tprlabel, tprvalues);
		 DoubleDataColumn fprcol=DoubleDataColumn.create(fprlabel, fprvalues);
		 
		 DefaultDataframeContainer dataframe=new DefaultDataframeContainer();
		 dataframe.appendDataColumnToDataframe(tprcol);
		 dataframe.appendDataColumnToDataframe(fprcol);
		 
		 ROCurvePlotCaller rocplotter=new ROCurvePlotCaller(dataframe, tprlabel, fprlabel);
		 return rocplotter;
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
	}
*/


}
