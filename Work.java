/* JAVA COMMAND PROMPT PROGRAM THAT REPLICATES SIMILAR TO AN ATM SYSTEM 
WITH FULL VALIDATION OF USER AND DATA INPUT OUTPUT IF THE DATA IS FOUND TO BE CORRECT AND VALID
DATABASE : MySQL WITH JAVA DATAVBASE CONNECTIVITY */



/* PACKAGES IMPORTED*/

import java.util.*;
import java.lang.*;
import java.sql.*;
import java.math.*;


//MODULE 1//
//PARENT CLASS CONNECTIVITY//

class  Connectivity
{
Scanner sb=new Scanner(System.in); 
Connection cn;
Statement stt;
ResultSet rs;
ResultSet rm;

// CONNECTION METHOD USED TO CHECK DATABASE CONNECTIVITY //

public void Connection() 
{
try
{
		System.out.println("...........................CHECKING SERVER STATUS.............................");
		System.out.println("");
		Thread.sleep(5000);
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sr="jdbc:mysql://localhost:3306/work?allowPublicKeyRetrieval=true&useSSL=False";
		String user="root";
		String passw="1234";
		cn=DriverManager.getConnection(sr,user,passw);	
		System.out.println("............................SERVER STATUS : ONLINE.............................");
		Thread.sleep(1000);
		System.out.println("");
}
		catch(com.mysql.cj.jdbc.exceptions.CommunicationsException yy) //FOR INACTIVE SERVER//
		{	
			System.out.println("SERVER STATUS : OFFLINE....... TRY AGAIN LATER");
			System.exit(1);
		}
		catch(Exception eee) //TO CHECK ANY OTHER EXCEPTION LIKE NULLPOINTER ETC//
		{
			System.out.println("Exception Encountered" +"   " +eee);
			System.exit(2);
		}
}
}

// MODULE 2 //

//CHILD CLASS USERINFO INHERITING CONNECTIVITY //

class UserInfo extends Connectivity
	
{
	
	static long acc; //ACCOUNT NUMBER INFO STORED IN THIS VARIABLE//

	//METHOD TO REGISTER FOR NEW USER//
	
	public void NewUser()
	{
		try
		{
		
		String sr="jdbc:mysql://localhost:3306/work?allowPublicKeyRetrieval=true&useSSL=False";
		String user="root";
		String passw="1234";
		cn=DriverManager.getConnection(sr,user,passw);
		System.out.println("PLEASE ENTER YOUR ACCOUNT NUMBER");
		acc=sb.nextLong();
		System.out.println("PLEASE ENTER YOUR PASSWORD");
		sb.nextLine();
		String psk=sb.nextLine();
		if("".equals(psk))  //NULL PASSWORD VALIDATION//
		{
			System.out.println("PASSWORD CANNOT BE NULL : RETRY...");
			Thread.sleep(2000);
			System.exit(1);
		}
		String srm="INSERT INTO Account_Info (Acc_no,Pass) values ("+acc+",'"+psk+"')";
		stt=cn.createStatement();
		stt.executeUpdate(srm);
		System.out.println("RECORD INSERTED SUCCESFULLY");
		
		}
		catch(SQLIntegrityConstraintViolationException e) // TO HANDLE AN EXCEPTION WHETHER AN ACCOUNT ALREADY EXIST //
		{
			System.out.println("ACCOUNT ALREADY EXIST : PLEASE LOGIN WITH YOUR EXISTING ACCOUNT");
			try
			{
			Thread.sleep(3000);
			}
			catch(Exception efx)
			{
				
			}
			ExistingUser(); // IF EXIST.... CALLS EXISTINGUSER MODULE TO LOGIN //
		}
		catch(InputMismatchException e)//VALIDATING INPUT MISMATCH//
		{
			System.out.println("INCORRECT TYPE OF DATA INSERTED : PLEASE TRY AGAIN");
			try
			{
			Thread.sleep(2000);
			}
			catch(Exception ebx)
			{
				
			}
			System.exit(1);// EXIT IF INVALID VALUE//
		}
		catch(Exception err)
		{
			System.out.println("Exception Encountered"+err);
			System.exit(2);
		}
		
		
	}
	
	//MODULE 2 IN SUB CLASS USERINFO//
	
	public void ExistingUser()
	{
	try
	{
		String sr="jdbc:mysql://localhost:3306/work?allowPublicKeyRetrieval=true&useSSL=False";
		String user="root";
		String passw="1234";
		cn=DriverManager.getConnection(sr,user,passw);
		System.out.println("ENTER YOUR ACCOUNT NUMBER");
		acc=sb.nextLong();
		System.out.println("ENTER YOUR PASSWORD");
		sb.nextLine();
		String sbc=sb.nextLine();
		String srr="Select *from  Account_info where Pass='"+sbc+"'&& Acc_no='"+acc+"'";
		stt=cn.createStatement();
		rs=stt.executeQuery(srr);
		if(rs.next()==false) // IF NO RECORD FOUND //
		{
			System.out.println("NO RECORD FOUND FOR THIS ACCOUNT: PLEASE TRY AGAIN");
			System.exit(1);
		}

		System.out.println("RECORD FOUND");
		Thread.sleep(1000);
		}
	catch(InputMismatchException a) //FOR INPUT MISMATCH EXCEPTION //
	{ 
		System.out.println("");
		System.out.println("INPUT MISMATCH : WRONG TYPE OF DATA INSERTED");
		try
		{
		Thread.sleep(1000);
		System.exit(1);
		}
		catch(Exception rm)
		{
			System.out.println("Exception Encounterd");
		}
	
	}
	catch(NullPointerException e) // NULL POINTER //
	{
		System.out.println("EXCEPTION ENCOUNTERD"+e);
		System.exit(1);	
	}
	catch(Exception e)
	{
	System.out.println(e);
	}	
}
}

//MODULE 3//

/*CHILD CLASS RELATED TO ALL 
TRANSACTION RELATED WORK INHERITING
PROPERTIES OF USERINFO MODULE MAINLY ACCOUNT NUMBER acc*/

		
class Transactions extends UserInfo
{
	
//ALL STATIC VARIABLES DEClared//
	
static int added=0;								
static int withdrawn=0;
static int transferred=0;
static int common=0;
static int total;
static long ac_no;
static int avail;	
static int total1;	

//METHOD 1 IN MODULE 3//

//FOR ADDING UP MONEY IN  THE ACCOUNT//

public void Add()
		{
			try
			{	
			Class.forName("com.mysql.cj.jdbc.Driver");
			String sr="jdbc:mysql://localhost:3306/work?allowPublicKeyRetrieval=true&useSSL=False";
			String user="root";
			String passw="1234";
			cn=DriverManager.getConnection(sr,user,passw);
			/*System.out.println("CONNECTED");
			System.exit(3);*/
			System.out.println(acc);
			System.out.print("PLEASE ENTER THE AMOUNT TO BE ADDED : Rs ");
			int add_on=sb.nextInt();
			String str="UPDATE Account_info Set added=added+"+add_on+",total=total+"+add_on+" where Acc_no="+acc+"";
			stt=cn.createStatement();
			stt.executeUpdate(str);
			Thread.sleep(1000);
			System.out.println("");	
			Thread.sleep(2000);
			System.out.println("MONEY ADDED SUCCESSFULLY");
			Thread.sleep(1000);
			}
			catch(com.mysql.cj.jdbc.exceptions.CommunicationsException yy) //EXCEPTIONS HANDLED FROM HERE LIKE COMMUNICATIONS,INPUT MISMATCH ETC//
			{
			System.out.println("SERVER OFFLINE TRY AGAIN LATER");
			System.exit(1);
			}	
			catch(InputMismatchException e)
			{
				System.out.println("ENTER VALID VALUE");
				System.exit(1);
			}
			catch(Exception e)
			{
				System.out.println("Exception Encountered"+e);
				System.exit(2);
			}
	}
	
// METHOD 2 IN MODULE 3//
//METHOD FOR WITHDRAWING MONEY FROM THE ACCOUNT//

public void Withdraw()
	{
		try
		{
			
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sr="jdbc:mysql://localhost:3306/work?allowPublicKeyRetrieval=true&useSSL=False";
		String user="root";
		String passw="1234";
		cn=DriverManager.getConnection(sr,user,passw);
		System.out.println("PLEASE ENTER THE AMOUNT TO BE WITHDRAWN :");
		int wd=sb.nextInt();
		String srr="Select total from  Account_info where Acc_no='"+acc+"'";
		stt=cn.createStatement();
		rs=stt.executeQuery(srr);
		while(rs.next()) //CHECKING FOR ENOUGH BALANCE IN ACCOUNT//
		{	
		avail=rs.getInt(1);
		}
		if(avail>wd)// IF ENOUGH MONEY //
		{
			String str="UPDATE Account_info Set withdrawn=withdrawn+"+wd+",total=total-"+wd+" where Acc_no="+acc+"";
			stt=cn.createStatement();
			stt.executeUpdate(str);
			Thread.sleep(2500);
			System.out.println("MONEY WITHDRAWN....");
		}
		else //IF NOT ENOUGH MONEY //
		{
			System.out.println("NOT ENOUGH MONEY");
			System.out.println("YOUR CURRENT ACCOUNT BALANCE IS :" +avail);
			try{
				Thread.sleep(1000);
				return;
			}
			catch(Exception e){}
		}

		}
		catch(InputMismatchException e) //INPUT MISMATCH EXCEPTION //
			{
				System.out.println("ENTER VALID VALUE");
				System.exit(1);
			}
		catch(Exception ex)
		{
			
		}
	}
	
	
// METHOD 3 IN MODULE 3//
//METHOD TO TRANSFER MONEY FROM ONE ACCOUNT TO ANOTHER//	
		
public void Transfer()
	{
		try
		{
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sr="jdbc:mysql://localhost:3306/work?allowPublicKeyRetrieval=true&useSSL=False";
		String user="root";
		String passw="1234";
		cn=DriverManager.getConnection(sr,user,passw);
		System.out.println("PLEASE ENTER THE ACCONT NO. TO TRANSFER MONEY");
		long tr=sb.nextLong();
		System.out.println("CONFIRM ACCOUNT NO.");
		long c_tr=sb.nextLong();
		if(c_tr==tr) //IF ACCOUNT NUMBER IS VERIFIED//
		{
			System.out.println("ENTER THE AMOUNT TO BE TRANSFERRED");
			int trans=sb.nextInt();
			String srr="Select total from  Account_info where Acc_no='"+acc+"'";
			stt=cn.createStatement();
			rs=stt.executeQuery(srr);
			while(rs.next())
			{
				total=rs.getInt(1);
			}
				
			if(trans>total) //IF TRANSCATION ACCOUNT IS MORE THAN AVAILABLE AMOUNT
			{
				System.out.println("NOT ENOUGH MONEY");
				return;
			}
			else
			{
				try
				{
					System.out.println("WAIT FOR A MOMENT...");
					String str="UPDATE Account_info Set transferred=transferred+"+trans+",total=total-"+trans+" where Acc_no="+acc+"";
					stt=cn.createStatement();
					stt.executeUpdate(str);
					System.out.println("EXECUTED SUCCESSFULLY");
					Thread.sleep(5000);
					common-=trans;
					transferred+=trans;
					System.out.println("MONEY TRANSFERRED TO ACCOUNT NUMBER : " + tr);
					Thread.sleep(1000);
					String srt="Select total from  Account_info where Acc_no='"+acc+"'";
					stt=cn.createStatement();
					rm=stt.executeQuery(srt);
					while(rm.next())
					{
						total1=rm.getInt(1);
					}
					System.out.println("AMOUNT REMAINING : "+total1);
				}catch(NullPointerException e)
				{
						System.out.println("NULL POINTER");
						System.exit(1);
				}
				catch(Exception e)
				{
				}
			}
		}
		else //IF ACCOUNT NUMBER IS MISMATCHED
			{
				System.out.println("ACCOUNT NUMBER MISMATCHED");
				return;
			}
		}
		catch(InputMismatchException e)
			{
				System.out.println("ENTER VALID VALUE");
				System.exit(1);
			}
		catch(Exception exx)
		{
			System.out.println("EXCEPTION CAUGHT");
			System.exit(1);
		}
			
	}
	
}

//MODULE 4  TO GET THE DETAILS OF A USER ACCOUNT

class Details extends Transactions
{
public void get_details()
	{
		try
		{
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sr="jdbc:mysql://localhost:3306/work?allowPublicKeyRetrieval=true&useSSL=False";
		String user="root";
		String passw="1234";
		cn=DriverManager.getConnection(sr,user,passw);
		String sbr="SELECT total,added,withdrawn,transferred from Account_info where Acc_no="+acc+"";
		stt=cn.createStatement();
		rs=stt.executeQuery(sbr);
		while(rs.next()==true)
		{
			System.out.println("ACCOUNT NUMBER  " + acc);
			Thread.sleep(500);
			System.out.println("TOTAL MONEY AVAILABLE IN YOUR ACCOUNT IS  " + rs.getInt(1));
			Thread.sleep(500);
			System.out.println("TOTAL MONEY ADDED  " + rs.getInt(2));
			Thread.sleep(500);
			System.out.println("TOTAL MONEY WITHDRAWN " + rs.getInt(3));
			Thread.sleep(500);
			System.out.println("TOTAL MONEY TRANSFERRED  " + rs.getInt(4));
		}
		}
		catch(InputMismatchException e)
			{
				System.out.println("ENTER VALID VALUE");
				System.exit(1);
			}
		catch(Exception e)
		{
		}
}
}
public class Work //MAIN METHOD//
{
	public static void main(String []ag)
		{   
			
			Scanner sc=new Scanner(System.in);
			System.out.println("");
            System.out.println("...........................ATM CONCEPT BLACK SCREEN PROGRAM.....................................");
			System.out.println("");
			System.out.println("");
			try
			{
			Thread.sleep(3000);
			}
			catch(Exception ex)
			{
			System.out.println(ex);
			}
			

			Connectivity cbx=new Connectivity();
			cbx.Connection();
			System.out.println("ENTER 1 FOR NEW USER");
			System.out.println("ENTER 2 FOR EXISTING USER");
			int abx=sc.nextInt();
			UserInfo ui=new UserInfo();
			switch(abx)
				{
				case 1:
				ui.NewUser();
				break;
				
				case 2:
				ui.ExistingUser();
				break;
				
				default:
				System.out.println("INVALID SELECTION");
				System.exit(1);
				
				
				}
			Transactions t=new Transactions();
			Details v=new Details();
			int factor= 1;
   		
				while(factor==1)
				{
					System.out.println("ENTER 1 TO ADD MONEY");
					System.out.println("ENTER 2 TO WITHDRAW");
					System.out.println("ENTER 3 TO TRANSFER");
					System.out.println("ENTER 4 TO CHECK DETAILS");
					System.out.println("ENTER 5 TO EXIT");
					int n=sc.nextInt();
					{
						
						switch(n)
						{
							
						case 1:
						t.Add();
						break;
						
						case 2:
						t.Withdraw();
						break;
						
						case 3:
						t.Transfer();
						break;
						
						case 4:
						v.get_details();
						break;
						
						case 5:
						factor+=1;
						break;
						
						default:
						try
						{
						System.out.println("INVALID INPUT");
						Thread.sleep(1000);
						}
						catch(Exception e)
						{
							System.out.println("");
						}
						
						}
					}
				}
		}

}

