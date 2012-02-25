var mFileEntry = null;
var mDirEntry = null;
var isScaning = false;
var TAG = "scan music";

function scanSDcard() {
	console.log("starting scan");
	window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, scanDir, fail);
}

function scanDir(fileSystem) {
    scan(fileSystem.root);
    showAlert("Starting scaning your sdcard, please hold on.", "Alert", "OK");
}

function fail(error) {
	console.log("in fail: " + error.code);
}

function scan(entry) {
	if(entry.isDirectory) {
		console.log("is directory: " + entry.name);
		mDirEntry = entry;
		var directoryReader = mDirEntry.createReader();
		directoryReader.readEntries(readEntrySuccess, readEntryFail);
	} else if(entry.isFile) {
		mFileEntry = entry;
		var fileName = mFileEntry.name;
		var pos = fileName.lastIndexOf(".");
		var ext = fileName.substr(pos);
		if(ext == MP3 || ext == M4A) {
			console.log("is file: " + entry.name);
			filesFullPath.push(mFileEntry.fullPath);
			//filesName.push(mFileEntry.name);
		}
	}
}


function readEntrySuccess(entries) {
	var i;
    for (i = 0; i < entries.length; i++) {
        console.log("in readEntrySuccess: " + i + " " + entries[i].name);
    	scan(entries[i]);
    }
}

function readEntryFail(err) {
	console.log("in readEntryFail: " + err.code);
}

function readFiles() {
	var i;
//	var element = document.getElementById("list");
//	for(i = 0; i < filesFullPath.length; i++) {
//		var oldText = element.innerHTML; 
//		element.innerHTML = oldText + filesFullPath[i] + "<br />";	
//	}
}
