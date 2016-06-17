package com.ccvee.cheliu.app.dao;


public class DBData {
	//数据库名称
	public static final String DATABASE_NAME="hlh.db";
	//数据库版本
	public static final int VERSION=1;
	
	//歌曲字段
	public static final String SONG_TABLENAME="song";
	public static final String SONG_ID="_id";
	public static final String SONG_ALBUMID="albumid";
	public static final String SONG_ARTISTID="artistid";
	public static final String SONG_NAME="name";
	public static final String SONG_DISPLAYNAME="displayName";
	public static final String SONG_NETURL="netUrl";
	public static final String SONG_DURATIONTIME="durationTime";
	public static final String SONG_SIZE="size";
	public static final String SONG_ISLIKE="isLike";
	public static final String SONG_LYRICPATH="lyricPath";
	public static final String SONG_FILEPATH="filePath";
	public static final String SONG_PLAYERLIST="playerList";
	public static final String SONG_ISNET="isNet";
	public static final String SONG_MIMETYPE="mimeType";
	public static final String SONG_ISDOWNFINISH="isDownFinish";
	
	//专辑字段
	public static final String ALBUM_TABLENAME="album";
	public static final String ALBUM_ID="_id";
	public static final String ALBUM_NAME="name";
	public static final String ALBUM_PICPATH="picPath";
	
	//歌手字段
	public static final String ARTIST_TABLENAME="artist";
	public static final String ARTIST_ID="_id";
	public static final String ARTIST_NAME="name";
	public static final String ARTIST_PICPATH="picPath";
	
	//播放列表字段
	public static final String PLAYERLIST_TABLENAME="playerList";
	public static final String PLAYERLIST_ID="_id";
	public static final String PLAYERLIST_NAME="name";
	public static final String PLAYERLIST_DATE="date";
	
	//下载信息字段
	public static final String DOWNLOADINFO_TABLENAME="downLoadInfo";
	public static final String DOWNLOADINFO_ID="_id";
	public static final String DOWNLOADINFO_URL="url";
	public static final String DOWNLOADINFO_FILESIZE="filesize";
	public static final String DOWNLOADINFO_NAME="name";
	public static final String DOWNLOADINFO_ARTIST="artist";
	public static final String DOWNLOADINFO_ALBUM="album";
	public static final String DOWNLOADINFO_DISPLAYNAME="displayName";
	public static final String DOWNLOADINFO_MIMETYPE="mimeType";
	public static final String DOWNLOADINFO_DURATIONTIME="durationTime";
	public static final String DOWNLOADINFO_COMPLETESIZE="completeSize";
	public static final String DOWNLOADINFO_FILEPATH="filePath";
	
	//多线程下载-每个线程信息字段
	public static final String THREADINFO_TABLENAME="threadInfo";
	public static final String THREADINFO_ID="_id";
	public static final String THREADINFO_STARTPOSITION="startPosition";
	public static final String THREADINFO_ENDPOSITION="endPosition";
	public static final String THREADINFO_COMPLETESIZE="completeSize";
	public static final String THREADINFO_DOWNLOADINFOID="downLoadInfoId";
	
}
