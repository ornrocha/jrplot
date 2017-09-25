package jrplot.plotpackages.interactive.shinywrapper.mainfunctions;

import java.util.ArrayList;

import jrplot.plotpackages.interactive.shinywrapper.shinyfeature.ShinyControlCenter;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.page.UIShinyPageLayout;
import jrplot.rbinders.components.interfaces.IDataLoader;
import jrplot.rbinders.functioncallers.AbstractRFunctionCallerSingleDataset;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

/**
 * @author orocha
 *
 */
public abstract class RShinyFeature extends AbstractRFunctionCallerSingleDataset{
	
	
	protected MainUiLayout mainlayout=MainUiLayout.fluidPage;
	protected boolean holdsession=true;

	public RShinyFeature(IDataLoader dataloader) {
		super(dataloader);
	}
	
	

	protected abstract ArrayList<RPackageInfo> loadRequiredLibraries();
	
	
	/**
	 * Ordered list of ui components to build the shiny UI layout  
	 * @return
	 */
	protected abstract UIShinyPageLayout getPageLayout();
	
	/**
	 * Get the output server functions defined in child classes to build the shiny server
	 * @return
	 */
	protected abstract ArrayList<String> getShinyServerFunctions();
	
	protected abstract boolean useStringTypeScripts();
	protected abstract String getShinyUIScript();
	protected abstract String getShinyServerScript();


	@Override
	protected ArrayList<RPackageInfo> requiredLibraries() {
		ArrayList<RPackageInfo> mandlist=new ArrayList<>();
		mandlist.add(RPackageInfo.define("shiny"));
		if(loadRequiredLibraries()!=null)
			mandlist.addAll(loadRequiredLibraries());
		return mandlist;
	}

	
	@Override
	protected void executeFunction() throws Exception {
		
		
		
		
	}
	
	

	/*@Override
	protected void executeFunction() throws Exception {
		
		String ui=null;
		String server=null;
		
		
		if(useStringTypeScripts()) {
			ui="ui <- "+getShinyUIScript();
			server="server <- "+getShinyServerScript();
		}
		else {
			ui="ui <- "+getPageLayout().getUICommand();
			server=buildServerCmds();
		}
		
		
		LogMessageCenter.getLogger().toClass(getClass()).addTraceMessage(ui);
		rsession.silentlyEval(ui);
  
		LogMessageCenter.getLogger().toClass(getClass()).addTraceMessage(server);
		rsession.silentlyEval(server);
	
		rsession.silentlyEval("runApp(list(ui = ui, server = server),launch.browser = TRUE, port = 4573)");
	}*/
	
	
	public void stopAPP() {
		//rsession.silentlyEval("stopApp(returnValue = invisible())");
		rsession.silentlyEval("stopApp()");
		//rsession.close();
		rsession.end();
	}
	
	
	/*protected String buildUICmds() {
		StringBuilder uicmd=new StringBuilder();

		ArrayList<UIShinyComponent> uicomp=getListUIShinyComponents();

		uicmd.append("ui <- "+mainlayout.toString()+"(\n");

		for (int i = 0; i < uicomp.size(); i++) {

			String uicmdline=uicomp.get(i).getUICommand();
			if(uicmdline!=null) {
				uicmd.append(uicmdline);
				if(i<(uicomp.size()-1)) {
					if(!uicmdline.endsWith(","))
						uicmd.append(",");
				}
				uicmd.append("\n");
			}
		}




		uicmd.append(")");
		return uicmd.toString();

	}*/
	
	
	protected String buildServerCmds() {
		
		StringBuilder servercmd=new StringBuilder();
		
		//servercmd.append("server <- function(input, output,session) {\n");
		servercmd.append("server <- function(input, output) {\n");
		
		ArrayList<String> serveroutputfunctions=getShinyServerFunctions();
		for (int i = 0; i < serveroutputfunctions.size(); i++) {
			
			String cmd=serveroutputfunctions.get(i);
			if(!cmd.endsWith(";"))
				cmd=cmd+";";
			servercmd.append(cmd+"\n\n");
			
		}
		
		if(!holdsession)
			servercmd.append("session$onSessionEnded(stopApp)\n");
		servercmd.append("}");
		return servercmd.toString();
	}

}
