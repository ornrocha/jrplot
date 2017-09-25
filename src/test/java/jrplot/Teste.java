package jrplot;

import org.rosuda.REngine.Rserve.RserveException;

import jrplot.rbinders.components.dataframe.DefaultDataframeContainer;
import jrplot.rbinders.components.dataframe.datatype.DoubleDataColumn;
import pt.ornrocha.logutils.MTULogLevel;
import pt.ornrocha.logutils.messagecomponents.LogMessageCenter;
import pt.ornrocha.rtools.connectors.RConnector;

public class Teste {

	public static void main(String[] args) throws RserveException {
		
		 LogMessageCenter.getLogger().setLogLevel(MTULogLevel.DEBUG);
		 
		 
		 DefaultDataframeContainer d=new DefaultDataframeContainer();
		 d.appendDataColumnToDataframe(DoubleDataColumn.create("c1", new double[]{3.4,3.5,6.7}));
		 d.appendDataColumnToDataframe(DoubleDataColumn.create("c2", new double[]{2.3,4.5,6.7}));
		 
		 d.loadData(RConnector.getSessionTryMode());
	}

}
