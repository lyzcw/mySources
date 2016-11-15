/**
 * 
 */
package com.lyzcw.common.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 * @author lyzcw
 *
 */
public class MyCrawler extends BreadthCrawler{
	private Logger LOGGER = LoggerFactory.getLogger(  this.getClass() );
	private final static DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * @param crawlPath
	 * @param autoParse
	 */
	public MyCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		// TODO Auto-generated constructor stub
		/*start page*/
       // this.addSeed("http://news.hfut.edu.cn/list-1-1.html"); 
		
	   this.addSeed("http://finance.sina.com.cn/realstock/income_statement/2014-12-31/issued_pdate_de_1.html"); 
		                   //http://vip.stock.finance.sina.com.cn/corp/go.php/vFD_ProfitStatement/stockid/600030/ctrl/part/displaytype/4.phtml
		
        /*fetch url like http://news.hfut.edu.cn/show-xxxxxxhtml*/
        //this.addRegex("http://news.hfut.edu.cn/show-.*html");
		this.addRegex("http://money.finance.sina.com.cn/corp/go.php/(vFD_FinanceSummary|vFD_ProfitStatement|vFD_BalanceSheet|vFD_CashFlow)/stockid/.+/.*displaytype/4.*html");
		                     //http://money.finance.sina.com.cn/corp/go.php/vFD_FinanceSummary/stockid/000590/displaytype/4.pt.html
		                     //http://money.finance.sina.com.cn/corp/go.php/vFD_CashFlow/stockid/000590/ctrl/part/displaytype/4.phtml
		
        /*do not fetch jpg|png|gif*/
        this.addRegex("-.*\\.(jpg|png|gif).*");
        /*do not fetch url contains #*/
        this.addRegex("-.*#.*");
	}

	@Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.getUrl();
        /*if page is news page*/
        //if (page.matchUrl("http://news.hfut.edu.cn/show-.*html")) {
        //if (page.matchUrl("http://money.finance.sina.com.cn/corp/go.php/.+/stockid/.+/ctrl/.+/displaytype/4.*html")) {
            /*we use jsoup to parse page*/
            //Document doc = page.doc();

            /*extract title and content of news by css selector*/
            //String title = page.select("div[id=Article]>h2").first().text();
            //String title = doc.title();
            //String content = page.select("div#artibody", 0).text();

            //System.out.println("URL:\r\n" + url);
            //System.out.println("title:\r\n" + title);
            //System.out.println("content:\r\n" + content);

            /*If you want to add urls to crawl,add them to nextLink*/
            /*WebCollector automatically filters links that have been fetched before*/
            /*If autoParse is true and the link you add to nextLinks does not match the regex rules,the link will also been filtered.*/
            //next.add("http://xxxxxx.com");
        //}
        //资产负债表
        if (page.matchUrl("http://money.finance.sina.com.cn/corp/go.php/vFD_BalanceSheet/stockid/.+/ctrl/.+/displaytype/4.*html")) {
        	/*we use jsoup to parse page*/
            Document doc = page.doc();

        	parseSinaBalance( doc, url );
        }
    }
	
	/**
	 * 解析资产负债表
	 * @param docs
	 */
	public  void parseSinaBalance( Document doc,  String url )  {
		
        /*extract title and content of news by css selector*/
        String title = doc.title();
        
        /*String regExp = "http://money.finance.sina.com.cn/corp/go.php/vFD_BalanceSheet/stockid/.+/ctrl/(.+)/displaytype/4.*html";
		Pattern p = Pattern.compile( regExp );
		Matcher m = p.matcher( url );
		if(m.find()){
			System.out.println("年份:" + m.group( 1 ) );
		}*/
		Element sheetTable = doc.getElementById("BalanceSheetNewTable0");
		Elements trs = sheetTable.select("tbody tr");
		Elements maxtds = trs.get( 0 ).select("td");
		List<String> trList = new ArrayList<String>(trs.size());
		List<String> rowTitles = new ArrayList<String>();
		List<String> colTitles = new ArrayList<String>();
		String[][] datas = new String[ trs.size()][ maxtds.size()]  ;
		int i = 0;
        for (final Element tr : trs) {
		//for ( int i=0; i<trs.size(); i++) {	
        	for( int j=0; j<tr.select("td").size(); j++){
	             datas[ i ][ j ] = tr.child(j).text();
	        }
        	i++;
        } 
        String outString = "================================================================================================================\r\n";
        System.out.println( "================================================================================================================" );
        outString += "URL：" + url + "\r\n" + "标题：" + title +  "        抓取时间：" + timeFormat.format(new Date() )+ "\r\n" ;
        System.out.println("URL：" + url);
        System.out.println("标题：" + title + "      抓取时间：" + timeFormat.format( new Date() ));
        outString += "================================================================================================================\r\n";
        System.out.println( "================================================================================================================" );
        for( int c=1; c<maxtds.size(); c++ ){
        	for( int r=0; r<trs.size(); r++ ){
				//System.out.println( datas[r][0]  + "------" +  datas[r][c]);
				if( null!= datas[r][0] ){
					if( null!= datas[r][1] ){
						if( 0==r ){
							outString += "*************************"+ "\r\n" ;
							System.out.println( "*************************");
						}
						outString += datas[r][0]  + "------" +  datas[r][c] + "\r\n";
						System.out.println( datas[r][0]  + "------" +  datas[r][c] );
						if( 0==r ){
							outString += "*************************"+ "\r\n" ;
							System.out.println( "*************************");
						}
					}else{
						outString += "========="+ "\r\n" + datas[r][0]  + "\r\n" + "========="+ "\r\n" ;
						System.out.println(  "=========" );
						System.out.println( datas[r][0] );
						System.out.println(  "=========" );
					}
				}
				
			}
		}
        String toFilePath ="d:/myCrawlers/";
		File tempDir = new File(toFilePath);
		if(!tempDir.exists()) tempDir.mkdir();
		String fileName = dateFormat.format(new Date() )+ ".txt";
		String fileUrl = toFilePath + fileName; // 报文文件生成路径
		writeFile(outString.toString(), fileUrl, "GBK");
		
	}
	private boolean writeFile(String message, String toFilePath, String encoding) {
		boolean flag = true;
		FileOutputStream fs = null;
		OutputStreamWriter osw = null;
		try {
			fs = new FileOutputStream(toFilePath, true);//追加写文件
			osw = new OutputStreamWriter(fs, encoding); 
			osw.write(message);
		} catch (IOException e) {
			flag = false;
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				if(null!=osw) osw.close();
				if(null!=fs) fs.close();
			} catch (IOException e) {
				flag = false;
			}
		}
		return flag;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MyCrawler crawler = new MyCrawler("crawl", true  ); 
        crawler.setThreads(10);
        crawler.setTopN(1000);
        //crawler.setResumable(true);//是否断点抓取
        /*start crawl with depth of 4*/
        crawler.start( 5 );
    }


}
