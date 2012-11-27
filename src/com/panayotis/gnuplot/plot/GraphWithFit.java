package com.panayotis.gnuplot.plot;

import static com.panayotis.gnuplot.GNUPlotParameters.ERRORTAG;
import static com.panayotis.gnuplot.GNUPlotParameters.ERROR_VAR;

import java.util.HashMap;

import com.panayotis.gnuplot.GNUPlotParameters;
import com.panayotis.gnuplot.layout.LayoutMetrics;

public class GraphWithFit extends Graph {

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
		bf.append(GNUPlotParameters.NOERROR_COMMAND).append(NL); // Reset error
																	// parameter.
																	// if
																	// everything
																	// OK
		/* Add plot data (if any) */
		for (Plot p : this) {
			p.retrieveData(bf);
		}

		/* Print error check */
		bf.append("if (").append(ERROR_VAR).append(" == 1) print '")
				.append(ERRORTAG).append('\'').append(NL);
	}

}
