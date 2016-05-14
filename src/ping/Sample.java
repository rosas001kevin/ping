package ping;

import java.io.File;
import org.icmp4j.IcmpPingUtil;
import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

// Sample class, copyright 2009 and beyond, icmp4j
public class Sample {

// the java entry point
public static void main (final String[ ] args)
throws Exception {
        //JFreeChart chart; 
        //chart = createPlot();         
        //ChartUtilities.saveChartAsPNG(new File("imagen.png"), chart, 1000, 600);
        
        // request
        final IcmpPingRequest request = IcmpPingUtil.createIcmpPingRequest ();
        request.setHost ("www.google.com");

        final XYSeries google = new XYSeries( "Ping a google" );  
        // repeat a few times
        for (int count = 1; count <= 4; count ++) {

        // delegate
        final IcmpPingResponse response = IcmpPingUtil.executePingRequest (request);

        // log
        final String formattedResponse = IcmpPingUtil.formatResponse (response);
        System.out.println (formattedResponse);
        //Aquí tomaremos el valor de los ms y los guardaremos en un arreglo. 
        String[] splitStr = formattedResponse.split("\\s+");
        int valor = 0; 
        if(splitStr.length>5)
        {   
            //Quitamos los ms
            char[] cadenaEnChars = splitStr[4].toCharArray();             
            if(cadenaEnChars.length==8){
            valor = Integer.parseInt(Character.toString(cadenaEnChars[5])); 
            }
            else{
                valor = Integer.parseInt(Character.toString(cadenaEnChars[5])+ Character.toString(cadenaEnChars[6]));
            }
             
        } 
        else
        {
            valor = 0; 
        }
                
        google.add( count , valor );                 
        
        

        // rest
        Thread.sleep (1000);
        }
        
        final XYSeriesCollection dataset = new XYSeriesCollection( );          
        dataset.addSeries( google );    
        
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Gráfica de ping" ,
         "Ping #" ,
         "ms" ,
         dataset,
         PlotOrientation.VERTICAL ,
         true , true , false);
        
        ChartUtilities.saveChartAsPNG(new File("imagen.png"), xylineChart, 1000, 600);
        
        
        
        
    
    }
}
