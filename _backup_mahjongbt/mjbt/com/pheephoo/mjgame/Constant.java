/*
 * Created on 29/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Constant {
	
	//expire time interval
	public static int EXPIRED_DATE=365;
	public static int VERSION=110;
	public static int CLIENTTYPE=0;//0=base   //1=update
	
	//31/08/2005
	//security exception
	public static int SECURITY_EXCEPTION_SMS=1;
	public static int SECURITY_EXCEPTION_NETWORK=2;
	public static int SECURITY_EXCEPTION_BT=3;

	
	//serverurl
	public static String SERVER_URL="socket://202.157.154.18";
//	public static String SERVER_URL="socket://localhost";
	public static String SERVER_PORT_PRV="5001";
	public static String SERVER_PORT_PUB="5002";
	
	
	
	//end 31/08/2005
	//ipx sms code and number
	public static String IPX_SMSCENTER="77877";
//	public static String IPX_SMSCENTER="93388580";
	public static String IPX_HEADER_COM="PPMJ0";
	public static String IPX_HEADER_PRV="PPMJ1";
	public static String IPX_HEADER_PUB="PPMJ2";
	
	public static String SMS_INVITE="Join me for a game of Mahjong on your mobile phone. Download the 'MAHJONG DEN' client at http://www.pheephoo.com:8080/mahjong";
	
	//payment
	public static String MSG_TRANSACTION_TITLE1="Payment";
	public static String MSG_TRANSACTION_PRICE1="Price: $";
	public static String MSG_TRANSACTION_PRICE2="(exc GST)";
	public static String MSG_TRANSACTIONPUB1="Item : Public Network Day Pass";
	public static String MSG_TRANSACTIONPRV1="Item : Private Network 1 Game";
	public static String MSG_TRANSACTION="Press OK to pay via SMS";

	//bot
	public static int BOT_NUM=10;	//numofBot
	public static String BOT_NAME[]={"Ah Mei","Suzy","Li Li","Meggie","Honey","Li Wah","Uncle Choo","Stephen","Eric","Ah Leong"};
	
	//registration
	public static String MSG_REGISTRATION="Press Ok to register the client via SMS.\nLocal SMS charges apply";
	public static String MSG_REGISTRATION2="Type your nick to register ";
	public static String MSG_REGISTRATION3="Successfully registered to network.\n\n";
	public static String MSG_REGISTRATION4="Please choose another nick ";
	
	//game playing constant.. contact to mark if player win a table limit
	public static int WIN_TABLELIMIT=99;
	
	//Constant used in EngineProxy
	//keep alive message & timeout
	public static int N_KEEPALIVETIMER=5000; //schedule keep alive task every 5s
	public static int N_KEEPALIVE=2;//if its 2 iteration, then send send the real keepalive message
	public static int N_TIMEOUTTIMER=10000; //schedule Timeout Task every 10 seconds

	//Timer for user response in online mode
	public static int N_USERWAITTIME=20;//in second  ... schedule auto response in online mode if user didnt respond to playing

	//Used in MainFormFORM CALL BACK CONSTANT
    public static int MAIN_PLAY=1001;			//used in MainForm,PauseForm
    public static int MAIN_SETTING=1002;		//MainForm, PauseForm
    public static int MAIN_PRIVATE=1003;		//MainForm
    public static int MAIN_PUBLIC=1004;			//MainForm
    public static int MAIN_ABOUT=1005;			//MainForm
    public static int MAIN_MAINMENU=1006;			//MainForm
    public static int MAIN_HISTORY=1007;			//MainForm

    public static int HISTORY_OFFLINE=1008;			//MainForm
    public static int HISTORY_ONLINE=1009;			//MainForm
    public static int MAIN_EXIT=1010;			//all form
    public static int MAIN_NOTAVAILABLE=1099;	
    public static int MAIN_TEST=1190;//only used during development		

    public static int MAIN_BTSERVER=1191;//only used during development		
    public static int MAIN_BTCLIENT=1192;//only used during development		
    
    
    public static int OFFLINE_QUITGAME=2001;
    public static int OFFLINE_CONTINUE=2004;
    public static int OFFLINE_PAUSE=2005;

    public static int ALL_SETTING=8002;			//invoke setting screen while playing
    public static int SETTING_CANCEL=8003;  //this constant is used by other screen aswell to return to the main screen
    public static int SETTING_OK=8004;

    public static int COMMON_PLAYERINFO=3001;
    public static int COMMON_GAMEINFO=3002;
    public static int COMMON_GAMEFINISH=3003;

    public static int PUBLIC_CONTINUE=4001;
    public static int PUBLIC_DISPLAY_MOTD=4004;
    public static int PUBLIC_CPAUSE=4005; //for use in PublicNetworkCanvas
    public static int PRIVATE_CONTINUE=5001;
    public static int PRIVATE_LPAUSE=5002;//login screen
    public static int PRIVATE_CPAUSE=5003;//playing screen
    public static int PRIVATE_INVITEFRIEND=5004;
    public static int PRIVATE_DELETEFRIEND=5005;
    public static int PRIVATE_DISPLAY_MOTD=5006;
    public static int PRIVATE_ERRSTART1=5010;
    public static int PRIVATE_ERRSTART2=5011;
    public static int PRIVATE_ERRINVITE1=5012;
    public static int PRIVATE_ERRINVITE2=5013;
    public static int PRIVATE_ERRINVITE3=5014;
    public static int PRIVATE_INVITEOK=5015;
    public static int PRIVATE_DELETEOK=5016;
    public static int PRIVATE_DELETECANCEL=5017;

    public static int REGISTRATION_ERR1=6001;//registration cancelled
    public static int REGISTRATION_ERR2=6002;//registration timeout
    public static int REGISTRATION_ERR3=6003;//unable to send sms
    public static int REGISTRATION_NICK=6004;
    public static int REGISTRATION_NICK_OK=6005;
    public static int REGISTRATION_NICK_DUPLICATE=6006;
    public static int NICK_ERR1=6010;//nick is empty
    public static int NICK_ERR2=6011;//invalid character for nick
    
    public static int TRANSACTION_ERR1=7001;//for displaying sms timeout error

    public static int MISC_NEEDUPDATE=8001;
    
    //only used during dev
    public static int TEST_SPEEDOFFLINE1=9001;	
    public static int TEST_SPEEDOFFLINE2=9002;	
    public static int TEST_PONG1=9100;	
    public static int TEST_CONCEALKONG1=9200;	
    public static int TEST_CONCEALKONG2=9201;	
    public static int TEST_CONCEALKONG3=9202;	
    public static int TEST_CONCEALKONG4=9203;	
    public static int TEST_KONG3=9209;	
    public static int TEST_CLEARHAND1=9300;	
    public static int TEST_CLEARHAND2=9301;	
    public static int TEST_CLEARHAND3=9302;	
    public static int TEST_CLEARHAND4=9303;	
    public static int TEST_ROBKONG1=9401;	
    public static int TEST_ROBKONG2=9402;	
        
    public static int NETWORKCOMMON_SENDMESSAGE=20003;
    public static int NETWORKCOMMON_PAUSE=20005;
    public static int NETWORKCOMMON_CONTINUE=20006;
    public static int NETWORKCOMMON_QUITGAME=20007;
    public static int NETWORKCOMMON_COMMUNICATIONERROR=20008;//displaying network timeout message
    public static int NETWORKCOMMON_NOTICESCREEN=20009;//displaying multipurpose notice, eg. player join a table, player left a table, player invite you to play
    public static int NETWORKCOMMON_STARTALLHUMAN=20010;
   
    
    
    
    
	
	
	/*game playing static
	2xxx= game playing
	6xxx= common network playing
	7xxx= public network playing featuere
	8xxx= private network playing feature
	9xxx= extra
	20xxx= transaction and registration
	*/

	//2xxx= game playing
	
	//9xxx= extra

	public static int DO_DISCARD=2010;   
	public static int DO_CONFIRM=2020;
	public static int DO_SPECIALRESPONSE=2021;
	public static int DO_CONCEALEDKONGRESPONSE=2022;
	public static int DO_WINRESPONSE=2023;
	public static int NEXTGAME=2024;
	public static int ON_TILERECEIVED=2111;
	public static int ON_TILEDISCARDED=2112;
	public static int ON_TURN=2113;
	public static int ON_NOTICE=2114;
	public static int ON_NOTICE2=2115;
	public static int ON_TILETAKEN=2116;
	public static int ON_WIN=2117;
	public static int ON_CLEARHAND=2118;
	public static int ON_FINISH=2119;
	
	public static int DO_POLL=6000;
	public static int DO_JOINROOM=6001;	
	public static int FORCESTART=6002;
	public static int ON_OTHERJOIN=6101;   
	public static int ON_JOININPROGRESS=6105;
    public static int ON_OTHERJOININPROGRESS=6106;
    public static int ON_OTHERLEAVEINPROGRESS=6107;
    public static int ON_ROOMCREATED=6108;
    public static int RESPONSE_LOGIN_OK=6110;
    public static int RESPONSE_LOGIN_DENIED=6111;
    public static int RESPONSE_ONNETWORKSTART=6112;    
    public static int RESPONSE_OK = 6120;
    public static int KEEP_ALIVE=6020;
    
    public static int PUB_DOLOGIN=7000;
    public static int PUB_GETROOM=7001;
    public static int PUB_CREATEROOM=7002;
    public static int PUB_QUITROOM=7003;
    public static int PUB_GETROOMCATEGORY=7004;
    public static int PUB_RESPONSE_ROOMLIST=7101;
    public static int PUB_RESPONSE_ROOMCATEGORY=7104;
    public static int PUB_RESPONSE_ROOMLISTUPDATE=7106;
    public static int PUB_ONPENDINGEVENT=7107;
    
    public static int PRV_DOLOGIN=8000;
    public static int PRV_CREATEROOM=8002;
    public static int PRV_GETCONTACT=8003;
    public static int PRV_ADDCONTACT=8004;
    public static int PRV_DELETECONTACT=8005;
    public static int PRV_REJECTJOINROOM=8006;
    public static int PRV_ACCEPTCONTACT=8007;
    public static int PRV_REJECTCONTACT=8008;
    public static int PRV_GETPENDINGEVENT=8010;
    public static int PRV_RESPONSE_CONTACTLIST=8103;
    public static int PRV_RESPONSE_CONTACTLISTUPDATE=8104;
    public static int PRV_ONINVITE=8105;
    public static int PRV_ONINVITEANSWERED=8106;
    public static int PRV_ONPENDINGEVENT=8107;
    public static int PRV_RESPONSE_TRANSOK=8108;
    public static int PRV_RESPONSE_CONTACTLISTADDDELETE=8109;

    
    public static int SEND_MESSAGETABLE=9001;
    public static int ON_MESSAGETABLE=9101;

    
    
    public static int NETWORKCOMMON_NEEDUPDATE=20010;
    public static int NETWORKCOMMON_TRANSPUB=20011;
    public static int NETWORKCOMMON_TRANSPRV=20012;
    public static int NETWORKCOMMON_REGISTERNICK=20020;
    public static int NETWORKCOMMON_RES_REGISTRATION=20100;
    public static int NETWORKCOMMON_RES_REGISTRATIONSTARTED=20101;
    public static int NETWORKCOMMON_RES_REGISTRATIONSUCCESSFUL=20102;
    public static int NETWORKCOMMON_RES_DUPLICATENICK=20103;


}
