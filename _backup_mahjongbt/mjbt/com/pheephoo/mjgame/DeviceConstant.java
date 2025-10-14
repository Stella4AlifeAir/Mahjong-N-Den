package com.pheephoo.mjgame;

public class DeviceConstant {
	
	public static String CLIENT_CODE="se_k700";
	
	//registration message
	public static String MSG_REGISTRATION_TITLE1="Registration (1/3)";
	public static String MSG_REGISTRATION_TITLE2="Registration (2/3)";
	public static String MSG_REGISTRATION_TITLE3="Registration (3/3)";

	public static int BG_COLOR=1;//background color 0=white ; 1=green  

	//Series60Client
	//tile dimension
	public static int TILE_WIDTH=12;
	public static int TILE_HEIGHT=16;
	public static int STILE_WIDTH=8;
	public static int TILE_TOPEDGE_HEIGHT=6;
	public static int TILE_SMALLEDGE_HEIGHT=4;
	public static int SIDETILE_SIZE=9;
	public static int SIDETILE_SPACE=14;

	public static int TILE_LEFTPLAYER_POS_V=18;//vpos of leftplayertile

	public static int POINTER_OFFSET=8;//horizontal offset of the pointer
	public static int POINTER_HEIGHT=13;
	
	//wind position
	public static int WIND_STARTTXTPOS_V=90;
	public static int WIND_STARTTXTPOS_H=15;
	public static int WIND_POSITION_H=163;
	public static int WIND_HEIGHT=18;
	public static int WIND_WIDTH=18;
	public static int INDO_IMGHEIGHT=18;
	public static int WAITARROW_SIZE=10;//smaller arrow used in network playing
	
	public static int NUMOFTILE_POS_H=141;
	public static int NUMOFTILE_POS_V=170;
	public static int TABLE_POSITION_V=24;//start position of the tile on table
	public static int RIGHTTILE_POSITION_H=166;
	public static int DEALINGTEXT_POSITION_H=0;

	//notice screen
	public static int NOTICE_POSITION_V=160;
	public static int NOTICE_NOTICEITEM_W=24;
	public static int NOTICE_NOTICEITEM_H=13;

	//finish screen
	public static int FINISHTILE_INITPOS_V=20;
	public static int FINISHTILE_INITPOS_H=33;
	public static int FINISHTILE_SPACING_V=50;
	public static int FINISHFACE_INITPOS_V=11;
	public static int FINISHFACE_INITPOS_H=3;
	public static int FINISHTEXT_INITPOS_V=40;
	public static int FINISH_WINDHAND_H=156;
	
	//setting form screen
	public static int SETTINGFACE_WIDTH=25;
	public static int SETTING_PLAYER_VPOS=30;
	public static int SETTING_PLAYERPARAM_VPOS=39;
	public static int SETTING_PLAYERPARAM_HPOS=114;
	public static int SETTING_PLAYER_HEIGHT=40;

	public static int SETTING_SOUND_VPOS=70;
	public static int SETTING_SOUNDPARAM_VPOS=83;
	public static int SETTING_SOUND_HEIGHT=30;
	public static int SETTING_SOUNDPARAM_HPOS=107;
	
	public static int SETTING_MIN_VPOS=130;
	public static int SETTING_MINPARAM_VPOS=139;
	public static int SETTING_MINPARAM_HPOS=124;
	public static int SETTING_MIN_HEIGHT=25;
	
	public static int SETTING_MAX_VPOS=160;
	public static int SETTING_MAXPARAM_VPOS=167;
	public static int SETTING_MAX_HEIGHT=25;
	
	//progress bar
	public static int PROGRESSBAR_POS_V=191;
	public static int STATUSTEXT_POS_H=30;	
	//animated image
	public static int ANIM_WIDTH=176;
	public static int ANIM_HEIGHT=50;
	
	//player info screen
	public static int PLAYERINFO_FACEPOS_V=65;
	public static int PLAYERINFO_FACEPOS2_V=63;//for end score position
	public static int PLAYERINFO_FACEPOS3_V=61;//for end score position
	public static int PLAYERINFO_FACEPOS_H=15;
	public static int PLAYERINFO_TEXTPOS1_H=55;
	public static int PLAYERINFO_TEXTPOS2_H=160;
	public static int PLAYERINFO_TEXTPOS3_H=130;//user score position
	public static int PLAYERINFO_TEXTPOS_V=69;
	public static int PLAYERINFO_TEXTPOS2_V=68;//for end score position
	public static int PLAYERINFO_TEXTPOS3_V=66;//for end score position
	public static int PLAYERINFO_SPACING_V=35;

	//game info screen
	public static int GAMEINFO_NICKPOS_V=60;
	public static int GAMEINFO_NICKPOS_H=18;
	public static int GAMEINFO_SPACING_V=15;
	public static int GAMEINFO_SPACING_H=40;
	public static int GAMEINFO_SCOREPOS_V=75;
	public static int GAMEINFO_SCOREPOS_H=15;
	public static int GAMEINFO_NUM_OF_ROW=8;
	public static int GAMEINFO_PAGENUMPOS_V=195;
	public static int GAMEINFO_PAGENUMPOS_H=135;

	//private network canvas
	public static int PRIVATE_CONTACTLIST_STARTPOS_V=66;//start position of the tableinforation
	public static int PRIVATE_CONTACTLIST_NUM_OF_ROW_PERSCREEN=7;
	public static int PRIVATE_CONTACTLIST_SPACING_V=16;
	public static int PRIVATE_CONTACTLIST_POINTER_WIDTH=167;
	public static int PRIVATE_CONTACTLIST_POINTER_HPOS=0;
	public static int PRIVATE_CONTACTLIST_POINTER_HEIGHT=16;
	public static int PRIVATE_CONTACTLIST_ICONPOSV=4;	

	//public network canvas
	public static int BUTTON_HEIGHT=37;	//height of the menu button
	public static int PUBLIC_TABLELIST_STARTPOS_V=73;//start position of the tableinforation
	public static int PUBLIC_TABLELIST_NUMOFFSET=9;
	public static int PUBLIC_TABLELIST_NEWHPOS=-2;
	public static int PUBLIC_TABLELIST_SPACING_V=15;
	public static int PUBLIC_TABLELIST_POINTER_WIDTH=167;
	public static int PUBLIC_TABLELIST_POINTER_HPOS=0;
	public static int PUBLIC_TABLELIST_POINTER_HEIGHT=13;
	public static int PUBLIC_TABLELIST_NUM_OF_ROW_PERSCREEN=7;
	public static int PUBLICTABLE_POS1H=15;//horizontal pos of TableNum
	public static int PUBLICTABLE_POS2H=50;//horizontal pos of minimumNum
	public static int PUBLICTABLE_POS3H=85;
	public static int PUBLICTABLE_POS4H=130;
	public static int PUBLICTABLE_ICONPOSH=2;
	public static int PUBLICTABLE_ICONPOSV=2;	

	
}
