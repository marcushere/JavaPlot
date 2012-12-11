package com.panayotis.gnuplot.plot;

import static com.panayotis.gnuplot.GNUPlotParameters.ERRORTAG;
import static com.panayotis.gnuplot.GNUPlotParameters.ERROR_VAR;

import java.util.HashMap;

import com.panayotis.gnuplot.GNUPlotParameters;
import com.panayotis.gnuplot.layout.LayoutMetrics;
import com.panayotis.*;

public class GraphWithFit extends Graph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7998507171272073222L;
	protected static final String NL = System.getProperty("line.separator");
	private HashMap<String, Axis> axis;
	private LayoutMetrics metrics;

	public GraphWithFit() {
		axis = new HashMap<String, Axis>();
		axis.put("x", new Axis("x"));
		axis.put("y", new Axis("y"));
		axis.put("z", new Axis("z"));
		metrics = null;
	}

	/**
	 * Get gnuplot commands for this graph.
	 * 
	 * @param bf
	 */
	void retrieveData(StringBuffer bf) {
		/* Do not append anything, if this graph is empty */
		if (size() == 0)
			return;

		/* Set GNUPlot to save the uncertainty in variables called varname_err */
		bf.append("set fit errorvariables").append(NL);

		/* Define fit functions */
		{
			int i = 0;
			for (Plot p : this) {
				if (p instanceof com.panayotis.gnuplot.plot.DataSetFit) {
					
					/* Set the function name if the existing one is null */
					final DataSetFit dataSetFit = (DataSetFit) p;
					if (dataSetFit.getFunctionName() == null)
						dataSetFit.setFuntionName("f" + i);
					
					/* Define the fit function */
					bf.append(dataSetFit.getFunctionName() + "("
							+ dataSetFit.getDepVarString() + ")="
							+ dataSetFit.getEquation()).append(NL);
					
					/* Set initial parameters */
					final String[] vars = dataSetFit.getVars();
					String[] initialVals = dataSetFit.getInitialVals();
					for (int j = 0; j < vars.length; j++) {
						if (initialVals != null) {
							bf.append(vars[j] + " = " + initialVals[i]).append(NL);
						} else {
							initialVals = new String[vars.length];
							bf.append(vars[j] + " = 1");
							initialVals[j] = "1";
						}
					}
					
					/* If initial parameters were not set in the DataSetPlot
					   class, set them all to be 1 */
					if (dataSetFit.getInitialVals() == null) {
						dataSetFit.setInitialVals(initialVals);
					}
				}
				i++;
			}
		}

		/* Create fits */
		for (Plot p : this) {
			if (p instanceof com.panayotis.gnuplot.plot.DataSetFit) {
				/* Set error parameter */
				bf.append(ERROR_VAR).append(" = 1").append(NL);

				/* Add fit command */
				bf.append(getFitCommand());
				
				/* Add fit definition */
				bf.append(' ');
				((DataSetFit) p).retrieveFitDefinition(bf);
				
				/* Reset error parameter. If everything OK */
				bf.append(GNUPlotParameters.NOERROR_COMMAND).append(NL); 
				
				/* Retrieve the fit data */
				p.retrieveData(bf);

				/* Retrieve the fit parameters */
				String[] vars = ((DataSetFit) p).getVarString().split(",");
				for (int i = 0; i < vars.length; i++) {
					final String label = "#fit "
							+ ((DataSetFit) p).getFunctionName();
					bf.append(label + " var " + vars[i]).append(NL);
					bf.append("print " + vars[i]).append(NL);
					bf.append(label + " error " + vars[i]).append(NL);
					bf.append("print " + vars[i] + "_err").append(NL);
				}
			}
		}

		/* Print error check */
		bf.append("if (").append(ERROR_VAR).append(" == 1) print '")
				.append(ERRORTAG).append('\'').append(NL);

		/* Set various axis parameters */
		for (Axis ax : axis.values()) {
			ax.appendProperties(bf);
		}

		/* Create data plots */
		bf.append(ERROR_VAR).append(" = 1").append(NL); // Set error parameter
		bf.append(getPlotCommand()); // Use the corresponding plot command
		
		/* Add plot definitions */
		for (Plot p : this) {
			bf.append(' ');
			p.retrieveDefinition(bf);
			bf.append(',');
		}
		bf.deleteCharAt(bf.length() - 1);
		
		/* Reset error parameter. If everything OK. */
		bf.append(GNUPlotParameters.NOERROR_COMMAND).append(NL);
																
		/* Add plot data (if any) */
		for (Plot p : this) {
			p.retrieveData(bf);
		}

		/* Print error check */
		bf.append("if (").append(ERROR_VAR).append(" == 1) print '")
				.append(ERRORTAG).append('\'').append(NL);
	}

	protected String getFitCommand() {
		return "fit";
	}

}
