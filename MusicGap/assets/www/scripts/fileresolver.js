var TAG = "store to file";

function storeToFile() {
	console.log("starting save to file");
	window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, save, fail);
}

function save(fileSystem) {
	fileSystem.root.getFile(SAVEDFILE, {create: true}, setupRecordSuccess, setupRecordFail);
}

function fail(error) {
	console.log("in fail: " + error.code);
}

function setupRecordSuccess(fileEntry) {
	console.log("in setupRecordSuccess");
	fileEntry.createWriter(createWriterSuccess, createWriterError);
}

function setupRecordFail(err) {
	console.log("in setupRecordFail: " + err.code);
}

function createWriterSuccess(writer) {
	console.log("in createWriterSuccess");
	writer.onwritestart = function(evt) {
		console.log(" writing start");
    };
    writer.onwrite = function(evt) {
        console.log(" writing success");
    };
	writer.onwriteend = function(evt) {
		console.log(" writing end");
    };
	//writer.seek(writer.length);
	var strFiles = filesFullPath.toString();
	strFiles = strFiles.replace(/,/g, "\n");
	writer.write(strFiles);
}

function createWriterError(err) {
	console.log("in createWriterError: " + err.code);
}