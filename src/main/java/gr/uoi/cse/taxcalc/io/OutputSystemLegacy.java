package gr.uoi.cse.taxcalc.io;

import gr.uoi.cse.taxcalc.data.Database;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.Dialog.ModalExclusionType;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;

@Deprecated
public class OutputSystemLegacy {
	
	public static void saveUpdatedTaxpayerTxtInputFile(String filePath, int taxpayerIndex){
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(new FileOutputStream(filePath));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening: "+filePath);
		}
		
		Taxpayer taxpayer = Database.getTaxpayerByIndex(taxpayerIndex);
		outputStream.println("Name: "+taxpayer.getName());
		outputStream.println("AFM: "+taxpayer.getAFM());
		outputStream.println("Status: "+taxpayer.getFamilyStatus());
		outputStream.println("Income: "+taxpayer.getIncome());
		
		if (taxpayer.getReceiptsArrayList().size() > 0){
			outputStream.println();
			outputStream.println("Receipts:");
			outputStream.println();
			
			for (Receipt receipt : taxpayer.getReceiptsArrayList()){
				outputStream.println("receipts ID: "+receipt.getId());
				outputStream.println("Date: "+receipt.getDate());
				outputStream.println("Kind: "+receipt.getKind());
				outputStream.println("Amount: "+receipt.getAmount());
				outputStream.println("Company: "+receipt.getCompany().getName());
				outputStream.println("Country: "+receipt.getCompany().getCountry());
				outputStream.println("City: "+receipt.getCompany().getCity());
				outputStream.println("Street: "+receipt.getCompany().getStreet());
				outputStream.println("Number: "+receipt.getCompany().getNumber());
				outputStream.println();
			}
		}
		
		outputStream.close();
	}
	
	public static void saveUpdatedTaxpayerXmlInputFile(String filePath, int taxpayerIndex){
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(new FileOutputStream(filePath));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening: "+filePath);
		}
		
		Taxpayer taxpayer = Database.getTaxpayerByIndex(taxpayerIndex);
		outputStream.println("<Name> "+taxpayer.getName()+" </Name>");
		outputStream.println("<AFM> "+taxpayer.getAFM()+" </AFM>");
		outputStream.println("<Status> "+taxpayer.getFamilyStatus()+" </Status>");
		outputStream.println("<Income> "+taxpayer.getIncome()+" </Income>");
		
		if (taxpayer.getReceiptsArrayList().size() > 0){
			outputStream.println();
			outputStream.println("<Receipts>");
			outputStream.println();
			
			for (Receipt receipt : taxpayer.getReceiptsArrayList()){
				outputStream.println("<ReceiptID> "+receipt.getId()+" </ReceiptID>");
				outputStream.println("<Date> "+receipt.getDate()+" </Date>");
				outputStream.println("<Kind> "+receipt.getKind()+" </Kind>");
				outputStream.println("<Amount> "+receipt.getAmount()+" </Amount>");
				outputStream.println("<Company> "+receipt.getCompany().getName()+" </Company>");
				outputStream.println("<Country> "+receipt.getCompany().getCountry()+" </Country>");
				outputStream.println("<City> "+receipt.getCompany().getCity()+" </City>");
				outputStream.println("<Street> "+receipt.getCompany().getStreet()+" </Street>");
				outputStream.println("<Number> "+receipt.getCompany().getNumber()+" </Number>");
				outputStream.println();
			}
			
			outputStream.println("</Receipts>");
		}
		
		outputStream.close();
	}
	
	public static void createTaxpayerReceiptsPieJFreeChart(int taxpayerIndex){
		DefaultPieDataset receiptPieChartDataset = new DefaultPieDataset();
		Taxpayer taxpayer = Database.getTaxpayerByIndex(taxpayerIndex);
		
		receiptPieChartDataset.setValue("Basic", taxpayer.getReceiptsTotalAmount(ReceiptKind.BASIC));
		receiptPieChartDataset.setValue("Entertainment", taxpayer.getReceiptsTotalAmount(ReceiptKind.ENTERTAINMENT));
		receiptPieChartDataset.setValue("Travel", taxpayer.getReceiptsTotalAmount(ReceiptKind.TRAVEL));
		receiptPieChartDataset.setValue("Health", taxpayer.getReceiptsTotalAmount(ReceiptKind.HEALTH));
		receiptPieChartDataset.setValue("Other", taxpayer.getReceiptsTotalAmount(ReceiptKind.OTHER));
		
		JFreeChart receiptPieJFreeChart = ChartFactory.createPieChart("receipts Pie Chart", receiptPieChartDataset);
		PiePlot piePlot = (PiePlot)receiptPieJFreeChart.getPlot();
		PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}: {1}$ ({2})", new DecimalFormat("0.00"), new DecimalFormat("0.00%"));
		piePlot.setLabelGenerator(generator); 

		ChartFrame receiptPieChartFrame = new ChartFrame(Database.getTaxpayerNameAfmValuesPairList(taxpayerIndex), receiptPieJFreeChart);
		receiptPieChartFrame.pack();
		receiptPieChartFrame.setResizable(false);
		receiptPieChartFrame.setLocationRelativeTo(null);
		receiptPieChartFrame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		receiptPieChartFrame.setVisible(true);
	}
	
	public static void createTaxpayerTaxAnalysisBarJFreeChart(int taxpayerIndex){
		DefaultCategoryDataset taxAnalysisBarChartDataset = new DefaultCategoryDataset();
		Taxpayer taxpayer = Database.getTaxpayerByIndex(taxpayerIndex);
		
		String taxVariationType = taxpayer.getTaxIncrease()!=0? "Tax Increase" : "Tax Decrease";
		double taxVariationAmount = taxpayer.getTaxIncrease()!=0? taxpayer.getTaxIncrease() : taxpayer.getTaxDecrease()*(-1);
		
		taxAnalysisBarChartDataset.setValue(taxpayer.getBasicTax(), "Tax", "Basic Tax");
		taxAnalysisBarChartDataset.setValue(taxVariationAmount, "Tax", taxVariationType);
		taxAnalysisBarChartDataset.setValue(taxpayer.getTotalTax(), "Tax", "Total Tax");

		JFreeChart taxAnalysisJFreeChart = ChartFactory.createBarChart("Tax Analysis Bar Chart", "",  "Tax Analysis in $", taxAnalysisBarChartDataset, PlotOrientation.VERTICAL, true, true, false);

		ChartFrame receiptPieChartFrame = new ChartFrame(Database.getTaxpayerNameAfmValuesPairList(taxpayerIndex), taxAnalysisJFreeChart);
		receiptPieChartFrame.pack();
		receiptPieChartFrame.setResizable(false);
		receiptPieChartFrame.setLocationRelativeTo(null);
		receiptPieChartFrame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		receiptPieChartFrame.setVisible(true);
	}
	
	public static void saveTaxpayerInfoToTxtLogFile(String folderSavePath, int taxpayerIndex){
		Taxpayer taxpayer = Database.getTaxpayerByIndex(taxpayerIndex);
		
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(new FileOutputStream(folderSavePath+"//"+taxpayer.getAFM()+"_LOG.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening: "+folderSavePath+"//"+taxpayer.getAFM()+"_LOG.txt");
		}
		
		outputStream.println("Name: "+taxpayer.getName());
		outputStream.println("AFM: "+taxpayer.getAFM());
		outputStream.println("Income: "+taxpayer.getIncome());
		outputStream.println("Basic Tax: "+taxpayer.getBasicTax());
		if (taxpayer.getTaxIncrease()!=0){
			outputStream.println("Tax Increase: "+taxpayer.getTaxIncrease());
		}else{
			outputStream.println("Tax Decrease: "+taxpayer.getTaxDecrease());
		}
		outputStream.println("Total Tax: "+taxpayer.getTotalTax());
		outputStream.println("Total Receipts Amount: "+taxpayer.getTotalReceiptsAmount());
		outputStream.println("Entertainment: "+taxpayer.getReceiptsTotalAmount(ReceiptKind.ENTERTAINMENT));
		outputStream.println("Basic: "+taxpayer.getReceiptsTotalAmount(ReceiptKind.BASIC));
		outputStream.println("Travel: "+taxpayer.getReceiptsTotalAmount(ReceiptKind.TRAVEL));
		outputStream.println("Health: "+taxpayer.getReceiptsTotalAmount(ReceiptKind.HEALTH));
		outputStream.println("Other: "+taxpayer.getReceiptsTotalAmount(ReceiptKind.OTHER));
		
		outputStream.close();
		
		JOptionPane.showMessageDialog(null, "Η αποθήκευση ολοκληρώθηκε", "Μήνυμα", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void saveTaxpayerInfoToXmlLogFile(String folderSavePath, int taxpayerIndex){
		Taxpayer taxpayer = Database.getTaxpayerByIndex(taxpayerIndex);
		
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(new FileOutputStream(folderSavePath+"//"+taxpayer.getAFM()+"_LOG.xml"));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening: "+folderSavePath+"//"+taxpayer.getAFM()+"_LOG.xml");
		}
		
		outputStream.println("<Name> "+taxpayer.getName()+" </Name>");
		outputStream.println("<AFM> "+taxpayer.getAFM()+" </AFM>");
		outputStream.println("<Income> "+taxpayer.getIncome()+" </Income>");
		outputStream.println("<BasicTax> "+taxpayer.getBasicTax()+" </BasicTax>");
		if (taxpayer.getTaxIncrease()!=0){
			outputStream.println("<TaxIncrease> "+taxpayer.getTaxIncrease()+" </TaxIncrease>");
		}else{
			outputStream.println("<TaxDecrease> "+taxpayer.getTaxDecrease()+" </TaxDecrease>");
		}
		outputStream.println("<TotalTax> "+taxpayer.getTotalTax()+" </TotalTax>");
		outputStream.println("<Receipts> "+taxpayer.getTotalReceiptsAmount()+" </Receipts>");
		outputStream.println("<Entertainment> "+taxpayer.getReceiptsTotalAmount(ReceiptKind.ENTERTAINMENT)+" </Entertainment>");
		outputStream.println("<Basic> "+taxpayer.getReceiptsTotalAmount(ReceiptKind.BASIC)+" </Basic>");
		outputStream.println("<Travel> "+taxpayer.getReceiptsTotalAmount(ReceiptKind.TRAVEL)+" </Travel>");
		outputStream.println("<Health> "+taxpayer.getReceiptsTotalAmount(ReceiptKind.HEALTH)+" </Health>");
		outputStream.println("<Other> "+taxpayer.getReceiptsTotalAmount(ReceiptKind.OTHER)+" </Other>");
		
		outputStream.close();
		
		JOptionPane.showMessageDialog(null, "Η αποθήκευση ολοκληρώθηκε", "Μήνυμα", JOptionPane.INFORMATION_MESSAGE);
	}
}
