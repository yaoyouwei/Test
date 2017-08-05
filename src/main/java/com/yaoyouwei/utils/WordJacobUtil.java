package com.yaoyouwei.utils;

import java.io.File;
import java.util.UUID;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class WordJacobUtil {
	private static boolean visible = false;
	
	   // word文档
    private Dispatch doc;

    // word运行程序对象
    private static ActiveXComponent word;

    // 所有word文档集合
    private Dispatch documents;

    // 选定的范围或插入点
    private Dispatch selection;

    private boolean saveOnExit = false;
    
    public WordJacobUtil(boolean visible) {
        if (word == null) {
            word = new ActiveXComponent("Word.Application");
            word.setProperty("Visible", new Variant(visible));
        }
        if (documents == null){
        	documents = word.getProperty("Documents").toDispatch();
        }
    }

    public void createNewDocument() {
        doc = Dispatch.call(documents, "Add").toDispatch();
        selection = Dispatch.get(word, "Selection").toDispatch();
    }
	

    public void openDocument(String docPath) {
        if (this.doc != null) {
            this.closeDocument();
        }
        doc = Dispatch.call(documents, "Open", docPath).toDispatch();
        selection = Dispatch.get(word, "Selection").toDispatch();
    }
    
    /**
     * 关闭当前word文档
     */
    public void closeDocument() {
        if (doc != null) {
            Dispatch.call(doc, "Save");
            Dispatch.call(doc, "Close", new Variant(saveOnExit));
            doc = null;
        }
    }

    public void close() {
        closeDocument();
        if (word != null) {
            Dispatch.call(word, "Quit");
            word = null;
        }
        selection = null;
        documents = null;

    }
	
	

	public static void main(String[] args) {
	    String srcFilePath =  "D://test.doc";
	    
	    /*WordJacobUtil wju = new WordJacobUtil(true);
	    wju.openDocument(srcFilePath);
	    int labelCount = wju.getLabelCount();
	    System.out.println("getLabelCount:"+labelCount );
	    
	    for(int i=1;i<=labelCount;i++){
	    	String labName = wju.getLabelName(i);
	    	String labValue = wju.getLabelValue(labName);
	    	System.out.println("index:"+i+" labName:"+labName+" labValue:"+labValue);
	    }*/
	    
	    //testSplitWord
		getBookMarks(srcFilePath);
	}
	
	public void testSplitWord(){
		Thread t= null;
	    for(int i=0;i<10;i++){
	    	t = new Thread(new Runnable(){
				@Override
				public void run() {
					String srcFilePath =  "D://test.doc";
				    String destDirPath = "D://" ;
				    File destDir = new File(destDirPath);
					WordJacobUtil.splitWord(srcFilePath, destDir);
				}
	    		
	    	});
	    	t.start();
	    	
	    }
	}
	
	public static  void splitWord (String srcFilePath,File destDir){
		if (!destDir.exists()) {
			throw new IllegalArgumentException(destDir + " does not exist");
		}
		if (!destDir.isDirectory()) {
			throw new IllegalArgumentException(destDir + " is not a directory");
		}
		File srcFile = new File(srcFilePath);
		if(!srcFile.exists()){
			throw new IllegalArgumentException(srcFilePath + " is not exist");
		}
		long time1 = System.currentTimeMillis();
		boolean visible = true;
    	ComThread.InitSTA(); // 初始化COM的线程，非常重要！！使用结束后要调用 realease方法
    	ActiveXComponent wordApp = new ActiveXComponent("Word.Application");
    	//System.out.println(wordApp.getPropertyAsString("UserName"));
    	Dispatch wordAppDispatch = (Dispatch) wordApp.getObject();
    	Dispatch.put(wordAppDispatch, "Visible", new Variant(visible)); //操作word过程是否可见
    	Dispatch documents = wordApp.getProperty("Documents").toDispatch();//获取所有打开的word窗口
    	//Dispatch document = Dispatch.call(documents, "Open",new Variant(srcFile.getAbsolutePath()),new Variant(true)).toDispatch();//以只读方式打开一个文档
    	  Dispatch document = Dispatch.invoke( documents, "Open", Dispatch.Method,
    	            new Object[] { srcFile.getAbsolutePath(), new Variant(false), new Variant(true) },
    	            new int[1]).toDispatch();
    	Dispatch activeWindow = Dispatch.get(document, "ActiveWindow").toDispatch();//设置当前文档的视图为页面 (视图->页面)
    	Dispatch View = Dispatch.get(activeWindow, "View").toDispatch();
    	Dispatch.put(View,"Type",new Variant(3));
    	Dispatch.call(document, "Repaginate");//重新编排页码
    	Dispatch selection = Dispatch.get(wordApp, "Selection").toDispatch();//获取文档选择区域或位置
	    int totalPageNum  = Dispatch.call(selection, "Information",new Variant(JacobConstant.wdNumberOfPagesInDocument)).getInt();
    	for(int i = 1 ;i<=totalPageNum;i++){
    		Dispatch.call(document, "Activate");
    		//光标移动到文档某一页的开始
    		 Dispatch.invoke(selection, "GoTo", Dispatch.Method, 
    				 new Object[]{new Variant(JacobConstant.wdGoToPage), new Variant(JacobConstant.wdGoToNext),new Variant(1),new Variant(i) },
    				 new int[1]); 
    		 //复制当前页面
    		 Dispatch bookmark = Dispatch.call(document, "Bookmarks",new Variant("\\Page")).toDispatch();
    		 Dispatch rangeOfBookMark = Dispatch.get(bookmark, "Range").toDispatch();
    		 Dispatch.call(rangeOfBookMark, "Copy");
    		 //Dispatch.invoke(rows, "item", Dispatch.Method, new Object[] { new Integer(rowIndex) }, new int[1]).toDispatch();
    		 //打开一个新的Word窗口
    		 Dispatch toSaveDocument = Dispatch.call(documents, "Add").toDispatch();
    		 //将选中页面的内容复制到新的窗口的document中
    		 Dispatch contentOfToSaveDocument = Dispatch.get(toSaveDocument, "Content").toDispatch();
    		 Dispatch.call(contentOfToSaveDocument, "Paste");
    		 /*选中一页时,光标会停在下一页的起始位置,复制就会会多出一个字符占据了一个空页面(图片结尾则不会)
    		      当出现空页面时,删除这个多出的字符即可删除空页面*/
    		 //计算新打开的窗口文档的页数,看是否多出一个字符占据了一页,如果有则删除该字符
    	     Dispatch selectionOfToSaveDocument= Dispatch.get(wordApp, "Selection").toDispatch();
    	     int toSaveDocumentTotalPageNum  = Dispatch.call(selectionOfToSaveDocument, "Information",new Variant(JacobConstant.wdNumberOfPagesInDocument)).getInt();
    		 if(toSaveDocumentTotalPageNum>1){
    			 //光标移动到文档最后一页
    			 Dispatch.call(selectionOfToSaveDocument, "EndKey", new Variant(JacobConstant.wdStory));
    			 Dispatch.call(selectionOfToSaveDocument, "TypeBackspace");
    			 Dispatch.call(selectionOfToSaveDocument, "Delete", new Variant(JacobConstant.wdCharacter),new Variant(1));
    		 }
    		 //保存新打开的窗口的文档
    		 //Dispatch.call(toSaveDocument, "SaveAs", new Variant(destDir.getAbsolutePath()+srcFile.getName()+"_"+ (i) + ".doc")); // 保存一个新文档
    		 Dispatch.call(toSaveDocument, "SaveAs", new Variant(destDir.getAbsolutePath()+UUID.randomUUID()+srcFile.getName()+"_"+ (i) + ".doc")); // 测试使用 保存一个新文档
    		 Dispatch.call(toSaveDocument, "Close", new Variant(visible));
    	 }
    	 Dispatch.call(document, "Close", new Variant(visible));
    	 Dispatch.call(wordApp, "Quit");
         ComThread.Release(); // 释放COM线程。根据Jacob的帮助文档，com的线程回收不由java的垃圾回收器处理
         long time2 = System.currentTimeMillis();
         double time3 = (time2 - time1) / 1000.0;
         System.out.println("\n" + time3 + " 秒.");
         System.out.println(System.currentTimeMillis()+"运行完毕");
	}
	
	public static  void getBookMarks (String srcFilePath){
		File srcFile = new File(srcFilePath);
		if(!srcFile.exists()){
			throw new IllegalArgumentException(srcFilePath + " is not exist");
		}
		long time1 = System.currentTimeMillis();
		boolean visible = true;
    	ComThread.InitSTA(); // 初始化COM的线程，非常重要！！使用结束后要调用 realease方法
    	ActiveXComponent wordApp = new ActiveXComponent("Word.Application");
    	//System.out.println(wordApp.getPropertyAsString("UserName"));
    	Dispatch wordAppDispatch = (Dispatch) wordApp.getObject();
    	Dispatch.put(wordAppDispatch, "Visible", new Variant(visible)); //操作word过程是否可见
    	Dispatch documents = wordApp.getProperty("Documents").toDispatch();//获取所有打开的word窗口
    	Dispatch document = Dispatch.invoke( documents, "Open", Dispatch.Method,
    	            new Object[] { srcFile.getAbsolutePath(), new Variant(false), new Variant(true) },
    	            new int[1]).toDispatch();
    	Dispatch activeWindow = Dispatch.get(document, "ActiveWindow").toDispatch();//设置当前文档的视图为页面 (视图->页面)
    	Dispatch View = Dispatch.get(activeWindow, "View").toDispatch();
    	Dispatch.put(View,"Type",new Variant(3));
    	Dispatch.call(document, "Repaginate");//重新编排页码
    	Dispatch selection = Dispatch.get(wordApp, "Selection").toDispatch();//获取文档选择区域或位置
	    int totalPageNum  = Dispatch.call(selection, "Information",new Variant(JacobConstant.wdNumberOfPagesInDocument)).getInt();
    	for(int i = 1 ;i<=totalPageNum;i++){
    	System.out.println("第"+i+"页书签-----------------------------------");
    		Dispatch.call(document, "Activate");
    		//光标移动到文档某一页的开始
    		 Dispatch.invoke(selection, "GoTo", Dispatch.Method, 
    				 new Object[]{new Variant(JacobConstant.wdGoToPage), new Variant(JacobConstant.wdGoToNext),new Variant(1),new Variant(i) },
    				 new int[1]); 
    		 //复制当前页面
    		 Dispatch pageBookmark = Dispatch.call(document, "Bookmarks",new Variant("\\Page")).toDispatch();
    		 Dispatch.call(pageBookmark, "Select");
    		 Dispatch bookMarks = Dispatch.call(selection, "Bookmarks").toDispatch();
    	     int labelCount = Dispatch.get(bookMarks, "Count").getInt(); // 书签数
    	     for(int k=1;k<=labelCount;k++){
    	    	 Dispatch bookmark = Dispatch.call(bookMarks, "Item", k).toDispatch();
    	         String labName = Dispatch.call(bookmark, "Name").toString();
    	         Dispatch bookmarkrange = Dispatch.get(bookmark, "Range").toDispatch();
    		     String labValue = Dispatch.get(bookmarkrange, "Text").toString();
    		     System.out.println("index:"+i+" labName:"+labName+" labValue:"+labValue);
    		 }
    		 
    	 }
    	 Dispatch.call(document, "Close", new Variant(visible));
    	 Dispatch.call(wordApp, "Quit");
         ComThread.Release(); // 释放COM线程。根据Jacob的帮助文档，com的线程回收不由java的垃圾回收器处理
         long time2 = System.currentTimeMillis();
         double time3 = (time2 - time1) / 1000.0;
         System.out.println("\n" + time3 + " 秒.");
         System.out.println(System.currentTimeMillis()+"运行完毕");
	}

	

    /************************************** 书签操作 *************************************/
    /**
     * 获取书签个数
     * 
     */
    public int getLabelCount() {
        Dispatch bookMarks = Dispatch.call(doc, "Bookmarks").toDispatch();
        int labelCount = Dispatch.get(bookMarks, "Count").getInt(); // 书签数
        return labelCount;
    }

    /** */
    /**
     * 获取书签标题
     * 
     * @param labelIndex
     */
    public String getLabelName(int labelIndex) {
        Dispatch bookMarks = Dispatch.call(doc, "Bookmarks").toDispatch();
        Dispatch rangeItem = Dispatch.call(bookMarks, "Item", labelIndex).toDispatch();
        String labelName = Dispatch.call(rangeItem, "Name").toString();
        return labelName;
    }

    /** */
    /**
     * 获取书签内容
     * 
     * @param labelName
     */
    public String getLabelValue(String labelName) {
        String rawLabelValue = getRawLabelValue(labelName);
        String labelValue = rawLabelValue.replaceAll("", "");
        // 如果含有抬头
        if (labelName.startsWith("head_")) {
            labelValue = rawLabelValue
                    .substring(rawLabelValue.indexOf("：") + 1).replaceAll(" ",
                            "");
        }
        // 如果含有头尾
        if (labelName.startsWith("full_")) {
            labelValue = rawLabelValue.substring(1, rawLabelValue.length() - 1)
                    .replaceAll(" ", "");        
        }
        if(labelName.startsWith("t_")){
            labelValue=rawLabelValue.substring(0, rawLabelValue.length()-2);
        }
        return labelValue;
    }

    /** */
    /**
     * 获取书签原始内容
     * 
     * @param labelName
     */
    public String getRawLabelValue(String labelName) {
        Dispatch bookMarks = Dispatch.call(doc, "Bookmarks").toDispatch();
        Dispatch rangeItem = Dispatch.call(bookMarks, "Item", labelName)
                .toDispatch();
        Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
        String rawLabelValue = Dispatch.get(range, "Text").toString();
        return rawLabelValue;
    }


}
