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
package jrplot.plotpackages.interactive.shinywrapper.shinyfeature;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.SwingWorker;

import org.apache.commons.io.FilenameUtils;
import org.math.R.Rsession;

import pt.ornrocha.fileutils.MTUDirUtils;
import pt.ornrocha.fileutils.MTUFileUtils;
import pt.ornrocha.ioutils.writers.MTUWriterUtils;
import pt.ornrocha.logutils.messagecomponents.LogMessageCenter;
import pt.ornrocha.rtools.installutils.RInstallTools;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;
import pt.ornrocha.stringutils.MTUStringUtils;
import pt.ornrocha.systemutils.MTUWindowsProcessManager;
import pt.ornrocha.systemutils.OSystemUtils;

public class ShinyControlCenter implements Runnable{

	
	private enum RunFileType{
		
		APPDIRSCRIPT,
		SOURCESCRIPT;
		
		
		public String getScriptFilePath() {
			return scriptfilepath;
		}
		
		public RunFileType setScriptFilePath(String filepath) {
			scriptfilepath=filepath;
			return this;
		}
		
		private String scriptfilepath=null;
		
	}
   
	protected String rhome;
	protected ShinyProgressChecker progressionchecker;
	protected Process p; 
	protected String ruserlibpath;
	protected String maindirapps=null;
	protected Rsession currentsession;
	protected boolean holdshinysession=true;
	protected Integer port;
	protected IShinyAppFeature shinyapp;
	protected String appdirpath;
	private RunFileType typerunfile=RunFileType.APPDIRSCRIPT;
	private ShinyMessageWorker messageworker;
	
	private String windowsprocessid=null;
	
	public ShinyControlCenter(IShinyAppFeature shinyappbuilder) {
		this.shinyapp=shinyappbuilder;
	}
	
	
	public ShinyControlCenter showDialogWarningMessages(Component parent) {
		messageworker=new ShinyMessageWorker(parent);
		return this;
	}
	
	public void setShinyAppBuilder(IShinyAppFeature shinyappbuilder) {
		this.shinyapp=shinyappbuilder;
	}

	
	public ShinyControlCenter holdShinySession(boolean hold) {
		this.holdshinysession=hold;
		return this;
	}
	
	
	

	public ShinyControlCenter setRHome(String dirRinstall) {
		this.rhome=RInstallTools.validatePath(dirRinstall);
		return this;
	}
	
	public ShinyControlCenter setPort(int port) {
		this.port=port;
		return this;
	}
	
	
	protected void configureAppDir() {
		
		String randomfoldername=MTUStringUtils.shortUUID();
		if(maindirapps==null)
			appdirpath=MTUDirUtils.makeDirectory(RInstallTools.validatePath(FilenameUtils.concat(System.getProperty("java.io.tmpdir"), randomfoldername)));
		else
			appdirpath=MTUDirUtils.makeDirectory(RInstallTools.validatePath(FilenameUtils.concat(maindirapps, randomfoldername)));
	}
	

	protected void buildAppScript() throws Exception {
		
		StringBuilder appstr=new StringBuilder();
		
		String needpackages=getRequiredLibsString();
		
		if(messageworker!=null) {
			messageworker.setMessage("Loading Data in R Environment....");
			messageworker.execute();
		}
		
		shinyapp.initRSessionEnvironment();
		
		/*if(messageworker!=null)
			messageworker.close();*/
		
		currentsession=shinyapp.getRsession();
		
		if(!needpackages.isEmpty())
			appstr.append(needpackages+"\n\n");
		
		
		String saveworkspacefilepath=setCurrentWorkspaceSavePath();
		appstr.append("load(\""+saveworkspacefilepath+"\")\n\n");
		
		ShinyLoadMode modetoload=shinyapp.getModeToLoadShiny();
		
		if(messageworker!=null)
			messageworker.setMessage("Opening Browser with R shiny app...");
		
		switch (modetoload) {
		case UIPAGELAYOUT_LISTSERVERFUNCTIONS:
			buildScript_UIPAGELAYOUT_LISTSERVERFUNCTIONS(appstr);
			break;
		case UISTRINGSCRIPT_LISTSERVERFUNCTIONS:
			buildScript_UISTRINGSCRIPT_LISTSERVERFUNCTIONS(appstr);
			break;
		case UISTRINGSCRIPT_SERVERSTINGSCRIPT:
			buildScript_UISTRINGSCRIPT_SERVERSTINGSCRIPT(appstr);
			break;
		case BUILTIN_PACKAGE_FUNCTION:
			buildScript_BUILTIN_PACKAGE_FUNCTION(appstr);
		default:
			break;
		}
		
		/*ArrayList<String> functionseval=shinyapp.getFunctionsToEvaluatePreviously();
		
		System.out.println("FUNCTIONS EVAL: "+functionseval);
		if(functionseval!=null)
			for (String func : functionseval) {
				currentsession.eval(func);
			}*/
		
		
		saveCurrentWorkspace(saveworkspacefilepath);
	}



    protected void buildScript_UIPAGELAYOUT_LISTSERVERFUNCTIONS(StringBuilder appstr) throws Exception {
    	appendPageLayout(appstr);
    	appendListServerCommands(appstr);
    	saveToAppFile(appstr);	
    }
    
    protected void buildScript_UISTRINGSCRIPT_LISTSERVERFUNCTIONS(StringBuilder appstr) throws Exception {
    	appendUIStringScript(appstr);
    	appendListServerCommands(appstr);
    	saveToAppFile(appstr);	
    }
    
    
    protected void buildScript_UISTRINGSCRIPT_SERVERSTINGSCRIPT(StringBuilder appstr) throws Exception {
    	appendUIStringScript(appstr);
    	appendStringServerScript(appstr);
    	saveToAppFile(appstr);	
    }
    
    
    protected void buildScript_BUILTIN_PACKAGE_FUNCTION(StringBuilder appstr) throws Exception {
    	
    	String packagelaunchfunction=shinyapp.getPackageShinyLaunchFunction();
    	
    	appstr.append(packagelaunchfunction);
    	saveScriptToFile(appstr, "launchApp.R");
    }
    
	
    protected void appendPageLayout(StringBuilder appstr) throws Exception {

		String shinyUICommands=shinyapp.getPageLayout().getUICommand();

		if(shinyUICommands!=null) {
			appstr.append("ui <- "+shinyUICommands);
			appstr.append("\n\n");
		}
		else
			throw new Exception("Invalid Shiny UI commands");

	}
	
	protected void appendUIStringScript(StringBuilder appstr) throws Exception {
		String shinyUICommands=shinyapp.getShinyStringUIScript();
		
		if(shinyUICommands!=null) {
			appstr.append(shinyUICommands);
			appstr.append("\n\n");
		}
		else
			throw new Exception("Invalid Shiny UI commands");
	}
	
	protected void appendListServerCommands(StringBuilder appstr) throws Exception {
		if(shinyapp.getShinyServerFunctions()!=null) {
			appstr.append("server <- "+buildServerCmds(shinyapp.getShinyServerFunctions()));
			 appstr.append("\n\n");	
		}
		else
			throw new Exception("Invalid Shiny Server commands");
	}
	
	
	protected void appendStringServerScript(StringBuilder appstr) throws Exception {
		String shinyServerCommands=shinyapp.getShinyStringServerScript();
		
		if(shinyServerCommands!=null) {
			appstr.append(shinyServerCommands);
			appstr.append("\n\n");
		}
		else
			throw new Exception("Invalid Shiny UI commands");
	}
	
	
	
	protected void saveToAppFile(StringBuilder appstr) throws IOException {
		appstr.append("shinyApp(ui = ui, server = server)");

		String filepath=FilenameUtils.concat(appdirpath, "app.R");

		LogMessageCenter.getLogger().toClass(getClass()).addTraceMessage("Shiny app script stored at: "+ filepath);

		MTUWriterUtils.writeStringWithFileChannel(appstr.toString(), filepath, 0);
		typerunfile=RunFileType.APPDIRSCRIPT.setScriptFilePath(appdirpath);
	}
	
	
	protected void saveScriptToFile(StringBuilder appstr, String namescript) throws IOException {
		String filepath=FilenameUtils.concat(appdirpath, namescript);
		
		if(OSystemUtils.isWindows())
			filepath=RInstallTools.validatePath(filepath);
		LogMessageCenter.getLogger().toClass(getClass()).addTraceMessage("Shiny script stored at: "+ filepath);
		MTUWriterUtils.writeStringWithFileChannel(appstr.toString(), filepath, 0);
		typerunfile=RunFileType.SOURCESCRIPT.setScriptFilePath(filepath);
		
	}
	
	

	protected String buildServerCmds(ArrayList<String> serveroutputfunctions) {
		
		StringBuilder servercmd=new StringBuilder();
		
		servercmd.append("server <- function(input, output) {\n");
		
		for (int i = 0; i < serveroutputfunctions.size(); i++) {
			
			String cmd=serveroutputfunctions.get(i);
			if(!cmd.endsWith(";"))
				cmd=cmd+";";
			servercmd.append(cmd+"\n\n");
			
		}
		
		if(!holdshinysession)
			servercmd.append("session$onSessionEnded(stopApp)\n");
		servercmd.append("}");
		return servercmd.toString();
	}
	
	
	
	protected String getRequiredLibsString() throws Exception {

		StringBuilder str=new StringBuilder();
		boolean validpackages=true;

		ArrayList<RPackageInfo> requiredpackages=shinyapp.getRequiredPackagesToShiny();
		ruserlibpath=shinyapp.getRuserLibsPath();

		
		if(messageworker!=null) {
			messageworker.setMessage("Verifing necessary R packages...");
			messageworker.execute();
		}
		
		if(requiredpackages!=null && requiredpackages.size()>0) {
			for (int i = 0; i < requiredpackages.size(); i++) {
				
				RPackageInfo packageinfo=requiredpackages.get(i);
				
				if(messageworker!=null)
					messageworker.setMessage("Loading/installing "+packageinfo.getPackageName()+"...wait please");
				
				boolean installed=RInstallTools.checkIfPackageIsInstalled(packageinfo, (rhome==null?RInstallTools.getSystemR_HOME():rhome), ruserlibpath);
				if(!installed) {
					if(packageinfo.isBioconductorPackage())
						installed=RInstallTools.installBioconductorPackage(packageinfo, ruserlibpath);
					else
						installed=RInstallTools.installPackage(packageinfo, ruserlibpath);
				}
				if(!installed) {
					validpackages=false;
				}
				else {
					str.append("library("+packageinfo.getPackageName()+")\n");
				}
			}
		}

		if(validpackages) {
			boolean installed=RInstallTools.checkIfPackageIsInstalled(RPackageInfo.define("shiny"), (rhome==null?RInstallTools.getSystemR_HOME():rhome), ruserlibpath);
			if(!installed) {
				installed=RInstallTools.installPackage(RPackageInfo.define("shiny"), ruserlibpath);
			}
			if(installed) {
				str.append("library(shiny)");
				return str.toString();
			}
			else
				throw new Exception("Missing essential shiny package to run Shiny APP");

		}
		else
			throw new Exception("Missing essential package to run Shiny APP");

	}
	
	
	
	


	public void stop() {
		if(OSystemUtils.isWindows() && windowsprocessid!=null) {
			MTUWindowsProcessManager.getIntance().killProcessRecursively(windowsprocessid);
			windowsprocessid=null;
		}
		if(OSystemUtils.isWindows() && windowsprocessid==null) {
			try {
				MTUWindowsProcessManager.killProcessByName("Rterm.exe");
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		else
			p.destroy();
		appdirpath=null;
	}
	
	protected String setCurrentWorkspaceSavePath() {
		return RInstallTools.validatePath(FilenameUtils.concat(appdirpath, "workspace.RData"));
	}

	protected void saveCurrentWorkspace(String pathworkspacefile) {
		currentsession.eval("save.image(\""+pathworkspacefile+"\")");
	}

	
	protected String getShinyRunCMD() {

		if(typerunfile.equals(RunFileType.APPDIRSCRIPT)) {
			if(port!=null) {
				return "shiny::runApp('"+typerunfile.getScriptFilePath()+"', launch.browser=TRUE, port="+String.valueOf(port)+")";
			}
			else
				return "shiny::runApp('"+typerunfile.getScriptFilePath()+"', launch.browser=TRUE)";
		}
		else if(typerunfile.equals(RunFileType.SOURCESCRIPT)) {
			if(OSystemUtils.isWindows())
				return "source('"+RInstallTools.validatePath(typerunfile.getScriptFilePath())+"')";
			return "source(\""+typerunfile.getScriptFilePath()+"\")";
		}
		
		return null;
	}
	
    
   // public void run() throws Exception {
	public void run(){

		try {
			configureAppDir();

			buildAppScript();

			boolean sysenvvar=false;
			String R_HOME=null;
			if(rhome!=null){
				R_HOME=rhome;
			}
			else{
				R_HOME=RInstallTools.getSystemR_HOME();
				if(R_HOME!=null)
					sysenvvar=true;
			}

			ArrayList<String> cmds=new ArrayList<>();
			if(OSystemUtils.isWindows())
				cmds.add(RInstallTools.getSystemR_HOME());
			else
				cmds.add("R");
			cmds.add("--vanilla");
			cmds.add("-e");

			String shinyruncmd=getShinyRunCMD();

			if(shinyruncmd!=null) {

				if(ruserlibpath!=null)
					cmds.add(".libPaths('"+RInstallTools.validatePath(ruserlibpath)+"');"+shinyruncmd);
				else
					cmds.add(shinyruncmd);
				//cmds.add("shiny::runApp('"+appdirpath+"')");
     
				ProcessBuilder pb= new ProcessBuilder(cmds);
				pb.directory(new File(RInstallTools.validatePath(appdirpath)));


				pb.redirectErrorStream(true);
				Map<String, String> jenv = pb.environment();
				if(R_HOME!=null && !R_HOME.isEmpty()){
					//Map<String, String> jenv = pb.environment();
					if(R_HOME!=null){
						if(!sysenvvar)
							jenv.put("R_HOME", R_HOME);
						else{
							if(!jenv.containsKey("R_HOME")){
								if(OSystemUtils.isWindows())
									R_HOME="\""+R_HOME+"\"";
								jenv.put("R_HOME", R_HOME);
							}
						}
					}
				}

				String pandocdir=RInstallTools.pandocDirInstallation();
				if( pandocdir!=null)
					jenv.put("RSTUDIO_PANDOC",  RInstallTools.validatePath(pandocdir));

				if(messageworker!=null)
					messageworker.close();
				
				p = pb.start();
				
				if(OSystemUtils.isWindows())
					windowsprocessid=MTUWindowsProcessManager.getIntance().followProcess(p);

				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						if(OSystemUtils.isWindows() && windowsprocessid!=null)
							MTUWindowsProcessManager.getIntance().killProcessRecursively(windowsprocessid);
						else
							p.destroy();
						try {
							MTUFileUtils.deleteFolder(RInstallTools.validatePath(appdirpath));
						} catch (IOException e) {
							LogMessageCenter.getLogger().addWarnMessage(e.getMessage());
						}
					}
				});

				if(progressionchecker!=null){
					progressionchecker.setInputStream(p.getInputStream());
					Thread stdout=new Thread(progressionchecker);
					stdout.run();
				}
				int exit=p.waitFor();
				System.out.println("Exit state: "+exit);


				//MTUFileUtils.deleteFolder(RInstallTools.validatePath(appdirpath));
				//currentsession.close();
				//currentsession.end();
			}

		} catch (Exception e) {
			LogMessageCenter.getLogger().addCriticalErrorMessage("Error executing R "+getClass().getSimpleName()+" Function: ", e);
		}

	}
    


	private class ShinyMessageWorker extends SwingWorker<Void, Void>{

		private ShinyMessageDialog messageviewer;

		public ShinyMessageWorker(Component parent, String initmessage) {
			messageviewer=new ShinyMessageDialog(parent,initmessage);
		}
		
		public ShinyMessageWorker(Component parent) {
			messageviewer=new ShinyMessageDialog(parent);
		}

		@Override
		protected Void doInBackground() throws Exception {
			messageviewer.display();

			return null;
		}
		
		
		
		public void close() {
			messageviewer.close();
		}

		
		public void setMessage(String message) {
			messageviewer.setMessage(message);
		}

	}





	/*public static boolean runShiny(ArrayList<String> cmds,String rhome, AbstractProcessProgressionChecker progressionchecker) throws Exception{

		boolean sysenvvar=false;
		String R_HOME=null;
		if(rhome!=null){
			R_HOME=rhome;
		}
		else{
			R_HOME=RInstallTools.getSystemR_HOME();
			if(R_HOME!=null)
				sysenvvar=true;
		}

		System.out.println(cmds);
		ProcessBuilder pb= new ProcessBuilder(cmds);

		pb.redirectErrorStream(true);
		if(R_HOME!=null && !R_HOME.isEmpty()){
			Map<String, String> jenv = pb.environment();
			if(R_HOME!=null){
				if(!sysenvvar)
					jenv.put("R_HOME", R_HOME);
				else{
					if(!jenv.containsKey(R_HOME_KEY)){
						if(OSystemUtils.isWindows())
							R_HOME="\""+R_HOME+"\"";
						jenv.put("R_HOME", R_HOME);
					}
				}
			}
		}
		Process p = pb.start();

		if(progressionchecker!=null){
			progressionchecker.setInputStream(p.getInputStream());
			Thread stdout=new Thread(progressionchecker);
			stdout.run();
		}
		int exit=p.waitFor();
		if(exit==0)
			return true;
		return false;
	}*/



}
