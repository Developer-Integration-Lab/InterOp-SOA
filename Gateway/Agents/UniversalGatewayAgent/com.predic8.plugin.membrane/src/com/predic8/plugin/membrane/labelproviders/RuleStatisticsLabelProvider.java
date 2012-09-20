/* Copyright 2009 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

package com.predic8.plugin.membrane.labelproviders;

import java.text.NumberFormat;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.predic8.membrane.core.Router;
import com.predic8.membrane.core.rules.ForwardingRule;
import com.predic8.membrane.core.rules.Rule;
import com.predic8.membrane.core.statistics.RuleStatistics;
import com.predic8.plugin.membrane.MembraneUIPlugin;
import com.predic8.plugin.membrane.resources.ImageKeys;


public class RuleStatisticsLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider  {
	
	private NumberFormat nf = NumberFormat.getInstance();
	
	private Image ruleProxyImage;
	
	private Image ruleForwardingImage;
	
	public RuleStatisticsLabelProvider() {
		nf.setMaximumFractionDigits(3);
		ruleProxyImage =  MembraneUIPlugin.getDefault().getImageRegistry().getDescriptor(ImageKeys.IMAGE_RULE_PROXY).createImage();
		ruleForwardingImage =  MembraneUIPlugin.getDefault().getImageRegistry().getDescriptor(ImageKeys.IMAGE_RULE_REVERSE_PROXY).createImage();
	}
	
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex != 0)
			return null;
		if (element instanceof ForwardingRule) {
			return ruleForwardingImage;
		}
		return ruleProxyImage;
	}

	public String getColumnText(Object element, int columnIndex) {
		Rule rule = (Rule)element;
		
		RuleStatistics statistics = Router.getInstance().getExchangeStore().getStatistics(rule.getKey());
		String min = (statistics.getMin() < 0) ? "" : "" +statistics.getMin(); 
		String max = (statistics.getMax() < 0) ? "" : "" +statistics.getMax(); 
		String avg = (statistics.getAvg() < 0) ? "" : nf.format(statistics.getAvg()); 
		String error = (statistics.getCountError() == 0) ? "" : "" + statistics.getCountError(); 
		String bytesSent = (statistics.getBytesSent() == 0) ? "" : "" + statistics.getBytesSent(); 
		String bytesReceived = (statistics.getBytesReceived() == 0) ? "" : "" + statistics.getBytesReceived(); 
		
		switch (columnIndex) {
		case 0:
			return rule.toString();
		case 1:
			return "" + statistics.getCountTotal();
		case 2:
			return min;
		case 3:
			return max;
		case 4:
			return avg;	
		case 5:
			return bytesSent;
		case 6:
			return bytesReceived;
		case 7:
			return error;	
		default:
			throw new RuntimeException("Rule table viewer must have only 6 columns");
		}
	}

	public Color getBackground(Object element, int columnIndex) {
		
		return null;
	}

	public Color getForeground(Object element, int columnIndex) {
		
		return null;
	}

}
