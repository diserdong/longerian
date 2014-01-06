package me.longerian.abc.fakeclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public final class Command
{
  // CONTACT
  public static final  byte[] MXCC_ADDPICTURE= { 'C','A'};
  public static final byte[] MXCC_NEW= {'C','N'};
  public static final byte[] MXCC_GETPICTURE= {'C','P'};
  public static final byte[] MXCC_REMOVEPICTURE= {'C','B'};
  public static final byte[] MXCC_GETCOUNT= {'C','G'};
  public static final byte[] MXCC_GETOIDLIST= {'C','U'};
  public static final byte[] MXCC_GETCONTACTLIST={'C','L'};
  public static final byte[] MXCC_GETCONTACTBATCH = {'C','C'};
  public static final byte[] MXCC_GETINFO= {'C','I'};
  public static final byte[] MXCC_SAVE= {'C','S'};
  public static final byte[] MXCC_DELETE= {'C','D'};

  public static final byte[] MXCC_ADDRINGTONE = {'C','E'};
  public static final byte[] MXCC_GETRINGTONE = {'C','H'};
  public static final byte[] MXCC_CLEARRINGTONE ={'C','J'};
  
  public static final byte[] MXCC_ADDTOFAVOR = {'C','F'};
  public static final byte[] MXCC_REMOVEFROMFAVOR ={'C','R'};
  
  public static final byte[] MXCC_GETGROUPIDS = {'C','0'};
  public static final byte[] MXCC_GETGROUPINFO = {'C','1'};
  public static final byte[] MXCC_GETGROUPLIST =  {'C','2'};
  public static final byte[] MXCC_CREATEGROUP =  {'C','3'};
  public static final byte[] MXCC_ADDTOGROUP =  {'C','4'};
  public static final byte[] MXCC_REMOVEFROMGROUP =  {'C','5'};
  public static final byte[] MXCC_DELETEGROUP =  {'C','6'};
  public static final byte[] MXCC_UPDATEGROUPINFO =  {'C','7'};
  public static final byte[] MXCC_UPDATEGROUPIDLISTBYID= {'C','8'};
  public static final byte[] MXCC_CREATECONTACTBATCH = {'C','9'};
  
  public static final byte[] MXCC_ADDPEOPLE = {'C','a'};
  public static final byte[] MXCC_ADDPHONE = {'C','b'};
  public static final byte[] MXCC_ADDCONTACTMETHOD = {'C','c'};
  public static final byte[] MXCC_ADDORGANIZATION = {'C','d'};
  
  public static final byte[] MXCC_MODIFYPEOPLE = {'C','e'};
  public static final byte[] MXCC_MODIFYPHONE ={'C','f'};
  public static final byte[] MXCC_MODIFYCONTACTMETHOD = {'C','g'};
  public static final byte[] MXCC_MODIFYORGANIZATION ={'C','h'};
  
  public static final byte[] MXCC_DELETEPHONE ={'C','i'};
  public static final byte[] MXCC_DELETECONTACTMETHOD ={'C','j'};
  public static final byte[] MXCC_DELETEORGANIZATION = {'C','k'};
  
  public static final byte[] MXCC_GETSIMSTATE = {'C','l'};
  public static final byte[] MXCC_QUERYONSIM = {'C','m'};
  public static final byte[] MXCC_UPDATETOSIM ={'C','n'};
  public static final byte[] MXCC_DELETEONSIM = {'C','o'};
  public static final byte[] MXCC_INSERTTOSIM = {'C','p'};
  
  public static final byte[] MXCC_DOCOMMAND = {'C','q'};
  public static final byte[] MXCC_GETGROUPLIST2={'C','r'};
  public static final byte[] MXCC_CREATEGROUPBATCH={'C','s'};
  public static final byte[] MXCC_CREATEMEMBERSIPBATCH={'C','t'};
  public static final byte[] MXCC_DELETEALL = {'C','u'};
  
  public static final byte[] MXCC_GETACCOUNTS = {'C','v'};
  public static final byte[] MXCC_SETACCOUNT = {'C','w'};
  public static final byte[] MXCC_ADDNOTE = {'C','x'};
  public static final byte[] MXCC_GETPHONEACCOUNT = {'C','y'};
  
  public static final byte[] MXCC_CREATECONTACTWITHACCOUNT = {'C','z'};
  public static final byte[] MXCC_CREATEGROUPWITHACCOUNT = {'C','!'};
  public static final byte[] MXCC_MODIFYCONTACTACCOUNT = {'C','@'};
  public static final byte[] MXCC_MODIFYGROUPACCOUNT   = {'C','#'};
  
  public static final byte[] MXCC_GETGROUPLIST3 = {'C','$'};
  public static final byte[] MXCC_CREATEGROUPBATCH2 = {'C','%'};
  public static final byte[] MXCC_CREATECONTACTBATCH2= {'C','^'};
  
  
  public static final byte[] MXCC_GETSTARCONTACT = {'C','*'};
  
  
  // CALL LOG
  public static final byte[] MXCL_GET= {'L','G'};// getCallLog by id
  public static final byte[] MXCL_DELETE={'L','D'};// delete calllog
  public static final byte[] MXCL_ADD={'L','A'}; // add
  public static final byte[] MXCL_GETOIDLIST={'L','L'}; // getidlist
  public static final byte[] MXCL_GETCOUNT={'L','C'};// getcount
  public static final byte[] MXCL_DELETEALL={'L','X'};// deleteAll
  public static final byte[] MXCL_GETCALLLOGLIST={'L','T'}; // get callloglist
  public static final byte[] MXCL_GETCALLLOGBATCH ={'L','B'};
  public static final byte[] MXCL_GETCALLLOGBATCH2 ={'L','E'};
  public static final byte[] MXCL_CREATECALLLOGBATCH ={'L','F'};

  // SMS
  public static final byte[] MXCS_SEND={'S','S'};// sendSMS
  public static final byte[] MXCS_SENDINNERSMS={'S','I'};// sendInnerSMS
  public static final byte[] MXCS_GETOIDLIST={'S','O'}; // getsmsidlist
  public static final byte[] MXCS_MARKREAD={'S','R'}; // markread
  public static final byte[] MXCS_DELETE={'S','D'}; // delete
  public static final byte[] MXCS_DELETE_CONVERSATION={'S','X'};// conversation delete
  public static final byte[] MXCS_BATCHDELETE={'S','B'}; // batch delete
  public static final byte[] MXCS_GET={'S','G'}; // getsms
  public static final byte[] MXCS_GETLIST={'S','L'}; // getlist
  public static final byte[] MXCS_MODIFYSTATUS={'S','A'};// modifystatus
  public static final byte[] MXCS_MOVE={'S','M'}; // movesms
  public static final byte[] MXCS_NEW={'S','N'}; // createsms
  public static final byte[] MXCS_COUNT={'S','C'}; // getcount
  public static final byte[] MXCS_UPDATESMS={'S','P'};// updateSMS
  public static final byte[] MXCS_DELETEBYNUMBER={'S','E'}; //
  public static final byte[] MXCS_GETSMSBATCH ={'S','F'};//
  public static final byte[] MXCS_GETUNREADCOUNT ={'S','H'};
  public static final byte[] MXCS_GETSMSBATCH2 ={'S','J'};
  public static final byte[] MXCS_SENDSMSBATCH ={'S','K'};
  public static final byte[] MXCS_GETSMSBATCH3 ={'S','Q'};
  public static final byte[] MXCS_CREATESMSBATCH ={'S','T'};
  public static final byte[] MXCS_GETSMSCALLBACK ={'S','U'};
  
  public static final byte[] MXCS_GETADDRESS = {'S','V'};
  public static final byte[] MXCS_DELETEIDLIST = {'S','W'};
  public static final byte[] MXCS_DELETEALLUNREAD = {'S','Y'};
  // SYSINFO
  public static final byte[] MXCA_DEVICEUID={'A','D'};
  public static final byte[] MXCA_WALLPAPER={'A','W'};
  public static final byte[] MXCA_RINGTONE={'A','R'};
  public static final byte[] MXCA_USERINFO={'A','U'};// device&user info
  public static final byte[] MXCA_STORAGEINFO={'A','S'}; // MemoryInfo
  public static final byte[] MXCA_SCREENSIZE={'A','Z'};// ScreenSize
  public static final byte[] MXCA_POWERINFO={'A','P'}; // PowerInfo
  public static final byte[] MXCA_PHONEINFO={'A','H'};// PhoneInfo
  public static final byte[] MXCA_MEMORYINFO={'A','M'};
  public static final byte[] MXCA_DeviceInfo={'A','I'};
  public static final byte[] MXCA_GetScreenShot ={'A','E'};
  public static final byte[] MXCA_GETSERVICEVERSION ={'A','F'};
  public static final byte[] MXCA_GETKERNELINFO ={'A','K'};
  public static final byte[] MXCA_CALL ={'A','C'};
  
  public static final byte[] MXCA_USBCAMERAMANAGER ={'A','G'};
  public static final byte[] MXCA_SAVEINFOTODEVICE = {'A','J'};
  public static final byte[] MXCA_GETINFOFROMDEVICE = {'A','N'};
  public static final byte[] MXCA_REMOVEINFO = {'A','O'};
  public static final byte[] MXCA_CLEARINFO = {'A','Q'};
  
  public static final byte[] MXCA_GC = {'A','V'};
  public static final byte[] MXCA_EXIT = {'A','X'};
  
  //SystemSetting  
  public static final byte[] MXCA_GETSETTING = {'A','Y'};
  public static final byte[] MXCA_WRITESETTING = {'A','3'};
  ////root operation  
  public static final byte[] MXCA_ROOT = {'A','0'};
  public static final byte[] MXCA_ISROOT = {'A','1'};
  public static final byte[] MXCA_ENABLESCREENSHOT = {'A','2'};
  public static final byte[] MXCA_GETSCREENSHOT2 = {'A','4'};
  public static final byte[] MXCA_GETSCREENSHOT3 = {'A','5'};
  
  public static final byte[] MXCA_SWITCHSERVICE={'A','6'};
  
  public static final byte[] MXCA_GETCPUINFO = {'A','7'};
  public static final byte[] MXCA_UNCRYPTFILE = {'A','8'};
  
  public static final byte[] MXCA_GETEXSDCARD = {'A','9'};
  public static final byte[] MXCA_GETEXSPACEINFO = {'A','a'};
  
  public static final byte[] MXCA_GetDPI = {'A','b'};
  public static final byte[] MXCA_GETWIFI = {'A','c'};
  
  public static final byte[] MXCA_GETIDENTITY = {'A','d'};
  
  public static final byte[] MXCA_QUERYCONTACTINTIME = {'A','e'};
  
  public static final byte[] MXCA_GET_MEM_EX ={'A','f'};
  public static final byte[] MXCA_GET_SD_SPECIALINFO = {'A','g'};
  //////////////////////Browser
  public static final byte[] MXCB_GETBOOKMARKLIST ={'B','A'};
  public static final byte[] MXCB_MODIFYBOOKMARK = {'B','B'};
  public static final byte[] MXCB_CREATEBOOKMARK ={'B','C'};
  public static final byte[] MXCB_CREATEBOOKMARKBATCH ={'B','D'};
  public static final byte[] MXCB_DELETEBOOKMARK = {'B','E'};
  public static final byte[] MXCB_DELETEBOOKMARKALL = {'B','F'};
  /////////////////////Alarm
  public static final byte[] MXCB_GETALARMS_ALL = {'B','G'};
  public static final byte[] MXCB_UPDATEALARM = {'B','H'};
  public static final byte[] MXCB_SETALARMSTATE = {'B','I'};
  public static final byte[] MXCB_ADDALARM = {'B','J'};
  public static final byte[] MXCB_CREATEALARMBATCH = {'B','K'};
  public static final byte[] MXCB_SETALARMALERT = {'B','L'};
  
  public static final byte[] MXCB_DELETEALARM = {'B','a'};
  public static final byte[] MXCB_DELETEALLALARM = {'B','b'};
  public static final byte[] MXCB_ISALARMSUPPORTED ={'B','c'};
  ///////////////////Calendar
  public static final byte[] MXCB_TIMETOTIME = {'B','M'};
  public static final byte[] MXCB_GETTIMEZONE = {'B','N'};
  public static final byte[] MXCB_GETTIMEZONES = {'B','O'};
  public static final byte[] MXCB_GETTIME2 = {'B','P'};
  public static final byte[] MXCB_GETCALENDAR = {'B','Q'};
  public static final byte[] MXCB_CLEARCALENDAR={'B','R'};
  public static final byte[] MXCB_ADDCALENDAR = {'B','S'};
  public static final byte[] MXCB_DELETECALENDAR = {'B','T'};
  public static final byte[] MXCB_GETCALENDAREVENTS = {'B','U'};
  public static final byte[] MXCB_ADDCALENDAREVENT = {'B','V'};
  public static final byte[] MXCB_ADDCALENDAREVENTS = {'B','W'};
  public static final byte[] MXCB_DELETECALENDAREVENT = {'B','X'};
  public static final byte[] MXCB_UPDATECALENDAREVENT = {'B','Y'};
  public static final byte[] MXCB_SETCALENDAREVENTSTATUS = {'B','Z'};
  
  ////
  public static final byte[] MXCB_GETURISETTING = {'B','d'};
  public static final byte[] MXCB_COMMITURISETTING = {'B','e'};
  
  public static final byte[]  MXCB_GETOPENGL_STATE = {'B','f'};
  
  
  public static final byte[]  MXCB_GETSENSOR = {'B','g'};
  public static final byte[]  MXCB_GET_OPENGL_STRING = {'B','h'};
  // apk
  public static final byte[] MXCP_GETAPKALL={'P','A'};
  public static final byte[] MXCP_INSTALLAPK ={'P','I'};
  public static final byte[] MXCP_UNINSTALLAPK ={'P','U'};
  public static final byte[] MXCP_GETAPKLIST ={'P','S'};
  public static final byte[] MSCP_GETAPKINFO ={'P','F'};
  public static final byte[] MXCP_ISPACKAGEEXSIT ={'P','B'};
  public static final byte[] MXCP_PACAKGEBAK ={'P','C'};
  public static final byte[] MXCP_PACKAGESOME={'P','D'};
  public static final byte[] MXCP_GETPACKAGECOUNT={'P','E'};
  public static final byte[] MXCP_GETAPKAKLL2={'P','G'};
  public static final byte[] MXCP_GETAPKINFO2={'P','H'};
  
  public static final byte[] MXCP_GETAPKSCANVERSION={'P','J'};
  public static final byte[] MXCP_UPDATEAPKSCAN={'P','K'};
  public static final byte[] MXCP_APKSCAN = {'P','L'};
  
  public static final byte[] MXCP_GETAPKALL3 ={'P','M'};
  public static final byte[] MXCP_GETAPKINFO3 = {'P','N'};
  
  public static final byte[] MXCP_PREPARE_FOR_ROOT_BACKUP = {'P','P'};
  public static final byte[] MXCP_PREPARE_FOR_ROOT_RESTORE = {'P','Q'};
  public static final byte[] MXCP_COMMIT_RESTORE = {'P','R'};
  
  public static final byte[] MXCP_POWERINSTALL ={'P','T'};
  public static final byte[] MXCP_POWERUNINSTALL ={'P','V'};
  
  public static final byte[] MXCP_GETAPKSECURE_LIST = {'P','W'};
  public static final byte[] MXCP_GETAPKSECURE_INFO = {'P','X'};
  
  public static final byte[] MXCP_CREATESHORT_CUT = {'P','Y'};
  public static final byte[] MXCP_REMOVE_SHORTCUT = {'P','Z'};
  
  public static final byte[] MXCP_PARSERPACKAGE = {'P','a'};
  public static final byte[] MXCP_PARSERPACKAGES = {'P','b'};
  
  public static final byte[] MXCP_GETAPKSECURE_LIST2 = {'P','c'};
  public static final byte[] MXCP_GETAPKSECURE_INFO2 = {'P','d'};
  
  public static final byte[] MXCP_GETSIGNATURE_INFO = {'P','e'};
  public static final byte[] MXCP_GETSIGNATURE_LIST = {'P','f'};
  //chat
  public static final byte[] MXCH_STARTCHAT ={'H','S'};
  public static final byte[] MXCH_RECONNECT ={'H','R'};
  public static final byte[] MXCH_SENDSMS ={'H','M'};
  public static final byte[] MXCH_GETSMS ={'H','G'};
  public static final byte[] MXCH_ENDCHAT ={'H','E'};
  public static final byte[] MXCH_CONNECTCHCEK ={'H','K'};
  public static final byte[] MXCH_CONNECTCHCEKCLIENT={'H','K','V','U'};
  // file
  public static final byte[] MXCF_GETROOT ={'F','A'};
  public static final byte[] MXCF_CREATEFILE={'F','B'};
  public static final byte[] MXCF_CREATEDIRECTORY ={'F','C'};
  public static final byte[] MXCF_ISFILEEXSIST ={'F','D'};
  public static final byte[] MXCF_GETINFO ={'F','E'};
  public static final byte[] MXCF_GETATTR ={'F','F'};
  public static final byte[] MXCF_SETATTR ={'F','G'};
  public static final byte[] MXCF_DELETEFILE ={'F','H'};
  public static final byte[] MXCF_DELETEFOLDER ={'F','I'};
  public static final byte[] MXCF_RENAMEFILE ={'F','J'};
  public static final byte[] MXCF_RENAMEDIC ={'F','K'};
  public static final byte[] MXCF_COPYFILE ={'F','L'};
  public static final byte[] MXCF_COPYFOLDER ={'F','M'};
  public static final byte[] MXCF_MOVEFILE={'F','N'};
  public static final byte[] MXCF_MOVEFOLDER ={'F','O'};
  
  public static final byte[] MXCF_LISTFILES ={'F','P'};
  public static final byte[] MXCF_GETCHILDAMOUT ={'F','Q'};
	
  public static final byte[] MXCF_UPLOADFILE={'F','S'};
  public static final byte[] MXCF_DONWLOADFILE={'F','R'};
  
  public static final byte[] MXCF_GETSDCARD ={'F','T'};
  public static final byte[] MXCF_LISTFILE_FILE ={'F','1'};
  public static final byte[] MXCF_LISTFILE_DIC ={'F','2'};
  public static final byte[] MXCF_LISTFILE_JNI ={'F','3'};
  public static final byte[] MXCF_GETSDCARDSTATE = {'F','4'};
  //used for fileBufferedProvider
  public static final byte[] MXCF_OPEN = {'F','5'};
  public static final byte[] MXCF_SEEK = {'F','6'};
  public static final byte[] MXCF_SKIP = {'F','7'};
  public static final byte[] MXCF_READ = {'F','8'};
  public static final byte[] MXCF_CLOSE = {'F','9'};
  //used for FileRunnable
  public static final byte[] MXCF_START = {'F','a'};
  
  public static final byte[] MXCF_ROOT_LISTFILE = {'F','b'};
  public static final byte[] MXCF_ROOT_GRANTEPERMISSION={'F','c'};
  
  public static final byte[] MXCF_GETCACHE ={'F','d'};
  public static final byte[] MXCF_CLEARMYCACHE={'F','e'};
  
  public static final byte[] MXCF_FINDFILES = {'F','f'};
  public static final byte[] MXCF_FINDFILELIST = {'F','g'};
  public static final byte[] MXCF_ROOTTOOL_SETFILE_PERMISSION = {'F','h'};
  public static final byte[] MXCF_ROOTTOOL_SETDIR_PERMISSION = {'F','i'};
  public static final byte[] MXCF_ROOTTOOL_SETDIRALL_PERMISSION = {'F','j'};
  
  public static final byte[] MXCF_CREATEFILEINFLASH = {'F','k'};
  public static final byte[] MXCF_ENCRYPTFILE = {'F','l'};
  
  public static final byte[] MXCF_GETFILEFLAGBATCH = {'F','m'};
  public static final byte[] MXCF_UNZIPTOFOLDER    = {'F','n'};
  public static final byte[] MXCF_UNZIPENTRYTOFOLDER={'F','o'};
  public static final byte[] MXCE_UNZIPENTRYSTOFOLDER={'F','p'};
  
  public static final byte[] MXCF_CREATEDIRINFLASH = {'F','q'};
  
  //image
  
  public static final byte[] MXCM_INSERTIMAGE ={'M','I'};
  public static final byte[] MXCM_REMOVEIMAGE ={'M','R'};
  public static final byte[] MXCM_GETIDLIST ={'M','L'};
  public static final byte[] MXCM_BATCHGET ={'M','B'};
  public static final byte[] MXCM_GETINFO ={'M','O'};
  public static final byte[] MXCM_GETTHUMB ={'M','T'};
  public static final byte[] MXCM_GETIMAGE ={'M','M'};
  public static final byte[] MXCM_GETCOUNT ={'M','C'};
  public static final byte[] MXCM_MODIFYIMAGE ={'M','A'};
  public static final byte[] MXCM_GETTHUMBBATCH ={'M','D'};
  public static final byte[] MXCM_GETIMAGEID ={'M','E'};
  public static final byte[] MXCM_BATCHGET2 ={'M','N'};

 
  public static final byte[] MXCM_SETWALLPAPERPATH ={'M','F'};
  public static final byte[] MXCM_SETWALLPAPER ={'M','S'};
  public static final byte[] MXCM_GETWALLPAPER ={'M','G'};
  public static final byte[] MXCM_CLEARIMAGEDIR ={'M','H'};
  public static final byte[] MXCM_SETWALLPAPERAUTOSIZE ={'M','J'};
  public static final byte[] MXCM_SETWALLPAPERAUTOSIZEPATH={'M','K'};
  public static final byte[] MXCM_GETBITMAPSIZE={'M','0'};
  public static final byte[] MXCM_GETBITMAPSIZE_PATH ={'M','1'};
  
  public static final byte[] MXCM_GETIMAGETOTALSIZE={'M','2'};  
  
  public static final byte[] MXCM_BATCHDELETE = {'M','3'};
  public static final byte[] MXCM_REPLACEIMAGE = {'M','4'};
  public static final byte[] MXCM_REPLACEIMAGE2 = {'M','5'};
  
  public static final byte[] MXCM_ROTATEIMAGE = {'M','6'};
  
  public static final byte[] MXCM_GETMEDIACOUNT = {'M','7'};
  
  public static final byte[] MXCM_GETPHOTOTIMESTAMP = {'M','8'};
  public static final byte[] MXCM_SETPHOTOTIMESTAMP = {'M','9'};
  
  public static final byte[] MXCM_PHOTOTASKSTART = {'M', 'a'};
  public static final byte[] MXCM_PHOTOTASKFAILED = { 'M','b'};
  public static final byte[] MXCM_PHOTOTASKFINISH = {'M', 'c'};
  
  public static final byte[] MXCM_BATCHGET3 = {'M','d'};
  public static final byte[] MXCM_GETINFO2  = {'M','e'};
  
  public static final byte[] MXCM_SETIMAGE_ROTATE ={'M','f'};
  
  //audio 
  public static final byte[] MXCU_INSERTAUDIO ={'U','I'};
  public static final byte[] MXCU_MXCU_REMOVEAUDIO ={'U','R'};
  public static final byte[] MXCU_GETIDLIST={'U','L'};
  public static final byte[] MXCU_GETAUDIOBATCH ={'U','B'};
  public static final byte[] MXCU_GETAUDIOINFO ={'U','A'};
  public static final byte[] MXCU_GETAUDIOCOUNT ={'U','C'};
  public static final byte[] MXCU_GETAUDIOPATH ={'U','P'};
  public static final byte[] MXCU_MODIFYAUDIO ={'U','M'};
  public static final byte[] MXCU_TOTALAUDIOSIZe ={'U','T'};
  
  public static final byte[] MXCU_GETAUDIOID ={'U','D'};
  public static final byte[] MXCU_SETFLAG ={'U','E'};
  public static final byte[] MXCU_SETRINGTONEPATH ={'U','H'};
  public static final byte[] MXCU_SETRINGTONE ={'U','S'};
  public static final byte[] MXCU_GETRINGTONE ={'U','G'};
  public static final byte[] MXCU_MODIFYAUDIOFLAG ={'U','F'};
  
  public static final byte[] MXCU_GETALBUMSART ={'U','N'};
  public static final byte[] MXCU_GETALBUMSARTALL={'U','O'};  
  //
  public static final byte[] MXCU_GETPLAYLISTLISt ={'U','0'};
  public static final byte[] MXCU_GETPLAYLIST ={'U','1'};
  public static final byte[] MXCU_GETSYSTEMLIST={'U','2'};
  public static final byte[] MXCU_GETPLAYLISTMEMBER ={'U','3'};
  public static final byte[] MXCU_CREATEPLYALIST ={'U','4'};
  public static final byte[] MXCU_DELETAPLAYLIST ={'U','5'};
  public static final byte[] MXCU_RENAMEPLAYLIST ={'U','6'};
  
  public static final byte[] MXCU_ADDPLAYLISTMEMBER ={'U','7'};
  public static final byte[] MXCU_CLEARPLAYLISTMEMBER ={'U','8'};
  public static final byte[] MXCU_SETPLAYLISTMEMBER ={'U','9'};  
  
  public static final byte[] MXCU_MODIFYAUDIOFLAG2 ={'U','J'};
  
  public static final byte[] MXCU_GETSPECIALCOUNT ={'U','K'};
  
  public static final byte[] MXCU_GETLATESTAUDIO ={'U','Q'};
  
  public static final byte[] MXCU_BATCHDELETE = {'U','U'};
  
  public static final byte[] MXCU_INSERTTOPLAYLIST = {'U','V'};
  public static final byte[] MXCU_DELETEFROMPLAYLIST = {'U','W'};
  
  public static final byte[] MXCU_INSERTAUDIO2 = {'U','X'};
  
  /////ebook
  public static final byte[] MXCE_ADDBOOK ={'E','A'};
  public static final byte[] MXCE_GETEBOOKIDLIST ={'E','I'};
  public static final byte[] MXCE_GETEBOOKINFO ={'E','O'};
  public static final byte[] MXCE_GETEBOOKBATCH ={'E','B'};
  public static final byte[] MXCE_SCANFILE ={'E','S'};
  public static final byte[] MXCE_REMOVEEBOOK ={'E','R'};
  public static final byte[] MXCE_GETEBOOKCOUNT ={'E','C'};
  public static final byte[] MXCE_MODIFYEBOOK ={'E','M'};
  public static final byte[] MXCE_MARKFILEMODIFY ={'E','F'};
  public static final byte[] MXCE_MARKREAD ={'E','D'};
  public static final byte[] MXCE_BOOKTOTALSIZE={'E','E'};
  
  public static final byte[] MXCE_BATCHDELETE ={'E','G'};
  //video
  public static final byte[] MXCV_ADDVIDEO ={'V','A'};
  public static final byte[] MXCV_GETVIDEOINFOBATCH ={'V','B'};
  public static final byte[] MXCV_GETVIDEOCOUNT={'V','C'};
  public static final byte[] MXCV_GETVIDEOIDLIST ={'V','D'};
  public static final byte[] MXCV_GETVIDEOINFO ={'V','I'};
  public static final byte[] MXCV_REMOVEVIDEO={'V','R'};
  public static final byte[] MXCV_MODIFYVIDEO ={'V','M'};
  public static final byte[] MXCV_GETTHUMB={'V','T'};
  public static final byte[] MXCV_TOTALVIDEOSIZE ={'V','S'};
  public static final byte[] MXCV_GETVIDEOID ={'V','E'};
  public static final byte[] MXCV_BATCHDELTETE={'V','F'};
  
  public static final byte[] MXCV_GETTOTALSIZEINPATH = {'V','G'};
  
  public static final byte[] MXCV_GETVIDEOCOUNTX     = {'V','H'};  
  
  public static final byte[]  MXCV_GETVIDEOINFO2     = {'V','J'};
  public static final byte[] MXCV_GETVIDEOINFOBATCH2 = {'V','K'};
  public static final byte[] MXCV_GETMEDIADEFAULTPATH= {'V','L'};
  /////process 
  public static final byte[] MXCG_GETPROCESSCOUNT = {'G','A'};
  public static final byte[] MXCG_GETPRCESSIDLIST = {'G','B'};
  public static final byte[] MXCG_GETPROCESSBYID = {'G','C'};
  public static final byte[] MXCG_GETPROCESSLIST = {'G','D'};
  public static final byte[] MXCG_KILLPROCESS = {'G','E'};
  
  ////////Root
  public static final byte[] MXCR_GETPERMISSIONLIST = {'R','A'};
  public static final byte[] MXCR_MODIFYPERMISSON   = {'R','B'};
  public static final byte[] MXCR_DELETEPERMISSION  = {'R','C'};
  public static final byte[] MXCR_GETONEKEYROOSTATE = {'R','D'};
  public static final byte[] MXCR_UNROOT            = {'R','E'};
  
  public static final byte[] MXCR_MOUNT_DIVICE_RW   = {'R','F'};
  public static final byte[] MXCR_MOUNT_DIVICE_RO   = {'R','G'};
  public static final byte[] MXCR_REMOVE_SYSTEM_APP = {'R','H'};
  public static final byte[] MXCR_GET_ROOT_VERSION  = {'R','I'};
  public static final byte[] MXCR_GET_PERMISSIONLIST2 = {'R','J'};
  public static final byte[] MXCR_CANROOT  = {'R','K'};
  
  /////////QXin
  public static final byte[] MXCQ_START_QXIN = {'Q','A'};
  public static final byte[] MXCQ_INIT_AUTO = {'Q','B'};
  public static final byte[] MXCQ_INIT_PHONE = {'Q','C'};
  public static final byte[] MXCQ_INIT_VERICODE = {'Q','D'};
  
  public static final byte[] MXCQ_HASINIT = {'Q','E'};
  public static final byte[] MXCQ_HASCONNECT = {'Q','F'};
  public static final byte[] MXCQ_CONNECT = {'Q','G'};
  public static final byte[] MXCQ_DISCONNECT = {'Q','H'};
  public static final byte[] MXCQ_RECONNECT = {'Q','I'};
  public static final byte[] MXCQ_SEND_MSG  = {'Q','J'};
  public static final byte[] MXCQ_SEND_STRMSG = {'Q','K'};
  public static final byte[] MXCQ_MARK_READ = {'Q','L'};
  
  public static final byte[] MXCQ_SEND_DATAMSG = {'Q','M'};
  public static final byte[] MXCQ_SEND_VERIFY = {'Q','N'};
  
  public static final byte[] MXCQ_SET_USERINFO = {'Q','O'};  
  public static final byte[] MXCQ_GETALLFRIENDS = {'Q','P'};
  public static final byte[] MXCQ_GETMYINFO = {'Q','Q'};
  public static final byte[] MXCQ_GETUSERINFO ={'Q','R'};
  public static final byte[] MXCQ_DELETEFRIEND = {'Q','S'};
  public static final byte[] MXCQ_GETALLCACHFRIENDS = {'Q','T'};
  
  public static final byte[] MXCQ_GETQXINACCOUNT = {'Q','U'};
  public static final byte[] MXCQ_SETQXINACCOUNT = {'Q','V'};
  
  public static final byte[] MARKALLREAD = {'Q','W'};
  public static final byte[] DownloadFILE = {'Q','X'};
  public static final byte[] MXCQ_GETENCRYPTKEY = {'Q','Y'};
  public static final byte[] MXCQ_CHECKFRIENDS = {'Q','Z'};
  
  public static final byte[] MXCQ_START_RECONNECT = {'Q','a'};
  public static final byte[] MXCQ_GETGRAFFITI = {'Q','b'};
  public static final byte[] MXCQ_SETUSERNAME = {'Q','c'};
  
  //im secure
  public static final byte[] MXCE_START_SERVICE = {'I','A'};
  public static final byte[] MXCE_STOP_SERVICE = {'I','B'};
  public static final byte[] MXCE_GETSERVICE_STATE = {'I','C'};
  public static final byte[] MXCE_CANRUNASROOT = {'I','D'};
  
  public static final byte[] MXCE_FINDALLAPP_CACHE = {'I','E'};
  public static final byte[] MXCE_CLEARCACHE = {'I','F'};
  public static final byte[] MXCE_FINDALLATUOBOOT = {'I','G'};
  public static final byte[] MXCE_SETAUTOBOOT_STATE = {'I','H'};
  public static final byte[] MXCE_GETALLRUNNING_TASK = {'I','I'};
  public static final byte[] MXCE_KILLTASK = {'I','J'};
  public static final byte[] MXCE_REQUESTFOR_ROOT = {'I','K'};
  public static final byte[] MXCE_CLEARCACHEBATCH  = {'I','L'};
  public static final byte[] MXCE_SET_AUTOBOOT_BATCH = {'I','M'};
  public static final byte[] MXCE_CLEARALLCACHE = {'I','N'};
  
  //viru scan
  public static final byte[] MXCE_INITFOR_VIRUSCAN = {'I','O'};
  public static final byte[] MXCE_FREE_SCANNER = {'I','P'};
  
  public static final byte[] MXCE_SCANINSTALLPKGS= {'I','Q'};
  public static final byte[] MXCE_SCANSDCARDAPKS = {'I','R'};
  public static final byte[] MXCE_SCANALL = {'I','S'};
  public static final byte[] MXCE_SCANAPKS = {'I','T'};
  public static final byte[] MXCE_SCANPKGS = {'I','U'};
  
  public static final byte[] MXCE_PAUSESCAN = {'I','V'};
  public static final byte[] MXCE_CANCLE    = {'I','W'};
  public static final byte[] MXCE_CONTINUESCAN = {'I','X'};
  
  public static final byte[] MXCE_CONNECTOSERVER = {'I','Y'};
  
 
   
  public static final byte[] HEADER={0,0,0,1};
  public static final byte[] EMPTY= { 0, 0, 0, 32 };
  public static final byte[] EOF = {0,0,0,0};
  public static final byte[] STRING_HEADER =  { (byte) -1, (byte) -2 };
    
  public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public  byte[] serialize(String cmd,ArrayList<byte[]> params)
  {
    // [Total cmd length],[CMD],[PHONE Len],[NUMBER],[MSG Len],[MSG]
    // 45, SS, 11,13588000000,29,this is just a test message.
    // 4bytes, 2bytes,4bytes,dynamicbytes,4bytes,dynamicbytes.
    if(cmd == null || cmd.length() == 0)
    {
      return null;
    }
    // byte[] _params;
    int totalLength=8;
    byte[] cmdBytes=new byte[4];
    cmdBytes=cmd.getBytes();
    byte[] totalCmdLength=int2bytes(cmdBytes.length);
    byte[] ret=sumBytes(totalCmdLength,cmdBytes);

    // ArrayList<byte[]> _tempRetParams=new ArrayList<byte[]>();
    if(params != null && params.size() > 0)
    {
      int length=params.size();
      // //item=length+param
      // for(int i=0;i<length;i++){
      // byte[] _tempParamBytesLength=new byte[4];
      // byte[] _tempParamBytes=params.get(i);
      // _tempParamBytesLength=int2bytes(_tempParamBytes.length);
      // //param item
      // byte[] paramItem=sumBytes(_tempParamBytesLength,_tempParamBytes);
      // totalLength+=paramItem.length;
      // _tempRetParams.add(paramItem);
      // }

      // reset total length
      for(int i=0;i < length;i++)
      {
        totalLength+=params.get(i).length;
      }
      totalLength+=length * 4;

      // reset return value
      ret=null;
      ret=new byte[totalLength];
      byte[] former=sumBytes(int2bytes(totalLength),cmdBytes);
      System.arraycopy(former,0,ret,0,8);

      // add params
      for(int i=8;i < totalLength;)
      {
        byte[] _tempParamBytesLength=new byte[4];
        byte[] _tempParamBytes=params.get(i);
        _tempParamBytesLength=int2bytes(_tempParamBytes.length);
        // add param item
        byte[] paramItem=sumBytes(_tempParamBytesLength,_tempParamBytes);
        System.arraycopy(paramItem,0,ret,i,paramItem.length);
        i+=paramItem.length;
      }
    }

    return ret;
  }

  
  public static byte[] serializeRet(int result ,ArrayList<byte[]> params)
  {
	  int totalLength=8;
	  byte[] cmdBytes=int2bytes(result);
	  byte[] ret = null;
	  if(params != null && params.size() > 0)
	    {
	      int length=params.size();
	      // //item=length+param
	      // for(int i=0;i<length;i++){
	      // byte[] _tempParamBytesLength=new byte[4];
	      // byte[] _tempParamBytes=params.get(i);
	      // _tempParamBytesLength=int2bytes(_tempParamBytes.length);
	      // //param item
	      // byte[] paramItem=sumBytes(_tempParamBytesLength,_tempParamBytes);
	      // totalLength+=paramItem.length;
	      // _tempRetParams.add(paramItem);
	      // }

	      // reset total length
	      for(int i=0;i < length;i++)
	      {
	        totalLength+=params.get(i).length;
	      }
	      totalLength+=length * 4;

	      // reset return value
	      
	      ret=new byte[totalLength];
	      System.arraycopy(Command.int2bytes(totalLength),0,ret,0,4);
	      System.arraycopy(cmdBytes, 0, ret, 4, 4);

	      // add params
	      int currentPos=8;
	      int paramsSize=params.size();
	      for(int i=0;i < paramsSize;i++)
	      {
	        byte[] tmp = params.get(i);       
	        System.arraycopy(Command.int2bytes(tmp.length),0,ret,currentPos,4);
	        currentPos+=4;
	        System.arraycopy(tmp, 0, ret, currentPos, tmp.length);
	        currentPos+=tmp.length;
	      }
	    }
	    else
	    {
	    	byte[] totalCmdLength=Command.int2bytes(8);
	    	ret=sumBytes(totalCmdLength,cmdBytes);
	    	//ret = cmdBytes;
	    }
	    
	    return ret;
  }
  

  public static int bytes2int(byte[] b)
  {
    // byte[] b=new byte[]{1,2,3,4};
    int mask=0xff;
    int temp=0;
    int res=0;
    for(int i=0;i < 4;i++)
    {
      res<<=8;
      temp=b[i] & mask;
      res|=temp;
    }
    return res;
  }
  
  public static int bytes2int(byte[] b ,int index)
  {
	  int mask=0xff;
	  int temp=0;
	  int res=0;
	  for(int i=index;i < index+4;i++)
	  {
		  res<<=8;
		  temp=b[i] & mask;
		  res|=temp;
	  }
	  return res;
  }

  public static byte[] int2bytes(int num)
  {
    byte[] b=new byte[4];
    for(int i=0;i < 4;i++)
    {
      b[i]=(byte) (num >>> (24 - i * 8));
    }
    return b;
    
  }

  public static byte[] sumBytes(byte[] first,byte[] second)
  {
    if(first == null)
    {
      return second;
    }else if(second == null)
    {
      return first;
    }
    int firstLength=first.length;
    int secondLength=second.length;
    byte[] ret=new byte[firstLength + secondLength];
    System.arraycopy(first,0,ret,0,firstLength);
    System.arraycopy(second,0,ret,firstLength,secondLength);
    return ret;
  }

  public static byte[] cutBytes(byte[] src,int begin,int length)
  {
    if(src == null)
    {
      return null;
    }
    byte[] ret=new byte[length];
    System.arraycopy(src,begin,ret,0,length);
    return ret;
  }

  public static byte[] cutBytes(byte[] src,int begin)
  {
    if(src == null)
    {
      return null;
    }
    byte[] ret=new byte[src.length - begin];
    System.arraycopy(src,begin,ret,0,src.length - begin);
    return ret;
  }

  public static byte[] cutBytes(String src)
  {
	 
    byte[] result=null;
    if(src != null && src.length() > 0)
    {
      /*
      try
      {
        result=Command.cutBytes(src.getBytes("Unicode"),2);
      }catch(UnsupportedEncodingException e)
      {
        Log.e(LogTag.iMolo_WORKER,e.getMessage(),e);
        return Command.EMPTY;
      }*/
      try{	
      result = src.getBytes();      
      } catch(Exception e)
      {
    	  result  = Command.EMPTY;
      }
    }else
    {     
        result=Command.EMPTY;     
    }
   
    return result;  
  }

  public static long bytes2long(byte[] b)
  {
    int mask=0xff;
    int temp=0;
    long res=0;
    for(int i=0;i < 8;i++)
    {
      res<<=8;
      temp=b[i] & mask;
      res|=temp;
    }
    return res;
  }
  
  public static long bytes2long(byte[] b,int index)
  {
	  int mask=0xff;
	  int temp=0;
	  long res=0;
	  for(int i=index;i < index+8;i++)
	  {
		  res<<=8;
		  temp=b[i] & mask;
		  res|=temp;
	  }
	  return res;
  }

  public static byte[] long2bytes(long num)
  {
    byte[] b=new byte[8];
    for(int i=0;i < 8;i++)
    {
      b[i]=(byte) (num >>> (56 - i * 8));
    }
    return b;
  }

  public static String IpIntToString(int iIP)
  {
    try
    {
      String str="";
      str+=String.valueOf((iIP & 0x000000FF)) + ".";
      str+=String.valueOf(((iIP & 0x0000FF00) >>8)) + ".";
      str+=String.valueOf(((iIP & 0x00FF0000) >>16)) + ".";
      str+=String.valueOf(((iIP & 0xFF000000) >>>24));
      return str;
    }catch(Exception e)
    {
      return "";
    }
  }
  
  public static String byte2String_Fast(byte[] src)
  {
	  int index = 0;
	  if(src.length==4)
	  {
	      int value=src[index+3] + (src[index] + src[index+1] + src[index+2]) * 256;
	      if(value == 32)
	      return null;
	  }
	 
	  if(src[index]==-1 && src[index+1]==-2)
	  {	    	
		  index+=2;
	  }
	  char[] ch = new char[(src.length-index)>>1];
	  for(int i = 0 ;i<ch.length;i++)
	  {
		  ch[i] = (char)((src[i*2+index])+(src[i*2+index+1])*256);
	  }
	  return String.copyValueOf(ch);
  }
  
  public static String byte2String_Fast(byte[] src,int offset,int length)
  {
	  int index = offset;
	  if(src.length==4)
	  {
	      int value=src[index+3] + (src[index] + src[index] + src[index+2]) * 256;
	      if(value == 32)
	      return null;
	  }
	  if(src[index]==-1 && src[index+1]==-2)
	  {
		  length-=2;
		  index+=2;
		  
	  }
	  char[] ch = new char[length/2];
	  for(int i = 0 ;i<(ch.length);i++)
	  {
		  ch[i] = (char)(((int) src[i*2+index])+((int)src[i*2+index+1])*256);
	  }
	  return String.copyValueOf(ch);
  }


  
  public static boolean isEmpty(String str)
  {
    if(str == null || str.equals("") )
      return true;
    else
      return false;
  }
  
  public  static Date StringDateToDate(String date)
  {
	  Date _date = null;
	  try {
			  _date = sdf.parse(date);
	   } catch (Exception e) {
		  _date = new Date();
		  //e.printStackTrace();
		}
	   return _date;
  }
  
  public static final Date _date = new Date();
    
  public synchronized static String DateToString(long time)
  {
	  _date.setTime(time);
	  return sdf.format(_date);
  }
  
  public static int strCmp(String str,String str2)
  {
	  if(str==null && str2 == null) return 0;
	  if(str==null) return -1;
	  if(str2==null) return 1;
	  if(str.equals(str2)) return 0;
	  else return -1;
  }
  
  
  public static boolean ByteStartWith(byte[] src,byte[] src2)
  {
	  if(src==null || src2==null)
		  return false;
	  if(src2.length> src.length)
		  return false;
	  for(int i = 0 ;i<src2.length;i++)
	  {
		  if(src[i]!= src2[i])
			  return false;
	  }
	  return true;
  }
  
  public static boolean BytesContain(byte[] src,byte[] src2 ,int length)
  {
	  if(src==null || src2==null)
	  {
		  return false;
	  }
	  if(length>src.length)
	  {
		length = src.length;  
	  }
	  	  
	  if(length<src2.length)
	  {
		  return false;
	  }
	  
	 
	  for(int i = 0 ;i <=(length-src2.length);i++)
	  {
		  if(src[i] != src2[0])
		  {
			  continue;
		  }
		  boolean equals  = true;
		  for(int j = 1 ; j <src2.length;j++)
		  {
			  if(src[j+i]!=src2[j])
			  {
				  equals = false;
				  break;
			  }
		  }
		  if(equals)
		  {
			  return true;
		  }
	  }
	  return false;
  }
  
  public static String ByteArrayToString(byte[] bytes ,int offset , int length)
  {
	  StringBuilder sb = new StringBuilder();
	  sb.append('{');
	  for( int i = 0 ;i < length ;i++ )
	  {
		  if( i != 0 )
			  sb.append(',');
		  sb.append( (   bytes[offset+i] + 256) % 256);
		  
	  }
	  sb.append('}');
	  
	  return sb.toString();
  }
  
}
