//
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//
//import org.apache.log4j.PropertyConfigurator;
//
//public class Log4JInit extends HttpServlet {
//
//	/**
//	 * Constructor of the object.
//	 */
//	public Log4JInit() {
//		super();
//	}
//
//	/**
//	 * Destruction of the servlet. <br>
//	 */
//	@Override
//	public void destroy() {
//		super.destroy(); // Just puts "destroy" string in log
//		// Put your code here
//	}
//
//
//
//	/**
//	 * Initialization of the servlet. <br>
//	 *
//	 * @throws ServletException if an error occurs
//	 */
//	@Override
//	public void init() throws ServletException {
//		// Put your code here
//
//		
//	     String prefix = getServletContext().getRealPath("/"); 
//	       String test = getServletContext().getRealPath(""); 
//	      
//	       System.setProperty("webappHome", test);
//	       
//	       SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHMM");//设置日期格式
//	       System.setProperty("date", df.format(new Date()));
//	       String file = getServletConfig().getInitParameter("log4j-config-file"); 
//	       // 从Servlet参数读取log4j的配置文件 
//	        if (file != null) { 
//	         PropertyConfigurator.configure(prefix + file); 
//	       
//	       }		
//		
//	}
//
//}
