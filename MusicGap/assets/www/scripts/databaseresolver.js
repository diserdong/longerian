var TAG = "store to database: ";

var selections = [];
var where = 1;

var db = window.openDatabase("MusicLib", "1.0", "MusicLib DB", 99 * 1024 * 1024);//database's size should be less than 99KB, otherwise db cannot be created, according to the experiment

function storeToDatabase() {
    db.transaction(populateDB, createDBError, createDBSuccess);
}

//Populate the database 
//
function populateDB(tx) {
	console.log("populateDB...");
	tx.executeSql('DROP TABLE IF EXISTS MUSIC');
	tx.executeSql('CREATE TABLE IF NOT EXISTS MUSIC (name, path, folder, fullpath)');
	insertData(tx);
}

//Insert data
function insertData(tx) {
	var i;
	for(i = 0; i < filesFullPath.length; i++)
		tx.executeSql('INSERT INTO MUSIC (name, path, folder, fullpath) VALUES ("' + extractName(filesFullPath[i]) + '", "' + extractPath(filesFullPath[i], "/mnt/sdcard/") + '", "' + extractFolder(filesFullPath[i], "/mnt/sdcard/") + '", "' + filesFullPath[i] + '")');
	console.log("data inserted...");
}

//Transaction error callback
//
function createDBError(err) {
	console.log("in createErrorCB processing SQL: " + err.code);
}

//Transaction success callback
//
function createDBSuccess() {
	console.log("in createSuccessCB");
}

function queryFolder(col) {
	selections = col;
	db.transaction(onQueryFolderStart, queryFolderError);
}

function onQueryFolderStart(tx) {
	tx.executeSql('SELECT DISTINCT ' + selections.toString() + ' FROM MUSIC', [], queryFolderSuccess, queryFolderError);
}

function queryFolderSuccess(tx, results) {
	var len = results.rows.length;
	for (var i=0; i < len; i++) {
	    console.log("folder = " + results.rows.item(i).folder);
	}
	displayFolders(results);
}

function queryFolderError(err) {
    console.log("Error processing SQL: "+err.code);
}

//query songs
function querySong(col, whr) {
	selections = col;
	console.log(selections);
	where = 1;
	for(var key in whr)
		where += (" AND " + key + " = '" + whr[key] + "'");
	console.log(where);
	db.transaction(onQuerySongStart, querySongError);
}

function onQuerySongStart(tx) {
	tx.executeSql('SELECT ' + selections.toString() + ' FROM MUSIC WHERE ' + where, [], querySongSuccess, querySongError);
}

function querySongSuccess(tx, results) {
	var len = results.rows.length;
	for (var i=0; i < len; i++) {
	    console.log("song = " + results.rows.item(i).name);
	}
	displaySongs(results);
}

function querySongError(err) {
	console.log("Error processing SQL: "+err.code);
}