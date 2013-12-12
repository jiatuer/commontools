package com.tool.sshexecute;

import net.neoremind.sshxcute.core.ConnBean;
import net.neoremind.sshxcute.core.Result;
import net.neoremind.sshxcute.core.SSHExec;
import net.neoremind.sshxcute.exception.TaskExecFailException;
import net.neoremind.sshxcute.task.CustomTask;
import net.neoremind.sshxcute.task.impl.ExecCommand;

public class LinuxTool {

	

	public static void main(String[] args) throws TaskExecFailException {
//		 Initialize a SSHExec instance without referring any object. 
		SSHExec ssh = null;
		// Wrap the whole execution jobs into try-catch block   
		try {
		    // Initialize a ConnBean object, parameter list is ip, username, password
		    ConnBean cb = new ConnBean("10.249.68.89", "oncall","welcome123");
//		    ConnBean cb = new ConnBean("aisetlcore03.vip.ebay.com", "oncall","welcome123");
		    // Put the ConnBean instance as parameter for SSHExec static method getInstance(ConnBean) to retrieve a real SSHExec instance
		    ssh = SSHExec.getInstance(cb);              
		    // Create a ExecCommand, the reference class must be CustomTask
//		    CustomTask ct1 = new ExecCommand("cd ../jiazhong \n ksh test.ksh");
//		    CustomTask ct1 = new ExecCommand("echo 1");
		    CustomTask ct1 = new ExecCommand("cd sfxfs");
//		    CustomTask ct1 = new ExecCommand("cd tmp \n cd 1");
		    // Create a ExecShellScript, the reference class must be CustomTask
//		    CustomTask ct2 = new ExecShellScript("/home/tsadmin","./sshxcute_test.sh","hello world");
		    // Connect to server
		    ssh.connect();
		    // Upload sshxcute_test.sh to /home/tsadmin on remote system
//		    ssh.uploadSingleDataToServer("data/sshxcute_test.sh", "/home/tsadmin");
		    // Execute task
		    // Execute task and get the returned Result object
		    Result res = ssh.exec(ct1);
//		    Result res = ssh.exec(ct2);
		    // Check result and print out messages.
		    if (res.isSuccess)
		    {
		        System.out.println("Return code: " + res.rc);
		        System.out.println("sysout: " + res.sysout);
		    }
		    else
		    {
		        System.out.println("Return code: " + res.rc);
		        System.out.println("error message: " + res.error_msg);
		    }
		} 
		catch (TaskExecFailException e) 
		{
		    System.out.println(e.getMessage());
		    e.printStackTrace();
		} 
		catch (Exception e) 
		{
		    System.out.println(e.getMessage());
		    e.printStackTrace();
		} 
		finally 
		{
		    ssh.disconnect();   
		}
		
	}
	
	
}
